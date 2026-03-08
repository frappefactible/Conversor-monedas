package com.conversor;

import com.conversor.dto.RespuestaMoneda;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {

    private final HttpClient clienteHttp;
    private final Gson gson;
    private final String apiKey;
    private static final String URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public ConsultaMoneda(String apiKey) {
        this.apiKey = apiKey;
        this.clienteHttp = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public RespuestaMoneda consultar(String monedaOrigen, String monedaDestino) {
        var uri = URI.create(URL_BASE + apiKey + "/pair/" + monedaOrigen + "/" + monedaDestino);

        var solicitud = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            var respuesta = clienteHttp.send(solicitud, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() != 200) {
                throw new ConversionException(
                        "Error en la petición HTTP. Código de estado: " + respuesta.statusCode());
            }

            var dto = gson.fromJson(respuesta.body(), RespuestaMoneda.class);

            if (!"success".equals(dto.resultado())) {
                throw new ConversionException(
                        "La API reportó un error. Verifica los códigos de moneda o tu API key.");
            }

            return dto;

        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new ConversionException("Error de conexión con la API: " + e.getMessage(), e);
        }
    }

    public static class ConversionException extends RuntimeException {
        public ConversionException(String mensaje) {
            super(mensaje);
        }

        public ConversionException(String mensaje, Throwable causa) {
            super(mensaje, causa);
        }
    }
}
