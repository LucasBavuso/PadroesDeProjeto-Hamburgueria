package com.example.vendas;

public class CanalSMS implements CanalNotificacao { 
    private String ultimaMensagemEnviada = "";

    @Override
    public void enviar(String msg) { 
        this.ultimaMensagemEnviada = msg;
    } 

    public String getUltimaMensagemEnviada() {
        return ultimaMensagemEnviada;
    }
}