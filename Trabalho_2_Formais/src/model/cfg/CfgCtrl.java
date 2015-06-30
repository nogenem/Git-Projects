package model.cfg;

import java.util.ArrayList;
import java.util.Collection;

import model.exceptions.GrammarException;

public abstract class CfgCtrl {
	
	private static final String patternCFG = 
			"([A-Z][0-9]?->(([^&]+|&)(\\|))*([^&]+|&)\\s*)+";
	private static final String patternNT = 
			"([A-Z][0-9]?)";
	private static final String patternTerminal = 
			"([^A-Z]+|&)";
	
	public static ContextFreeGrammar createGrammar(String grammar) throws GrammarException {
		if(!isValidCFG(grammar))
			throw new GrammarException("Parece haver algum erro no formato da gramatica entrada.\r\n"+
											"Por favor reveja a gramatica.");
		
		ContextFreeGrammar glc = new ContextFreeGrammar();
		
		String[] split;
		String NT;
		ArrayList<String> tmpSentence;
		boolean isToAdd = true;
		for(String linha : grammar.split("[\r\n]+")){
			split = linha.split("->");
			
			NT = split[0].trim();
			if(!isNtSymbol(NT))
				throw new GrammarException("Simbolo invalido encontrado: "+NT+";\r\nEsperava-se um simbolo não terminal.");
			
			glc.addNTerminal(NT);
			if(glc.getInitialSymbol() == null)
				glc.setInitialSymbol(NT);
			
			for(String sentence : split[1].split("\\|")){
				sentence = sentence.trim();
				
				isToAdd = true;
				tmpSentence = new ArrayList<>();
				for(String s : sentence.split(" ")){
					if(isTerminalSymbol(s))
						glc.addTerminal(s);
					else if(!isNtSymbol(s))
						throw new GrammarException("Simbolo invalido encontrado: "+s+
								"\r\nEsperava-se um simbolo terminal ou não terminal valido.");
					
					if(isNtSymbol(s) && !isValidNtSymbol(s, grammar))
						isToAdd = false;
					
					tmpSentence.add(s);
				}
				if(isToAdd)
					glc.addProduction(NT, tmpSentence);
			}
		}
		
		return glc;
	}
	
	public static ArrayList<String> checkIfIsLL1(ContextFreeGrammar cfg){
		// result(0) => rec. esq.; result(1) => fat; result(2) => intersection
		// result(3) => é LL(1)?
		ArrayList<String> result = new ArrayList<>();
		boolean isLL1 = true;
		
		// Checagem de Recursão a Esquerda
		cfg.getFirstNT();
		switch (cfg.getRecEsq()) {
		case 0:
			result.add("Não possui recursão a esquerda.");
			break;
		case 1:
			result.add("Possui recursão a esquerda direta.");
			isLL1 = isLL1 && false;
			break;
		case 2:
			result.add("Possui recursão a esquerda indireta.");
			isLL1 = isLL1 && false;
			break;
		case 3:
			result.add("Possui recursão a esquerda direta e indireta.");
			isLL1 = isLL1 && false;
			break;
		default:
			break;
		}
		
		// Checagem de fatoração
		cfg.getFirst();
		switch (cfg.getFat()) {
		case 0:
			result.add("Esta fatorada.");
			break;
		case 1:
			result.add("Possui não-determinismo direto.");
			isLL1 = isLL1 && false;
			break;
		case 2:
			result.add("Possui não-determinismo indireto.");
			isLL1 = isLL1 && false;
			break;
		case 3:
			result.add("Possui não-determinismo direto e indireto.");
			isLL1 = isLL1 && false;
			break;
		default:
			break;
		}
		
		// Checagem da 3* condição
		cfg.getFollow();
		boolean empty = true;
		for(String NT : cfg.getVn()){
			if(cfg.getFirst().get(NT).contains("&")){
				if(!isEmptyIntersection(cfg.getFirst().get(NT), cfg.getFollow().get(NT))){
					cfg.getNonEmptyIntersection().add(NT);
					empty = false;
				}
			}
		}
		if(empty)
			result.add("Funcionou.");
		else{
			result.add("Falhou.");
			isLL1 = isLL1 && false;
		}
		
		// Checagem se é LL(1)
		if(isLL1){
			result.add("A gramatica é LL(1)!");
			cfg.setIsLL1(true);
		}else{
			result.add("A gramatica não é LL(1)!");
			cfg.setIsLL1(false);
		}
		
		return result;
	}
	
	/**
	 * Método que faz uma verificação simplificada
	 * da gramatica passada para saber se ela é uma G.L.C.
	 * 
	 * @param grammar		Gramatica a se verificada.
	 * @return				TRUE caso a gramatica esteja no
	 * 						padrão G.L.C.
	 */
	private static boolean isValidCFG(String grammar){
		grammar = grammar.replaceAll("[\t ]+", "");
		return grammar.matches(patternCFG);
	}
	
	private static boolean isNtSymbol(String symbol){
		return symbol.matches(patternNT);
	}
	
	/**
	 * Verifica se o simbolo passado é um simbolo não-terminal
	 * e tambem verifica se ele foi declarado no lado esquerdo
	 * de alguma produção.
	 * 
	 * @param symbol		simbolo a ser verificado.
	 * @param grammar		gramatica passada pelo usuario.
	 * @return				TRUE caso o simbolo seja não-terminal e
	 * 						tenha sido declarado no lado esquerdo de 
	 * 						alguma produção.
	 */
	private static boolean isValidNtSymbol(String symbol, String grammar){
		return isNtSymbol(symbol) && grammar.replaceAll(symbol+"[ \t]*->", "FOUND").contains("FOUND");
	}
	
	private static boolean isTerminalSymbol(String symbol){
		return symbol.matches(patternTerminal);
	}
	
	public static boolean isEmptyIntersection(Collection<String> l1, Collection<String> l2){
		boolean result = true;
		
		for(String s : l1){
			if(l2.contains(s))
				result = false;
		}
	
		return result;
	}
}
