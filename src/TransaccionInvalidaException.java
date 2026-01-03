public class TransaccionInvalidaException extends RuntimeException{

    private String razon;

    public TransaccionInvalidaException(String razon) {
        super("Transacción inválida: " + razon);
        this.razon = razon;
    }

    public String getRazon() {
        return razon;
    }

}
