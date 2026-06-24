package com.example.cozinha;


public class AtendenteCaixa extends ColaboradorLoja {
    private String ultimaMensagemRecebida = "";

    public AtendenteCaixa(CentralMediator m) { 
        super(m); 
    }

    public void enviarOrdemProducao(String m) { 
        mediator.enviarMensagem(m, this); 
    }

    @Override 
    public void receberDirecionamento(String msg) { 
        this.ultimaMensagemRecebida = msg;
    }

    public String getUltimaMensagemRecebida() {
        return ultimaMensagemRecebida;
    }
}