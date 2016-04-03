package model.events;

import controller.Simulator;

/**
 * Classe abstrata representando um evento do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public abstract class Event {
	
	/**
	 * Contador de eventos da simulação.
	 */
    private static int EVENT_COUNTER = 0;
    /**
     * Tempo de inicio do evento.
     */
    protected double startTime;
    /**
     * Id do evento.
     */
    protected int id;
    /**
     * Evento foi gerado na célula 1?
     */
    protected boolean isCell1; 
    
    /**
     * Construtor da classe Event
     * 
     * @param startTime		Tempo de inicio do evento.
     */
    public Event(double startTime){
    	this.startTime = startTime;
    	this.id = EVENT_COUNTER;
        EVENT_COUNTER++;
    }
    
    /**
     * Construtor da classe Event
     * 
     * @param isCell1		TRUE se o evento foi gerado na célula 1,
     * 						FALSE caso contrario.
     * @param startTime		Tempo de inicio do evento.
     */
    public Event(boolean isCell1, double startTime) {
        this.startTime = startTime;
        this.isCell1 = isCell1;
        this.id = EVENT_COUNTER;
        EVENT_COUNTER++;
    }
    
    /**
     * Construtor da classe Event
     * 
     * @param isCell1		TRUE se o evento foi gerado na célula 1,
     * 						FALSE caso contrario.
     * @param id			Id do evento.
     * @param startTime		Tempo de inicio do evento.
     */
    public Event(boolean isCell1, int id, double startTime) {
        this.isCell1 = isCell1;
        this.id = id;
        this.startTime = startTime;
    }
    
    /**
     * Reseta o contador de eventos do sistema.
     */
    public static void resetEventCounter(){
    	EVENT_COUNTER = 0;
    }
    
    /**
     * 
     * @return		TRUE se o evento foi gerado na célula 1,
     * 				FALSE caso contrario.
     */
    public boolean isCell1(){
        return isCell1;
    }
    
    /**
     * 
     * @return		Id do evento.
     */
    public int getId(){
    	return id;
    }
    
    /**
     * 
     * @return		Tempo de inicio do evento.
     */
    public double getStartTime() {
        return this.startTime;
    }
    
    /**
     * Função abstrata para ser sobrescrita pelas classes filhas.
     * Deve retornar o tipo do evento filho.
     * 
     * @return		Tipo do evento.
     */
    public abstract String getEventType();
    
    /**
     * Função abstrata para ser sobrescrita pelas classes filhas.
     * Deve executar as funções de um tipo de evento.
     * 
     * @param sim		O simulador do sistema.
     */
    public abstract void processEvent(Simulator sim);
}
