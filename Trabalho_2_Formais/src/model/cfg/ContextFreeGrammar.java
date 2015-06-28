package model.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextFreeGrammar {
	
	// conjunto Vn da gramatica
	private ArrayList<String> Vn;
	// conjunto Vt da gramatica
	private ArrayList<String> Vt;
	// conjunto de produções da gramatica
	private ArrayList<Production> productions;
	// simbolo inicial da gramatica
	private String initialSymbol;
	
	//titulo da gramatica
	private String titulo;
	private boolean extras;
	private String parser;

	private HashMap<String, Set<String>> first;
	private HashMap<String, Set<String>> firstNT;
	private HashMap<String, Set<String>> follow;
	
	// variaveis para saber se a gramatica tem rec a esq, esta fatorada
	// e se a intersecção do first e follow é vazia
	
	// -1 => não foi checado ainda; 0 => nao tem rec. esq; 1 => só rec. esq direta; 
	//  2 => só rec. esq indireta; 3 => ambos direto e indireto;
	private byte recEsq; 
	// guarda quais são os NTs recursivos;
	private Set<String> recSymbols;
	// -1 => não foi checado ainda; 0 => esta fatorada; 1 => só não-determinismo direto; 
	//  2 => só não-determinismo indireto; 3 => ambos direto e indireto;
	private byte fat;
	// guarda quais são os NTs não-fatorados;
	private Set<String> nonFatSymbols;
	// -1 => não foi checado ainda; 0 => intersecções vazias; 1 => intersecções não vazias;
	private byte emptyIntersection;
	// guarda quais são os NTs que a intersecção do first e follow não é vazia;
	private Set<String> nonEmptyIntersection;

	public ContextFreeGrammar() {
		this(new ArrayList<>(), new ArrayList<>(), 
				new ArrayList<>(), null);
	}
	
	public ContextFreeGrammar(ArrayList<String> vn, ArrayList<String> vt,
			ArrayList<Production> productions, String initialSimbol) {
		Vn = vn;
		Vt = vt;
		this.productions = productions;
		this.initialSymbol = initialSimbol;
		
		this.recEsq = -1;
		this.fat = -1;
		this.emptyIntersection = -1;
	}
	
	/* Vn */
	public ArrayList<String> getVn() {
		return Vn;
	}

	public void setVn(ArrayList<String> vn) {
		Vn = vn;
	}
	
	public boolean isNTerminal(String symbol){
		return Vn.contains(symbol);
	}
	
	public void addNTerminal(String nt){
		if(!Vn.contains(nt))
			Vn.add(nt);
	}
	
	/* Vt */
	public ArrayList<String> getVt() {
		return Vt;
	}

	public void setVt(ArrayList<String> vt) {
		Vt = vt;
	}
	
	public boolean isTerminal(String symbol){
		return Vt.contains(symbol);
	}
	
	public void addTerminal(String t){
		if(!Vt.contains(t))
			Vt.add(t);
	}
	
	/* Productions */
	public ArrayList<Production> getProductions() {
		return productions;
	}

	public void setProductions(ArrayList<Production> productions) {
		this.productions = productions;
	}
	
	public void addProduction(String NT, ArrayList<String> sentence){
		Production p = new Production(NT, sentence);
		if(!productions.contains(p))
			productions.add(p);
	}
	
	public ArrayList<Production> getNtProductions(String nt){
		ArrayList<Production> result = new ArrayList<>();
		for(Production p : productions){
			if(p.getNT().equals(nt))
				result.add(p);
		}
		return result;
	}
	
	/* Initial Symbol*/
	public String getInitialSymbol() {
		return initialSymbol;
	}

	public void setInitialSymbol(String initialSymbol) {
		this.initialSymbol = initialSymbol;
	}
	
	/* Titulo */
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/* Extras */
	public boolean hasExtras(){
		return extras;
	}
	
	public void setExtras(boolean extras){
		this.extras = extras;
	}
	
	/* First */
	public HashMap<String, Set<String>> getFirst() {
		if(first == null)
			setFirst();
		return first;
	}
	
	private void setFirst(){
		first = new HashMap<>();
		nonFatSymbols = new HashSet<>();
		boolean modified = true;
		
		for(String NT : Vn)
			first.put(NT, new HashSet<>());
		
		while(modified){
			modified = false;
			for(String NT : Vn)
				modified = modified | first.get(NT).addAll(getNtFirst(NT));
		}
		if(fat == -1)
			fat = 0;
	}
	
	/* Arrumar problema no Fat... [Aa | ABc] onde A => & */
	private Set<String> getNtFirst(String nt){
		Set<String> result = new HashSet<>();
		ArrayList<Struct> tmpFirsts = new ArrayList<>();
		
		Set<String> tmpSet;
		Struct tmpStruct;
		for(Production p : getNtProductions(nt)){
			tmpSet = getSentenceFirst(p.getSentence());
			
			tmpStruct = new Struct(p.getSentence().get(0), tmpSet);
			for(Struct s : tmpFirsts){
				if(!CfgCtrl.isEmptyIntersection(s.first, tmpStruct.first)){ // s.emptyIntersection(tmpStruct) //
					if(s.firstSymbol.equals(tmpStruct.firstSymbol)){
						if(fat == -1)
							fat = 1;
						else if(fat == 2)
							fat = 3;
					}else{
						if(fat == -1)
							fat = 2;
						else if(fat == 1)
							fat = 3;
					}
					nonFatSymbols.add(nt);
				}
			}
			tmpFirsts.add(tmpStruct);

			result.addAll(tmpSet);
		}

		return result;
	}
	
	private Set<String> getSentenceFirst(List<String> sentence){
		Set<String> result = new HashSet<>();
		
		Set<String> tmpSet = null;
		boolean hasEpsilon = true;
		int index = 0;
		String tmp = sentence.get(index);
		
		if(isTerminal(tmp))
			result.add(tmp);
		else{
			while(hasEpsilon){
				if(isTerminal(tmp)){
					tmpSet = null;
					result.add(tmp);
				}else{
					tmpSet = first.get(tmp);
					result.addAll(tmpSet);
					result.remove("&");
				}
				if(tmpSet != null && tmpSet.contains("&")){
					++index;
					if(index >= sentence.size()){
						hasEpsilon = false;
						result.add("&");
					}else
						tmp = sentence.get(index);
				}else
					hasEpsilon = false;
			}
		}
		
		return result;
	}
	
	/* FirstNT */
	public HashMap<String, Set<String>> getFirstNT() {
		if(firstNT == null)
			setFirstNT();
		return firstNT;
	}
	
	private void setFirstNT(){
		firstNT = new HashMap<>();
		recSymbols = new HashSet<>();
		boolean modified = true;
		
		for(String NT : Vn)
			firstNT.put(NT, new HashSet<>());			
		
		while(modified){
			modified = false;
			for(String NT : Vn)
				modified = modified | firstNT.get(NT).addAll(getNtFirstNT(NT));
		}
		if(recEsq == -1)
			recEsq = 0;
	}
	
	private Set<String> getNtFirstNT(String nt){
		Set<String> result = new HashSet<>();
		
		for(Production p : getNtProductions(nt))
			result.addAll(getSentenceFirstNT(p.getNT(), p.getSentence()));

		return result;
	}
	
	private Set<String> getSentenceFirstNT(String NT, ArrayList<String> sentence){
		Set<String> result = new HashSet<>();
		
		boolean hasEpsilon = true;
		int index = 0;
		Set<String> tmpSet = null;
		String tmp = sentence.get(index);
		
		if(tmp.equals(NT)){
			if(recEsq == -1)
				recEsq = 1;
			else if(recEsq == 2)
				recEsq = 3;
			recSymbols.add(NT);
		}
		
		while(hasEpsilon){
			if(isTerminal(tmp)){
				hasEpsilon = false;
			}else{
				result.add(tmp);
				tmpSet = firstNT.get(tmp);
				result.addAll(tmpSet);
				if(!tmp.equals(NT) && tmpSet.contains(NT)){
					if(recEsq == -1)
						recEsq = 2;
					else if(recEsq == 1)
						recEsq = 3;
					recSymbols.add(NT);
				}
			}
			if(hasEpsilon){
				hasEpsilon = false;
				for(Production p : getNtProductions(tmp)){
					if(p.getSentence().contains("&"))
						hasEpsilon = true;
				}
				if(hasEpsilon){
					++index;
					if(index >= sentence.size())
						hasEpsilon = false;
					else
						tmp = sentence.get(index);
				}
			}
		}
		return result;
	}

	/* Follow */
	public HashMap<String, Set<String>> getFollow() {
		if(follow == null)
			setFollow();
		return follow;
	}
	
	private void setFollow(){
		getFirst();
		follow = new HashMap<>();
		
		for(String NT : Vn)
			follow.put(NT, new HashSet<>());
		
		// Passo 1
		follow.get(initialSymbol).add("Z'"); //simbolo de final de palavra
		
		// Passo 2
		ArrayList<String> sentence;
		String tmpSymbol;
		
		for(String NT : Vn){
			for(Production p : getNtProductions(NT)){
				sentence = p.getSentence();
				for(int i = 0; i<sentence.size(); i++){
					tmpSymbol = sentence.get(i);
					if(!isTerminal(tmpSymbol)){
						if(i < sentence.size()-1){
							follow.get(tmpSymbol).addAll(
									getSentenceFirst(sentence.subList(i+1, sentence.size())));
						}
					}
				}
			}
		}
		
		// Passo 3
		boolean modified = true;
		boolean hasEpsilon = true;
		int index = 0;
		while(modified){
			modified = false;
			for(String NT : Vn){
				for(Production p : getNtProductions(NT)){
					sentence = p.getSentence();
					index = sentence.size()-1;
					hasEpsilon = true;
					while(hasEpsilon){
						tmpSymbol = sentence.get(index);
						if(isNTerminal(tmpSymbol)){
							modified = modified | follow.get(tmpSymbol).addAll(follow.get(NT));
							if(first.get(tmpSymbol).contains("&")){
								--index;
								if(index < 0)
									hasEpsilon = false;
							}else
								hasEpsilon = false;
						}else
							hasEpsilon = false;
					}
				}
			}
		}
		
		for(String NT : Vn)
			follow.get(NT).remove("&");
	}
	
	/* Parser */
	public String getParser() {
		return parser;
	}
	
	public void setParser(String parser){
		this.parser = parser;
	}
	
	/* Rec Esq */
	public byte getRecEsq() {
		return recEsq;
	}

	public Set<String> getRecSymbols() {
		return recSymbols;
	}
	
	/* Fat */
	public byte getFat() {
		return fat;
	}

	public Set<String> getNonFatSymbols() {
		return nonFatSymbols;
	}
	
	/* Empty Intersection */
	public byte getEmptyIntersection() {
		return emptyIntersection;
	}

	public Set<String> getNonEmptyIntersection() {
		return nonEmptyIntersection;
	}
	
	/* Geral */
	public String printSet(String key){
		HashMap<String, Set<String>> tmp = null;
		key = key.toLowerCase();
		
		if(key.equals("first"))
			tmp = getFirst();
		else if(key.equals("firstnt"))
			tmp = getFirstNT();
		else if(key.equals("follow"))
			tmp = getFollow();
		
		if(tmp == null || tmp.isEmpty())
			return null;
		
		String result = "";
		for(String NT : tmp.keySet()){
			result += NT+":\n";
			result += "   "+tmp.get(NT).toString()+"\n";
		}
		return result;
	}
	
	/* Override */
	@Override
	public String toString() {
		String s = "";
		for(String nt : Vn){
			s += nt+" -> ";
			for(Production p : getNtProductions(nt))
				s += p.toString()+"| ";
			s = s.substring(0, s.length()-2);
			s += "\n";
		}
		return s;
	}
	
	private class Struct {
		String firstSymbol;
		Set<String> first;
		
		public Struct(String s, Set<String> f){
			this.firstSymbol = s;
			this.first = f;
		}
		
		public boolean emptyIntersection(Struct s){
			boolean result = true;
			
			for(String f : this.first){
				if(s.first.contains(f))
					result = false;
			}
		
			return result;
		}
	}
}
