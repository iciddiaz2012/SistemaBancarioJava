public class TransaccionInvalidaException extends Exception{

    private String razon;

    public TransaccionInvalidaException(String razon) {
        super("Transacción invalidad: " + razon);
        this.razon = razon;
    }

    public String getRazon() {
        return razon;
    }
}
