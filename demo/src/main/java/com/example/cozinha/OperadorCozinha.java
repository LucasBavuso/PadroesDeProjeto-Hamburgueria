package com.example.cozinha;

public class OperadorCozinha extends ColaboradorLoja {
    public OperadorCozinha(CentralMediator m) { super(m); }
    @Override public void receberDirecionamento(String msg) { System.out.println("Cozinha leu a ordem do Caixa: " + msg); }
}
