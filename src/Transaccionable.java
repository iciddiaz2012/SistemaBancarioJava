public interface Transaccionable {

    void depositar(double monto) throws TransaccionInvalidaException;

    void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException, TransaccionInvalidaException;

    void transferir(Transaccionable destino, double monto) throws SaldoInsuficienteException, CuentaBloqueadaException, LimiteTransaccionException;
}
