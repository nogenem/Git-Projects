package model.cfg;

import model.exceptions.ParsingException;

public class ParsingCtrl {
	private static final String basicTemplate = 
			  "function alex(x)\r\n"
			+ "   local vt = {ALFABETO}\r\n"
			+ "   local y = table.remove(x, 1)\r\n"
			+ "   if isInArray(vt, y) then\r\n"
			+ "      return y, x\r\n"
			+ "   else\r\n"
			+ "      return my_erro(y,x,\"Simbolo invalido: \"..y)\r\n"
			+ "   end\r\n"
			+ "end\r\n\r\n"
			+ "FUNCTIONS\r\n\r\n"
			+ "return SIMBOLO_INICIAL(alex(x))";
	private static final String functionTemplate = 
			  "function SIMBOLO_NT(y, x)\r\n"
			+ "   if x == nil then\r\n"
			+ "      return y\r\n"
			+ "   end\r\n"
			+ "   table.insert(seq, \"SIMBOLO_NT\")\r\n\r\n"
			+ "   CODIGO\r\n"
			+ "end\r\n";
	
	public static void generateParser(ContextFreeGrammar cfg){
		String parser = basicTemplate;
		String tmp = "";
		
		// insere alfabeto na função alex(x)
		for(String t : cfg.getVt()){
			if(!t.equals("&"))
				tmp += "\""+t+"\", ";
		}
		tmp += "\"Z'\"";
		parser = parser.replace("ALFABETO", tmp);
		
		// insere as funções
		for(String NT : cfg.getVn()){
			tmp = functionTemplate;
			tmp = tmp.replace("SIMBOLO_NT", NT);
			
			// faz as funções
			
			tmp += "\r\nFUNCTIONS";
			parser = parser.replace("FUNCTIONS", tmp);
		}
		parser = parser.replace("\r\nFUNCTIONS\r\n", "");
		
		// Troca o nome da função inicial
		parser = parser.replace("SIMBOLO_INICIAL", cfg.getInitialSymbol());
		
		cfg.setParser(parser);
	}
	
	public static void parsing(String input, ContextFreeGrammar cfg) throws ParsingException, Exception {
		//parte de LUA
	}
}
