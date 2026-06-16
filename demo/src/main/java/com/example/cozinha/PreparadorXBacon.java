package com.example.cozinha;

public class PreparadorXBacon extends PreparadorHamburguerTemplate {
    @Override protected void grelharProteina() { System.out.println("[TEMPLATE] Grelhando Blend Bovino ao ponto do chef"); }
    @Override protected void adicionarMolhosEspeciais() { System.out.println("[TEMPLATE] Adicionando Maionese Verde e muito Bacon"); }
}
