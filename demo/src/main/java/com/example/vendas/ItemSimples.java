package com.example.vendas;

public class ItemSimples implements ComponenteCardapio {
    private String nome; private double preco;
    public ItemSimples(String nome, double preco) { 
        this.nome = nome; this.preco = preco; 
    }
    @Override public void exibir() { 
        System.out.println(" - " + nome + ": R$ " + preco); 
    }
    @Override public double getPreco() { 
        return preco; 
    }
}