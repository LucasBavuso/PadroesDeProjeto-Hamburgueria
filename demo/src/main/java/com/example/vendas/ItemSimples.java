package com.example.vendas;

public class ItemSimples implements ComponenteCardapio {
    private String nome; 
    private double preco;

    public ItemSimples(String nome, double preco) { 
        this.nome = nome; 
        this.preco = preco; 
    }

    @Override 
    public void exibir() { 
    }

    @Override 
    public double getPreco() { 
        return preco; 
    }

    public String getNome() {
        return nome;
    }
}