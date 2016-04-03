package controller;

import java.util.ArrayList;

import model.Cell;
import model.Clock;
import model.ListOfEvents;
import model.RVGenerator;
import model.RaffleOfCalls;
import model.Statistics;
import model.distribuitions.Distribution;
import model.events.EndSimulationEvent;
import model.events.Event;
import model.events.StartCallEvent;
import view.NewJFrame;

/**
 * Classe responsavel por simular um sistema especifico.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Simulator extends Thread {
	
	/**
	 * Interface grafica do programa.
	 */
	private NewJFrame UI;
	/**
	 * Coleção de estatísticas geradas ao longo da execução
	 *  de uma simulação.
	 */
    private ArrayList<Statistics> statistics;
    /**
     * Tempo limite da somulação.
     */
    private int simulationTime;
    /**
     * Relógio da simulação.
     */
    private Clock clock;
    /**
     * Tempo, em milissegundos, entre execução dos eventos da simulação.
     */
    private int tick;
    /**
     * Coleção com as células da simulação.
     */
    private Cell[] cells;
    /**
     * Gerador de variáveis aleatórias da simulação.
     */
    private RVGenerator generator;
    /**
     * Gerador de chamadas da simulação.
     */
    private RaffleOfCalls raffle;
    /**
     * Coleção contendo os proximos eventos a serem executados
     *  na simulação.
     */
    private ListOfEvents events;
    /**
     * Variavel que diz se a simulação esta pausada ou não.
     */
    private boolean isPaused;
    /**
     * Variavel que diz se a simulação foi cancelada ou não.
     */
    private boolean destroyed;
    
    //Variaveis referentes as estatisticas da simulação.
    private int callCounter, callsCounter;
    private double durationMin, durationMed, durationMax, callsOnSystem_Med;
    private int callsOnSystem, callsOnSystem_Min , callsOnSystem_Max;
    
    /**
     * Construtor da classe Simulator.
     * 
     * @param ui					Interface do programa.
     * @param simulationTime		Tempo de simulação escolhido pelo usuario.
     * @param tick					Velocidade de execução da simulação.
     * @param channelsC1			Quantidade de canais na célula 1.
     * @param channelsC2			Quantidade de canais na célula 2.
     * @param seed					Semente escolhida pela usuario.
     * @param TSDistr				Distribuição da duração das chamadas.
     * @param TECDistrC1			Distribuição do tempo entre chamadas em C1.
     * @param TECDistrC2			Distribuição do tempo entre chamadas em C2.
     * @param callsPercentages		Porcentagens dos tipos de chamadas, C1C1, C1C2...
     */
    public Simulator(NewJFrame ui, int simulationTime, int tick, int channelsC1, int channelsC2,
            long seed, Distribution TSDistr, Distribution TECDistrC1,
            Distribution TECDistrC2, double[] callsPercentages) {
    	
    	Event.resetEventCounter();
    	
    	this.UI = ui;
    	this.simulationTime = simulationTime;
        this.clock = new Clock(0);
        this.tick = tick;
        this.cells = new Cell[]{new Cell("C1", channelsC1),
            new Cell("C2", channelsC2)};

        generator = new RVGenerator(seed, TSDistr, TECDistrC1, TECDistrC2);
        raffle = new RaffleOfCalls(generator, callsPercentages);
        
        events = new ListOfEvents();
        isPaused = false;
        destroyed = false;
        statistics = new ArrayList<>();

        durationMin = Integer.MAX_VALUE;
        durationMed = -1;
        durationMax = Integer.MIN_VALUE;
        
        callsOnSystem_Min = Integer.MAX_VALUE;
        callsOnSystem_Med = 0;
        callsOnSystem_Max = Integer.MIN_VALUE;
    }
    
    /**
     * Função responsavel por executar a simulação.
     */
    public void run() {
    	System.out.println("Inicio da simulação.");
    	
    	// Fim da simulação
    	addEvent(new EndSimulationEvent(simulationTime));
    	
        // Primeira chegada na Célula 1
        StartCallEvent eventC1 = new StartCallEvent(true, generator.nextTEC1());
        addEvent(eventC1);

        // Primeira chegada na Célula 2
        StartCallEvent eventC2 = new StartCallEvent(false, generator.nextTEC2());
        addEvent(eventC2);

        try {
            Event current_event = null;
            while (!destroyed) {
                checkPaused();

                // Executa a simulação
                if (events.size() > 0) {
                    current_event = events.get();
                    clock.setTime(current_event.getStartTime());
                    
                    current_event.processEvent(this);
                    this.generateStatistics();
                    
                    if (current_event instanceof EndSimulationEvent)
                    	break;
                    
                    Thread.sleep(tick);
                } else {
                    System.out.println("Lista de Eventos Vazia!");
                }

            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Fim da simulação.");
        if(!destroyed)
        	UI.endOfSimulation();
    }
    
    /**
     * Função que da play na simulação.
     */
    public synchronized void play() {
        isPaused = false;
        notifyAll();
        System.out.println("Simulação reiniciada.");
    }
    
    /**
     * Função que checa se a simulação esta pausada.
     * 
     * @throws InterruptedException
     */
    private synchronized void checkPaused()
            throws InterruptedException {
        while (isPaused) {
            wait();
        }
    }
    
    /**
     * Função que pausa a simulação.
     */
    public synchronized void pause() {
        isPaused = true;
        System.out.println("Simulação pausada.");
    }
    
    /**
     * Função que termina a simulação.
     */
    public void destroy() {
        destroyed = true;
    }
    
    /**
     * Função para mudar a velocidade de execução
     *  da simulação.
     * 
     * @param tick		Novo valor para 'tick'.
     */
    public void setTick(int tick) {
        this.tick = tick;
    }
    
    /**
     * Função que retorna o gerador de variaveis aleatórias 
     *  usado na simulação.
     * 
     * @return		O gerador de variaveis aleatórias da simulação.
     */
    public RVGenerator getGenerator() {
        return generator;
    }
    
    /**
     * Função que retorna o sorteador de chamadas
     *  usado na simulação.
     *  
     * @return		O sorteador de chamadas da simulação.
     */
    public RaffleOfCalls getRaffle() {
        return raffle;
    }
    
    /**
     * Função que retorna a lista das células
     *  da simulação.
     * 
     * @return		A lista das células da simulação.
     */
    public Cell[] getCells() {
        return cells;
    }
    
    /**
     * Função que retorna a lista de estatísticas geradas
     *  durante a simulação.
     * 
     * @return		A lista de estatísticas da simulação.
     */
    public ArrayList<Statistics> getStatistics(){
    	return statistics;
    }
    
    /**
     * Função que adiciona um novo evento a lista de
     *  eventos da simulação e manda a UI adiciona-lo
     *  também em sua JList.
     * 
     * @param event		Novo evento a ser adicionado.
     */
    public void addEvent(Event event) {
        events.add(event);
        UI.addEvent(event);
    }
    
    /**
     * 
     * @param duration
     */
    public void callDurationStatistics(double duration) {
        // Mínimo ou máximo?
        if (duration < durationMin) 
            durationMin = duration;
        if (duration > durationMax) 
            durationMax = duration;

        // Calcula nova média
        durationMed = durationMed * callCounter + duration;
        callCounter++;
        durationMed = durationMed / callCounter;
    }
    
    /**
     * 
     */
    public void callsOnSystemStatistics() {
        // Quantidade de chamadas atualmente no sistema
        callsOnSystem = cells[0].getBusyChannels() + cells[1].getBusyChannels();

        // Mínimo ou Máximo?
        if (callsOnSystem < callsOnSystem_Min) {
            callsOnSystem_Min = callsOnSystem;
        }
        if (callsOnSystem > callsOnSystem_Max) {
            callsOnSystem_Max = callsOnSystem;
        }
        // Calcula nova média
        callsOnSystem_Med = callsOnSystem_Med * callsCounter + callsOnSystem;
        callsCounter++;
        callsOnSystem_Med = callsOnSystem_Med / callsCounter;
    }
    
    /**
     * 
     * @param isCell1
     */
    public void callOutOfArea(boolean isCell1) {
        if (isCell1) {
            this.cells[0].outOfArea();
        } else {
            this.cells[1].outOfArea();
        }
    }
    
    /**
     * Função que gera as estatísticas da simulação 
     *  e manda a UI carregar a ultima estatística
     *  gerada. 
     */
    public void generateStatistics() {

        System.out.println("Generate Statistic!");

        // Atualiza Taxas de Ocupação das Células
        cells[0].updateOcupation();
        cells[1].updateOcupation();

        // Atualiza Número de Chamadas no Sistema
        this.callsOnSystemStatistics();

        // Coleta dados das Células
        double[] c1Ocupation = cells[0].getOcupation();
        int[] c1CallCount = cells[0].getCallCounts();
        double[] c2Ocupation = cells[1].getOcupation();
        int[] c2CallCount = cells[1].getCallCounts();

        // Prepara arranjo de doubles da Estatística
        double[] doubles = new double[11];
        
        // Tempo de simulação
        doubles[0] = clock.getTime();
        
        // Taxa de Ocupação
        doubles[1] = c1Ocupation[0] * 100;
        doubles[2] = c1Ocupation[1] * 100;
        doubles[3] = c1Ocupation[2] * 100;
        doubles[4] = c2Ocupation[0] * 100;
        doubles[5] = c2Ocupation[1] * 100;
        doubles[6] = c2Ocupation[2] * 100;

        // Duração de Chamadas
        doubles[7] = this.durationMin;
        doubles[8] = this.durationMed;
        doubles[9] = this.durationMax;
        
        // Prepara arranjo de inteiro da Estatística
        int[] ints = new int[10];

        // Número de Chamadas no Sistema
        ints[0] = this.callsOnSystem_Min;
        doubles[10] = this.callsOnSystem_Med;
        ints[1] = this.callsOnSystem_Max;

        // Chamadas Totais : Completas + Perdidas + Fora de Área
        ints[2] = c1CallCount[0] + c1CallCount[1] + c1CallCount[2];
        ints[2] += c2CallCount[0] + c2CallCount[1] + c2CallCount[2];

        // Chamadas Completadas : C1 + C2
        ints[3] = c1CallCount[0] + c2CallCount[0];

        // Chamadas Perdidas
        ints[4] = c1CallCount[1];
        ints[5] = c2CallCount[1];

        // Chamadas Fora de Área
        ints[6] = c1CallCount[2];
        ints[7] = c2CallCount[2];

        // Cria e adiciona uma nova estatística
        statistics.add(new Statistics(doubles, ints));
        
        UI.loadLatestStatistics();
    }
}
