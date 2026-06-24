package com.example.vendas;

public class CanalWhatsApp implements CanalNotificacao { 
    private String ultimaMensagemEnviada = "";

    @Override
    public void enviar(String msg) { 
        this.ultimaMensagemEnviada = msg;
    } 

    public String getUltimaMensagemEnviada() {
        return ultimaMensagemEnviada;
    }
}