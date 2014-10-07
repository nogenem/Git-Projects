package Modelo;

public class Vertice {
	
	public final int Ce, Me, Cd, Md;
	public final String b;
	protected boolean temLaco; //TRUE se o vertice tem laco

	public Vertice(int Ce, int Me, int Cd, int Md, String b){
		this.Ce = Ce; //canibais no lado esquerdo
		this.Me = Me; //missionarios no lado esquerdo
		this.Cd = Cd; //canibais no lado direito
		this.Md = Md; //missionarios no lado direito
		this.b = b; //aonde esta o barco, esq ou dir
		this.temLaco = false; 
	}
	
	public boolean temLaco(){
		return this.temLaco;
	}
	
	public void setLaco(boolean value){
		this.temLaco = value;
	}
	
	public String toString(){
		return String.format("(%d, %d, %d, %d, %s)", Ce, Me, Cd, Md, b);
	}

	@Override
	public int hashCode() {
		final int prime = 3;
		int result = 2;
		result = prime * result + Cd;
		result = prime * result + Ce;
		result = prime * result + Md;
		result = prime * result + Me;
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (Cd != other.Cd || Ce != other.Ce)
			return false;
		if (Md != other.Md || Me != other.Me)
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
	
	
}
