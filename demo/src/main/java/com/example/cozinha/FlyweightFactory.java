package com.example.cozinha;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    private static final Map<String, DetalhesIngredienteFlyweight> cache = new HashMap<>();
    public static DetalhesIngredienteFlyweight getIngrediente(String nome) {
        if (!cache.containsKey(nome)) cache.put(nome, new DetalhesIngredienteFlyweight(nome, "http://cdn/img/" + nome + ".png"));
        return cache.get(nome);
    }
}