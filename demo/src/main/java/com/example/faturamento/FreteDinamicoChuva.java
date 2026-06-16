package com.example.faturamento;

public class FreteDinamicoChuva implements FreteStrategy {
    @Override public double calcular(double d) { return d * 3.50; }
}
