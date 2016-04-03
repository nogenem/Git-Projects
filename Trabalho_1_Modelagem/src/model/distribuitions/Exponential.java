package model.distribuitions;

import java.util.Random;

/**
 * Classe representando uma distribuição exponencial.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Exponential extends Distribution {
	
	/**
	 * Média para ser usada no calculo do valor
	 *  aleatório desta distribuição.
	 */
	private double lambda;
	
	/**
	 * Construtor da classe Exponential.
	 * 
	 * @param lambda		Média para ser usada nesta
	 * 						 distribuição.
	 */
	public Exponential(double lambda) {
		this.lambda = lambda;
	}
	
	/**
	 * Retorna um valor aleatório usando a fórmula da distribuição
	 *  exponencial.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor aleatório exponencialmente distribuído.
	 */
	@Override
	public double nextValue(Random r) {
		return -lambda * Math.log(1 - r.nextDouble());
	}

}
