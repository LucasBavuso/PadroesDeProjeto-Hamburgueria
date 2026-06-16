package com.example.faturamento;

public class PratoComida implements ElementoCardapio {
    private double preco;
    public PratoComida(double p) { this.preco = p; }
    public double getPreco() { return preco; }
    @Override public void aceitarVisitor(ContabilidadeVisitor v) { v.visitarPrato(this); }
}
