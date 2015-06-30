package model.cfg;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import com.naef.jnlua.LuaRuntimeException;
import com.naef.jnlua.LuaState;

import static java.util.stream.Collectors.joining;
import model.exceptions.ParsingException;
import model.exceptions.SucessException;

public class ParsingCtrl {
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
	private static final String functionTemplate = 
			  "function SIMBOLO_NT(y, x)\r\n"
			+ "   table.insert(seq, \"SIMBOLO_NT\")\r\n\r\n"
			+ "CODIGO\r\n"
			+ "end\r\n";
	private static final String firstTemplate = 
			  "if isInArray({FIRST}, y) then\r\n"
			+ "INNER_CODE"
			+ "ELSECODE";
	private static final String innerIfTemplate = 
			  "if y == 'SIMBOL' then\r\n"
			+ "INNER_CODE"
			+ "else\r\n"
			+ "my_erro(y,x,\"Esperava-se: 'SIMBOL'.\")\r\n"
			+ "end\r\n";
	private static final String returnTemplate =
			"return Y_CODE\r\n";
	private static final String yxTemplate =
			"y,x = Y_CODE\r\n";
	private static final String errTemplate = 
			"my_erro(y,x,\"Esperava-se: ARRAY.\")\r\n";
	
	public static void generateParser(ContextFreeGrammar cfg){
		String parser = basicTemplate;
		String tmp = "";
		
		// insere alfabeto na função alex(x)
		for(String t : cfg.getVt()){
			if(!t.equals("&"))
				tmp += "'"+t.replace("'", "\\'")+"', ";
		}
		tmp += "\"Z'\"";
		parser = parser.replace("ALFABETO", tmp);
		
		// insere as funções
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
				c += firstTemplate.replace("FIRST", formatArrayString(first));
				for(int i = 0; i<sentence.size(); i++){
					current = sentence.get(i);
					
					if(cfg.isTerminal(current) && i != 0)
						c = c.replace("INNER_CODE", innerIfTemplate.replace("SIMBOL", current));
					
					if(i==sentence.size()-1)
						c = c.replace("INNER_CODE", returnTemplate+"INNER_CODE");
					else
						c = c.replace("INNER_CODE", yxTemplate+"INNER_CODE");

					
					if(cfg.isTerminal(current)){
						c = c.replace("Y_CODE", "alex(x)");
					}else{
						c = c.replace("Y_CODE", current+"(y,x)");
					}
				}
				c = c.replace("INNER_CODE", "");
			}else
				hasEpsilon = true;
		}
		first = cfg.getFirst(NT);
		if(!hasEpsilon){
			c = c.replace("ELSECODE", "else\r\n"+errTemplate+"end");
			c = c.replace("ARRAY", formatArrayString(first));
		}else
			c = c.replace("ELSECODE", "else\r\nreturn y,x\r\nend");
		
		return correctIdentation(c);
	}
	
	public static void parsing(String input, ContextFreeGrammar cfg) throws ParsingException, SucessException, Exception {
		String msg = null;
		byte errType = 0;
		
		//parte de LUA
		LuaState luaState = new LuaState();
		try{
			luaState.openLibs();
			// Define a function 
			String text = new String(Files.readAllBytes(Paths.get("lib/parser.lua")), StandardCharsets.UTF_8);
			luaState.load(text, "Parser");
			
			// Evaluate the chunk, thus defining the function 
			luaState.call(0, 0); // No arguments, no returns 

			// Prepare a function call 
			luaState.getGlobal("parser"); // Push the function on the stack 
			luaState.pushString(cfg.getParser());
			luaState.pushString(input);

			// Call 
			luaState.call(2, 0); // 2 arguments, 1 return 
		}catch(LuaRuntimeException e){
			msg = e.getMessage();
			msg = msg.replaceAll("Parser:\\d+: ", "");
			if(msg.contains("Sucesso")) //sucesso
				errType = 1;
			else if(msg.contains("Simbolo atual:")) //erro no input passado
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
	
	private static String formatArrayString(Collection<String> arr){
		String s = "";
		
		for(String tmp : arr){
			if(!tmp.equals("&"))
				s += "'"+tmp.replace("'", "\\'")+"', ";
		}
		s = s.substring(0, s.length()-2);
		
		return s;
	}
	
	private static String correctIdentation(String code){
		StringBuilder s = new StringBuilder();
		int ifs = 0;
		
		for(String tmp : code.split("\r\n")){
			tmp = tmp.trim();

			if(ifs == 0)
				ifs += 1;
			else if(tmp.matches("elseif(.+)then|else|end") && ifs > 1)
				ifs -= 1;
			
			tmp = repeat(" ", ifs*3)+tmp;
			
			if(tmp.matches("( )*((else)?if(.+)then|else)"))
				ifs += 1;
			
			s.append(tmp+"\r\n");
		}
		
		return s.toString();
	}
	
	private static String repeat(String str, int times){
		return new String(new char[times]).replace("\0", str);
	}
}
