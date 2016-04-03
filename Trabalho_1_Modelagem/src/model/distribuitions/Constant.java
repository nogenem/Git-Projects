package model.distribuitions;

import java.util.Random;

/**
 * Classe representando a distribuição constante.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Constant extends Distribution {
	
	private double value;
	
	/**
	 * Construtor da classe Constant.
	 * 
	 * @param value		Valor constante para esta distribuição.
	 */
	public Constant(double value){
		this.value = value;
	}
	
	/**
	 * Retorna o valor constante passado inicialmente.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor constante.
	 */
	@Override
	public double nextValue(Random r) {
		return value;
	}

}
