package com.example.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorCaixa {
    private static GerenciadorCaixa instancia;
    private double saldoDiario = 0.0;

    private GerenciadorCaixa() {}

    public static synchronized GerenciadorCaixa getInstance() {
        if (instancia == null) {
            instancia = new GerenciadorCaixa();
        }
        return instancia;
    }

    public void registrarVenda(double valor) { 
        this.saldoDiario += valor; 
    }
    public double getSaldoDiario() { 
        return saldoDiario; 
    }
}
