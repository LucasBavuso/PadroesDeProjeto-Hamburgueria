package com.example.faturamento;

public class HamburguerBase implements ItemVenda {
    @Override public double calcularPreco() { 
        return 25.0; 
    }
    @Override public String getDescricao() { 
        return "Hambúrguer Clássico"; 
    }
}