package com.example.vendas;

public class EntregaDelivery extends Entrega {
    public EntregaDelivery(CanalNotificacao canal) { 
        super(canal); 
    }
    @Override public void despachar() { 
        canal.enviar("Seu pedido saiu para entrega via motoboy!"); 
    }
}