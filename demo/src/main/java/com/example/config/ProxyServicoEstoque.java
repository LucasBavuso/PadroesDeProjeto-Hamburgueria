package com.example.config;

public class ProxyServicoEstoque implements ServicoEstoque {
    private RealServicoEstoque servicoReal = new RealServicoEstoque();
    private String cargoUsuario;

    public ProxyServicoEstoque(String cargo) { 
        this.cargoUsuario = cargo; 
    }

    @Override
    public void alterarEstoque(int qtd) {
        if ("GERENTE".equalsIgnoreCase(cargoUsuario)) {
            servicoReal.alterarEstoque(qtd);
        } else {
            System.out.println("[PROXY - ACESSO NEGADO] Apenas Gerentes podem alterar estoque!");
        }
    }
}