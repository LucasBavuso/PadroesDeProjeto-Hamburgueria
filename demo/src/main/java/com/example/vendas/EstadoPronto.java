package com.example.vendas;

public class EstadoPronto implements EstadoPedido {
    @Override 
    public String getNome() { 
        return "Pronto"; 
    }

    @Override 
    public void avancar(PedidoContexto c) { 
        throw new IllegalStateException("Fim da linha. O pedido já foi entregue e não pode mais avançar!");
    }
}