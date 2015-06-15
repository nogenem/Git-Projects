package model;

/**
 * Classe usada como base para as Grs/Ers/Afs.
 * 
 * @author Gilney
 *
 */
public abstract class Regular {
	
	// titulo da gr/er/af
	private String titulo;
	// operações ja executadas pelo usuario
	// AF, AFD, AFD_Min, AFD_Comp, Complete
	// e tambem se é um AF da linguagem vazia.
	private String extras;
	
	public Regular(){
		this.extras = "";
	}
	
	// Titulo
	public String getTitulo(){
		return this.titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	
	// Extras
	public String getExtras(){
		return this.extras;
	}
	
	public void setExtras(String extras){
		this.extras = extras;
	}
	
	public void addExtra(String extra){	
		this.extras = extras.equals("") ?
				extra : extras+("|"+extra);
	}
	
	// Outras funcoes
	public boolean isGrammar(){
		return false;
	}
	
	public boolean isExpression(){
		return false;
	}
	
	public boolean isAutomation(){
		return false;
	}
	
	public boolean isDumbGrEr(){
		return false;
	}
}
