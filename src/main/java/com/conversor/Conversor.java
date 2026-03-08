package com.conversor;

import com.conversor.dto.RespuestaMoneda;

public class Conversor {

    private final String monedaOrigen;
    private final String monedaDestino;
    private final double tasaDeCambio;

    public Conversor(RespuestaMoneda respuesta) {
        this.monedaOrigen = respuesta.monedaBase();
        this.monedaDestino = respuesta.monedaObjetivo();
        this.tasaDeCambio = respuesta.tasaDeConversion();
    }

    public String calcularConversion(double monto) {
        double resultado = monto * tasaDeCambio;
        return String.format("%.2f [%s] equivalen a %.2f [%s]",
                monto, monedaOrigen, resultado, monedaDestino);
    }

    public String describirTasa() {
        return String.format("Tasa de cambio: 1 [%s] = %.6f [%s]",
                monedaOrigen, tasaDeCambio, monedaDestino);
    }
}
