package com.example.vendas;

import java.util.HashMap;
import java.util.Map;

public class RegistroPrototypes {
    private static final Map<String, ItemCardapioPrototype> mapa = new HashMap<>();

    static {
        ItemCardapioPrototype xBacon = new ItemCardapioPrototype("X-Bacon");
        xBacon.addIngrediente("Pão");
        xBacon.addIngrediente("Carne");
        xBacon.addIngrediente("Bacon");
        mapa.put("X-Bacon", xBacon);
    }

    public static ItemCardapioPrototype getPrototype(String chave) {
        ItemCardapioPrototype prototype = mapa.get(chave);
        return prototype != null ? prototype.clone() : null;
    }
}
