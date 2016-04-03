package model.distribuitions;

import java.util.Random;

/**
 * Classe representando uma distribuição normal.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Normal extends Distribution {
	
	/**
	 * Média para ser usada no calculo do valor
	 *  aleatório desta distribuição.
	 */
    private double mean;
    /**
	 * Desvio padrão para ser usado no calculo do valor
	 *  aleatório desta distribuição.
	 */
    private double dv;
    private double nextValue;
    private boolean hasNextValue;
    
    /**
     * Construtor da classe Normal.
     * 
     * @param mean		Média para ser usada nesta distribuição.
     * @param dv		Desvio padrão para ser usado nesta distribuição.
     */
    public Normal(double mean, double dv) {
        this.mean = mean;
        this.dv = dv;

        this.nextValue = 0;
        this.hasNextValue = false;        
    }
    
    /**
	 * Retorna um valor aleatório usando a fórmula da distribuição
	 *  normal.
	 * 
	 * @param r		Gerador de numeros aleatórios entre 0 e 1.
	 * 
	 * @return		Um valor aleatório normalmente distribuído.
	 */
    @Override
    public double nextValue(Random r) {
        if (hasNextValue) {
            hasNextValue = false;
            return nextValue;
        } else {
            double R1 = r.nextDouble(), R2 = r.nextDouble();

            double Z1 = Math.sqrt(-2 * Math.log(R1)) * Math.cos(Math.toRadians(2 * Math.PI * R2));
            double Z2 = Math.sqrt(-2 * Math.log(R1)) * Math.sin(Math.toRadians(2 * Math.PI * R2));

            this.nextValue = mean + dv * Z2;
            this.hasNextValue = true;

            return mean + dv * Z1;
        }
    }

}
