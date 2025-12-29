public class CuentaNoEncontradaException extends Exception{

    private String numeroCuenta;

    public CuentaNoEncontradaException(String numeroCuenta) {
        super("Cuenta no encontrada: " + numeroCuenta);
        this.numeroCuenta = numeroCuenta;
    }

    public CuentaNoEncontradaException(String message, String numeroCuenta) {
        super(message);
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
}
