package com.example.cozinha;

public abstract class ColaboradorLoja {
    protected CentralMediator mediator;

    public ColaboradorLoja(CentralMediator m) {
        this.mediator = m;
    }

    public abstract void receberDirecionamento(String msg);
}
