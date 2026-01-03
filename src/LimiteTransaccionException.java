public class LimiteTransaccionException extends Exception{

    private double montoTransaccion;
    private double limiteMaximo;

    public LimiteTransaccionException(double montoTransaccion, double limiteMaximo) {
        super("Transacción de $" + montoTransaccion + " excede el límite de $" + limiteMaximo);
        this.montoTransaccion = montoTransaccion;
        this.limiteMaximo = limiteMaximo;
    }

    public LimiteTransaccionException(String mensaje) {
        super(mensaje);
    }

    public double getExceso() {
        return montoTransaccion - limiteMaximo;
    }

    public double getMontoTransaccion() {
        return montoTransaccion;
    }

    public double getLimiteMaximo() {
        return limiteMaximo;
    }

}
