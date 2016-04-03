package model.events;

import controller.Simulator;
import model.calls.Call;

/**
 * Classe representando um evento de inicio de uma
 *  chamada no sistema.
 *  
 * @author Gilney e Eduardo
 *
 */
public class StartCallEvent extends Event {
	
	/**
	 * Construtor da classe StartCallEvent.
	 * 
	 * @param isCell1		TRUE se o evento foi gerado na célula 1,
     * 						FALSE caso contrario.
	 * @param startTime		Tempo de inicio do evento. 
	 */
	public StartCallEvent(boolean isCell1, double startTime) {
        super(isCell1, startTime);
        // TODO Auto-generated constructor stub
    }
	
	/**
     * Retorna o tipo deste evento.
     * 
     * @return		Rertorna o tipo deste evento.
     */
	public String getEventType(){
		return "Inicio de chamada";
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
        Call call = null;
        if (isCell1) {
            call = sim.getRaffle().generateCallforC1();
            cell = 0; // 0 é o id da Célula 1
        } else {
            call = sim.getRaffle().generateCallforC2();
            cell = 1; // 1 é o id da Célula 2
        }
        // Há canais livres na Célula em questão?
        if (sim.getCells()[cell].takeChannel()) {
            double halfDuration = call.getDuration() / 2;
            // Qual o tipo de chamada?
            switch (call.getType()) {
                case C1C1:
                case C2C2:
                    // Agenda-se o fim da chamada 
                    sim.addEvent(new EndCallEvent(isCell1, id, startTime + call.getDuration()));
                    // Contabiliza estatísticas de duração da chamada
                    sim.callDurationStatistics(call.getDuration());
                    break;
                case C1C2:
                case C2C1:
                    double newStartTime = startTime + halfDuration;
                    // Agenda-se o fim da chamada...
                    sim.addEvent(new EndCallEvent(isCell1, id, newStartTime));
                    // ... e chegada na outra Célula 
                    sim.addEvent(new ChangingCellEvent(!isCell1, id, newStartTime, halfDuration));
                    break;
                case C1FA:
                case C2FA:
                    // Agenda-se o fim antecipado da chamada
                    sim.addEvent(new EndCallEvent(isCell1, id, startTime + (call.getDuration() / 2)));
                    // Contabiliza estatísticas de duração da chamada
                    sim.callDurationStatistics(halfDuration);
                    // Contabiliza chamada fora de área
                    sim.callOutOfArea(isCell1);
                    break;
            }
        }
        // Obs: Cada Célula contabiliza suas próprias estatísticas

        // Calcula-se o tempo até a próxima chegada na mesma Célula
        double extraTime;
        if (isCell1) {
            extraTime = sim.getGenerator().nextTEC1();
        } else {
            extraTime = sim.getGenerator().nextTEC2();
        }

        // Agenda a próxima chegada
        sim.addEvent(new StartCallEvent(isCell1, startTime + extraTime));
    }
}
