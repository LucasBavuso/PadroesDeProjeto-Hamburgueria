package com.example.cozinha;

public class OriginatorBurgerCustom {
    private String ingredientesAtuais;

    public void setIngredientes(String ing) {
        this.ingredientesAtuais = ing;
    }

    public String getIngredientes() {
        return ingredientesAtuais;
    }

    public CustomizacaoBurgerMemento criarSnapshot() {
        return new CustomizacaoBurgerMemento(ingredientesAtuais);
    }

    public void restaurar(CustomizacaoBurgerMemento m) {
        this.ingredientesAtuais = m.getEstado();
    }
}
