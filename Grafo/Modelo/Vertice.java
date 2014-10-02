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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
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
		Vertice other = (Vertice) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
}
