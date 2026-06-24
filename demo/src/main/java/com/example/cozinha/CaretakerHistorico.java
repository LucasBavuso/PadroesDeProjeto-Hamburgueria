package com.example.cozinha;

import java.util.Stack;

public class CaretakerHistorico {
    private Stack<CustomizacaoBurgerMemento> pilha = new Stack<>();

    public void salvar(OriginatorBurgerCustom o) {
        pilha.push(o.criarSnapshot());
    }

    public void desfazer(OriginatorBurgerCustom o) {
        if (!pilha.isEmpty())
            o.restaurar(pilha.pop());
    }
}
