package Modelo;

public class Vertice {

	protected final String descricao;
	protected boolean temLaco; //TRUE se o vertice tem laco

	public Vertice(String descricao){
		this.descricao = descricao;
		this.temLaco = false;
	}
	
	public boolean temLaco(){
		return this.temLaco;
	}
	
	public void setLaco(boolean value){
		this.temLaco = value;
	}
	
	public String toString(){
		return this.descricao;
	}

	public boolean equals(Object obj){
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;

		return this.descricao.equals(obj.toString());
	}
}
