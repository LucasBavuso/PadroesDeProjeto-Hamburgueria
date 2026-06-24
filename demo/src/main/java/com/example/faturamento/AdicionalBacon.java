package com.example.faturamento;

public class AdicionalBacon extends AdicionalDecorator {
    public AdicionalBacon(ItemVenda item) {
        super(item);
    }

    @Override
    public double calcularPreco() {
        return itemDecorado.calcularPreco() + 5.0;
    }

    @Override
    public String getDescricao() {
        return itemDecorado.getDescricao() + " + Bacon Crocante";
    }
}