public class CuentaInversion extends CuentaBancaria implements Transaccionable, Reportable{

    private double montoMinimoInversion;
    private double rendimientoAnual;
    private int plazoMeses;

    public CuentaInversion(String numeroCuenta, String titular, double saldoInicial,
                           double montoMinimoInversion, double rendimientoAnual, int plazoMeses)
            throws TransaccionInvalidaException {
        super(numeroCuenta, titular, saldoInicial, 50000); // Límite alto
        if (saldoInicial < montoMinimoInversion) {
            throw new TransaccionInvalidaException(
                    "Saldo inicial ($" + saldoInicial + ") menor al mínimo requerido ($" + montoMinimoInversion + ")"
            );
        }
        this.montoMinimoInversion = montoMinimoInversion;
        this.rendimientoAnual = rendimientoAnual;
        this.plazoMeses = plazoMeses;
    }

    @Override
    public void depositar(double monto) throws TransaccionInvalidaException {
        validarMonto(monto);
        setSaldo(getSaldo() + monto);
        registrarTransaccion("Inversión adicional: +$" + monto);
        System.out.println("✓ Inversión adicional de $" + monto + " en cuenta " + getNumeroCuenta());
    }

    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException, TransaccionInvalidaException {
        validarMonto(monto);
        validarCuentaActiva();

        if (getSaldo() - monto < montoMinimoInversion) {
            throw new TransaccionInvalidaException(
                    "El retiro dejaría el saldo por debajo del mínimo de inversión ($" + montoMinimoInversion + ")"
            );
        }

        if (getSaldo() < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente", getSaldo(), monto);
        }

        setSaldo(getSaldo() - monto);
        registrarTransaccion("Retiro parcial: -$" + monto);
        System.out.println("✓ Retiro exitoso de $" + monto + " de cuenta " + getNumeroCuenta());
    }

    @Override
    public void transferir(Transaccionable destino, double monto)
            throws SaldoInsuficienteException, CuentaBloqueadaException, LimiteTransaccionException {
        throw new UnsupportedOperationException("Las cuentas de inversión no permiten transferencias directas");
    }

    @Override
    public double calcularComision() {
        return 0; // Sin comisiones
    }

    @Override
    public String obtenerTipoCuenta() {
        return "Cuenta de Inversión";
    }

    public double proyectarRendimiento() {
        return getSaldo() * Math.pow(1 + rendimientoAnual / 12, plazoMeses);
    }

    @Override
    public String generarReporte() {
        return String.format("=== %s ===\nTitular: %s\nSaldo actual: $%.2f\nRendimiento proyectado (%d meses): $%.2f",
                obtenerTipoCuenta(), getTitular(), getSaldo(), plazoMeses, proyectarRendimiento());
    }

    @Override
    public void mostrarEstado() {
        System.out.println(generarReporte());
    }

}
