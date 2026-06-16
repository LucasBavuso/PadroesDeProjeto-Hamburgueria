package com.example.cozinha;

public class DetalhesIngredienteFlyweight {
    private final String nome; private final String imagemUrl;
    public DetalhesIngredienteFlyweight(String n, String img) { 
        this.nome = n; this.imagemUrl = img; 
    }
    public String getNome() { 
        return nome; 
    }
}