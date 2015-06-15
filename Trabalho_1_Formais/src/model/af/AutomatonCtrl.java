package model.af;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.Regular;
import model.er.RegExpression;
import model.er.RegExpressionCtrl;
import model.gr.RegGrammar;
import model.gr.RegGrammarCtrl;

public class AutomatonCtrl {
	
	/**
	 * Cria um AF usando uma GR ou ER como base.
	 * 
	 * @param reg		'Conjunto' Regular [Gr ou Er] usada como
	 * 					base para fazer o AF.
	 * @return			AF criado.
	 */
	public static Automaton createAutomaton(Regular reg) {
		if(reg.isGrammar())
			return RegGrammarCtrl.createAutomaton((RegGrammar)reg);
		else if(reg.isExpression())
			return RegExpressionCtrl.createAutomaton((RegExpression)reg);
		else
			return (Automaton)reg;
	}
	
	/**
	 * Determiniza o AF dado.
	 * 
	 * @param af		AF a determinizar.
	 * @return			AF Determinizado equivalente ao AF dado.
	 */
	public static Automaton determinize(Automaton af) {
		if(af.getExtras().contains("AFD"))
			return af;
		
		HashMap<State, Set<State>> 
			fechos = af.getEpsilonFechos();
		
		Automaton afd = new Automaton(af.getAlphabet());
		afd.setTitulo(af.getTitulo());		
		afd.removeSimbol('&');//após calcular os &-fechos ja pode tirar o & do alfabeto
		
		recDeterminize(af, afd, fechos, 
				fechos.get(af.getStartingState()), true);
		
		afd.addExtra("AFD");
		
		return afd;
	}
	
	/**
	 * Função recursiva interna usada para determinizar um AF.
	 * 
	 * @param af			AF base para ser determinizado.
	 * @param afd			AFD intermediario para recursão.
	 * @param fechos		HashMap contendo os estados como chaves
	 * 						e seus &-fechos como values.
	 * @param states		&-fecho do estado alvo atual da 
	 * 						determinização.
	 * @param first			É a primeira execução da função recursiva?
	 */
	private static void recDeterminize(Automaton af, Automaton afd,
			HashMap<State, Set<State>> fechos, Set<State> states, boolean first) {
		
		State current = new State(states.toString());
		
		if(afd.stateBelongsToAF(current))//para recursao
			return;
		
		Set<State> newStates = new HashSet<>();
		Set<Set<State>> lastAdded = new HashSet<>();
		
		afd.addState(current);
		if(first)//seta o 1* estado como startingState
			afd.setStartingState(current);

		for(State s : states)
			if(af.isFinalState(s))//verifica se contem estado final
				afd.addFinalState(current);
		
		for(char c : afd.getAlphabet()){
			for(State s : states)
				for(State s1 : af.getNextStates(s, c))
					newStates.addAll(fechos.get(s1));

			if(!newStates.isEmpty()){
				State sNew = new State(newStates.toString());
				afd.addTransition(current, c, sNew);
				lastAdded.add(new HashSet<>(newStates));
			}
			newStates.clear();
		}

		for(Set<State> s : lastAdded)
			recDeterminize(af, afd, fechos, s, false);
	}
	
	/**
	 * Função que determiniza, caso necessario, o AF dado
	 * e retorna seu complemento.
	 * 
	 * @param af		AF base para a operação de complemento.
	 * @return			AFD Complementado equivalente ao AF dado.
	 */
	public static Automaton complement(Automaton af){
		/*if(af.getExtras().contains("AFD_Comp"))
			return af;*/
		
		Automaton comp; 
		if(!af.getExtras().contains("AFD"))
			comp = determinize(af);
		else
			comp = new Automaton(af);
		
		comp = complete(comp);
		comp.setFinalStates(comp.getNonFinalStates());
		
		comp.addExtra("AFD_Comp");
		
		return comp;
	}
	
	/**
	 * Função interna que determiniza, caso necessario,
	 * e completa, tambem caso necessario, o AF dado.
	 * 
	 * @param afd		AF base para ser completado.
	 * @return			AFD Completo equivalente ao AF dado.
	 */
	private static Automaton complete(Automaton afd){
		/*if(afd.getExtras().contains("Complete"))
			return afd;*/
		
		afd = determinize(afd);
		
		State err = new State("[ERRO]");
		boolean edited = false;
		
		for(State s : afd.getStates()){
			for(char c : afd.getAlphabet()){
				if(afd.getNextStates(s, c).isEmpty()){
					afd.addTransition(s, c, err);
					edited = true;
				}
			}
		}
		
		if(edited){
			afd.addState(err);
			for(char c : afd.getAlphabet())
				afd.addTransition(err, c, err);
		}
		afd.addExtra("Complete");
		return afd;
	}
	
	/**
	 * Função que determiniza, caso necessario, e retorna
	 * o AFD Minimo do AF dado.
	 * 
	 * @param afd		AF base a ser determinizado.
	 * @return			AFD Minimo equivalente ao AF dado.
	 */
	public static Automaton minimize(Automaton afd){
		if(afd.getExtras().contains("AFD_Min"))
			return afd;
		
		if(!afd.getExtras().contains("AFD"))
			afd = determinize(afd);
		else
			afd = new Automaton(afd);
		
		//Check ferteis e alcancaveis
		//Estados inalcancaveis ja sao retirados pela determinizacao
		Set<State> fertile = getFertileStates(afd);
		ArrayList<State> toRemove = new ArrayList<>();
		for(State s : afd.getStates())
			if(!fertile.contains(s))
				toRemove.add(s);
		
		if(!toRemove.isEmpty()){
			for(State s : toRemove)
				afd.removeState(s);
			afd.setExtras("AFD");
		}
		
		if(afd.getStartingState() == null)//linguagem vazia
			return createEmptyAutomaton(afd.getAlphabet());
		
		afd = complete(afd);
		ArrayList<ArrayList<State>> eqSets = new ArrayList<>();
		
		eqSets.add(afd.getFinalStates());
		ArrayList<State> tmp = afd.getNonFinalStates();
		if(tmp.size() > 0)
			eqSets.add(tmp);
		
		eqSets = calculeEqSets(afd, eqSets);	
		
		Automaton min = new Automaton(afd.getAlphabet());
		min.setTitulo(afd.getTitulo());
		HashMap<State, ArrayList<State>> states = new HashMap<>();
		
		State tmpState;
		ArrayList<State> set;
		for(int i = 0; i<eqSets.size(); i++){
			set = eqSets.get(i);
			if(!set.get(0).getName().contains("[ERRO]")){
				tmpState = new State("Q"+i);
				states.put(tmpState, set);
			}
		}
		
		int nSet;
		State tmpState2;
		for(State s : states.keySet()){
			min.addState(s);
			for(State s2 : states.get(s)){
				if(afd.isFinalState(s2))
					min.addFinalState(s);
				if(afd.getStartingState().equals(s2))
					min.setStartingState(s);
			}
		}
		
		for(State s : states.keySet()){
			tmpState = states.get(s).get(0);
			for(char c : min.getAlphabet()){
				nSet = getWhichEqSetIsIn(eqSets, afd.getNextStates(tmpState, c).get(0));
				if(nSet != -1){
					tmpState2 = min.getState("Q"+nSet);
					if(tmpState2 != null)
						min.addTransition(s, c, tmpState2);
				}
			}
		}
		
		min.addExtra("AFD_Min");
		
		return min;
	}
	
	/**
	 * Função recursiva interna que calcula os 'Conjuntos de equivalencia'
	 * intermediarios para a construção do AFD Minimo.
	 * 
	 * @param min		AFD Minimo intermediario.
	 * @param eqSets	'Conjuntos de equivalencia' intermediarios.
	 * @return			'Conjuntos de equivalencia' final.
	 */
	private static ArrayList<ArrayList<State>> calculeEqSets(Automaton min,
			ArrayList<ArrayList<State>> eqSets) {
		
		ArrayList<ArrayList<State>> newEqSets = new ArrayList<>();
		ArrayList<State> tmp;
		boolean toAdd;
		
		for(ArrayList<State> set : eqSets){
			for(int i = 0; i < set.size(); i++){
				if(getWhichEqSetIsIn(newEqSets, set.get(i)) == -1){
					tmp = new ArrayList<>();
					tmp.add(set.get(i));
					for(int k = i+1; k < set.size(); k++){
						toAdd = true;
						for(char c : min.getAlphabet()){
							if(getWhichEqSetIsIn(eqSets, min.getNextStates(set.get(i), c).get(0)) !=
									getWhichEqSetIsIn(eqSets, min.getNextStates(set.get(k), c).get(0)))
								toAdd = false;
						}
						if(toAdd)
							tmp.add(set.get(k));
					}
					newEqSets.add(tmp);
				}
			}
		}

		if(eqSets.size() == newEqSets.size())
			return newEqSets;
		else
			return calculeEqSets(min, newEqSets);
	}

	/**
	 * Retorna o numero do 'Conjunto de equivalencia' que 
	 * contem o estado dado.
	 * 
	 * @param eqSets		'Conjunto de equivalencia' a ser checado.
	 * @param state			Estado que se quer saber a qual conjunto pertence.
	 * @return
	 */
	private static int getWhichEqSetIsIn(ArrayList<ArrayList<State>> eqSets,
			State state) {
		
		for(int i = 0; i<eqSets.size(); i++)
			if(eqSets.get(i).contains(state))
				return i;
				
		return -1;
	}
	
	/**
	 * Determiniza, caso necessario, e retorna todos os 
	 * estados alcançaveis do AFD dado apatir do estado 
	 * inicial do mesmo. 
	 * 
	 * @param afd		AF que se quer saber os estados alcançaveis.
	 * @return			Lista com todos os estados alcançaveis do AFD.
	 */
	private static Set<State> getReachableStates(Automaton afd){
		if(!afd.getExtras().contains("AFD"))
			afd = determinize(afd);
		
		Set<State> reachableStates = new HashSet<>();
		Set<State> toAdd;
		
		reachableStates.add(afd.getStartingState());
		
		do{
			toAdd = new HashSet<>();
			for(State s : reachableStates)
				toAdd.addAll(afd.getAllNextStates(s));
		}while(reachableStates.addAll(toAdd));
		
		return reachableStates;
	}
	
	/**
	 * Determiniza, caso necessario, e retorna todos os 
	 * estados férteis do AFD dado. 
	 * 
	 * @param afd		AF que se quer saber os estados férteis.
	 * @return			Lista com todos os estados fertéis do AFD.
	 */
	private static Set<State> getFertileStates(Automaton afd){
		if(!afd.getExtras().contains("AFD"))
			afd = determinize(afd);
		
		Set<State> fertileStates = new HashSet<>();
		Set<State> toAdd;
		
		fertileStates.addAll(afd.getFinalStates());
		
		do{
			toAdd = new HashSet<>();
			for(State s : fertileStates)
				toAdd.addAll(afd.getAllPreviousStates(s));
		}while(fertileStates.addAll(toAdd));
		
		return fertileStates;
	}
	
	/**
	 * Cria um AFD que reconhece a linguagem vazia.
	 * 
	 * @param alphabet		Alfabeto base para o AFD Vazio.
	 * @return				AFD que aceita soh a linguagem vazia.
	 */
	private static Automaton createEmptyAutomaton(ArrayList<Character> alphabet){
		Automaton empty = new Automaton(alphabet);
		State s = new State("S");
		empty.addState(s);
		empty.setStartingState(s);
		empty.setExtras("AFD|AFD_Min|Empty");
		return empty;
	}
	
	/**
	 * Função que determiniza, caso necessario, o AF dado e
	 * o utiliza para fazer uma busca de padrão no texto
	 * dado.
	 * 
	 * @param txt				Texto que se quer fazer a busca de padrão.
	 * @param af				AF que sera utilizado.
	 * @param determinized		AF ja esta determinizado?
	 * @return					Lista com todas as palavras achadas no texto
	 * 							usando o AFD dado.
	 */
	public static ArrayList<String> search(String txt, Automaton af, boolean determinized){
		if(!determinized)
			af = determinize(new Automaton(af));
			
		ArrayList<String> result = new ArrayList<>();
		ArrayList<State> tmp;
		
		State current = af.getStartingState();
		char c;
		String found = "";
		
		txt += "$";//simbolo para final do texto
		
		if(af.isFinalState(current))
			result.add("&");
		
		for(int i = 0; i<txt.length(); i++){
			c = txt.charAt(i);
			found += c;
			tmp = af.getNextStates(current, c);
			if(!tmp.isEmpty()){
				current = tmp.get(0);
				if(af.isFinalState(current))
					result.add(found);
				
				for(int k = i+1; k<txt.length(); k++){
					c = txt.charAt(k);
					found += c;
					tmp = af.getNextStates(current, c);
					if(!tmp.isEmpty()){
						current = tmp.get(0);
						if(af.isFinalState(current))
							result.add(found);
					}else{
						found = "";
						break;
					}
				}
			}else
				found = "";
			
			current = af.getStartingState();
		}
			
		return result;
	}
	
	/**
	 * Função que compara dois AFs dados.
	 * 
	 * @param afd1		AF usado para comparação.
	 * @param afd2		AF usado para comparação.
	 * @return			TRUE caso os dois AFs sejam equivalentes.
	 */
	public static boolean compare(Automaton afd1, Automaton afd2){
		Automaton a1 = difference(afd1, afd2);
		Automaton a2 = difference(afd2, afd1);
		Automaton union = union(a1, a2);
		union = minimize(union);
		return union.getExtras().contains("Empty");
	}
	
	/**
	 * Função que retorna a diferença entre dois AFs.
	 * 
	 * @param afd1		AF usado para a diferença.
	 * @param afd2		AF usado para a diferença.
	 * @return			AF equivalente a AFD1 - AFD2.
	 */
	private static Automaton difference(Automaton afd1, Automaton afd2){
		return intersection(afd1, complement(afd2));
	}
	
	/**
	 * Função que retorna a intersecção entre dois AFs.
	 * 
	 * @param afd1		AF usado para a intersecção.
	 * @param afd2		AF usado para a intersecção.
	 * @return			AF equivalente a intersecção dos dois
	 * 					AFs dados.
	 */
	public static Automaton intersection(Automaton afd1, Automaton afd2){
		afd1 = complement(afd1);
		afd2 = complement(afd2);
		Automaton inter = union(afd1, afd2);
		inter = complement(inter);
		inter.setExtras("AF|AFD");
		return inter;//minimize(inter);
	}
	
	/**
	 * Função que une dois alfabetos.
	 * 
	 * @param a1		Alfabeto usado para a união.
	 * @param a2		Alfabeto usado para a união.
	 * @return			Um novo alfabeto equivalente a união
	 * 					dos dois alfabetos dados.
	 */
	private static ArrayList<Character> mergeAlphabets(ArrayList<Character> a1, 
			ArrayList<Character> a2){
		
		ArrayList<Character> newAlphabet = new ArrayList<>(a1);
		for(char c : a2){
			if(!newAlphabet.contains(c))
				newAlphabet.add(c);
		}
		return newAlphabet;
	}
	
	/**
	 * Miniminiza, caso necessario, os dois AFs dados e 
	 * retorna a união dos dois.
	 * 
	 * @param afd1		AF usado na união.
	 * @param afd2		AF usado na união.
	 * @return			AF representando a união dos dois
	 * 					AFs dados.
	 */
	private static Automaton union(Automaton afd1, Automaton afd2){
		if(!afd1.getExtras().contains("AFD_Min"))
			afd1 = minimize(afd1);
		
		if(!afd2.getExtras().contains("AFD_Min"))
			afd2 = minimize(afd2).clone(true);
		else
			afd2 = afd2.clone(true);
		
		Automaton union = 
				new Automaton(mergeAlphabets(afd1.getAlphabet(), afd2.getAlphabet()));
		
		State newQ0 = new State("Q03");
		union.addState(newQ0);
		union.setStartingState(newQ0);
		if(afd1.isFinalState(afd1.getStartingState()) ||
				afd2.isFinalState(afd2.getStartingState()))
			union.addFinalState(newQ0);
		
		union.addStates(afd1.getStates());	
		union.addStates(afd2.getStates());
		union.addFinalStates(afd1.getFinalStates());
		union.addFinalStates(afd2.getFinalStates());
		union.addTransitions(afd1.getTransitions());
		union.addTransitions(afd2.getTransitions());
		
		union.addSimbol('&');
		union.addTransition(newQ0, '&', afd1.getStartingState());
		union.addTransition(newQ0, '&', afd2.getStartingState());
		
		/*for(char c : union.getAlphabet())
			for(State s : afd1.getNextStates(afd1.getStartingState(), c))
				union.addTransition(newQ0, c, s);
		
		for(char c : union.getAlphabet())
			for(State s : afd2.getNextStates(afd2.getStartingState(), c))
				union.addTransition(newQ0, c, s);*/
		
		return union;
	}
}
