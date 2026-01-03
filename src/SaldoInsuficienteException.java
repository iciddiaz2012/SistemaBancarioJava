public class SaldoInsuficienteException extends Exception{

    private double saldoActual;
    private double montoRequerido;

    public SaldoInsuficienteException(String mensaje, double saldoActual, double montoRequerido) {
        super(mensaje);
        this.saldoActual = saldoActual;
        this.montoRequerido = montoRequerido;
    }

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public double getDeficit() {
        return montoRequerido - saldoActual;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getMontoRequerido() {
        return montoRequerido;
    }

}
