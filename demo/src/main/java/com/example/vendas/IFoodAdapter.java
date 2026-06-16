package com.example.vendas;

public class IFoodAdapter implements PedidoNativo {
    private PedidoIFoodExterno ifood;
    public IFoodAdapter(PedidoIFoodExterno ifood) { 
        this.ifood = ifood; 
    }
    @Override public String getDescricaoPedido() { 
        return "Convertido: " + ifood.getItensDoIFood(); 
    }
}