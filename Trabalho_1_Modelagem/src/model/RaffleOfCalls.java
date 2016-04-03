package model;

import model.calls.Call;
import model.calls.CallType;

/**
 * Classe que faz os sorteios das chamadas do sistema
 *  usando as porcentagens definidas pelo usuário.
 *  
 * @author Gilney e Eduardo
 *
 */
public class RaffleOfCalls {

    /*
     *   0  |   1  |   2  |   3  |   4  |   5
     * C1C1 | C1C2 | C1FA | C2C2 | C2C1 | C2FA
     */
    private double[] percentages;
    private RVGenerator generator;
    
    /**
     * Construtor da classe RaffleOfCalls.
     * 
     * @param generator			Gerador de variaveis aleatórias da simulação.
     * @param percentages		Porcentagens dos tipos de chamadas, C1C1, C1C2...
     */
    public RaffleOfCalls(RVGenerator generator, double[] percentages) {
        // Célula 1 : percentages[0] & percentages[1] 
        // Célula 2 : percentages[2] & percentages[3]
        this.percentages = percentages;
        this.generator = generator;
    }
    
    /**
     * Função que gera uma chamada originada na célula 1.
     * 
     * @return		Nova chamada originada na célula 1.
     */
    public Call generateCallforC1() {
        double R = generator.nextRand();
        Call call = null;
        if (R < percentages[0]) {
            call = new Call(generator.nextTS(), CallType.C1C1);
        } else if (R < percentages[1]) {
            call = new Call(generator.nextTS(), CallType.C1C2);
        } else {
            call = new Call(generator.nextTS(), CallType.C1FA);
        }               
        return call;
    }
    
    /**
     * Função que gera uma chamada originada na célula 2.
     * 
     * @return		Nova chamada originada na célula 2.
     */
    public Call generateCallforC2() {
        double R = generator.nextRand();
        Call call = null;
        if (R < percentages[2]) {
            call = new Call(generator.nextTS(), CallType.C2C2);
        } else if (R < percentages[3]) {
            call = new Call(generator.nextTS(), CallType.C2C1);
        } else {
            call = new Call(generator.nextTS(), CallType.C2FA);
        }
        return call;
    }

}
