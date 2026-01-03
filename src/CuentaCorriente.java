public class CuentaCorriente extends CuentaBancaria implements Transaccionable, Reportable{

    private double sobregiro;
    private double comisionMensual;

    public CuentaCorriente(String numeroCuenta, String titular, double saldoInicial,
                           double sobregiro, double comisionMensual) throws TransaccionInvalidaException {
        super(numeroCuenta, titular, saldoInicial, 10000); // Límite de $10000
        this.sobregiro = sobregiro;
        this.comisionMensual = comisionMensual;
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

        // Permite sobregiro
        if (getSaldo() - totalRequerido < -sobregiro) {
            throw new SaldoInsuficienteException(
                    "Excede el límite de sobregiro de $" + sobregiro,
                    getSaldo(),
                    totalRequerido
            );
        }

        setSaldo(getSaldo() - totalRequerido);
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

        try {
            retirar(monto);
            destino.depositar(monto);
        } catch (TransaccionInvalidaException e) {
            throw new RuntimeException("Error en transferencia", e);
        }

        registrarTransaccion("Transferencia enviada: -$" + monto);
        System.out.println("✓ Transferencia exitosa de $" + monto);
    }

    @Override
    public double calcularComision() {
        return comisionMensual;
    }

    @Override
    public String obtenerTipoCuenta() {
        return "Cuenta Corriente";
    }

    public void cobrarComisionMensual() throws SaldoInsuficienteException {
        if (getSaldo() < comisionMensual) {
            throw new SaldoInsuficienteException(
                    "Saldo insuficiente para cobrar comisión mensual",
                    getSaldo(),
                    comisionMensual
            );
        }
        setSaldo(getSaldo() - comisionMensual);
        registrarTransaccion("Comisión mensual: -$" + comisionMensual);
        System.out.println("✓ Comisión mensual cobrada: $" + comisionMensual);
    }

    @Override
    public String generarReporte() {
        return String.format("=== %s ===\nTitular: %s\nSaldo: $%.2f\nSobregiro disponible: $%.2f\nComisión mensual: $%.2f",
                obtenerTipoCuenta(), getTitular(), getSaldo(), sobregiro, comisionMensual);
    }

    @Override
    public void mostrarEstado() {
        System.out.println(generarReporte());
    }

}
