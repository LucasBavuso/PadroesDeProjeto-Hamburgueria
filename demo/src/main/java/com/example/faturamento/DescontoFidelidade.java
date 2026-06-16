package com.example.faturamento;

public class DescontoFidelidade extends DescontoHandler {
    @Override public double aplicarDesconto(double valor) {
        System.out.println("[CHAIN] Aplicado 5% de desconto fidelidade de veterano");
        valor = valor * 0.95;
        return proximo != null ? proximo.aplicarDesconto(valor) : valor;
    }
}
