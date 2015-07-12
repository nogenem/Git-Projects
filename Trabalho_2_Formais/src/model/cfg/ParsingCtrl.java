package model.cfg;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import model.exceptions.ParsingException;
import model.exceptions.SucessException;

import com.naef.jnlua.LuaRuntimeException;
import com.naef.jnlua.LuaState;

public class ParsingCtrl {
	// Template basico usado para gerar o parser
	private static final String basicTemplate = 
			  "function alex(x)\r\n"
			+ "   local vt = {ALFABETO}\r\n"
			+ "   local y = table.remove(x, 1)\r\n"
			+ "   if isInArray(vt, y) then\r\n"
			+ "      return y, x\r\n"
			+ "   else\r\n"
			+ "      my_erro(y,x,\"Simbolo invalido.\")\r\n"
			+ "   end\r\n"
			+ "end\r\n\r\n"
			+ "FUNCTIONS\r\n\r\n"
			+ "return SIMBOLO_INICIAL(alex(x))";
	// Template basico de uma função do parser
	private static final String functionTemplate = 
			  "function SIMBOLO_NT(y, x)\r\n"
			+ "   table.insert(seq, \"SIMBOLO_NT\")\r\n\r\n"
			+ "CODIGO\r\n"
			+ "end\r\n";
	// Template usado para verificação do first de uma produção
	private static final String firstTemplate = 
			  "if isInArray({FIRST}, y) then\r\n"
			+ "   INNER_CODE"
			+ "ELSECODE";
	// Template usado para verificação de simbolos terminais dentro
	// de uma 'sentença' de uma produção 
	// [Ex: S -> a S a, este 'if' serve para checar o ultimo 'a' da sentença]
	private static final String innerIfTemplate = 
			  "if y == 'SIMBOL' then\r\n"
			+ "   INNER_CODE"
			+ "else\r\n"
			+ "   my_erro(y,x,\"Esperava-se: 'SIMBOL'.\")\r\n"
			+ "end\r\n";
	// Template de um return usado no parser
	// [Ex: return alex(x) OR return A(y,x)]
	private static final String returnTemplate =
			"return Y_CODE\r\n";
	// Template de atribuição usada no parser
	// [Ex: y,x = alex(x) OR y,x = A(y,x)]
	private static final String yxTemplate =
			"y,x = Y_CODE\r\n";
	// Template de uma mensagem de erro usada no parser
	private static final String errTemplate = 
			"my_erro(y,x,\"Esperava-se: ARRAY.\")\r\n";
	
	/**
	 * Função usada para gerar o parser da gramatica dada.
	 * 
	 * @param cfg		Gramatica para a qual quer se gerar o parser.
	 */
	public static void generateParser(ContextFreeGrammar cfg){
		String parser = basicTemplate;
		String tmp = "";
		
		// Insere alfabeto na função alex(x)
		for(String t : cfg.getVt()){
			if(!t.equals("&"))
				tmp += "'"+t.replace("'", "\\'")+"', ";
		}
		tmp += "'Z\\''";// Simbolo final de sentença
		parser = parser.replace("ALFABETO", tmp);
		
		// Insere as funções
		for(String NT : cfg.getVn()){
			tmp = functionTemplate;
			tmp = tmp.replace("SIMBOLO_NT", NT);
			
			tmp = tmp.replace("CODIGO", generateParserFunctions(NT, cfg));
			
			tmp += "\r\nFUNCTIONS";
			parser = parser.replace("FUNCTIONS", tmp);
		}
		parser = parser.replace("\r\nFUNCTIONS\r\n", "");
		
		// Troca o nome da função inicial
		parser = parser.replace("SIMBOLO_INICIAL", cfg.getInitialSymbol());
		
		cfg.setParser(parser);
	}
	
	/**
	 * Função usada para gerar a função do parser correspondente ao
	 * simbolo não terminal dado.
	 * 
	 * @param NT			Simbolo não terminal para o qual se 
	 * 						quer criar a função
	 * @param cfg			Gramatica dada.
	 * @return				String contendo a função do simbolo não terminal.
	 */
	private static String generateParserFunctions(String NT, ContextFreeGrammar cfg){
		
		ArrayList<String> sentence = null;
		Set<String> first = null;
		String c = "";
		String current = null;
		boolean hasEpsilon = false;
		
		for(Production p : cfg.getNtProductions(NT)){
			sentence = p.getSentence();
			if(!sentence.contains("&")){
				first = cfg.getSentenceFirst(sentence);
				if(c.contains("ELSECODE"))
					c = c.replace("ELSECODE", "else");
				c += firstTemplate.replace("FIRST", formatArrayString(first, false));
				for(int i = 0; i<sentence.size(); i++){
					current = sentence.get(i);
					
					if(cfg.isTerminal(current) && i != 0)
						c = c.replace("INNER_CODE", innerIfTemplate.replace("SIMBOL", current));
					
					if(i==sentence.size()-1)
						c = c.replace("INNER_CODE", returnTemplate+"INNER_CODE");
					else
						c = c.replace("INNER_CODE", yxTemplate+"INNER_CODE");

					
					if(cfg.isTerminal(current))
						c = c.replace("Y_CODE", "alex(x)");
					else
						c = c.replace("Y_CODE", current+"(y,x)");
				}
				c = c.replace("INNER_CODE", "");
			}else
				hasEpsilon = true;
		}
		first = cfg.getFirst(NT);
		if(!hasEpsilon){
			c = c.replace("ELSECODE", "else\r\n"+errTemplate+"end");
			c = c.replace("ARRAY", formatArrayString(first, true));
		}else{// Se tem epsilon, então só retorna os valores passados como parametro pra função
			if(!c.contains("ELSECODE"))
				c = "return y,x";
			else
				c = c.replace("ELSECODE", "else\r\nreturn y,x\r\nend");
		}
		
		return correctIdentation(c);
	}
	
	/**
	 * Função responsavel por executar o parser da gramatica dada.
	 * 
	 * @param input					Entrada dada pelo usuario.
	 * @param cfg					Gramatica corrente do programa.
	 * 
	 * @throws ParsingException
	 * @throws SucessException
	 * @throws Exception
	 */
	public static void parsing(String input, ContextFreeGrammar cfg) throws ParsingException, SucessException, Exception {
		String msg = null;
		byte errType = 0;
		
		// Parte de LUA
		LuaState luaState = new LuaState();
		try{
			luaState.openLibs();
			// Define a function
			
			//String text = new String(Files.readAllBytes(Paths.get("lib/parser.lua")), StandardCharsets.UTF_8);
			ClassLoader cl = ParsingCtrl.class.getClassLoader();
			InputStream text = cl.getResourceAsStream("asint.lua");
			
			luaState.load(text, "Asint");
			
			// Evaluate the chunk, thus defining the function 
			luaState.call(0, 0); // No arguments, no returns 

			// Prepare a function call 
			luaState.getGlobal("asint"); // Push the function on the stack 
			luaState.pushString(cfg.getParser());
			luaState.pushString(input);

			// Call 
			luaState.call(2, 0); // 2 arguments, no returns [sucesso tambem é passado como erro]
		}catch(LuaRuntimeException e){
			msg = e.getMessage();
			msg = msg.replaceAll("Asint:\\d+: ", "");
			if(msg.contains("Sucesso")) // Sucesso
				errType = 1;
			else if(msg.contains("Simbolo atual:")) // Erro no input passado
				errType = 2;
			
		}finally{
			luaState.close();
		}
		
		if(errType == 1)
			throw new SucessException(msg);
		else if(errType == 2)
			throw new ParsingException(msg);
		else
			throw new Exception(msg);
		
	}
	
	/**
	 * Função usada para formatar um conjunto de simbolos
	 * terminais para serem usados na função LUA.
	 * [a, b, c] => 'a', 'b', 'c' OR 'a', 'b' ou 'c'.
	 * 
	 * @param arr		Conjunto de simbolos terminais.
	 * @param putOr		É para colocar 'ou' antes do ultimo elemento?
	 * @return			String contendo a lista de simbolos
	 * 					formatados.
	 */
	private static String formatArrayString(Collection<String> arr, boolean putOr){
		String s = "";
		
		for(String tmp : arr){
			if(!tmp.equals("&"))
				s += "'"+tmp.replace("'", "\\'")+"', ";
		}
		s = s.substring(0, s.length()-2);
		if(putOr){
			int last = s.lastIndexOf(",");
			if(last != -1)
				s = s.subSequence(0, last)+" ou"+s.substring(last+1, s.length());  
		}
		
		return s;
	}
	
	/**
	 * Função usada para identar o codigo das funções criadas
	 * para o parser.
	 * 
	 * @param code		Codigo da função que se quer identar.
	 * @return			String contendo a função identada.
	 */
	private static String correctIdentation(String code){
		StringBuilder s = new StringBuilder();
		int ifs = 0;
		
		for(String tmp : code.split("\r\n")){
			tmp = tmp.trim();

			if(ifs == 0)
				ifs += 1;
			else if(tmp.matches("elseif(.+)then|else|end") && ifs > 1)
				ifs -= 1;
			
			tmp = repeat(" ", ifs*3)+tmp;// Da 3 espaços para identar
			
			if(tmp.matches("( )*((else)?if(.+)then|else)"))
				ifs += 1;
			
			s.append(tmp+"\r\n");
		}
		
		return s.toString();
	}
	
	/**
	 * Função usada para repetir uma string dada 'times' vezes.
	 * 
	 * @param str		String que se quer repetir.
	 * @param times		Quantas vezes se quer repetir.
	 * @return			String contendo a string dada repetida
	 * 					'times' vezes.
	 */
	private static String repeat(String str, int times){
		return new String(new char[times]).replace("\0", str);
	}
}
