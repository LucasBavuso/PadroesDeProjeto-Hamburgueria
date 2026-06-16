package com.example.vendas;

public class NotificadorPainelCliente implements Observer {
    @Override public void atualizar(String est) { System.out.println("[OBSERVER] TV do Salão atualizou status para: " + est); }
}
