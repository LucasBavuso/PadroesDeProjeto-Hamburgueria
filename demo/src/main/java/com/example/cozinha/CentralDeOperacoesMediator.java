package com.example.cozinha;

public class CentralDeOperacoesMediator implements CentralMediator {
    private AtendenteCaixa caixa;
    private OperadorCozinha cozinha;
    public void setCaixa(AtendenteCaixa c) { this.caixa = c; }
    public void setCozinha(OperadorCozinha cz) { this.cozinha = cz; }
    @Override
    public void enviarMensagem(String msg, ColaboradorLoja remetente) {
        if (remetente == caixa) cozinha.receberDirecionamento(msg);
    }
}
