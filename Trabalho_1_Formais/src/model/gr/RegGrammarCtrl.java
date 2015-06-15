package model.gr;

import java.util.Arrays;

import model.af.Automaton;
import model.af.State;

public abstract class RegGrammarCtrl {
	
	public static final String allowedSimbols = 
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789&";
	public static final String allowedExtraSimbols =
			"->|";
	
	public static RegGrammar createRegGrammar(String titulo, String grammar){
		if(!isValidRegGrammar(grammar))
			return null;
		
		grammar = grammar.replaceAll("[ \t]*", "");
		//String newLine = System.getProperty("line.separator");
		
		RegGrammar G = new RegGrammar(titulo, grammar);
		
		String[] split;
		String vn;
		String tmpVt, tmpVn;
		for(String linha : grammar.split("\n")) {
			split = linha.split("->");
			vn = split[0]; 
			G.addVn(vn);
			if(G.getInitialSimbol() == null)
				G.setInitialSimbol(vn);
			
			split = split[1].split("\\|");
			for(String elements : split){
				if(elements.length() > 0){
					tmpVt = elements.substring(0, 1);
					if(elements.length() > 1)
						tmpVn = elements.substring(1, elements.length());
					else
						tmpVn = "$";
					
					G.addVt(tmpVt.charAt(0));
					G.addProduction(vn, tmpVt.charAt(0), tmpVn);
				}
			}
		}

		return G;
	}
	
	public static boolean isValidRegGrammar(String grammar){
		grammar = grammar.replaceAll("\\s*", "");
		return grammar.matches("([A-Z][0-9]?->(([a-z0-9][A-Z]?|[a-z0-9&])(\\|)?)+(\n)?)+");
	}
	
	public static Automaton createAutomaton(RegGrammar grammar){
		
		Automaton af = new Automaton(grammar.getVt());
		af.setTitulo(grammar.getTitulo());
		grammar.addExtra("AF");
		
		State sFinal = new State("FINAL");
		af.addState(sFinal);
		af.addFinalState(sFinal);

		for(String vn : grammar.getVn()){
			State s = new State(vn);
			af.addState(s);
			if(vn.equals(grammar.getInitialSimbol()))
				af.setStartingState(s);
			
			for(Production p : grammar.getProductions(vn)){
				if(p.getNext().equals("$"))
					af.addTransition(s, p.getGenerated(), sFinal);
				else
					af.addTransition(s, p.getGenerated(), new State(p.getNext()));
				
				if(vn.equals(grammar.getInitialSimbol()) && p.getGenerated() == '&')
					af.addFinalState(s);
			}
		}
		af.sortStates();
		af.sortAlphabet();
		
		return af;
	}
}
