package model;

/**
 * Classe que representa uma célula do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Cell {

    private String id;
    private int ocupationCounter, freeChannels, maxChannels,
            completedCalls, missedCalls, outOfAreaCalls;
    private double ocupation_Min, ocupation_Med, ocupation_Max,
            callsOnSystem_Med, callsOnSystem_Max;
    
    /**
     * Construtor da classe Cell.
     * 
     * @param id				Id da célula.
     * @param maxChannels		Quantidade de canais da célula.
     */
    public Cell(String id, int maxChannels) {
        this.id = id;
        this.freeChannels = maxChannels;
        this.maxChannels = maxChannels;
        
        ocupation_Min = Integer.MAX_VALUE;
        ocupation_Med = -1;
        ocupation_Max = Integer.MIN_VALUE;
    }
    
    /**
     * Função que retorna a quantidade de canais
     *  ocupados nesta célula durante o tempo 
     *  atual da simulação.
     * 
     * @return		Numero de canais ocupados atualmente
     * 				 nesta célula
     */
    public int getBusyChannels() {
        return maxChannels - freeChannels;
    }
    
    /**
     * 
     */
    public void updateOcupation() {

        // Ocupação é : 1 - (proporção de canais livres)
        double ocupation = (double)((double)(maxChannels - freeChannels) / maxChannels);

        // Máximo ou Mínimo?
        if (ocupation < ocupation_Min) {
            ocupation_Min = ocupation;
        }
        if (ocupation > ocupation_Max) {
            ocupation_Max = ocupation;
        }
        // Atualiza valor Médio da Ocupação
        if (ocupation_Med < 0) {
            ocupation_Med = ocupation;
        }
        ocupation_Med = ocupationCounter * ocupation_Med + ocupation;
        ocupationCounter++;
        ocupation_Med = ocupation_Med / ocupationCounter;
    }
    
    /**
     * Função que libera um canal nesta célula e
     *  contabiliza o numero de chamadas completadas.
     */
    public void freeChannel() {
        freeChannels++;
        if (freeChannels > maxChannels) {
            freeChannels = maxChannels;
        } else {
            // Contabiliza Chamada Completada
            completedCalls++;
        }
    }
    
    /**
     * Função que tenta ocupar um canal nesta célula
     *  e contabiliza o numero de chamadas perdidas
     *  por falta de canal.
     * 
     * @return		TRUE caso consiga ocupar um canal,
     * 				FALSE caso contrario.
     */
    public boolean takeChannel() {
        freeChannels--;
        if (freeChannels < 0) {
            freeChannels = 0;
            // Não há canais livres, contabiliza Chamada Perdida
            missedCalls++;
            return false;
        }
        return true;
    }
    
    /**
     * Função que aumenta o numero de chamadas
     *  fora de area nesta celula.
     */
    public void outOfArea() {
        outOfAreaCalls++;
    }
    
    /**
     * 
     * @return
     */
    public double[] getOcupation() {
        double[] ocupation = new double[4];
        ocupation[0] = ocupation_Min; // Mínima
        ocupation[1] = ocupation_Med; // Média
        ocupation[2] = ocupation_Max; // Máxima
        return ocupation;
    }
    
    /**
     * 
     * @return
     */
    public int[] getCallCounts() {
        int[] callCount = new int[4];
        callCount[0] = this.completedCalls;
        callCount[1] = this.missedCalls;
        callCount[2] = this.outOfAreaCalls;
        return callCount;
    }
}
