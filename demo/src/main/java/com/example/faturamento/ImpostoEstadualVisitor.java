package com.example.faturamento;

public class ImpostoEstadualVisitor implements ContabilidadeVisitor {
    private double ultimoCalculoIcms = 0.0;

    @Override
    public void visitarPrato(PratoComida p) {
        this.ultimoCalculoIcms = p.getPreco() * 0.18;
    }

    public double getUltimoCalculoIcms() {
        return ultimoCalculoIcms;
    }
}