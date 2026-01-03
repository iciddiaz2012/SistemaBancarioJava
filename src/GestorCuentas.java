import java.util.ArrayList;

public class GestorCuentas <T extends CuentaBancaria> {

    private ArrayList<T> cuentas;

    public GestorCuentas() {
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta(T cuenta) throws IllegalArgumentException {
        for (T c : cuentas) {
            if (c.getNumeroCuenta().equals(cuenta.getNumeroCuenta())) {
                throw new IllegalArgumentException("Ya existe una cuenta con el número: " + cuenta.getNumeroCuenta());
            }
        }
        cuentas.add(cuenta);
    }

    public T buscarCuenta(String numeroCuenta) throws CuentaNoEncontradaException {
        for (T cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        throw new CuentaNoEncontradaException(numeroCuenta);
    }

    public void eliminarCuenta(String numeroCuenta) throws CuentaNoEncontradaException {
        T cuenta = buscarCuenta(numeroCuenta);
        cuentas.remove(cuenta);
    }

    public ArrayList<T> obtenerCuentasBloqueadas() {
        ArrayList<T> bloqueadas = new ArrayList<>();
        for (T cuenta : cuentas) {
            if (cuenta.isBloqueada()) {
                bloqueadas.add(cuenta);
            }
        }
        return bloqueadas;
    }

    public double calcularSaldoTotal() {
        double total = 0;
        for (T cuenta : cuentas) {
            total += cuenta.getSaldo();
        }
        return total;
    }

    public void generarReporteGeneral() {
        System.out.println("\n=== REPORTE GENERAL ===");
        System.out.println("Total de cuentas: " + cuentas.size());
        System.out.println("Saldo total: $" + calcularSaldoTotal());
        System.out.println("Cuentas bloqueadas: " + obtenerCuentasBloqueadas().size());
    }

}
