package Modelo;

import java.util.HashMap;
import java.util.HashSet;
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
	 * Devolve o grafo, vertices e suas ligacoes.
	 * 
	 * @return		os vertices do grafo com suas ligacoes.
	 */
	public HashMap<Vertice, HashMap<Vertice, Label>> getGrafo(){
		return new HashMap<>(vertices);
	}

	/**
     * Adiciona um vertice desconexo, caso nao tenha nenhum outro vertice com a
     * mesma descricao.
     *
     * @param v				vertice que sera adicionado ao grafo.
     * @throws Exception 	se ja existe um vertice com a mesma descricao.
     * @return 				o novo vertice adicionado no grafo.
     */
	public Vertice adicionaVertice(Vertice v) throws Exception {
		if(vertices.containsKey(v))
			throw new Exception("Vertice v ja existe.");
		
		vertices.put(v, new HashMap<Vertice, Label>());
		return v;
	}

	/**
     * Remove o vertice v do Grafo.
     *
     * @param v 			vertice a ser removido.
     * @throws Exception 	se o vertice passado eh nulo.
     * @throws Exception	se o vertice v não pertencer ao grafo.
     */
	public void removeVertice(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice v passado eh nulo.");
		if(!vertices.containsKey(v))
			throw new Exception("Vertice v não pertence ao grafo.");

		removeLigacoes(v);
		vertices.remove(v);
	}

	/**
	 * Remove todas as ligacoes que o vertice v tem.
	 *
	 * @param v 	vertice para o qual serao removidas as ligacoes.
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
     * @throws Exception	se o vertice v1 ou v2 for nulo.
     * @throws Exception	{@link #estaConectado(v1,v2)} lança exceptions.
     * @throws Exception	se o vertice v1 ja esta conectado ao vertice v2.
     * @throws Exception	se v1 for igual a v2.
     */
	public void conecta(Vertice v1, Vertice v2, Label rotulo) throws Exception {
		if(v1 == null || v2 == null)
			throw new Exception("Vertice v1 ou v2 passados eh nulo.");
		if(estaConectado(v1, v2))
			throw new Exception("Vertice v1 ja esta conectado ao vertice v2.");
		if(v1.equals(v2))
			throw new Exception("Este grafo nao aceita lacos.");

		vertices.get(v1).put(v2, rotulo);
		vertices.get(v2).put(v1, rotulo);
	}

	/**
	 * Verifica se dois vertices estao conectados.
	 *
	 * @param v1			primeiro vertice para checagem.
	 * @param v2			segundo vertice para checagem.
	 * @throws Exception	se o vertice v1 ou v2 for nulo.
	 * @throws Exception	se v1 ou v2 nao pertencer ao grafo.
	 * @return 				TRUE caso v1 esteja conectado a v2.
	 */
	public boolean estaConectado(Vertice v1, Vertice v2) throws Exception {
		if(v1 == null || v2 == null)
			throw new Exception("Vertice v1 ou v2 passados eh nulo.");
		if(!vertices.containsKey(v1) || !vertices.containsKey(v2))
			throw new Exception("Vertice v1 ou v2 não pertence ao grafo.");
		
		return vertices.get(v1).keySet().contains(v2);
	}

	/**
	 * Retorna os vertices adjacentes ao vertice v.
	 *
	 * @param v				vertice que desejasse pegar os adjacentes.
	 * @throws Exception	se o vertice v for nulo.
	 * @throws Exception	se o vertice v nao fizer parte deste grafo.
	 * @return				um conjunto contendo os vertices adjacentes
	 * 						ao vertice v.
	 */
	public Set<Vertice> adjacentes(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice v passado eh nulo.");
		if(!vertices.containsKey(v))
			throw new Exception("Vertice v não faz parte deste grafo.");
		
		return new HashSet<>(vertices.get(v).keySet());
	}

	/**
	 * Desconecta dois vertices.
	 *
	 * @param v1			primeiro vertice da ligacao a ser desconectado.
	 * @param v2			segundo vertice da ligacao a ser desconectado.
	 * @throws Exception	se o vertice v1 ou v2 for nulo.
	 * @throws Exception	{@link #estaConectado(v1,v2)} lança exceptions.
	 * @throws Exception	se o vertice v1 nao esta conectado ao v2.
	 */
	public void desconecta(Vertice v1, Vertice v2) throws Exception {
		if(v1 == null || v2 == null)
			throw new Exception("Vertice v1 ou v2 passados eh nulo.");
		if(!estaConectado(v1, v2))
			throw new Exception("Vertice v1 nao esta conectado ao vertice v2.");

		vertices.get(v1).remove(v2);
		vertices.get(v2).remove(v1);
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
	 * @param v				vertice que desejasse saber o seu grau.
	 * @throws Exception	se o vertice v for nulo.
	 * @throws Exception	se o vertice v nao pertencer ao grafo.
	 * @return				a quantidade de ligacoes que o vertice v possui.
	 */
	public int grau(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice v passado eh nulo.");
		if(!vertices.containsKey(v))
			throw new Exception("Vertice v nao pertence ao grafo.");

		return vertices.get(v).size(); 
	}

	/**
	 * Verifica se o grafo eh regular.
	 * 
	 * @throws Exception	{@link #grau(v)} lanca exceptions.
	 * @return				TRUE caso o grafo seja regular.
	 */
	public boolean isRegular() throws Exception {
		if(vertices.isEmpty())
			return false; 
		
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
	 * @throws Exception	{@link #grau(v)} lanca exceptions.
	 * @return				TRUE caso o grafo seja completo.
	 */
	public boolean isCompleto() throws Exception {
		if(vertices.isEmpty())
			return false;

		int g = ordem()-1;
		for(Vertice v : vertices.keySet())
			if(grau(v) != g)
				return false;

		return true;
	}

	/**
	 * Calcula o fecho transitivo a partir de um vertice.
	 *
	 * @param v				vertice inicial da busca.
	 * @throws Exception	se o vertice passado seja nulo.
	 * @throws Exception	se v nao pertencer ao grafo.
	 * @return				um conjunto contendo o fecho transitivo apartir
	 * 						de um vertice dado.
	 */
	public Set<Vertice> fechoTransitivo(Vertice v) throws Exception {
		if(v == null)
			throw new Exception("Vertice v passado eh nulo.");
		if(!vertices.containsKey(v))
			throw new Exception("Vertice v não pertence ao grafo.");
		
		Set<Vertice> visitados = new HashSet<Vertice>();
		fechoTransitivo(v, visitados);
		
		return visitados;
	}

	/**
	 * Calcula o fecho transitivo a partir de um vertice.
	 *
	 * @param v				vertice inicial da busca.
	 * @param visitados		conjunto com os vertices que ja foram visitados.
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
	 * @throws Exception	{@link #fechoTransitivo(v)} lanca exceptions.
	 * @return 				TRUE caso o grafo seja conexo.
	 */
	public boolean isConexo() throws Exception {
		if(vertices.isEmpty())
			return false; 

		return fechoTransitivo(umVertice()).size() == vertices.size();
	}
	
	/**
	 * Verifica se o grafo eh uma arvore.
	 * 
	 * @throws Exception	{@link #fechoTransitivo(v)}, usado na funcao {@link #isConexo()}, 
	 * 						lanca exceptions.
	 * @return				TRUE caso o grafo seja uma arvore.
	 */
	public boolean isArvore() throws Exception {
		if(vertices.isEmpty())
			return false;
		
		int nArestas = sumGraus() / 2;
		return isConexo() && nArestas == (vertices.size()-1);
	}
	
	/**
	 * Soma os graus dos vertices do grafo.
	 * 
	 * @return		a soma dos graus dos vertices.
	 */
	private int sumGraus(){
		int value = 0;
		for(Vertice v : vertices.keySet())
			value += vertices.get(v).size();
		
		return value;
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
					grafo += "["+ v +", "+ vi +"], ";
		
		if(!grafo.endsWith("{"))
			grafo = grafo.substring(0, grafo.length()-2);
		
		grafo += "}";
		return grafo;
	}
}