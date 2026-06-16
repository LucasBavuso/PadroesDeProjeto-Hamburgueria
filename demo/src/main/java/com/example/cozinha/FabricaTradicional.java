package com.example.cozinha;

public class FabricaTradicional implements FabricaInsumos {
    @Override
    public Pao criarPao() {
        return new PaoBrioche();
    }

    @Override
    public Proteina criarProteina() {
        return new BurgerBovino();
    }
}
