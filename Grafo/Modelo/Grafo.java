package Modelo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Grafo {

	private HashMap<Vertice, HashMap<Vertice, Label>> vertices;

	public Grafo(){
		vertices = new HashMap<>();
	}

	/**
     * Devolve um conjunto com todos os vertices do grafo.
     *
     * @return		os vertices do grafo.
     */
	public Set<Vertice> vertices() {
		return new HashSet<>(vertices.keySet());
	}

	/**
     * Adiciona um vertice desconexo, caso nao tenha nenhum outro vertice com a
     * mesma descricao.
     *
     * @param descricao 	eh a descricao do vertice que sera criado.
     * @throws Exception 	se ja existe um vertice com a mesma descricao.
     * @return 				o novo vertice adicionado no grafo.
     */
	public Vertice adicionaVertice(String descricao) throws Exception {
		Vertice v1 = new Vertice(descricao);

		for(Vertice v : vertices.keySet())
			if(v.equals(v1))
				throw new Exception("Vertice ja existe.");

		vertices.put(v1, new HashMap<Vertice, Label>());
		return v1;
	}

	/**
     * Remove o vertice v do Grafo.
     *
     * @param v 			eh o vertice a ser removido.
     * @throws Exception 	se o vertice passado eh nulo.
     */
	public void removeVertice(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice passado eh nulo.");

		removeLigacoes(v);
		vertices.remove(v);
	}

	/**
	 * Remove todas as ligacoes que o vertice v tem.
	 *
	 * @param v 	eh o vertice para o qual serao removidas as ligacoes.
	 */
	private void removeLigacoes(Vertice v){
		for(Vertice adj : vertices.get(v).keySet())
			vertices.get(adj).remove(v);
	}

	/**
     * Conecta dois vertices.
     *
     * @param v1 			primeiro vertice a ser conectado.
     * @param v2 			segundo vertice a ser conectado.
     * @param rotulo  		rotulo da ligacao.
     * @throws Exception	se o vertice v1 ou v2 for nulo
     * @throws Exception	se o vertice v1 ja esta conectado ao vertice v2
     */
	public void conecta(Vertice v1, Vertice v2, Label rotulo) throws Exception {
		if(v1 == null || v2 == null)
			throw new Exception("Vertice v1 ou v2 passados eh nulo.");
		else if(estaConectado(v1, v2))
			throw new Exception("Vertice v1 ja esta conectado ao vertice v2.");

		vertices.get(v1).put(v2, rotulo);
		vertices.get(v2).put(v1, rotulo);
		
		if(v1.equals(v2)) //caso v1 e v2 sejam iguais, estamos adicionando um laco a v1.
			v1.setLaco(true);
	}

	/**
	 * Verifica se dois vertices estao conectados.
	 *
	 * @param v1			primeiro vertice para checagem.
	 * @param v2			segundo vertice para checagem.
	 * @throws Exception	se o vertice v1 ou v2 for nulo
	 * @return 				TRUE caso v1 esteja conectado a v2.
	 */
	public boolean estaConectado(Vertice v1, Vertice v2) throws Exception {
		if(v1 == null || v2 == null)
			throw new Exception("Vertice v1 ou v2 passados eh nulo.");
		
		return adjacentes(v1).contains(v2);
	}

	/**
	 * Retorna os vertices adjacentes ao vertice v.
	 *
	 * @param v				vertice que desejasse pegar os adjacentes.
	 * @throws Exception	se o vertice v for nulo
	 * @return				um conjunto contendo os vertices adjacentes
	 * 						ao vertice v.
	 */
	public Set<Vertice> adjacentes(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice v passado eh nulo.");
		
		return new HashSet<>(vertices.get(v).keySet());
	}

	/**
	 * Desconecta dois vertices.
	 *
	 * @param v1			primeiro vertice da ligacao a ser desconectado.
	 * @param v2			segundo vertice da ligacao a ser desconectado.
	 * @throws Exception	se o vertice v1 ou v2 for nulo.
	 * @throws Exception	se o vertice v1 nao esta conectado ao v2.
	 */
	public void desconecta(Vertice v1, Vertice v2) throws Exception {
		if(v1 == null || v2 == null)
			throw new Exception("Vertice v1 ou v2 passados eh nulo.");
		else if(!estaConectado(v1, v2))
			throw new Exception("Vertice v1 nao esta conectado ao vertice v2.");

		vertices.get(v1).remove(v2);
		vertices.get(v2).remove(v1);
		
		if(v1.equals(v2)) //caso v1 e v2 sejam iguais, estamos removendo um laco a v1.
			v1.setLaco(false);
	}

	/**
	 * Devolve a quantidade de vertices do grafo.
	 *
	 * @return		a quantidade de vertices do grafo.
	 */
	public int ordem(){
		return vertices.size();
	}

	/**
	 * Devolve um vertice qualquer do grafo.
	 *
	 * @return		um vertice qualquer do grafo ou
	 * 				null caso o grafo nao tenha vertices.
	 */
	public Vertice umVertice(){
		if(vertices.isEmpty())
			return null;

		Object[] keys = vertices.keySet().toArray();
		int random = (int) (Math.random() * keys.length);

		return (Vertice) keys[random];
	}

	/**
	 * Devolve a quantidade de ligacoes que o vertice v possui.
	 *
	 * @param v				eh o vertice que desejasse saber o seu grau.
	 * @throws Exception	se o vertice v for nulo
	 * @return				a quantidade de ligacoes que o vertice v possui.
	 */
	public int grau(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice v passado eh nulo.");
		
		int g = vertices.get(v).size(); 
		return v.temLaco() ? g+1 : g; //caso v tenha laco, ele conta como 2 no grau dele.
	}

	/**
	 * Verifica se o grafo eh regular.
	 * 
	 * @throws Exception	grau(v) lanca exception caso o 
	 * 						vertice v seja nulo.
	 * @return				TRUE caso o grafo seja regular.
	 */
	public boolean isRegular() throws Exception {
		int g = -1;
		for(Vertice v : vertices.keySet()){
			if(g == -1)
				g = grau(v);
			else if (g != grau(v))
				return false;
		}
		return true;
	}

	/**
	 * Verifica se o grafo eh completo.
	 * 
	 * @throws Exception	grau(v) lanca exception caso o 
	 * 						vertice v seja nulo.
	 * @return				TRUE caso o grafo seja completo.
	 */
	public boolean isCompleto() throws Exception {
		if(vertices.isEmpty())
			return false; //ta certo?

		int g = ordem()-1;
		for(Vertice v : vertices.keySet())
			if(grau(v) != g)
				return false;

		return true;
	}

	/**
	 * Calcula o fecho transitivo a partir de um vertice.
	 *
	 * @param v				vertice inicial da procura.
	 * @throws Exception	se o vertice passado seja nulo.
	 * @return				um conjunto contendo o fecho transitivo apartir
	 * 						de um vertice dado.
	 */
	public Set<Vertice> fechoTransitivo(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice passado eh nulo.");
		
		Set<Vertice> visitados = new HashSet<Vertice>();
		fechoTransitivo(v, visitados);
		
		return visitados;
	}

	/**
	 * Calcula o fecho transitivo a partir de um vertice.
	 *
	 * @param v				vertice inicial da procura.
	 * @param visitados		conjunto com os vertices que ja foram visitados.
	 * @return				um conjunto contendo o fecho transitivo apartir
	 * 						de um vertice dado.
	 */
	private void fechoTransitivo(Vertice v, Set<Vertice> visitados){
		visitados.add(v);
		for(Vertice y : vertices.get(v).keySet())
			if(!visitados.contains(y))
				fechoTransitivo(y, visitados);
	}
	
	/**
	 * Verifica se o grafo eh conexo.
	 *
	 * @throws Exception	fechoTransitivo(v) lanca exception caso o vertice
	 * 						passado seja nulo.
	 * @return 				TRUE caso o grafo seja conexo.
	 */
	public boolean isConexo() throws Exception {
		if(vertices.isEmpty())
			return false; //ta certo?

		return fechoTransitivo(umVertice()).size() == vertices.size();
	}
	
	/**
	 * Verifica se o grafo eh uma arvore.
	 * 
	 * @throws Exception	fechoTransitivo(v), usado na funcao isConexo(), 
	 * 						lanca exception caso o vertice passado seja nulo.
	 * @return				TRUE caso o grafo seja uma arvore.
	 */
	public boolean isArvore() throws Exception {
		//ta certo?
		int nArestas = vertices.values().size() / 2;
		return isConexo() && nArestas == Math.ceil(vertices.size()-1); //precaucao para caso de lacos
	}

	/**
	 * Devolve uma representacao do grafo.
	 *
	 * @return		uma string com a representacao do grafo.
	 */
	public String toString(){
		if(vertices.isEmpty())
			return "V = {}\nA = {}";
		
		String grafo = "V = ";
			grafo += vertices.keySet();
			grafo = grafo.replace("[", "{").replace("]", "}");
			grafo += "\nA = {";

		for(Vertice v : vertices.keySet())
			for(Vertice vi : vertices.get(v).keySet())
					grafo += "("+ v +", "+ vi +"), ";

			grafo = grafo.substring(0, grafo.length()-2);
			grafo += "}";
		return grafo;
	}

	/* Funcoes extras */

	/**
	 * Verifica se existe pelo menos um ciclo no grafo.
	 *
	 * @throws Exception	adjacentes(v), usado na funcao temCiclos interna,
	 * 						lanca exception caso o vertice passado seja nulo.
	 * @return				TRUE caso tenha pelo menos um ciclo no grafo.
	 */
	public boolean temCiclo() throws Exception {
		Set<Vertice> visitados = new HashSet<Vertice>();
		Set<Vertice> vs = vertices(); 
		
		while(!vs.isEmpty()){
			if(temCiclo(vs.iterator().next(), null, visitados, vs))
				return true;
		}
		
		return false;
	}

	/**
	 * Verifica se existe pelo menos um ciclo no grafo.
	 *
	 * @param v				vertice atual.
	 * @param ant			vertice anterior.
	 * @param visitados		conjunto dos vertices ja visitados.
	 * @throws Exception	adjacentes(v) lanca exception caso o vertice 
	 * 						passado seja nulo.
	 * @return 				TRUE caso tenha pelo menos um ciclo no grafo.
	 */
	private boolean temCiclo(Vertice v, Vertice ant, Set<Vertice> visitados, Set<Vertice> vs) throws Exception {
		vs.remove(v);
		if(visitados.contains(v))
			return true;
		else
			visitados.add(v);
		
		for(Vertice y : adjacentes(v)){
			if(!y.equals(ant) && temCiclo(y, v, visitados, vs))
				return true;
		}
		return false;
	}
	
	/**
	 * Devolve um conjunto contendo todos os componentes conexos
	 * do grafo.
	 *  
	 * @throws Exception		fechoTransitivo(v) lanca exception
	 * 							caso o vertive v seja nulo.
	 * @return					um conjunto contendo todos os componentes
	 * 							conexos do grafo.
	 */
	public Set<Set<Vertice>> componentesConexos() throws Exception{
		Set<Set<Vertice>> comps = new HashSet<>();
		Set<Vertice> vs = vertices(); 
		Set<Vertice> tmp;
		
		while(!vs.isEmpty()){
			tmp = fechoTransitivo(vs.iterator().next());
			vs.removeAll(tmp); 
			comps.add(tmp);
		}
		
		return comps;
	}
	
	public void visita(Vertice v){
		System.out.println("Busca achou o vertice: " + v);
	}

	public void buscaEmLargura(Vertice v) throws Exception {
		Queue<Vertice> fila = new LinkedList<>();
		Set<Vertice> visitados = new HashSet<>();
		Vertice x;

		fila.add(v);
		while(!fila.isEmpty()){
			x = fila.poll();
			visita(x);
			visitados.add(x);
			for(Vertice y : adjacentes(x))
				if(!visitados.contains(y) && !fila.contains(y))
					fila.add(y);
		}
	}

	public void buscaEmProfundidade(Vertice v) throws Exception {
		buscaEmProfundidade(v, new HashSet<Vertice>());
	}

	private void buscaEmProfundidade(Vertice v, Set<Vertice> visitados) throws Exception {
		visita(v);
		visitados.add(v);
		for(Vertice y : adjacentes(v))
			if(!visitados.contains(y))
				buscaEmProfundidade(y, visitados);

		visitados.remove(v);
	}
}
