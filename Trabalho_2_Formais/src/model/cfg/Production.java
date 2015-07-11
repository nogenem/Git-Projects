package model.cfg;

import java.util.ArrayList;

public class Production {
	// Simbolo Não-Terminal 'cabeça' da produção
	private String NT;
	// Uma das senteças geradas pelo simbolo NT
	// em questão. EX: S -> a S a ==> sentence = [a, S, a]
	private ArrayList<String> sentence;
	
	public Production(String NT, ArrayList<String> sentence) {
		this.NT = NT;
		this.sentence = sentence;
	}
	
	/* NT */
	public String getNT() {
		return NT;
	}

	public void setNT(String nT) {
		NT = nT;
	}
	
	/* Sentence */
	public ArrayList<String> getSentence() {
		return sentence;
	}

	public void setSentence(ArrayList<String> sentence) {
		this.sentence = sentence;
	}
	
	/* Override */
	@Override
	public String toString() {
		String result = "";
		for(String s : sentence)
			result += s+" ";
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		if (NT == null) {
			if (other.NT != null)
				return false;
		} else if (!NT.equals(other.NT))
			return false;
		if (sentence == null) {
			if (other.sentence != null)
				return false;
		} else if (!sentence.equals(other.sentence))
			return false;
		return true;
	}
}
