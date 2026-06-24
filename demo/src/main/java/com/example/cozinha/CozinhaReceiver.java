package com.example.cozinha;

public class CozinhaReceiver {
    private String ultimoStatus = "Ocioso";

    public void producirBurger() {
        this.ultimoStatus = "Chapeando";
    }

    public void descartarBurger() {
        this.ultimoStatus = "Cancelado";
    }

    public String getUltimoStatus() {
        return ultimoStatus;
    }
}