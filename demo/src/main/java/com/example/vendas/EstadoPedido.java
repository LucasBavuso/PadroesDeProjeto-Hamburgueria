package com.example.vendas;

public interface EstadoPedido {
    void avancar(PedidoContexto contexto);
    String getNome();
}
