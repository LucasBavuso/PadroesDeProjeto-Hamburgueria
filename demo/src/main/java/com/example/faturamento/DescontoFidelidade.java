package com.example.faturamento;

public class DescontoFidelidade extends DescontoHandler {
    @Override
    public double aplicarDesconto(double valor) {
        valor = valor * 0.95;
        return proximo != null ? proximo.aplicarDesconto(valor) : valor;
    }
}