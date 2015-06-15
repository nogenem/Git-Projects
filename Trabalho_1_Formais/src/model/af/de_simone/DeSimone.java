package model.af.de_simone;

import java.util.ArrayList;
import java.util.HashMap;

import model.af.Automaton;
import model.af.State;
import model.er.RegExpressionCtrl;

public abstract class DeSimone {
	
	public DeSimone() {
	}
	
	/**
	 * Função responsavel por criar o AFD apartir de uma 
	 * Expressão Regular dada utilizando o algoritmo De Simone.
	 * 
	 * @param regEx		Expressão regular que se quer achar o AFD.
	 * @return			AFD equivalente a Expressão Regular dada.
	 */
	public static Automaton createAutomaton(String regEx){
		Tree tree = new Tree(regEx);
		HashMap<CompositeState, ArrayList<Node>> comp = new HashMap<>();//composicao de estados
		Automaton af = new Automaton();
		
		for(Node n : tree.getListLeaves()){
			af.addSimbol(n.getC());
		}
		
		State sTmp = new State("Q0");
			af.setStartingState(sTmp);
		CompositeState cState = new CompositeState(sTmp);
			cState.addNode(tree.getRoot());
		
		createAutomatonRec(cState, af, tree, comp);	
		
		return af;
	}

	/**
	 * Função recursiva interna que cria o AFD equivalente a ER dada.
	 * 
	 * @param sTmp		Estado atual da recursão.
	 * @param af		AFD que esta sendo criado.
	 * @param tree		Arvore base.
	 * @param comp		HashMap que contem o estado base como key e 
	 * 					a lista de nodos da sua composição como value.
	 */
	private static void createAutomatonRec(CompositeState sTmp, Automaton af, Tree tree,
			HashMap<CompositeState, ArrayList<Node>> comp) {
		
		ArrayList<Node> tmp;
		boolean equal = false;
		ArrayList<Node> compTmp = getComposition(sTmp, tree);
		if(compTmp.size() == 0)
			return;
		
		for(CompositeState s : comp.keySet()){
			tmp = comp.get(s);
			equal = true;
			for(Node n : compTmp){
				if(!tmp.contains(n))
					equal = false;
			}
			if(equal && compTmp.size() == tmp.size()){
				af.swapNextStatesTransition(sTmp.getState(), s.getState());
				break;
			}else if(equal && compTmp.size() != tmp.size()){
				equal = false;
			}
		}
		if(!equal){
			af.addState(sTmp.getState());
			comp.put(sTmp, compTmp);
			
			HashMap<Character, ArrayList<State>> newStates = new HashMap<>();
			for(char c : af.getAlphabet())
				newStates.put(c, new ArrayList<>());
			
			for(Node n : compTmp){
				if(n.getC() != '$')
					newStates.get(n.getC()).add(new State("Q"+n.getNumero()));
				else
					af.addFinalState(sTmp.getState());
			}
			
			CompositeState nState;
			ArrayList<CompositeState> tmp2 = new ArrayList<>();
			for(char c : newStates.keySet()){
				if(newStates.get(c).size() > 0){
					nState = new CompositeState(new State(newStates.get(c).toString()));
					for(Node n : compTmp)
						if(n.getC() == c)
							nState.addNode(n);
					
					af.addTransition(sTmp.getState(), c, nState.getState());
					tmp2.add(nState);
				}
			}
			for(CompositeState s : tmp2)
				createAutomatonRec(s, af, tree, comp);
		}
	}
	
	/**
	 * Função interna que pega os nodos da composição de um estado 
	 * do AF.
	 * 
	 * @param sTmp		Estado que se quer achar a composição.
	 * @param tree		Arvore base.
	 * @return			Lista de nodos que são a composição do estado dado.
	 */
	private static ArrayList<Node> getComposition(CompositeState sTmp, Tree tree) {
		
		ArrayList<Node> result = new ArrayList<>();
		for(Node n : sTmp.getComposition()){
			if(n.getC() != '$')
				searchTree(n, !RegExpressionCtrl.isOperator(n.getC(),false), result, tree);
		}
		
		return result;
	}
	
	/**
	 * Função recursiva interna usada para 'explorar' a arvore
	 * gerada apatir da expressão regular que se quer criar o 
	 * AFD.
	 * 
	 * @param n			Nodo atual da recursão.
	 * @param dir		Direção de exploração da arvore.
	 * 					TRUE => subindo na arvore,
	 * 					FALSE => descendo na arvore.
	 * @param result	Nodos folhas achados pela exploração.
	 * @param tree		Arvore base.
	 */
	private static void searchTree(Node n, boolean dir, ArrayList<Node> result, Tree tree) {
		
		if(n == null)
			return;
		
		if(!dir){//descida
			switch (n.getC()) {
				case '.':
					searchTree(n.getFilhoEsq(), false, result, tree);
					break;
				case '|':
					searchTree(n.getFilhoEsq(), false, result, tree); 
					searchTree(n.getFilhoDir(), false, result, tree);
					break;
				case '*':
					searchTree(n.getFilhoEsq(), false, result, tree);
					searchTree(n.getCostura(), true, result, tree); 
					break;
				case '?':
					searchTree(n.getFilhoEsq(), false, result, tree);
					searchTree(n.getCostura(), true, result, tree); 
					break;
				default: //folha
					if(!result.contains(n))
						result.add(n);
					break;
			}
		}else{//subindo
			switch (n.getC()) {
			case '.':
				searchTree(n.getFilhoDir(), false, result, tree);
				break;
			case '|':
				while(n.getFilhoDir() != null){
					n = n.getFilhoDir();
				}
				searchTree(n.getCostura(), true, result, tree);
				break;
			case '*':
				searchTree(n.getFilhoEsq(), false, result, tree);
				searchTree(n.getCostura(), true, result, tree);
				break;
			case '?':
				searchTree(n.getCostura(), true, result, tree); 
				break;
			default://folha
				if(n.getC() == '$')
					result.add(n);
				else
					searchTree(n.getCostura(), true, result, tree);
				break;
			}
		}
	}
}
