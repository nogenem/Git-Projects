package model.calls;

/**
 * Enumeração dos tipos de chamadas do sistema.
 * 
 * @author Gilney e Eduardo
 *
 */
public enum CallType {

    C1C1(0), C1C2(1), C1FA(2),
    C2C2(3), C2C1(4), C2FA(5);

    private final int value;

    CallType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
