public class CuentaBloqueadaException extends Exception{

    private String numeroCuenta;
    private String motivoBloqueo;

    public CuentaBloqueadaException(String numeroCuenta, String motivoBloqueo) {
        super("Cuenta: " + numeroCuenta +  " Bloqueada: " + motivoBloqueo);
        this.numeroCuenta = numeroCuenta;
        this.motivoBloqueo = motivoBloqueo;
    }

    public CuentaBloqueadaException(String message) {
        super(message);
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getMotivoBloqueo() {
        return motivoBloqueo;
    }
}
