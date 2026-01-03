public class CuentaAhorro extends CuentaBancaria implements Transaccionable, Reportable{

    private double tasaInteres;
    private int retirosMensuales;

    public CuentaAhorro(String numeroCuenta, String titular, double saldoInicial, double tasaInteres)
            throws TransaccionInvalidaException {
        super(numeroCuenta, titular, saldoInicial, 5000); // Límite de $5000
        this.tasaInteres = tasaInteres;
        this.retirosMensuales = 0;
    }

    @Override
    public void depositar(double monto) throws TransaccionInvalidaException {
        validarMonto(monto);
        setSaldo(getSaldo() + monto);
        registrarTransaccion("Depósito: +$" + monto);
        System.out.println("✓ Depósito exitoso de $" + monto + " en cuenta " + getNumeroCuenta());
    }

    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException, TransaccionInvalidaException {
        validarMonto(monto);
        validarCuentaActiva();

        double comision = calcularComision();
        double totalRequerido = monto + comision;

        if (getSaldo() < totalRequerido) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente para retirar $" + monto + " (comisión: $" + comision + ")",
                    getSaldo(),
                    totalRequerido
            );
        }

        setSaldo(getSaldo() - totalRequerido);
        retirosMensuales++;
        registrarTransaccion("Retiro: -$" + monto + " (comisión: $" + comision + ")");
        System.out.println("✓ Retiro exitoso de $" + monto + " de cuenta " + getNumeroCuenta());
    }

    @Override
    public void transferir(Transaccionable destino, double monto)
            throws SaldoInsuficienteException, CuentaBloqueadaException, LimiteTransaccionException {
        validarCuentaActiva();

        if (monto > getLimiteTransaccion()) {
            throw new LimiteTransaccionException(monto, getLimiteTransaccion());
        }

        // Retirar de esta cuenta
        try {
            retirar(monto);
        } catch (TransaccionInvalidaException e) {
            throw new RuntimeException("Error inesperado en transferencia", e);
        }

        // Depositar en destino
        try {
            destino.depositar(monto);
        } catch (TransaccionInvalidaException e) {
            // Revertir el retiro
            try {
                depositar(monto);
            } catch (TransaccionInvalidaException ex) {
                // Situación crítica
            }
            throw new RuntimeException("Error al depositar en cuenta destino", e);
        }

        registrarTransaccion("Transferencia enviada: -$" + monto);
        System.out.println("✓ Transferencia exitosa de $" + monto);
    }

    @Override
    public double calcularComision() {
        return retirosMensuales > 3 ? 1.0 : 0.0;
    }

    @Override
    public String obtenerTipoCuenta() {
        return "Cuenta de Ahorro";
    }

    public void aplicarIntereses() {
        double intereses = getSaldo() * tasaInteres;
        setSaldo(getSaldo() + intereses);
        registrarTransaccion("Intereses aplicados: +$" + intereses);
        System.out.println("✓ Intereses aplicados: $" + intereses);
    }

    @Override
    public String generarReporte() {
        return String.format("=== %s ===\nTitular: %s\nSaldo: $%.2f\nTasa interés: %.2f%%\nRetiros mensuales: %d",
                obtenerTipoCuenta(), getTitular(), getSaldo(), tasaInteres * 100, retirosMensuales);
    }

    @Override
    public void mostrarEstado() {
        System.out.println(generarReporte());
    }

}
