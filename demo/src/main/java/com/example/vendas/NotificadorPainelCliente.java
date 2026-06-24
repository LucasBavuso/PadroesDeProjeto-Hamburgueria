package com.example.vendas;

public class NotificadorPainelCliente implements Observer {
    private String ultimoStatusRecebido = "";

    @Override
    public void atualizar(String est) {
        this.ultimoStatusRecebido = est;
    }

    public String getUltimoStatusRecebido() {
        return ultimoStatusRecebido;
    }
}