package model.distribuitions;

import java.util.Random;

/**
 * Classe representando uma distribuição uniforme.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Uniform extends Distribution {

    private double a, b;
    
    /**
     * Construtor da classe Uniform.
     * 
     * @param a		Valor inicial da distribuição.
     * @param b		Valor final da distribuição.
     */
    public Uniform(double a, double b) {
        this.a = a;
        this.b = b;
    }
    
    /**
	 * Retorna um valor aleatório usando a fórmula da distribuição
	 *  uniforme.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor aleatório uniformemente distribuído.
	 */
    @Override
    public double nextValue(Random r) {
        return a + (b - a) * r.nextDouble();
    }

}
