package Programa;

import Modelo.Grafo;
import Modelo.Vertice;

public class Main {

	public static void main(String[] args) {
		try{
			Grafo G = new Grafo();
			
			Vertice v1 = G.adicionaVertice("1");
			Vertice v2 = G.adicionaVertice("2");
			Vertice v3 = G.adicionaVertice("3");
			//Vertice v4 = G.adicionaVertice("4");
			//Vertice v5 = G.adicionaVertice("5");
			//Vertice v6 = G.adicionaVertice("6");
			
			G.conecta(v1, v2, null);
			G.conecta(v1, v3, null);
			G.conecta(v2, v3, null);
			G.conecta(v1, v1, null);
			//G.conecta(v3, v4, null);
			//G.conecta(v4, v5, null);
			//G.conecta(v3, v6, null);
			
			System.out.println(G);
			
			//G.removeVertice(v2);
			//G.desconecta(v1, v2);
			System.out.println("Grau de v1: " +G.grau(v1));
			
			System.out.println(G);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
