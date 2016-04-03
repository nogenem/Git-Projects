package model.events;

import controller.Simulator;

/**
 * Classe representando um evento de um usuário
 *  trocando de célula.
 *  
 * @author Gilney e Eduardo
 *
 */
public class ChangingCellEvent extends Event {
	
	/**
	 * Duração restante da chamada.
	 */
    public double duration;
    
    /**
     * Construtor da classe ChangingCellEvent.
     * 
     * @param isCell1		TRUE se o evento foi gerado na célula 1,
     * 						FALSE caso contrario.
     * @param id			Id do evento.
     * @param startTime		Tempo de inicio do evento.
     * @param duration		Duração restante da chamada.
     */
    public ChangingCellEvent(boolean isCell1, int id, double startTime, double duration) {
        super(isCell1, id, startTime);
        this.duration = duration;
    }
    
    /**
     * Retorna o tipo deste evento.
     * 
     * @return		Rertorna o tipo deste evento.
     */
    public String getEventType(){
		return "Troca de canal";
	}
    
    /**
     * Executa as funções que este evento deve realizar.
     * 
     * @param sim		O simulador deste sistema.
     */
    @Override
    public void processEvent(Simulator sim) {
        // Célula 1 ou 2?
        int cell;
        if (isCell1) {
            cell = 0; // 0 é o id da Célula 1
        } else {
            cell = 1; // 1 é o id da Célula 2
        }

        // Há canais livres na Célula em questão?
        if (sim.getCells()[cell].takeChannel()) {
            // Agenda-se o fim da chamada 
            sim.addEvent(new EndCallEvent(isCell1, id, startTime + duration));
        }
        // Obs: Cada Célula contabiliza suas próprias estatísticas
    }

}
