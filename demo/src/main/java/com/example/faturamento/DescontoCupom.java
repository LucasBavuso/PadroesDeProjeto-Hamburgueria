package com.example.faturamento;

public class DescontoCupom extends DescontoHandler {
    @Override
    public double aplicarDesconto(double valor) {
        if (valor > 50) {
            valor -= 10;
        }
        return proximo != null ? proximo.aplicarDesconto(valor) : valor;
    }
}