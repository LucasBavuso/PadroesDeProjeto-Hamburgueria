package com.example.vendas;

import com.example.cozinha.FabricaDeCombo;

public class CriadorComboKids extends FabricaDeCombo {
    @Override
    public Produto criarCombo() {
        return new ComboKids();
    }
}
