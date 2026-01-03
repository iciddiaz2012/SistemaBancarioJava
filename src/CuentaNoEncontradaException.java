public class CuentaNoEncontradaException extends RuntimeException{

    private String numeroCuenta;

    public CuentaNoEncontradaException(String numeroCuenta) {
        super("Cuenta no encontrada: " + numeroCuenta);
        this.numeroCuenta = numeroCuenta;
    }

    public CuentaNoEncontradaException(String mensaje, String numeroCuenta) {
        super(mensaje);
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

}
