package com.example.vendas;

public class EstadoPreparando implements EstadoPedido {
    @Override public String getNome() { return "Preparando"; }
    @Override public void avancar(PedidoContexto c) { c.setEstado(new EstadoPronto()); }
}
