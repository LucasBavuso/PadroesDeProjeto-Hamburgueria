package com.example.vendas;

public class EstadoRecebido implements EstadoPedido {
    @Override
    public String getNome() {
        return "Recebido";
    }

    @Override
    public void avancar(PedidoContexto c) {
        c.setEstado(new EstadoPreparando());
    }
}
