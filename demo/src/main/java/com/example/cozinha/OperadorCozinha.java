package com.example.cozinha;

public class OperadorCozinha extends ColaboradorLoja {
    private String ultimaOrdemRecebida = "";

    public OperadorCozinha(CentralMediator m) {
        super(m);
    }

    @Override
    public void receberDirecionamento(String msg) {
        this.ultimaOrdemRecebida = msg;
    }

    public String getUltimaOrdemRecebida() {
        return ultimaOrdemRecebida;
    }
}