package com.example.vendas;

import com.example.config.GerenciadorCaixa;
import com.example.config.RealServicoEstoque;

public class HamburgueriaFacade {
    public void fecharPedidoCompleto(String detalhesPedido, double valor) {
        System.out.println("[FACADE] Iniciando transação unificada...");
        GerenciadorCaixa.getInstance().registrarVenda(valor);
        new RealServicoEstoque().alterarEstoque(-1);
        System.out.println("[FACADE] Sucesso! Pedido finalizado: " + detalhesPedido);
    }
}