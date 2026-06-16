package com.example.vendas;

public class CanalSMS implements CanalNotificacao { 
    public void enviar(String msg) { 
        System.out.println("[SMS] " + msg); 
    } 
}