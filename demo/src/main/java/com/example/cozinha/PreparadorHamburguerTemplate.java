package com.example.cozinha;

public abstract class PreparadorHamburguerTemplate {
    
    public final void prepararComboFinal() {
        tostarPao();
        grelharProteina();
        adicionarMolhosEspeciais();
        embalar();
    }

    private void tostarPao() {
    }

    private void embalar() {
    }

    protected abstract void grelharProteina();

    protected abstract void adicionarMolhosEspeciais();
}