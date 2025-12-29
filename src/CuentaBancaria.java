import java.util.ArrayList;

public abstract class CuentaBancaria {

    private String numeroCuenta;
    private String titular;
    private double saldo;
    private boolean bloqueada;
    private double limiteTransaccion;
    private ArrayList<String> historialTransacciones;

    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial, double limiteTransaccion)
    throws TransaccionInvalidaException{
        if ( saldoInicial < 0){
            throw new TransaccionInvalidaException("El saldo inicial no puede ser negativo");
        }
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo =  saldoInicial;
        this.limiteTransaccion = limiteTransaccion;
        this.bloqueada = false;
        this.historialTransacciones = new ArrayList<>();

    }

    public abstract double calcularComision();

    public abstract String obtenerTipoCuenta();

    protected void registrarTransaccion(String descripcion){
        historialTransacciones.add(descripcion);
    }

    public void bloquear(String motivo){
        this.bloqueada = true;
        registrarTransaccion("Cuenta bloqueada: " + motivo);
    }

    public void desbloquear(){
        this.bloqueada = false;
        registrarTransaccion("Cuenta desbloqueada: ");
    }

    protected void validarMonto(double monto) throws TransaccionInvalidaException{
        if (monto <= 0){
            throw new TransaccionInvalidaException("El monto debe ser mayor a cero");
        }
        if (Double.isNaN(monto) || Double.isFinite(monto)){
            throw new TransaccionInvalidaException("El monto no es un número válido");
        }
    }

    protected void validarCuentaActiva() throws CuentaBloqueadaException{
        if (bloqueada){
            throw  new CuentaBloqueadaException(numeroCuenta, "Cuenta bloqueada");
        }
    }



}
