package model.distribuitions;

import java.util.Random;

/**
 * Classe representando uma distribuição triangular.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Triangular extends Distribution {

    private double a, b, c;
    
    /**
     * Construtor da classe Triangular.
     * 
     * @param a		Valor inicial da distribuição.
     * @param b		Valor mediano da distribuição.
     * @param c		Valor superior da distribuição.
     */
    public Triangular(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    /**
	 * Retorna um valor aleatório usando a fórmula da distribuição
	 *  triangular.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor aleatório triangularmente distribuído.
	 */
    @Override
    public double nextValue(Random r) {
        double R = r.nextDouble();
        if (R <= (b - a) / (c - a)) {
            return a + Math.sqrt(R * (b - a) * (c - a));
        } else {
            return c - Math.sqrt(R * (c - b) * (c - a));
        }
    }

}
