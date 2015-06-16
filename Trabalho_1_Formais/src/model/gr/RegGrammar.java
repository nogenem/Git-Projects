package model.gr;

import java.util.ArrayList;

import model.Regular;

public class RegGrammar extends Regular {
	
	private ArrayList<String> Vn;
	private ArrayList<Character> Vt;
	private ArrayList<Production> productions;
	private String initialSimbol;

	private String grammar; //representacao em String da gramatica

	public RegGrammar(String titulo) {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
				null, titulo, "");
	}
	
	public RegGrammar(String titulo, String grammar) {
		this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 
				null, titulo, grammar);
	}
		
	public RegGrammar(ArrayList<String> vn, ArrayList<Character> vt,
			ArrayList<Production> productions, String initialSimbol, String titulo, String grammar) {
		super();
		Vn = vn;
		Vt = vt;
		this.productions = productions;
		this.initialSimbol = initialSimbol;
		this.grammar = grammar;
		setTitulo(titulo);
	}

	/* Vn */
	public ArrayList<String> getVn() {
		return Vn;
	}

	public void setVn(ArrayList<String> vn) {
		Vn = vn;
	}
	
	public void addVn(String vn){
		if(!this.Vn.contains(vn))
			this.Vn.add(vn);
	}
	
	/* Vt */
	public ArrayList<Character> getVt() {
		return Vt;
	}

	public void setVt(ArrayList<Character> vt) {
		Vt = vt;
	}
	
	public void addVt(char vt){
		if(!this.Vt.contains(vt))
			this.Vt.add(vt);
	}

	/* Productions */
	public ArrayList<Production> getProductions() {
		return productions;
	}
	
	public ArrayList<Production> getProductions(String currentSimbol){
		ArrayList<Production> prods = new ArrayList<>();
		for(Production p : productions){
			if(p.getCurrent().equals(currentSimbol))
				prods.add(p);
		}
		return prods;
	}

	public void setProductions(ArrayList<Production> productions) {
		this.productions = productions;
	}
	
	public void addProduction(String current, char generated, String next){
		Production p = new Production(current, generated, next);
		if(!this.productions.contains(p))
			this.productions.add(p);
	}
	
	public void addProduction(Production p){
		if(!this.productions.contains(p))
			this.productions.add(p);
	}

	/* Initial Simbol */
	public String getInitialSimbol() {
		return initialSimbol;
	}

	public void setInitialSimbol(String initialSimbol) {
		this.initialSimbol = initialSimbol;
	}
	
	/* Grammar */
	public String getGrammar() {
		return grammar;
	}
	
	public void setGrammar(String grammar){
		this.grammar = grammar;
	}
	
	/* Regular */
	@Override
	public boolean isGrammar() {
		return true;
	}
	
	@Override
	public boolean isDumbGrEr(){
		return grammar.equals("");
	}
	
	@Override
	public String toString() {
		return grammar;
	}
}
