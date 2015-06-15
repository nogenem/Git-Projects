package model.af;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import model.Regular;

/**
 * Classe modelo que representa um Automato Finito.
 * 
 * @author Gilney
 *
 */
public class Automaton extends Regular {
	
	// estados do AF
	private ArrayList<State> states;
	// alfabeto do AF
	private ArrayList<Character> alphabet;
	// transições do AF
	private Transitions transitions;
	// estado inicial do AF
	private State startingState;
	// estados finais do AF
	private ArrayList<State> finalStates;
	
	/**
	 * Construtor da classe. Recebe um outro AF como entrada 
	 * e tenta clona-lo parcialmente.
	 * 
	 * @param af	AF que sera parcialmente clonado.
	 */
	public Automaton(Automaton af){
		this(new ArrayList<>(af.getStates()), new ArrayList<>(af.getAlphabet()),
				new Transitions(af.getTransitions()), new State(af.getStartingState()),
				new ArrayList<>(af.getFinalStates()), af.getTitulo(), af.getExtras());
	}
	
	/**
	 * Construtor da classe. Recebe um alfabeto como entrada.
	 * 
	 * @param alphabet
	 */
	public Automaton(ArrayList<Character> alphabet){
		this(new ArrayList<>(), new ArrayList<>(alphabet), 
				new Transitions(), null, new ArrayList<>(), "", "");
	}
	
	/**
	 * Construtor basico da classe.
	 */
	public Automaton(){
		this(new ArrayList<>(), new ArrayList<>(), 
				new Transitions(), null, new ArrayList<>(), "", "");
	}
	
	/**
	 * Construtor completo da classe. Recebe todos os atributos
	 * da classe como entrada.
	 * 
	 * @param states			estados do AF.
	 * @param alphabet			alfabeto do AF.
	 * @param transitions		transições do AF.
	 * @param startingState		estado inicial do AF.
	 * @param finalStates		estados finais do AF.
	 * @param titulo			titulo do AF.
	 * @param extras			valor 'extras' do AF.
	 */
	public Automaton(ArrayList<State> states, ArrayList<Character> alphabet,
			Transitions transitions, State startingState,
			ArrayList<State> finalStates, String titulo, String extras) {
		super();
		this.states = states;
		this.alphabet = alphabet;
		this.transitions = transitions;
		this.startingState = startingState;
		this.finalStates = finalStates;
		setTitulo(titulo);
		setExtras(extras);
	}

	// States
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	
	/**
	 * Retorna um estado da lista de estados do AF,
	 * usando o nome como chave de busca.
	 * 
	 * @param name		nome do estado a ser encontrado.
	 * @return			estado com o mesmo nome dado como entrada.
	 */
	public State getState(String name){
		for(State s : states){
			if(s.getName().equals(name))
				return s;
		}
		return null;
	}
	
	/**
	 * Adiciona um estado ao AF
	 * verificando se o estado ja não esta na lista de 
	 * estados do mesmo.
	 * 
	 * @param s		Estado a ser adicionado ao AF.
	 */
	public void addState(State s){
		if(!states.contains(s))
			states.add(s);
	}
	
	/**
	 * Adiciona uma lista de estados ao AF.
	 * 
	 * @param states	Estados a serem adicionados.
	 */
	public void addStates(ArrayList<State> states){
		for(State s : states)
			addState(s);
	}
	
	/**
	 * Remove um estado do AF. O estado é removido
	 * de todas as transições do AF como tambem da 
	 * lista de estados, da lista de estados finais
	 * e caso seja o estado inicial, este tambem é
	 * setado como nulo.
	 * 
	 * @param s		estado a ser removido.
	 */
	public void removeState(State s){
		transitions.removeTransitionsByState(s);
		states.remove(s);
		finalStates.remove(s);
		if(s.equals(startingState))
			startingState = null;
	}
	
	/**
	 * Verifica se um dado estado pertence ao AF.
	 * 
	 * @param s		Estado para verificação.
	 * @return		TRUE caso o estado pertença ao AF.
	 */
	public boolean stateBelongsToAF(State s){
		return states.contains(s);
	}
	
	/**
	 * Ordena os estados do AF deixando os estados com
	 * nome == "FINAL" ou "ERRO" por ultimo.
	 */
	public void sortStates(){
		Collections.sort(states, new Comparator<State>(){
			public boolean isLast(State s){
				return s.getName().contains("FINAL") ||
						s.getName().contains("ERRO"); 
			}
			
			@Override
			public int compare(State s1, State s2) {
				if(isLast(s1) && !isLast(s2))
					return 1;
				else if(!isLast(s1) && isLast(s2))
					return -1;
				else 
					return 0;
			}
		});
	}
	
	// Alphabet
	public ArrayList<Character> getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(ArrayList<Character> alphabet) {
		this.alphabet = alphabet;
	}
	
	/**
	 * Adiciona um novo simbolo ao alfabeto do AF
	 * verificando se este simbolo ja não pertence ao 
	 * alfabeto do AF.
	 * 
	 * @param simbol
	 */
	public void addSimbol(char simbol){
		if(!alphabet.contains(simbol))
			alphabet.add(simbol);
	}
	
	/**
	 * Remove um simbolo do alfabeto do AF. 
	 * Remove tambem todas as transições com este simbolo.
	 * 
	 * @param simbol	Simbolo a ser removido.
	 */
	public void removeSimbol(char simbol){
		if(alphabet.contains(simbol)){
			transitions.removeTransitionsBySimbol(simbol);
			alphabet.remove((Character) simbol);
		}
	}
	
	/**
	 * Ordena o alfabeto do AF deixando o simbolo
	 * '&' por ultimo.
	 */
	public void sortAlphabet(){
		Collections.sort(this.alphabet, new Comparator<Character>(){
			@Override
			public int compare(Character o1, Character o2) {
				if(o1 == '&' && o2 != '&')
					return 1;
				else if(o1 != '&' && o2 == '&')
					return -1;
				else 
					return o1.compareTo(o2);
			}
		});
	}
	
	// Transitions
	public Transitions getTransitions() {
		return transitions;
	}

	public void setTransitions(Transitions transitions) {
		this.transitions = transitions;
	}
	
	/**
	 * Adiciona uma nova transição ao AF 
	 * verificando se o estado current e o simbolo
	 * trigger ja existem no AF.
	 * 
	 * @param current		Estado 'corrente' da transição.	
	 * @param trigger		Simbolo 'disparador' da transição.
	 * @param next			Estado 'alvo' do disparo da transição.
	 */
	public void addTransition(State current, char trigger, State next){
		if(!states.contains(current) || 
				!alphabet.contains(trigger))
			return;
		
		transitions.addTransition(current, trigger, next);
	}
	
	/**
	 * Adiciona um conjuto de transições ao AF.
	 * 
	 * @param t		Transições a serem adicionadas.
	 */
	public void addTransitions(Transitions t){
		this.transitions.addTransitions(t);
	}
	
	/**
	 * Retorna os &-Fechos de todos os estados do AF.
	 * 
	 * @return	Um HashMap contendo o estado como key e seu &-fecho como value.
	 */
	public HashMap<State, Set<State>> getEpsilonFechos(){
		HashMap<State, Set<State>> 
			result = new HashMap<>();
		Set<State> tmp;
		for(State s : states){
			tmp = new HashSet<>();
			getEpsilonFecho(s, tmp);
			result.put(s, tmp);
		}
		return result;
	}
	
	/**
	 * Função interna que calcula o &-Fecho de um dado estado.
	 * 
	 * @param s			Estado que se quer achar o &-fecho.
	 * @param list		Lista que irá conter o &-fecho do 1* estado chamado pela função.
	 */
	private void getEpsilonFecho(State s, Set<State> list){
		list.add(s);
		for(State s2 : getNextStates(s, '&')){
			if(!s2.equals(s))
				getEpsilonFecho(s2, list);
		}
	}
	
	/**
	 * Retorna a lista de estados alcançaveis por um estado
	 * 'corrente' com um simbolo 'disparador'.
	 * 
	 * @param current		Estado 'corrente'.
	 * @param trigger		Simbolo 'disparador'.
	 * @return				Lista com os estados alcançaveis pelo estado 'corrente' 
	 * 						usando o simbolo 'disparador'.
	 */
	public ArrayList<State> getNextStates(State current, char trigger){
		if(!states.contains(current) || !alphabet.contains(trigger))
			return new ArrayList<>();
		return transitions.getNextStates(current, trigger);
	}
	
	/**
	 * Retorna a lista de estados alcançaveis por um estado
	 * 'corrente' com todos os simbolos do alfabeto do AF.
	 * 
	 * @param current		Estado 'corrente'.
	 * @return				Lista com os estados alcançaveis pelo estado
	 * 						'corrente' com todos os simbolos do alfabeto
	 * 						do AF.
	 */
	public ArrayList<State> getAllNextStates(State current){
		ArrayList<State> result = new ArrayList<>();
		
		for(char c : alphabet)
			result.addAll(transitions.getNextStates(current, c));
		
		return result;
	}
	
	/**
	 * Retorna a lista com todos os estados que alcançam o estado
	 * 'corrente' com todos os simbolos do alfabeto do AF.
	 * 
	 * @param current		Estado 'corrente'.
	 * @return				Lista com os estados que alcançam o estado
	 * 						'corrente' com todos os simbolos do alfabeto
	 * 						do AF
	 */
	public ArrayList<State> getAllPreviousStates(State current){
		ArrayList<State> result = new ArrayList<>();
		
		for(State s : states)
			if(getAllNextStates(s).contains(current))
				result.add(s);
		
		return result;
	}
	
	/**
	 * Troca todas as transições que levam ao estado 'sOld' 
	 * para que levem agora para o estado 'sNew'.
	 * 
	 * @param sOld		Estado ira perder as transições que levem nele.
	 * @param sNew		Estado que ira ganhar as transições para ele.
	 */
	public void swapNextStatesTransition(State sOld, State sNew){
		if(states.contains(sNew))
			transitions.swapNextStatesTransition(sOld, sNew);
	}
	
	// Starting State
	public State getStartingState() {
		return startingState;
	}

	public void setStartingState(State startingState) {
		this.startingState = startingState;
	}
	
	// Final States
	public ArrayList<State> getFinalStates() {
		return finalStates;
	}

	public void setFinalStates(ArrayList<State> finalStates) {
		this.finalStates = finalStates;
	}
	
	/**
	 * Adiciona um estado a lista de estados finais do AF.
	 * 
	 * @param s		Estado a ser adicionado a lista.
	 */
	public void addFinalState(State s){
		if(states.contains(s) && !finalStates.contains(s))
			finalStates.add(s);
	}
	
	/**
	 * Adiciona uma lista de estados a lista de 
	 * estados finais do AF.
	 * 
	 * @param states	Lista de estados a ser adicionado.
	 */
	public void addFinalStates(ArrayList<State> states){
		for(State s : states)
			addFinalState(s);
	}
	
	/**
	 * Remove um estado da lista de estados finais do AF.
	 * 
	 * @param s		Estado a ser removido.
	 */
	public void removeFinalState(State s){
		if(states.contains(s) && finalStates.contains(s))
			finalStates.remove(s);
	}
	
	/**
	 * Verifica se um estado esta na lista de estados finais do AF.
	 * 
	 * @param s		Estado que se quer verificar.
	 * @return		TRUE caso o estado esteja na lista de estados finais.
	 */
	public boolean isFinalState(State s){
		return finalStates.contains(s);
	}
	
	/**
	 * Retorna todos os estados não finais do AF.
	 * 
	 * @return	Lista com os estados não finais do AF.
	 */
	public ArrayList<State> getNonFinalStates(){
		ArrayList<State> result = new ArrayList<>();
		for(State s : states)
			if(!isFinalState(s))
				result.add(s);
		
		return result;
	}

	/* Regular */
	@Override
	public boolean isAutomation() {
		return true;
	}
	
	/**
	 * Clona este automato recriando todos os estados, listas
	 * e transições deste automato.
	 * 
	 * @param uniqueStatesName		É para deixar os estados do clone com nomes unicos?
	 * @return						Novo AF clone deste.
	 */
	public Automaton clone(boolean uniqueStatesName) {
		Automaton newAF = new Automaton(alphabet);
		HashMap<State, State> tmpHash = new HashMap<>();
		
		State tmp;
		for(State s : states){
			if(uniqueStatesName)
				tmp = new State(s+"'");
			else
				tmp = new State(s);
			newAF.addState(tmp);
			if(isFinalState(s))
				newAF.addFinalState(tmp);
			if(startingState.equals(s))
				newAF.setStartingState(tmp);
			tmpHash.put(s, tmp);
		}
		
		for(State s : states){
			for(char c : alphabet){
				for(State s2 : getNextStates(s, c))
					newAF.addTransition(tmpHash.get(s), c, tmpHash.get(s2));
			}
		}
		
		return newAF;
	}
	
}
