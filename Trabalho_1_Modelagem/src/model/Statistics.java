package model;

/**
 * Classe guardando as variáveis de estatísticas do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Statistics {

    public double tempo, ocupacao_min_C1, ocupacao_med_C1,
            ocupacao_max_C1, ocupacao_min_C2, ocupacao_med_C2,
            ocupacao_max_C2, chamadasNoSistema_med, duracao_min,
            duracao_med, duracao_max;
    public int chamadasNoSistema_min, chamadasNoSistema_max,
            chamadasTotais, chamadasCompletadas, chamadasPerdidas_C1,
            chamadasPerdidas_C2, chamadasForaDeArea_C1, chamadasForaDeArea_C2;
    
    /**
     * Construtor da classe Statistics.
     * 
     * @param doubles		Valores doubles de estatísticas da simulação.
     * @param ints			Valores ints de estatísticas da simulação.
     */
    public Statistics(double[] doubles, int[] ints) {
        System.out.println("Nova estatística!");
        if (doubles.length >= 10 && ints.length >= 10) {
            // Doubles
            tempo = doubles[0];
            ocupacao_min_C1 = doubles[1];
            ocupacao_med_C1 = doubles[2];
            ocupacao_max_C1 = doubles[3];
            ocupacao_min_C2 = doubles[4];
            ocupacao_med_C2 = doubles[5];
            ocupacao_max_C2 = doubles[6];
            duracao_min = doubles[7];
            duracao_med = doubles[8];
            duracao_max = doubles[9];
            chamadasNoSistema_med = doubles[10];
            
            // Ints
            chamadasNoSistema_min = ints[0];
            chamadasNoSistema_max = ints[1];
            chamadasTotais = ints[2];
            chamadasCompletadas = ints[3];
            chamadasPerdidas_C1 = ints[4];
            chamadasPerdidas_C2 = ints[5];
            chamadasForaDeArea_C1 = ints[6];
            chamadasForaDeArea_C2 = ints[7];
        }
    }
}
