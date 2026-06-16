package com.example.faturamento;

public class ImpostoEstadualVisitor implements ContabilidadeVisitor {
    @Override public void visitarPrato(PratoComida p) { System.out.println("[VISITOR] Calculado ICMS de 18% sobre o prato: R$ " + (p.getPreco() * 0.18)); }
}
