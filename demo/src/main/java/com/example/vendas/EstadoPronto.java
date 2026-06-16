package com.example.vendas;

public class EstadoPronto implements EstadoPedido {
    @Override public String getNome() { return "Pronto"; }
    @Override public void avancar(PedidoContexto c) { System.out.println("Fim da linha. O pedido já foi entregue!"); }
}
