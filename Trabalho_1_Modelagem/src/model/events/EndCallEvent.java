package model.events;

import controller.Simulator;

public class EndCallEvent extends Event {

    public EndCallEvent(boolean isCell1, int id, double startTime) {
        super(isCell1, id, startTime);
        // TODO Auto-generated constructor stub
    }
    
    public String getEventType(){
		return "Fim de chamada";
	}

    @Override
    public void processEvent(Simulator sim) {
        // Célula 1 ou 2?
        int cell;
        if (isCell1) {
            cell = 0; // 0 é o id da Célula 1
        } else {
            cell = 1; // 1 é o id da Célula 2
        }

        // Libera um canal da Célula em questão
        sim.getCells()[cell].freeChannel();        
    }

}
