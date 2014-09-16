package Modelo;

public class Vertice {

	protected final String descricao;

	public Vertice(String descricao){
		this.descricao = descricao;
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
