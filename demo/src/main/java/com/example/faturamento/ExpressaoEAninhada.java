package com.example.faturamento;

public class ExpressaoEAninhada implements ExpressaoCupom {
    private ExpressaoCupom e1;
    private ExpressaoCupom e2;
    public ExpressaoEAninhada(ExpressaoCupom expressao1, ExpressaoCupom expressao2) { this.e1 = expressao1; this.e2 = expressao2; }
    @Override public boolean interpretar(String txt) { return e1.interpretar(txt) && e2.interpretar(txt); }
}
