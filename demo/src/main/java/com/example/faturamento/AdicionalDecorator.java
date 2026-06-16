package com.example.faturamento;

public abstract class AdicionalDecorator implements ItemVenda {
    protected ItemVenda itemDecorado;
    public AdicionalDecorator(ItemVenda item) { 
        this.itemDecorado = item; 
    }
}