package model.distribuitions;

import java.util.Random;

/**
 * Classe representando uma distribuição de poisson.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Poisson extends Distribution {
	
	/**
	 * Média para ser usada no calculo do valor
	 *  aleatório desta distribuição.
	 */
	private double lambda;
	
	/**
	 * Construtor da classe Poisson.
	 * 
	 * @param lambda		Média para ser usada nesta
	 * 						 distribuição.
	 */
	public Poisson(double lambda) {
		this.lambda = lambda;
	}
	
	/**
	 * Retorna um valor aleatório usando a fórmula da distribuição
	 *  de Poisson.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor aleatório usando.
	 */
	@Override
	public double nextValue(Random r) {
		int n = -1;
		double p = 1;
		
		do{
			n++;
			p *= r.nextDouble();
		}while(p >= Math.pow(Math.E, -lambda));
		
		return n;
	}

}
