package com.conversor;

public class Principal {

    private static final String API_KEY = System.getenv().getOrDefault("c2288f66a4535c0eefc3d983", "c2288f66a4535c0eefc3d983");

    public static void main(String[] args) {
        var consulta = new ConsultaMoneda(API_KEY);
        var menu = new MenuInteractivo(consulta);
        menu.iniciar();
    }
}
