package model.af.de_simone;

import java.util.ArrayList;

import model.af.State;

/**
 * Classe usada pelo codigo De Simone. Contem
 * o estado e os nodos que o constituem.
 * 
 * @author Gilney
 *
 */
public class CompositeState {
	
	private State state;
	private ArrayList<Node> composition;
	
	public CompositeState(State state) {
		this(state, new ArrayList<>());
	}

	public CompositeState(State state, ArrayList<Node> composition) {
		this.state = state;
		this.composition = composition;
	}
	
	// State
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	// Composition
	public ArrayList<Node> getComposition() {
		return composition;
	}

	public void setComposition(ArrayList<Node> composition) {
		this.composition = composition;
	}
	
	public void addNode(Node n){
		this.composition.add(n);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((composition == null) ? 0 : composition.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeState other = (CompositeState) obj;
		if (composition == null) {
			if (other.composition != null)
				return false;
		} else if (!composition.equals(other.composition))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
}
