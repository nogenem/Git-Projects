package model.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextFreeGrammar {
	
	// Conjunto Vn da gramatica
	private ArrayList<String> Vn;
	// Conjunto Vt da gramatica
	private ArrayList<String> Vt;
	// Conjunto de produções da gramatica
	private ArrayList<Production> productions;
	// Simbolo inicial da gramatica
	private String initialSymbol;
	
	// Titulo da gramatica
	private String titulo;
	// Ja foi calculado o First, FirstNT, Follow e verificações?
	private boolean extras;
	// Parser desta gramatica
	private String parser;
	// Verificações LL(1) 
	private String verificacoes;
	
	// -1 => não foi checado ainda; 0 => é LL(1); 1 => não é LL(1)
	private byte checkLL1;

	private HashMap<String, Set<String>> first;
	private HashMap<String, Set<String>> firstNT;
	private HashMap<String, Set<String>> follow;
	
	// Variaveis para saber se a gramatica tem rec a esq, esta fatorada
	// e se a intersecção do first e follow é vazia
	
	// -1 => não foi checado ainda; 0 => nao tem rec. esq; 1 => só rec. esq direta; 
	//  2 => só rec. esq indireta; 3 => ambos direto e indireto;
	private byte recEsq; 
	// Guarda quais são os NTs recursivos;
	private Set<String> recSymbols;
	// -1 => não foi checado ainda; 0 => esta fatorada; 1 => só não-determinismo direto; 
	//  2 => só não-determinismo indireto; 3 => ambos direto e indireto;
	private byte fat;
	// Guarda quais são os NTs não-fatorados;
	private Set<String> nonFatSymbols;
	// -1 => não foi checado ainda; 0 => intersecções vazias; 1 => intersecções não vazias;
	private byte emptyIntersection;
	// Guarda quais são os NTs que a intersecção do first e follow não é vazia;
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
		
		this.checkLL1 = -1;
		
		this.nonEmptyIntersection = new HashSet<>();
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
	
	/**
	 * Retorna todas as produções de um simbolo 
	 * não terminal dado.
	 * 
	 * @param nt		Simbolo não terminal que se quer
	 * 					saber as produções.
	 * @return			Lista contendo as produções do 
	 * 					simbolo não terminal dado.
	 */
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
	
	public Set<String> getFirst(String NT){
		if(!getFirst().containsKey(NT)){// Provavelmente é um simbolo terminal
			Set<String> r = new HashSet<>();
			r.add(NT);
			return r;
		}
		return getFirst().get(NT);
	}
	
	private void setFirst(){
		first = new HashMap<>();
		boolean modified = true;
		
		for(String NT : Vn)
			first.put(NT, new HashSet<>());
		
		while(modified){
			modified = false;
			for(String NT : Vn)
				modified = modified | first.get(NT).addAll(getNtFirst(NT));
		}
	}

	private Set<String> getNtFirst(String nt){
		Set<String> result = new HashSet<>();

		Set<String> tmpSet;
		for(Production p : getNtProductions(nt)){
			tmpSet = getSentenceFirst(p.getSentence());
			result.addAll(tmpSet);
		}

		return result;
	}
	
	/**
	 * Função usada para achar o first de uma sentença de uma produção
	 * da gramatica.
	 * 
	 * @param sentence		Sentença da produção.
	 * @return				Lista contendo o first da sentença.
	 */
	public Set<String> getSentenceFirst(List<String> sentence){
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
					tmpSet = getFirst().get(tmp);
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
	
	public Set<String> getFirstNT(String NT){
		return getFirstNT().get(NT);
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
		if(recEsq == -1)// Nao tem rec a esq
			recEsq = 0;
	}
	
	private Set<String> getNtFirstNT(String nt){
		Set<String> result = new HashSet<>();
		
		for(Production p : getNtProductions(nt))
			result.addAll(getSentenceFirstNT(p.getNT(), p.getSentence()));

		return result;
	}
	
	/**
	 * Função usada para achar o firstNT de uma sentença de uma produção
	 * da gramatica.
	 * 
	 * @param NT			Simbolo não terminal 'cabeça' da produção.
	 * @param sentence		Sentença da produção.
	 * @return				Lista contendo o first da sentença.
	 */
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
				tmpSet = getFirstNT().get(tmp);
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
	
	public Set<String> getFollow(String NT){
		return getFollow().get(NT);
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
	
	/* LL1 */
	public void setIsLL1(boolean v){
		this.checkLL1 = (byte) (v?0:1);
	}
	
	public boolean isLL1Checked(){
		return checkLL1 != -1;
	}
	
	public boolean isLL1(){
		return checkLL1 == 0;
	}
	
	/* Parser */
	public String getParser() {
		return parser;
	}
	
	public void setParser(String parser){
		this.parser = parser;
	}
	
	/* Verificações */
	public String getVerificacoes(){
		return verificacoes;
	}
	
	public void setVerificacoes(String v){
		this.verificacoes = v;
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
	/**
	 * Função responsavel por verificar se a gramatica esta fatorada.
	 * Caso não esteja, ela tambem verifica o tipo de não-determinismo.
	 */
	public void checkFat(){
		nonFatSymbols = new HashSet<>();
		
		ArrayList<Production> prods = null;
		ArrayList<String> tmp1 = null;
		ArrayList<String> tmp2 = null;		
		boolean checkFirst = true;
		for(String NT : getVn()){
			prods = getNtProductions(NT);
			for(int i = 0; i<prods.size(); i++){
				for(int j = i+1; j<prods.size(); j++){
					tmp1 = prods.get(i).getSentence();
					tmp2 = prods.get(j).getSentence();
					
					if(tmp1.get(0).equals(tmp2.get(0))){ //direto
						if(fat == -1)
							fat = 1;
						else if(fat == 2)
							fat = 3;
						nonFatSymbols.add(NT);
						checkFirst = !(isTerminal(tmp1.get(0)) && isTerminal(tmp2.get(0)));
					}
					if(checkFirst && (!CfgCtrl.isEmptyIntersection(getSentenceFirst(tmp1), getSentenceFirst(tmp2)) ||
							!CfgCtrl.isEmptyIntersection(getSentenceFirstNT(NT, tmp1), getSentenceFirstNT(NT, tmp2)))){ //indireto
						if(fat == -1)
							fat = 2;
						else if(fat == 1)
							fat = 3;
						nonFatSymbols.add(NT);
					}
				}
				checkFirst = true;
			}
		}
		if(fat == -1)// Esta fatorada
			fat = 0;
	}
	
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
}
