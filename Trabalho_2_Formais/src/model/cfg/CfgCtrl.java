package model.cfg;

import java.util.ArrayList;
import java.util.Collection;

import model.exceptions.GrammarException;

public abstract class CfgCtrl {
	
	// Pattern usado para checar a gramatica do usuario.
	private static final String patternCFG = 
			"([A-Z][0-9]?->(([^&]+|&)(\\|))*([^&]+|&)\\s*)+";
	// Pattern usado para checar simbolos não-terminais.
	private static final String patternNT = 
			"([A-Z][0-9]?)";
	// Pattern usado para checar simbolos terminais.
	private static final String patternTerminal = 
			"([^A-Z]+|&)";
	
	/**
	 * Função usada para criar uma gramatica a partir da entrada do usuario.
	 * 
	 * @param grammar				Entrada do usuario.
	 * @return						Gramatica contendo os elementos formais.
	 * 
	 * @throws GrammarException
	 */
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
			if(linha.lastIndexOf("->") != linha.indexOf("->"))// Verifica se só tem um simbolo '->'
				throw new GrammarException("Por favor não utilize o símbolo '->' como símbolo terminal de sua gramatica.");
			
			split = linha.split("->");
			
			NT = split[0].trim();
			if(!isNtSymbol(NT))
				throw new GrammarException("Símbolo invalido encontrado: "+NT+";\r\nEsperava-se um símbolo não-terminal.");
			
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
						throw new GrammarException("Símbolo invalido encontrado: "+s+
								"\r\nEsperava-se um símbolo terminal ou não-terminal valido.");
					
					// Caso o simbolo NT não apareça no lado esquerdo de nenhuma produção
					// a 'senteça' que contem ele é ignorada pelo programa.
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
	
	/**
	 * Função usada para checar se uma gramatica é LL(1).
	 * 
	 * @param cfg			Gramatica a ser checada.
	 * @return				Lista contendo as strings resultantes 
	 * 						da checagem.
	 */
	public static ArrayList<String> checkIfIsLL1(ContextFreeGrammar cfg){
		// result(0) => rec. esq.; result(1) => fat; result(2) => intersection;
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
			isLL1 = false;
			break;
		case 2:
			result.add("Possui recursão a esquerda indireta.");
			isLL1 = false;
			break;
		case 3:
			result.add("Possui recursão a esquerda direta e indireta.");
			isLL1 = false;
			break;
		default:
			break;
		}
		
		// Checagem de fatoração
		cfg.checkFat();
		switch (cfg.getFat()) {
		case 0:
			result.add("Esta fatorada.");
			break;
		case 1:
			result.add("Possui não-determinismo direto.");
			isLL1 = false;
			break;
		case 2:
			result.add("Possui não-determinismo indireto.");
			isLL1 = false;
			break;
		case 3:
			result.add("Possui não-determinismo direto e indireto.");
			isLL1 = false;
			break;
		default:
			break;
		}
		
		// Checagem da 3* condição
		cfg.getFollow();
		boolean empty = true;
		for(String NT : cfg.getVn()){
			if(cfg.getFirst(NT).contains("&")){
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
			isLL1 = false;
		}
		
		// Checagem se é LL(1)
		if(isLL1)
			result.add("A gramatica é LL(1)!");
		else
			result.add("A gramatica não é LL(1)!");
		
		cfg.setIsLL1(isLL1);
		
		return result;
	}
	
	/**
	 * Função que faz uma verificação simplificada
	 * da gramatica passada para saber se ela é uma G.L.C.
	 * 
	 * @param grammar		Gramatica a ser verificada.
	 * @return				TRUE caso a gramatica esteja no
	 * 						padrão G.L.C.
	 */
	private static boolean isValidCFG(String grammar){
		grammar = grammar.replaceAll("[\t ]+", "");
		return grammar.matches(patternCFG);
	}
	
	/**
	 * Função usada para verificar se um dado simbolo 
	 * é um simbolo não terminal valido. 
	 * 
	 * @param symbol		Simbolo a ser verificado.
	 * @return				TRUE caso o simbolo seja um simbolo
	 * 						não terminal valido.
	 */
	private static boolean isNtSymbol(String symbol){
		return symbol.matches(patternNT);
	}
	
	/**
	 * Verifica se o simbolo passado é um simbolo não-terminal
	 * e tambem verifica se ele foi declarado no lado esquerdo
	 * de alguma produção.
	 * 
	 * @param symbol		Simbolo a ser verificado.
	 * @param grammar		Gramatica passada pelo usuario.
	 * @return				TRUE caso o simbolo seja não-terminal e
	 * 						tenha sido declarado no lado esquerdo de 
	 * 						alguma produção.
	 */
	private static boolean isValidNtSymbol(String symbol, String grammar){
		return isNtSymbol(symbol) && grammar.replaceAll(symbol+"[ \t]*->", "FOUND").contains("FOUND");
	}
	
	/**
	 * Função usada para verificar se um dado simbolo 
	 * é um simbolo terminal valido. 
	 * 
	 * @param symbol		Simbolo a ser verificado.
	 * @return				TRUE caso o simbolo seja um simbolo
	 * 						terminal valido.
	 */
	private static boolean isTerminalSymbol(String symbol){
		return symbol.matches(patternTerminal);
	}
	
	/**
	 * Função usada para verificar se a intersecção de dois conjuntos
	 * é vazia ou não.
	 * 
	 * @param l1		Conjunto 1 a ser verificado.
	 * @param l2		Conjunto 2 a ser verificado.
	 * @return			TRUE caso a intersecção seja vazia.
	 */
	public static boolean isEmptyIntersection(Collection<String> l1, Collection<String> l2){
		boolean result = true;
		
		for(String s : l1){
			if(l2.contains(s))
				result = false;
		}
	
		return result;
	}
}
