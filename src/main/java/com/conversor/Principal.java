package com.conversor;

public class Principal {

    private static final String API_KEY = System.getenv().getOrDefault("TU_CLAVE", "CLAVE_DEFAULT");

    public static void main(String[] args) {
        var consulta = new ConsultaMoneda(API_KEY);
        var menu = new MenuInteractivo(consulta);
        menu.iniciar();
    }
}

