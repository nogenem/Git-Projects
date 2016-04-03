package model.events;

import controller.Simulator;

/**
 * Classe representando o evento do final da simulação.
 *  
 * @author Gilney e Eduardo
 *
 */
public class EndSimulationEvent extends Event {
	
	/**
	 * Construtor da classe EndSimulationEvent.
	 * 
	 * @param startTime		Tempo de inicio do evento.
	 */
	public EndSimulationEvent(double startTime) {
		super(startTime);
	}
	
	/**
     * Retorna o tipo deste evento.
     * 
     * @return		Rertorna o tipo deste evento.
     */
	@Override
	public String getEventType() {
		return "Fim da simulação";
	}
	
	/**
     * Executa as funções que este evento deve realizar.
     * 
     * @param sim		O simulador deste sistema.
     */
	@Override
	public void processEvent(Simulator sim) {
		
	}

}
