package com.example.vendas;

public abstract class Entrega {
    protected CanalNotificacao canal;
    public Entrega(CanalNotificacao canal) { 
        this.canal = canal; 
    }
    public abstract void despachar();
}