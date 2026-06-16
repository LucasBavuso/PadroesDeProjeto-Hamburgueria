package com.example.cozinha;

public class CustomizacaoBurgerMemento {
    private final String estadoIngredientes;
    public CustomizacaoBurgerMemento(String est) { this.estadoIngredientes = est; }
    public String getEstado() { return estadoIngredientes; }
}
