package com.example.cozinha;

public class AtendenteCaixa extends ColaboradorLoja {
    public AtendenteCaixa(CentralMediator m) { super(m); }
    public void enviarOrdemProducao(String m) { mediator.enviarMensagem(m, this); }
    @Override public void receberDirecionamento(String msg) { System.out.println("Caixa leu o aviso: " + msg); }
}
