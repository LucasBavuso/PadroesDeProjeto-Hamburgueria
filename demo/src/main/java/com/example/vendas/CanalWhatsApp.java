package com.example.vendas;

public class CanalWhatsApp implements CanalNotificacao { 
    public void enviar(String msg) { 
        System.out.println("[WhatsApp] " + msg); 
    } 
}