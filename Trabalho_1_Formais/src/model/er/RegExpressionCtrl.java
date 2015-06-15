package model.er;

import model.af.Automaton;
import model.af.de_simone.DeSimone;

public class RegExpressionCtrl {
	
	public static final String allowedSimbols = 
			"abcdefghijklmnopqrstuvwxyz0123456789&";
	public static final String allowedOps = 
			"()*+.|?";
	
	public static RegExpression createRegExpression(String titulo, String regEx){
		regEx = formatRegExpression(regEx);
		if(regEx == null) return null;
		return new RegExpression(regEx, titulo);
	}
	
	public static Automaton createAutomaton(RegExpression regEx){
		Automaton af = DeSimone.createAutomaton(regEx.getRegEx());
		af.setTitulo(regEx.getTitulo());
		af.setExtras("AF");
		return af;
	}
	
	private static String formatRegExpression(String regEx){
		if(isValidRegExpression(regEx)){
			regEx = replaceComplexOperators(regEx); 
			regEx = explicitConcats(regEx);
			return regEx;
		}else
			return null;
	}
	
	public static boolean isValidRegExpression(String regEx){
		int parenthesisCount = 0;
		regEx = regEx.replaceAll("\\s*", "");
		
		if(regEx.contains("()"))
			return false;
		
		for(int i = 0; i<regEx.length(); i++){
			char c = regEx.charAt(i);
			if(isOperator(c,true)){
				if(c == '(')
					++parenthesisCount;
				else if(c == ')')
					--parenthesisCount;
				else if(isUnaryOperator(c)){
					if(i == 0) 
						return false;
					char c1 = regEx.charAt(i-1);
					if(isOperator(c1,true) && c1 != ')') 
						return false;
				}else if(isBinaryOperator(c)){
					if(i == 0 || i >= regEx.length()) 
						return false;
					char c1 = regEx.charAt(i-1);
					char c2 = regEx.charAt(i+1);
					if((c1 == '(' || isBinaryOperator(c1)) || (isOperator(c2,true) && c2 != '('))
						return false;
				}
			}else if(allowedSimbols.indexOf(c) == -1)
				return false;
		}
		return parenthesisCount == 0;
	}
	
	private static String explicitConcats(String regEx){
		String tmp = "";
		regEx = regEx.replaceAll("\\s*", "");
		
		for(int i = 0; i<regEx.length(); i++){
			char c = regEx.charAt(i);
			if(!isOperator(c,true)){
				if(i > 0 && (regEx.charAt(i-1) == ')' || isUnaryOperator(regEx.charAt(i-1))))
					tmp += '.';
				if(i < regEx.length()-1 && (!isOperator(regEx.charAt(i+1),true) || regEx.charAt(i+1) == '('))
					tmp += (c+""+'.');
				else
					tmp += c;
			}else if(i > 0 && c == '(' && (regEx.charAt(i-1) == ')' || isUnaryOperator(regEx.charAt(i-1)) ))
				tmp += ('.'+""+c);
			else
				tmp += c;
		}
		return tmp;
	}
	
	private static String replaceComplexOperators(String regEx){
		regEx = regEx.replaceAll("\\s*", "");
		
		//regEx = regEx.replaceAll("(\\w)\\?", "\\($1|&\\)");//retira operador '?'
		//regEx = regEx.replaceAll("(\\([a-z0-9.+|*]+\\))\\?", "\\($1|&\\)");//retira operador '?'
		regEx = regEx.replaceAll("(\\w)\\+", "$1$1\\*");//retira operador '+'
		regEx = regEx.replaceAll("(\\([a-z0-9.|*]+\\))\\+", "$1$1\\*");//retira operador '+'
		return regEx;
	}
	
	public static boolean isOperator(char c, boolean withParentheses){
		if(!withParentheses)
			return allowedOps.substring(2, allowedOps.length()).indexOf(c) != -1;
		else
			return allowedOps.indexOf(c) != -1;
	}
	
	public static boolean isBinaryOperator(char c){
		return (c=='|' || c=='.');
	}
	
	public static boolean isUnaryOperator(char c){
		return (c=='?' || c=='+' || c=='*');
	}
}
