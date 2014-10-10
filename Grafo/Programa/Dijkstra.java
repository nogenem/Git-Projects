package Programa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import Modelo.Label;
import Modelo.Vertice;

public class Dijkstra {
	private HashMap<Vertice, HashMap<Vertice, Label>> ligacoes;
	private HashMap<Vertice, Integer> valorAtual; //valor atual dos vertices durante a busca
	private Set<Vertice> taFechado; //vertices que estao 'fechados' na busca
	private HashMap<Vertice, Queue<Vertice>> vsAtualiza; //vertices utilizados para 
											   	         //atualizar o valor do custo
	
	public Dijkstra(HashMap<Vertice, HashMap<Vertice, Label>> ligacoes){
		this.ligacoes = ligacoes;
	}
	
	public void setLigacoes(HashMap<Vertice, HashMap<Vertice, Label>> ligacoes){
		this.ligacoes = ligacoes;
	}
	
	public void cleanData(){
		valorAtual = new HashMap<>();
		taFechado = new HashSet<>();
		vsAtualiza = new HashMap<>();
	}
	
	/**
	 * Executa a busca do caminho de custo minimo partindo do 
	 * vertice Source.
	 * 
	 * @param source		vertice inicial da busca.
	 */
	public void executeCode(Vertice source){
		cleanData();
		
		Vertice fechado = null;
		Set<Vertice> adjacentes = null;
		int base = 0, tmp = 0;
		
		for(Vertice v : ligacoes.keySet()){ //inicia os valores
			valorAtual.put(v, v.equals(source) ? 0 : Integer.MAX_VALUE);
			vsAtualiza.put(v, new LinkedList<Vertice>());
		}
		
		//enquanto taFechado nao tiver todos os vertices do grafo...
		while(taFechado.size() < ligacoes.keySet().size()){
			fechado = getMenorValorAtual(); //pega o menor valor atual
			taFechado.add(fechado); //fecha o vertice com o menor valor atual
			base = valorAtual.get(fechado); //o valor dele vai ser usado como base do calculo
			adjacentes = ligacoes.get(fechado).keySet();
			
			for(Vertice v : adjacentes){
				if(!v.equals(fechado)){
					tmp = base + ligacoes.get(fechado).get(v).getDist();
					if(tmp == valorAtual.get(v))
						vsAtualiza.get(v).add(fechado);
					else if(tmp < valorAtual.get(v)){
						valorAtual.put(v, tmp); //sobreescreve o valor atual
						vsAtualiza.get(v).clear(); //remove os valores desatualizados
						vsAtualiza.get(v).add(fechado);
					}
				}
			}
		}
		
	}
	
	/**
	 * Pega o vertice que nao esteja fechado ainda e que 
	 * tenha o menor 'valorAtual'.
	 * 
	 * @return		vertice com o menor 'valorAtual' e que
	 * 				nao esteja fechado ainda.
	 */
	public Vertice getMenorValorAtual(){
		Vertice min = null;
		for(Vertice v : valorAtual.keySet()){ 
			if(!taFechado.contains(v)){
				if(min == null)
					min = v;
				else if(valorAtual.get(v) < valorAtual.get(min))
					min = v;
			}
		}
		return min;
	}
	
	/**
	 * Apos a busca do caminho, retorna uma lista com os caminhos
	 * de custo minimo da source ate o destino.
	 * 
	 * @param dest			vertice de destino da busca.
	 * @exception			caso o codigo de busca ainda nao tenha sido executado.
	 * @return				lista com os caminhos minimos entre
	 * 						o vertice source usado na busca e o vertice dest.
	 */
	public List<Queue<Vertice>> findMinimalDistanceTo(Vertice dest) throws Exception {
		List<Queue<Vertice>> list = new ArrayList<>();
		
		if(vsAtualiza == null || vsAtualiza.isEmpty())
			throw new Exception("Execute o codigo de busca antes.");
		
		Queue<Vertice> result = new LinkedList<>();
		Vertice tmp;
		boolean morePaths; //variavel auxiliar que diz se ha mais 
						   //caminhos minimos entre source utilizado 
						   //na busca e dest
					
		do{
			morePaths = false;
			tmp = dest;
			result.add(tmp);
			while(vsAtualiza.get(tmp).size() != 0){
				if(vsAtualiza.get(tmp).size() > 1){
					tmp = vsAtualiza.get(tmp).poll();
					morePaths = true;
				}else
					tmp = vsAtualiza.get(tmp).element();
				result.add(tmp);
			}
			Collections.reverse((LinkedList<Vertice>) result);
			list.add(result);
			result = new LinkedList<>();
		}while(morePaths);
		
		return list;
	}
}
