package com.example.vendas;

import com.example.cozinha.FabricaDeCombo;

public class CriadorComboBrabo extends FabricaDeCombo {
    @Override
    public Produto criarCombo() {
        return new ComboBrabo();
    }
}
