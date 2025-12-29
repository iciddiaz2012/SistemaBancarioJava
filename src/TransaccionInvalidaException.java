public class TransaccionInvalidaException extends Exception{

    private String razon;

    public TransaccionInvalidaException(String razon) {
        super("Transacción inválida: " + razon);
        this.razon = razon;
    }

    public String getRazon() {
        return razon;
    }
}
