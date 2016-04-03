package model.calls;

/**
 * Classe que representa uma chamada no sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public class Call {
	
	/**
	 * Duração da chamada.
	 */
    private double duration;
    /**
     * Tipo da chamada.
     */
    private CallType type;
    
    /**
     * Construtor da classe Call.
     * 
     * @param duration		Duração da chamada.
     * @param type			Tipo da chamada.
     */
    public Call(double duration, CallType type) {
        this.duration = duration;
        this.type = type;
    }
    
    /**
     * 
     * @return		Duração da chamada.
     */
    public double getDuration() {
        return duration;
    }
    
    /**
     * 
     * @return		Tipo da chamada.
     */
    public CallType getType() {
        return type;
    }

}
