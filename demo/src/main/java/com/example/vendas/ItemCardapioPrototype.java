package com.example.vendas;

import java.util.ArrayList;
import java.util.List;

public class ItemCardapioPrototype implements Cloneable {
    private String nome;
    private List<String> ingredientes = new ArrayList<>();

    public ItemCardapioPrototype(String nome) {
        this.nome = nome;
    }

    public void addIngrediente(String ing) {
        this.ingredientes.add(ing);
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public ItemCardapioPrototype clone() {
        try {
            ItemCardapioPrototype clonado = (ItemCardapioPrototype) super.clone();
            clonado.ingredientes = new ArrayList<>(this.ingredientes);
            return clonado;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
