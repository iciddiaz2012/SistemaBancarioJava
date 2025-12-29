public class LimiteTransaccionException extends Exception{

    private double montoTransaccion;
    private double limiteMaximo;

    public LimiteTransaccionException(double montoTransaccion, double limiteMaximo) {
        super("Monto de la transacción: $ " + montoTransaccion + " Limite excedido: $" + limiteMaximo);
        this.montoTransaccion = montoTransaccion;
        this.limiteMaximo = limiteMaximo;
    }

    public LimiteTransaccionException(String message) {
        super(message);
    }

    public double getExceso(){
        return montoTransaccion - limiteMaximo;
    }

    public double getMontoTransaccion() {
        return montoTransaccion;
    }

    public double getLimiteMaximo() {
        return limiteMaximo;
    }
}
