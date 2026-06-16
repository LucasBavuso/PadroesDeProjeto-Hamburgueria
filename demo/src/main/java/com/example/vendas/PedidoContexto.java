package com.example.vendas;

import java.util.ArrayList;
import java.util.List;

public class PedidoContexto {
    private EstadoPedido estadoAtual = new EstadoRecebido();
    private List<Observer> obs = new ArrayList<>();

    public void registrarObserver(Observer o) { obs.add(o); }
    public void setEstado(EstadoPedido e) { 
        this.estadoAtual = e; 
        notificarTodos();
    }
    public EstadoPedido getEstado() { return estadoAtual; }
    public void proximoEstagio() { estadoAtual.avancar(this); }
    private void notificarTodos() { for(Observer o : obs) o.atualizar(estadoAtual.getNome()); }
}
