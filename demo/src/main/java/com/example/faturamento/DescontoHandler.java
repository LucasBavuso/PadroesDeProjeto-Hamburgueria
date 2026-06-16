package com.example.faturamento;

public abstract class DescontoHandler {
    protected DescontoHandler proximo;
    public void setProximo(DescontoHandler proximo) { this.proximo = proximo; }
    public abstract double aplicarDesconto(double valor);
}
