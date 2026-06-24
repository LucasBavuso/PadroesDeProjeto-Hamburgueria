package com.example.faturamento;

public class ContemItemExpressao implements ExpressaoCupom {
    private String item;

    public ContemItemExpressao(String i) {
        this.item = i;
    }

    @Override
    public boolean interpretar(String txt) {
        return txt.toUpperCase().contains(item.toUpperCase());
    }
}
