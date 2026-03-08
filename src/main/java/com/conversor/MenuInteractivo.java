package com.conversor;

import java.util.Scanner;

public class MenuInteractivo {

    private final Scanner scanner;
    private final ConsultaMoneda consultaMoneda;

    public MenuInteractivo(ConsultaMoneda consultaMoneda) {
        this.scanner = new Scanner(System.in);
        this.consultaMoneda = consultaMoneda;
    }

    public void iniciar() {
        mostrarBienvenida();

        var continuar = true;

        while (continuar) {
            mostrarMenu();
            var opcion = leerOpcion();
            continuar = procesarOpcion(opcion);
        }

        System.out.println("\n¡Gracias por usar el Conversor de Monedas!");
        scanner.close();
    }

    private void mostrarBienvenida() {
        System.out.println("=".repeat(58));
        System.out.println(" CONVERSOR DE MONEDAS hecho en Java 25 + ExchangeRate API ");
        System.out.println("=".repeat(58));
    }

    private void mostrarMenu() {
        System.out.println("""
                
                ╔════════════════════════════════════════╗
                ║        MENÚ DE CONVERSIONES            ║
                ╠════════════════════════════════════════╣
                ║  1) Dólar (USD)       - Peso Arg (ARS) ║
                ║  2) Peso Arg (ARS)    - Dólar (USD)    ║
                ║  3) Dólar (USD)       - Real Bra (BRL) ║
                ║  4) Real Bra (BRL)    - Dólar (USD)    ║
                ║  5) Dólar (USD)       - Peso Col (COP) ║
                ║  6) Peso Col (COP)    - Dólar (USD)    ║
                ║  7) Dólar (USD)       - Sol Peru (PEN) ║
                ║  8) Sol Peru (PEN)    - Dólar (USD)    ║
                ║  9) Salir                              ║
                ╚════════════════════════════════════════╝
                """);
        System.out.print("Elige una opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean procesarOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> {
                ejecutarConversion("USD", "ARS");
                yield true;
            }
            case 2 -> {
                ejecutarConversion("ARS", "USD");
                yield true;
            }
            case 3 -> {
                ejecutarConversion("USD", "BRL");
                yield true;
            }
            case 4 -> {
                ejecutarConversion("BRL", "USD");
                yield true;
            }
            case 5 -> {
                ejecutarConversion("USD", "COP");
                yield true;
            }
            case 6 -> {
                ejecutarConversion("COP", "USD");
                yield true;
            }
            case 7 -> {
                ejecutarConversion("USD", "PEN");
                yield true;
            }
            case 8 -> {
                ejecutarConversion("PEN", "USD");
                yield true;
            }
            case 9 -> false;
            default -> {
                System.out.println("Opción no válida. Por favor, elige entre 1 y 9.");
                yield true;
            }
        };
    }

    private void ejecutarConversion(String origen, String destino) {
        var monto = solicitarMonto();
        if (monto <= 0) {
            System.out.println("El monto debe ser un número positivo.");
            return;
        }

        try {
            System.out.println("Consultando tasa de cambio " + origen + " - " + destino + "...");

            var respuestaDto = consultaMoneda.consultar(origen, destino);
            var conversor = new Conversor(respuestaDto);

            System.out.println(conversor.describirTasa());
            System.out.println(conversor.calcularConversion(monto));

        } catch (ConsultaMoneda.ConversionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private double solicitarMonto() {
        System.out.print("Ingresa la cantidad a convertir: ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
