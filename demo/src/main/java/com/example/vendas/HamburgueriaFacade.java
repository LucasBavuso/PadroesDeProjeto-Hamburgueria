package com.example.vendas;

import com.example.config.GerenciadorCaixa;
import com.example.config.RealServicoEstoque;

public class HamburgueriaFacade {
    private boolean transacaoFinalizadaComSucesso = false;

    public void fecharPedidoCompleto(String detalhesPedido, double valor) {
        this.transacaoFinalizadaComSucesso = false;
        
        GerenciadorCaixa.getInstance().registrarVenda(valor);
        new RealServicoEstoque().alterarEstoque(-1);
        
        this.transacaoFinalizadaComSucesso = true;
    }

    public boolean isTransacaoFinalizadaComSucesso() {
        return transacaoFinalizadaComSucesso;
    }
}