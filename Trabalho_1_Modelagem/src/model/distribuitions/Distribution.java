package model.distribuitions;

import java.util.Random;

/**
 * Classe abstrata representando uma distribuição.
 * 
 * @author Gilney e Eduardo
 *
 */
public abstract class Distribution {
	
	/**
	 * Retorna uma distribuição baseada no parâmetro 'distr' passado.
	 * 
	 * @param distr		Tipo de distribuição que se quer.
	 * @param values	Valores para serem usados na distribuição.
	 * 
	 * @return			Uma distribuição dependendo do parâmetro 'distr'.
	 */
	public static Distribution getDistribution(char distr, double[] values){
		Distribution d = null;
		switch(distr){
		case 'n':
			d = new Normal(values[0], values[1]);
			break;
		case 'u':
			d = new Uniform(values[0], values[1]);
			break; 
		case 'e':
			d = new Exponential(values[0]);
			break;
		case 't':
			d = new Triangular(values[0], values[1], values[2]);
			break;
		case 'c':
			d = new Constant(values[0]);
			break;
		case 'x':
			break;
		}
		return d;
	}
	
	/**
	 * Função abstrata para ser sobrescrita pelas classes filhas.
	 * Deve retornar um valor aleatório usando a fórmula da distribuição
	 *  filha.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor aleatório dependendo do tipo de distribuição.
	 */
	public abstract double nextValue(Random r);
}
