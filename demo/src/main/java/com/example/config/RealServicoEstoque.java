package com.example.config;

public class RealServicoEstoque implements ServicoEstoque {
    @Override public void alterarEstoque(int qtd) { 
        System.out.println("Estoque atualizado no Banco de Dados para: " + qtd); 
    }
}