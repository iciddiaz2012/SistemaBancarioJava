public class Main {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA BANCARIO CON MANEJO DE EXCEPCIONES ===\n");

        Banco banco = new Banco("Banco Central");

        // ===== PARTE 1: CREAR CUENTAS =====
        System.out.println("========== PARTE 1: CREANDO CUENTAS ==========\n");

        try {
            banco.crearCuentaAhorro("AH001", "Juan Pérez", 1000, 0.03);
            banco.crearCuentaAhorro("AH002", "María García", 2000, 0.03);
            banco.crearCuentaCorriente("CC001", "Pedro López", 5000, 1000, 10);
            banco.crearCuentaCorriente("CC002", "Ana Martínez", 3000, 500, 8);
            banco.crearCuentaInversion("INV001", "Carlos Ruiz", 10000, 5000, 0.08, 12);

            // Intentar crear cuenta con saldo negativo
            System.out.println("\nIntentando crear cuenta con saldo negativo...");
            banco.crearCuentaAhorro("AH003", "Usuario Test", -500, 0.03);

        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        // ===== PARTE 2: PROBAR SaldoInsuficienteException =====
        System.out.println("\n========== PARTE 2: PROBANDO SaldoInsuficienteException ==========\n");

        try {
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("AH001");
            if (cuenta instanceof CuentaAhorro) {
                CuentaAhorro ahorro = (CuentaAhorro) cuenta;
                System.out.println("Intentando retirar $5000 de cuenta con $1000...");
                ahorro.retirar(5000);
            }
        } catch (SaldoInsuficienteException e) {
            System.out.println("✗ " + e.getMessage());
            System.out.println("  Saldo actual: $" + e.getSaldoActual());
            System.out.println("  Monto requerido: $" + e.getMontoRequerido());
            System.out.println("  Déficit: $" + e.getDeficit());
        } catch (CuentaNoEncontradaException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        // ===== PARTE 3: PROBAR CuentaBloqueadaException =====
        System.out.println("\n========== PARTE 3: PROBANDO CuentaBloqueadaException ==========\n");

        try {
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("CC001");
            System.out.println("Bloqueando cuenta CC001...");
            cuenta.bloquear("Actividad sospechosa");

            if (cuenta instanceof CuentaCorriente) {
                CuentaCorriente corriente = (CuentaCorriente) cuenta;
                System.out.println("Intentando retirar de cuenta bloqueada...");
                corriente.retirar(100);
            }
        } catch (CuentaBloqueadaException e) {
            System.out.println("✗ " + e.getMessage());
            System.out.println("  Número cuenta: " + e.getNumeroCuenta());
            System.out.println("  Motivo: " + e.getMotivoBloqueo());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        } finally {
            System.out.println("  Operación de prueba finalizada");
        }

        // ===== PARTE 4: PROBAR LimiteTransaccionException =====
        System.out.println("\n========== PARTE 4: PROBANDO LimiteTransaccionException ==========\n");

        try {
            CuentaBancaria origen = banco.buscarCualquierCuenta("AH002");
            CuentaBancaria destino = banco.buscarCualquierCuenta("CC002");

            // Desbloquear CC001 primero
            CuentaBancaria bloqueada = banco.buscarCualquierCuenta("CC001");
            bloqueada.desbloquear();

            if (origen instanceof CuentaAhorro && destino instanceof CuentaCorriente) {
                CuentaAhorro ahorro = (CuentaAhorro) origen;
                System.out.println("Intentando transferir $6000 (límite es $5000)...");
                ahorro.transferir((Transaccionable) destino, 6000);
            }
        } catch (LimiteTransaccionException e) {
            System.out.println("✗ " + e.getMessage());
            System.out.println("  Monto transacción: $" + e.getMontoTransaccion());
            System.out.println("  Límite máximo: $" + e.getLimiteMaximo());
            System.out.println("  Exceso: $" + e.getExceso());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        // ===== PARTE 5: PROBAR CuentaNoEncontradaException =====
        System.out.println("\n========== PARTE 5: PROBANDO CuentaNoEncontradaException ==========\n");

        try {
            System.out.println("Buscando cuenta inexistente XX999...");
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("XX999");
        } catch (CuentaNoEncontradaException e) {
            System.out.println("✗ " + e.getMessage());
            System.out.println("  Número cuenta buscado: " + e.getNumeroCuenta());
        }

        // ===== PARTE 6: PROBAR TransaccionInvalidaException =====
        System.out.println("\n========== PARTE 6: PROBANDO TransaccionInvalidaException ==========\n");

        try {
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("AH001");
            if (cuenta instanceof CuentaAhorro) {
                CuentaAhorro ahorro = (CuentaAhorro) cuenta;
                System.out.println("Intentando depositar monto negativo...");
                ahorro.depositar(-100);
            }
        } catch (TransaccionInvalidaException e) {
            System.out.println("✗ " + e.getMessage());
            System.out.println("  Razón: " + e.getRazon());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        try {
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("INV001");
            if (cuenta instanceof CuentaInversion) {
                CuentaInversion inversion = (CuentaInversion) cuenta;
                System.out.println("\nIntentando retirar de inversión dejando saldo bajo mínimo...");
                inversion.retirar(8000); // Dejaría $2000, mínimo es $5000
            }
        } catch (TransaccionInvalidaException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        // ===== PARTE 7: TRANSACCIONES EXITOSAS =====
        System.out.println("\n========== PARTE 7: TRANSACCIONES EXITOSAS ==========\n");

        try {
            // Depósitos
            System.out.println("--- Depósitos ---");
            CuentaBancaria ah1 = banco.buscarCualquierCuenta("AH001");
            if (ah1 instanceof CuentaAhorro) {
                ((CuentaAhorro) ah1).depositar(500);
            }

            CuentaBancaria cc1 = banco.buscarCualquierCuenta("CC001");
            if (cc1 instanceof CuentaCorriente) {
                ((CuentaCorriente) cc1).depositar(1000);
            }

            // Retiros válidos
            System.out.println("\n--- Retiros ---");
            if (ah1 instanceof CuentaAhorro) {
                ((CuentaAhorro) ah1).retirar(200);
            }

            // Transferencias exitosas
            System.out.println("\n--- Transferencias ---");
            banco.realizarTransferencia("AH001", "CC002", 300);

            // Aplicar intereses
            System.out.println("\n--- Aplicar Intereses ---");
            if (ah1 instanceof CuentaAhorro) {
                ((CuentaAhorro) ah1).aplicarIntereses();
            }

            // Cobrar comisiones
            System.out.println("\n--- Cobrar Comisiones ---");
            if (cc1 instanceof CuentaCorriente) {
                ((CuentaCorriente) cc1).cobrarComisionMensual();
            }

        } catch (Exception e) {
            System.out.println("✗ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        // ===== PARTE 8: FINALLY BLOCKS =====
        System.out.println("\n========== PARTE 8: DEMOSTRANDO FINALLY ==========\n");

        try {
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("AH002");
            System.out.println("Realizando operación...");
            if (cuenta instanceof CuentaAhorro) {
                ((CuentaAhorro) cuenta).depositar(100);
            }
        } catch (TransaccionInvalidaException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } finally {
            System.out.println("✓ Bloque finally ejecutado (SIEMPRE se ejecuta)");
        }

        // ===== PARTE 9: MULTI-CATCH =====
        System.out.println("\n========== PARTE 9: DEMOSTRANDO MULTI-CATCH ==========\n");

        try {
            System.out.println("Intentando operación que puede fallar de varias formas...");
            banco.realizarTransferencia("AH001", "CC001", 50);
        } catch (Exception e) {
            System.out.println("Capturado con catch genérico: " + e.getMessage());
        }

        // Ejemplo más específico de multi-catch
        try {
            CuentaBancaria cuenta = banco.buscarCualquierCuenta("CC001");
            if (cuenta instanceof CuentaCorriente) {
                ((CuentaCorriente) cuenta).retirar(100);
            }
        } catch (SaldoInsuficienteException | CuentaBloqueadaException e) {
            System.out.println("✗ Error en transacción: " + e.getMessage());
        } catch (TransaccionInvalidaException e) {
            System.out.println("✗ Transacción inválida: " + e.getMessage());
        } catch (CuentaNoEncontradaException e) {
            System.out.println("✗ Cuenta no encontrada: " + e.getMessage());
        }

        // ===== PARTE 10: REPORTES FINALES =====
        System.out.println("\n========== PARTE 10: REPORTES FINALES ==========\n");

        try {
            CuentaBancaria ah1 = banco.buscarCualquierCuenta("AH001");
            CuentaBancaria cc1 = banco.buscarCualquierCuenta("CC001");
            CuentaBancaria inv1 = banco.buscarCualquierCuenta("INV001");

            if (ah1 instanceof Reportable) {
                ((Reportable) ah1).mostrarEstado();
            }
            System.out.println();

            if (cc1 instanceof Reportable) {
                ((Reportable) cc1).mostrarEstado();
            }
            System.out.println();

            if (inv1 instanceof Reportable) {
                ((Reportable) inv1).mostrarEstado();
            }

        } catch (CuentaNoEncontradaException e) {
            System.out.println("✗ " + e.getMessage());
        }

        // Reporte general del banco
        banco.generarReporteBanco();

        // ===== RESUMEN FINAL =====
        System.out.println("\n========== RESUMEN DE EXCEPCIONES PROBADAS ==========");
        System.out.println("✓ SaldoInsuficienteException (checked)");
        System.out.println("✓ CuentaBloqueadaException (checked)");
        System.out.println("✓ LimiteTransaccionException (checked)");
        System.out.println("✓ CuentaNoEncontradaException (unchecked)");
        System.out.println("✓ TransaccionInvalidaException (unchecked)");
        System.out.println("✓ Finally blocks");
        System.out.println("✓ Multi-catch");
        System.out.println("\n=== FIN DEL SISTEMA BANCARIO ===");
    }

    }