package com.example.cozinha;

public abstract class PreparadorHamburguerTemplate {
    public final void prepararComboFinal() {
        tostarPao();
        grelharProteina();
        adicionarMolhosEspeciais();
        embalar();
    }

    private void tostarPao() { System.out.println("[TEMPLATE] Tostando pão na chapa com manteiga"); }
    private void embalar() { System.out.println("[TEMPLATE] Embalando em papel térmico personalizado"); }

    protected abstract void grelharProteina();
    protected abstract void adicionarMolhosEspeciais();
}
