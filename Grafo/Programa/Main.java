package Programa;

import java.util.List;
import java.util.Queue;

import Modelo.Grafo;
import Modelo.Label;
import Modelo.Vertice;

public class Main {
	
	private final int limit = 3; //limite de missionarios e canibais
	private Grafo G;
	private Dijkstra d;
	
	public static void main(String[] args) {
		Vertice Inicial = new Vertice(0,0,3,3,"dir"); //vertice inicial do problema
		Vertice Final = new Vertice(3,3,0,0,"esq"); //vertice final do problema
		try{
			Main m = new Main();
			
			System.out.println("Grafo:");
			System.out.println(m.getGrafo()+ "\n");			
			
			m.buscaSolucao(Inicial);		
			
			String sol = m.getSolucao(Final).toString();
			System.out.println("Solucao:");
			System.out.println(sol.replace("],", "],\n"));
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public Main() throws Exception {
		this.G = new Grafo();
		adicionaVertices();
		adicionaArestas();
		this.d = new Dijkstra(G.ligacoes());
	}
	
	public Grafo getGrafo(){
		return G;
	}
	
	/**
	 * Cria os vertices do grafo seguindo as restricoes
	 * do problema.
	 */
	public void adicionaVertices() throws Exception{
		String[] margem = {"esq", "dir"};
		int Cd, Md;
		for(int Ce=0; Ce<=limit; Ce++)
			for(int Me=0; Me<=limit; Me++)
				for(int b=0; b<margem.length; b++){
					Cd = limit-Ce; Md = limit-Me;
					if((Ce <= Me && Cd <= Md) || Md==0 || Me==0)
						G.adicionaVertice(new Vertice(Ce,Me,Cd,Md,margem[b]));
				}
	}
	
	/**
	 * Adiciona as arestas do grafo seguindo as restricoes
	 * do problema.
	 */
	public void adicionaArestas() throws Exception{
		int tmp = 0;
		for(Vertice v1 : G.vertices()){
			for(Vertice v2 : G.vertices()){
				tmp = Math.abs((v1.Me+v1.Ce)-(v2.Me+v2.Ce));
				if(!v1.b.equals(v2.b) && 1<=tmp && tmp<=2){
					if(((v2.b.equals("esq") && v2.Ce>=v1.Ce && v2.Me>=v1.Me) || 
					   (v2.b.equals("dir") && v1.Ce>=v2.Ce && v1.Me>=v2.Me)) && !G.estaConectado(v1, v2))
						G.conecta(v1, v2, new Label(1));
				}	
			}
		}
	}
	
	/**
	 * Busca a solucao para o problema dos canibais e missionarios
	 * executando o algoritmo de busca de caminho de custo minimo
	 * de Dijkstra.
	 * 
	 * @param source		vertice inicial da busca.
	 */
	public void buscaSolucao(Vertice source){
		this.d.executeCode(source);
	}
	
	/**
	 * Retorna os caminhos de custo minimo partindo do
	 * vertice source usado na busca ate o vertice dest.
	 * 
	 * @param dest			vertice final do caminho.
	 * @return				lista com os caminhos de custo minimo
	 * 						entre source usado na busca e dest.
	 */
	public List<Queue<Vertice>> getSolucao(Vertice dest){
		return this.d.findMinimalDistanceTo(dest);
	}
}
