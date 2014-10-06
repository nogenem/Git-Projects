package Modelo;

//rotulo para as arestas do grafo
public class Label {
	private int dist;
	
	public Label(int dist){
		this.dist = dist;
	}
	
	public int getDist(){
		return dist;
	}
	
	public void setDist(int dist){
		this.dist = dist;
	}
}
