package com.example.cozinha;

public class FabricaVegana implements FabricaInsumos {
    @Override
    public Pao criarPao() {
        return new PaoVegano();
    }

    @Override
    public Proteina criarProteina() {
        return new BurgerFuturo();
    }
}
