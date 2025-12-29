public class SaldoInsuficienteException extends Exception{

    private double saldoActual;
    private double montoRequerido;

    public SaldoInsuficienteException(String message, double saldoActual, double montoRequerido) {
        super(message);
        this.saldoActual = saldoActual;
        this.montoRequerido = montoRequerido;
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public double getDeficit(){
        return montoRequerido - saldoActual;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getMontoRequerido() {
        return montoRequerido;
    }
}
