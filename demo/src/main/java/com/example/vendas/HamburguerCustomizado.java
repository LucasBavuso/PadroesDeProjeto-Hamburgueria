package com.example.vendas;

public class HamburguerCustomizado {
    private String pao;
    private String proteina;
    private boolean queijoExtra;
    private boolean molhoEspecial;

    private HamburguerCustomizado(Builder builder) {
        this.pao = builder.pao;
        this.proteina = builder.proteina;
        this.queijoExtra = builder.queijoExtra;
        this.molhoEspecial = builder.molhoEspecial;
    }

    @Override
    public String toString() {
        return "Burger Custom: " + pao + ", " + proteina + (queijoExtra ? " + Queijo Extra" : "") + (molhoEspecial ? " + Molho Especial" : "");
    }

    public static class Builder {
        private String pao; private String proteina;
        private boolean queijoExtra; private boolean molhoEspecial;

        public Builder(String pao, String proteina) { this.pao = pao; this.proteina = proteina; }
        public Builder comQueijoExtra() { this.queijoExtra = true; return this; }
        public Builder comMolhoEspecial() { this.molhoEspecial = true; return this; }
        public HamburguerCustomizado build() { return new HamburguerCustomizado(this); }
    }
}
