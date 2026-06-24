package com.example.config;

public class RealServicoEstoque implements ServicoEstoque {
    private int estoqueAtual = 0;

    @Override 
    public void alterarEstoque(int qtd) { 
        this.estoqueAtual = qtd;
    }
    public int getEstoqueAtual() {
        return estoqueAtual;
    }
}