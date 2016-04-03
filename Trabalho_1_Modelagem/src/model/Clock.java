package model;

/**
 * Classe que representa o relógio do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Clock {
	
	/**
	 * Tempo atual do relógio.
	 */
    private double time;
    
    /**
     * Construtor da classe Clock.
     * 
     * @param time		Tempo inicial do relógio.
     */
    public Clock(double time) {
        this.time = time;
    }
    
    /**
     * Função que retorna o tempo atual do relógio.
     * 
     * @return		Tempo atual do relógio.
     */
    public double getTime() {
        return this.time;
    }
    
    /**
     * Função para mudar o tempo atual do relógio.
     * 
     * @param time		Novo tempo atual para o relógio.
     */
    public void setTime(double time) {
        this.time = time;
    }
    
    /**
     * Função que reseta o tempo do relógio para zero.
     */
    public void reset() {
        this.time = 0;
    }
}
