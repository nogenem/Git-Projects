package model;

import java.util.Random;

import model.distribuitions.Distribution;

/**
 * Classe que representa um gerador de variáveis aleatórias
 *  do sistema usando certas distribuições dadas pelo usuário.
 *  
 * @author Gilney e Eduardo
 *
 */
public class RVGenerator {
	
	/**
	 * Distribuição de TS da simulação.
	 */
    private Distribution TSDistr;
    /**
     * Distribuição de TEC da C1 da simulação.
     */
    private Distribution TECDistrC1;
    /**
     * Distribuição de TEC da C2 da simulação.
     */
    private Distribution TECDistrC2;
    
    /**
     * Variável que gera um numero aleatório entre 0 e 1.
     */
    private Random r;
    
    /**
     * Construtor da classe RVGenerator.
     * 
     * @param seed			Seed usada na criação de uma variável aleatória.
     * @param TSDistr		Distribuição de TS.
     * @param TECDistrC1	Distribuição de TEC para C1.
     * @param TECDistrC2	Distribuição de TEC para C2.
     */
    public RVGenerator(long seed, Distribution TSDistr, Distribution TECDistrC1,
            Distribution TECDistrC2) {
        this.TSDistr = TSDistr;
        this.TECDistrC1 = TECDistrC1;
        this.TECDistrC2 = TECDistrC2;
        
        //seed = (Integer.MAX_VALUE / 10) * (seed % 10);
        this.r = new Random(seed);
    }
    
    /**
     * Retorna um valor aleatório usando a distribuição de TS.
     * 
     * @return		Um numero aleatório usando uma distribuição.
     */
    public double nextTS() {
        return TSDistr.nextValue(r);
    }
    
    /**
     * Retorna um valor aleatório usando a distribuição de TEC para C1.
     * 
     * @return		Um numero aleatório usando uma distribuição.
     */
    public double nextTEC1() {
        return TECDistrC1.nextValue(r);
    }
    
    /**
     * Retorna um valor aleatório usando a distribuição de TEC para C2.
     * 
     * @return		Um numero aleatório usando uma distribuição.
     */
    public double nextTEC2() {
        return TECDistrC2.nextValue(r);
    }
    
    /**
     * Retorna um numero aleatório entre 0 e 1.
     * 
     * @return		Um numero aleatório entre 0 e 1.
     */
    public double nextRand() {
        return r.nextDouble();
    }
}
