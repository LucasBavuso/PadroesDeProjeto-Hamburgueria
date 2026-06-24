package com.example.vendas;

import java.util.ArrayList;
import java.util.List;

public class SecaoCardapioComposite implements ComponenteCardapio {
    private String nome;
    private List<ComponenteCardapio> componentes = new ArrayList<>();

    public SecaoCardapioComposite(String nome) {
        this.nome = nome;
    }

    public void adicionar(ComponenteCardapio c) {
        componentes.add(c);
    }

    @Override
    public void exibir() {
        System.out.println("Categoria: " + nome);
        for (ComponenteCardapio c : componentes)
            c.exibir();
    }

    @Override
    public double getPreco() {
        return componentes.stream().mapToDouble(ComponenteCardapio::getPreco).sum();
    }
}