 class Banco {

     private String nombre;
     private GestorCuentas<CuentaAhorro> gestorAhorros;
     private GestorCuentas<CuentaCorriente> gestorCorrientes;
     private GestorCuentas<CuentaInversion> gestorInversiones;

     public Banco(String nombre) {
         this.nombre = nombre;
         this.gestorAhorros = new GestorCuentas<>();
         this.gestorCorrientes = new GestorCuentas<>();
         this.gestorInversiones = new GestorCuentas<>();
     }

     public void crearCuentaAhorro(String numero, String titular, double saldoInicial, double tasa) {
         try {
             CuentaAhorro cuenta = new CuentaAhorro(numero, titular, saldoInicial, tasa);
             gestorAhorros.agregarCuenta(cuenta);
             System.out.println("✓ Cuenta de ahorro creada: " + numero);
         } catch (TransaccionInvalidaException e) {
             System.out.println("✗ Error al crear cuenta: " + e.getMessage());
         } catch (IllegalArgumentException e) {
             System.out.println("✗ " + e.getMessage());
         }
     }

     public void crearCuentaCorriente(String numero, String titular, double saldoInicial,
                                      double sobregiro, double comision) {
         try {
             CuentaCorriente cuenta = new CuentaCorriente(numero, titular, saldoInicial, sobregiro, comision);
             gestorCorrientes.agregarCuenta(cuenta);
             System.out.println("✓ Cuenta corriente creada: " + numero);
         } catch (TransaccionInvalidaException e) {
             System.out.println("✗ Error al crear cuenta: " + e.getMessage());
         } catch (IllegalArgumentException e) {
             System.out.println("✗ " + e.getMessage());
         }
     }

     public void crearCuentaInversion(String numero, String titular, double saldoInicial,
                                      double montoMinimo, double rendimiento, int plazo) {
         try {
             CuentaInversion cuenta = new CuentaInversion(numero, titular, saldoInicial,
                     montoMinimo, rendimiento, plazo);
             gestorInversiones.agregarCuenta(cuenta);
             System.out.println("✓ Cuenta de inversión creada: " + numero);
         } catch (TransaccionInvalidaException e) {
             System.out.println("✗ Error al crear cuenta: " + e.getMessage());
         } catch (IllegalArgumentException e) {
             System.out.println("✗ " + e.getMessage());
         }
     }

     public CuentaBancaria buscarCualquierCuenta(String numeroCuenta) throws CuentaNoEncontradaException {
         try {
             return gestorAhorros.buscarCuenta(numeroCuenta);
         } catch (CuentaNoEncontradaException e1) {
             try {
                 return gestorCorrientes.buscarCuenta(numeroCuenta);
             } catch (CuentaNoEncontradaException e2) {
                 return gestorInversiones.buscarCuenta(numeroCuenta);
             }
         }
     }

     public void realizarTransferencia(String cuentaOrigen, String cuentaDestino, double monto) {
         try {
             CuentaBancaria origen = buscarCualquierCuenta(cuentaOrigen);
             CuentaBancaria destino = buscarCualquierCuenta(cuentaDestino);

             if (origen instanceof Transaccionable && destino instanceof Transaccionable) {
                 ((Transaccionable) origen).transferir((Transaccionable) destino, monto);
             }
         } catch (CuentaNoEncontradaException e) {
             System.out.println("✗ " + e.getMessage());
         } catch (SaldoInsuficienteException e) {
             System.out.println("✗ Saldo insuficiente. Déficit: $" + e.getDeficit());
         } catch (CuentaBloqueadaException e) {
             System.out.println("✗ " + e.getMessage());
         } catch (LimiteTransaccionException e) {
             System.out.println("✗ Excede límite. Exceso: $" + e.getExceso());
         }
     }

     public void generarReporteBanco() {
         System.out.println("\n========================================");
         System.out.println("       BANCO " + nombre.toUpperCase());
         System.out.println("========================================");
         gestorAhorros.generarReporteGeneral();
         gestorCorrientes.generarReporteGeneral();
         gestorInversiones.generarReporteGeneral();

         double saldoTotal = gestorAhorros.calcularSaldoTotal() +
                 gestorCorrientes.calcularSaldoTotal() +
                 gestorInversiones.calcularSaldoTotal();
         System.out.println("\n💰 SALDO TOTAL DEL BANCO: $" + saldoTotal);
         System.out.println("========================================\n");
     }

 }
