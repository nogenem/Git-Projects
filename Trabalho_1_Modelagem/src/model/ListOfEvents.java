package model;

import java.util.ArrayList;
import model.events.Event;

/**
 * Lista dos próximos eventos do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public class ListOfEvents {
	
	/**
	 * Lista contendo os proximos eventos
	 *  da simulação.
	 */
    private ArrayList<Event> list;
    
    /**
     * Construtor da classe ListOfEvents
     */
    public ListOfEvents() {
        list = new ArrayList<Event>();
    }
    
    /**
     * Função que adiciona um novo evento a lista,
     *  os eventos são adicionados em ordem de 
     *  'startTime'.
     * 
     * @param event		Novo evento a ser adicionado.
     */
    public void add(Event event) {
        // Compara-se o tempo inicial do evento com os da lista
        for (int i = 0; i < list.size(); i++) {
            // Se tempo inicial do evento menor que o da lista
            if (event.getStartTime() < list.get(i).getStartTime()) {
                // Adiciona-se o novo evento na posição do mesmo
                list.add(i, event);
                return;
            }
        }
        // Se o tempo do evento for o maior, adiciona-se no fim
        list.add(event);
    }
    
    /**
     * Função que retorna o próximo evento eminente da 
     *  lista de eventos.
     * 
     * @return		Próximo evento.
     */
    public Event get() {
        // Se a lista estiver vazia, retorna null
        if (list.isEmpty()) {
            return null;
        }
        // Caso contrário, retorna o primeiro elemento
        Event event = list.get(0);
        list.remove(0);
        return event;
    }
    
    /**
     * Função que retorna a quantidade de eventos
     *  na lista de eventos.
     * 
     * @return		Quantidade de eventos da lista.
     */
    public int size(){
        return list.size();
    }
}
