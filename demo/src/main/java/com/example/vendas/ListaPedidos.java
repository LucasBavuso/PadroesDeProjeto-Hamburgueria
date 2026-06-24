package com.example.vendas;

public class ListaPedidos {
    private String[] lista = { "Pedido #101", "Pedido #102", "Pedido #103" };

    public CustomIterator obterIterator() {
        return new InternoIterator();
    }

    private class InternoIterator implements CustomIterator {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < lista.length;
        }

        @Override
        public String next() {
            return lista[index++];
        }
    }
}
