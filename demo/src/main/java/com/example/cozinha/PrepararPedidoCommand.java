package com.example.cozinha;

public class PrepararPedidoCommand implements ComandaCommand {
    private CozinhaReceiver cozinha;
    public PrepararPedidoCommand(CozinhaReceiver c) { this.cozinha = c; }
    @Override public void execute() { cozinha.producirBurger(); }
    @Override public void undo() { cozinha.descartarBurger(); }
}
