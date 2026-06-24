package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.config.*;
import com.example.cozinha.*;
import com.example.faturamento.*;
import com.example.vendas.*;

import static org.junit.jupiter.api.Assertions.*;

public class HamburgueriaCompletaTest {

    @Test
    public void testSingletonCaixa() {
        GerenciadorCaixa c1 = GerenciadorCaixa.getInstance();
        GerenciadorCaixa c2 = GerenciadorCaixa.getInstance();
        assertSame(c1, c2, "Singleton falhou: instâncias diferentes na memória.");

        c1.registrarVenda(50.0);
        assertEquals(50.0, c2.getSaldoDiario(), 0.001);
    }

    @Test
    public void testAbstractFactoryInsumos() {
        FabricaInsumos trad = new FabricaTradicional();
        assertTrue(trad.criarPao() instanceof PaoBrioche);
        assertTrue(trad.criarProteina() instanceof BurgerBovino);

        FabricaInsumos veg = new FabricaVegana();
        assertTrue(veg.criarPao() instanceof PaoVegano);
        assertTrue(veg.criarProteina() instanceof BurgerFuturo);
    }

    @Test
    public void testPrototypeClonagem() {
        ItemCardapioPrototype xBaconOriginal = RegistroPrototypes.getPrototype("X-Bacon");
        ItemCardapioPrototype xBaconClonado = xBaconOriginal.clone();

        assertNotSame(xBaconOriginal, xBaconClonado);
        assertEquals(xBaconOriginal.getNome(), xBaconClonado.getNome());

        xBaconClonado.addIngrediente("Cheddar");
        assertFalse(xBaconOriginal.getIngredientes().contains("Cheddar"));
        assertTrue(xBaconClonado.getIngredientes().contains("Cheddar"));
    }

    @Test
    public void testBuilderHamburguer() {
        HamburguerCustomizado burger = new HamburguerCustomizado.Builder("Brioche", "Costela")
                .comQueijoExtra()
                .comMolhoEspecial()
                .build();

        String resultadoTextual = burger.toString();
        assertTrue(resultadoTextual.contains("Brioche"));
        assertTrue(resultadoTextual.contains("Costela"));
        assertTrue(resultadoTextual.contains("Queijo Extra"));
        assertTrue(resultadoTextual.contains("Molho Especial"));
    }

    @Test
    public void testFactoryMethodCombos() {
        FabricaDeCombo f1 = new CriadorComboKids();
        Produto p1 = f1.criarCombo();
        assertTrue(p1 instanceof ComboKids);
        assertTrue(p1.getNome().contains("Kids"));

        FabricaDeCombo f2 = new CriadorComboBrabo();
        Produto p2 = f2.criarCombo();
        assertTrue(p2 instanceof ComboBrabo);
        assertTrue(p2.getNome().contains("Brabo"));
    }

    @Test
    public void testAdapterIFood() {
        PedidoIFoodExterno apiExterna = new PedidoIFoodExterno();
        PedidoNativo adaptador = new IFoodAdapter(apiExterna);

        assertTrue(adaptador.getDescricaoPedido().contains("Convertido"));
        assertTrue(adaptador.getDescricaoPedido().contains("iFood JSON"));
    }

    @Test
    public void testBridgeEntregaNotificacao() {
        assertDoesNotThrow(() -> {
            Entrega entrega1 = new EntregaDelivery(new CanalWhatsApp());
            entrega1.despachar();

            Entrega entrega2 = new EntregaDelivery(new CanalSMS());
            entrega2.despachar();
        });
    }

    @Test
    public void testCompositeCardapio() {
        SecaoCardapioComposite comboFamilia = new SecaoCardapioComposite("Combo Família");
        comboFamilia.adicionar(new ItemSimples("Hambúrguer", 25.0));
        comboFamilia.adicionar(new ItemSimples("Batata", 10.0));
        comboFamilia.adicionar(new ItemSimples("Refrigerante", 5.0));

        assertEquals(40.0, comboFamilia.getPreco(), 0.001);
        assertDoesNotThrow(() -> comboFamilia.exibir());
    }

    @Test
    public void testDecoratorAdicionais() {
        ItemVenda pedido = new HamburguerBase();
        pedido = new AdicionalBacon(pedido);

        assertEquals(30.0, pedido.calcularPreco(), 0.001);
        assertTrue(pedido.getDescricao().contains("Hambúrguer Clássico"));
        assertTrue(pedido.getDescricao().contains("Bacon Crocante"));
    }

    @Test
    public void testFlyweightCompartilhamento() {
        DetalhesIngredienteFlyweight fly1 = FlyweightFactory.getIngrediente("Cheddar");
        DetalhesIngredienteFlyweight fly2 = FlyweightFactory.getIngrediente("Cheddar");
        DetalhesIngredienteFlyweight fly3 = FlyweightFactory.getIngrediente("Cebola");

        assertSame(fly1, fly2, "Flyweight Factory falhou: recriou instância idêntica.");
        assertNotSame(fly1, fly3);
        assertEquals("Cheddar", fly1.getNome());
    }

    @Test
    public void testProxySegurancaBarrarFuncionario() {
        ProxyServicoEstoque proxyFuncionario = new ProxyServicoEstoque("FUNCIONARIO");
        SecurityException excecao = assertThrows(SecurityException.class, () -> {
            proxyFuncionario.alterarEstoque(10);
        }, "O Proxy deveria ter lançado uma SecurityException para o funcionário comum.");

        assertTrue(excecao.getMessage().contains("ACESSO NEGADO"));
    }

    @Test
    public void testProxySegurancaPermitirGerente() {
        ProxyServicoEstoque proxyGerente = new ProxyServicoEstoque("GERENTE");
        assertDoesNotThrow(() -> proxyGerente.alterarEstoque(5),
            "O Proxy não deveria ter barrado o acesso do GERENTE.");
        assertEquals(5, proxyGerente.getServicoReal().getEstoqueAtual());
    }

    @Test
    public void testFacadeCheckout() {
        HamburgueriaFacade fachada = new HamburgueriaFacade();
        assertDoesNotThrow(() -> fachada.fecharPedidoCompleto("1x Artesanal", 35.0));
    }

    @Test
    public void testVisitorCalcularIcmsSucesso() {
        PratoComida prato = new PratoComida(50.0);
        ImpostoEstadualVisitor visitor = new ImpostoEstadualVisitor();
        prato.aceitarVisitor(visitor);
        assertEquals(9.0, visitor.getUltimoCalculoIcms(), 0.001, 
            "O cálculo do ICMS (18% de 50.0 = 9.0) falhou.");
    }

    @Test
    public void testBridge_CanalWhatsApp_Sucesso() {
        CanalWhatsApp whats = new CanalWhatsApp();
        whats.enviar("Seu Combo Brabo saiu para entrega!");
        assertEquals("Seu Combo Brabo saiu para entrega!", whats.getUltimaMensagemEnviada(),
            "A mensagem enviada não corresponde à armazenada no canal.");
    }

    @Test
    public void testStrategyFrete() {
        FreteStrategy fixo = new FreteFixoMoto();
        assertEquals(7.00, fixo.calcular(10.0), 0.001);

        FreteStrategy chuva = new FreteDinamicoChuva();
        assertEquals(14.00, chuva.calcular(4.0), 0.001);
    }

    @Test
    public void testChainResponsibilityDescontos() {
        DescontoHandler c1 = new DescontoCupom();
        DescontoHandler c2 = new DescontoFidelidade();
        c1.setProximo(c2);

        double valorFinal = c1.aplicarDesconto(60.0);
        assertEquals(47.5, valorFinal, 0.001);
    }

    @Test
    public void testTemplateMethodPreparo() {
        PreparadorHamburguerTemplate cozinha = new PreparadorXBacon();
        assertDoesNotThrow(() -> cozinha.prepararComboFinal());
    }

    @Test
    public void testStateEObserverPedido() {
        PedidoContexto pedido = new PedidoContexto();
        NotificadorPainelCliente painel = new NotificadorPainelCliente();
        pedido.registrarObserver(painel);
        assertEquals("Recebido", pedido.getEstado().getNome());
        pedido.proximoEstagio();
        assertEquals("Preparando", pedido.getEstado().getNome());
        pedido.proximoEstagio();
        assertEquals("Pronto", pedido.getEstado().getNome());
        assertThrows(IllegalStateException.class, () -> pedido.proximoEstagio(),
                "O sistema deveria ter barrado a tentativa de avançar além do estado Pronto.");
    }

    @Test
    public void testCommandCozinha() {
        CozinhaReceiver chapa = new CozinhaReceiver();
        ComandaCommand comando = new PrepararPedidoCommand(chapa);

        assertDoesNotThrow(() -> comando.execute());
        assertDoesNotThrow(() -> comando.undo());
    }

    @Test
    public void testMementoCustomizacao() {
        OriginatorBurgerCustom lanche = new OriginatorBurgerCustom();
        CaretakerHistorico historico = new CaretakerHistorico();

        lanche.setIngredientes("Pão, Carne");
        historico.salvar(lanche);

        lanche.setIngredientes("Pão, Carne, Bacon, Picles, Cebola, Ovo");
        assertEquals("Pão, Carne, Bacon, Picles, Cebola, Ovo", lanche.getIngredientes());

        historico.desfazer(lanche);
        assertEquals("Pão, Carne", lanche.getIngredientes(), "Memento falhou ao aplicar o Ctrl+Z.");
    }

    @Test
    public void testIteratorPedidos() {
        ListaPedidos colecao = new ListaPedidos();
        CustomIterator iterador = colecao.obterIterator();

        int contagem = 0;
        while (iterador.hasNext()) {
            String pedido = iterador.next();
            assertNotNull(pedido);
            contagem++;
        }
        assertEquals(3, contagem, "Iterator deveria varrer exatamente os 3 itens da lista interna.");
    }

    @Test
    public void testMediatorCentral() {
        CentralDeOperacoesMediator hub = new CentralDeOperacoesMediator();
        AtendenteCaixa caixa = new AtendenteCaixa(hub);
        OperadorCozinha cozinha = new OperadorCozinha(hub);

        hub.setCaixa(caixa);
        hub.setCozinha(cozinha);

        assertDoesNotThrow(() -> caixa.enviarOrdemProducao("Mesa 02: 1x Combo Brabo"));
    }

    @Test
    public void testInterpreterCupons() {
        ExpressaoCupom expressaoRegra = new ExpressaoEAninhada(
                new ContemItemExpressao("BURGER"),
                new ContemItemExpressao("BATATA"));

        assertTrue(expressaoRegra.interpretar("No carrinho tem BURGER, REFRIGERANTE e BATATA"));
        assertFalse(expressaoRegra.interpretar("No carrinho tem BURGER e REFRIGERANTE")); 
    }

    @Test
    public void testVisitorImpostos() {
        PratoComida itemRefeicao = new PratoComida(50.00);
        ImpostoEstadualVisitor fiscal = new ImpostoEstadualVisitor();

        assertEquals(50.00, itemRefeicao.getPreco());
        assertDoesNotThrow(() -> itemRefeicao.aceitarVisitor(fiscal));
    }

    @Test
    public void testFalhaCommandUndoSemExecute() {
        CozinhaReceiver chapa = new CozinhaReceiver();
        ComandaCommand comando = new PrepararPedidoCommand(chapa);

        assertDoesNotThrow(() -> comando.undo(),
                "O rollback do comando falhou ao lidar com um estado sem execução prévia.");
    }

    @Test
    public void testFalhaBuilderValoresVazios() {
        HamburguerCustomizado burgerFantasma = new HamburguerCustomizado.Builder(null, "")
                .build();

        assertNotNull(burgerFantasma);
        assertTrue(burgerFantasma.toString().contains("null"));
    }

    @Test
    public void testFalhaStrategyValoresInvalidos() {
        FreteStrategy chuva = new FreteDinamicoChuva();

        assertEquals(0.0, chuva.calcular(0.0), 0.001);

        assertEquals(-7.0, chuva.calcular(-2.0), 0.001);
    }

    @Test
    public void testFalhaCompositeSemFilhos() {
        SecaoCardapioComposite secaoVazia = new SecaoCardapioComposite("Bebidas Alcoólicas");

        assertEquals(0.0, secaoVazia.getPreco(), 0.001,
                "Uma seção vazia do cardápio deveria registrar preço total igual a zero.");
        assertDoesNotThrow(() -> secaoVazia.exibir());
    }

    @Test
    public void testFalhaIteratorEstouroDeLimite() {
        ListaPedidos colecao = new ListaPedidos();
        CustomIterator iterador = colecao.obterIterator();

        iterador.next();
        iterador.next();
        iterador.next();

        assertFalse(iterador.hasNext(), "O iterador não percebeu o fim da linha da coleção.");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            iterador.next();
        }, "O iterador deveria barrar o avanço além do tamanho físico do array.");
    }

    @Test
    public void testFalhaMediatorColaboradoresDesconectados() {
        CentralDeOperacoesMediator hubOrfao = new CentralDeOperacoesMediator();
        AtendenteCaixa caixaSolto = new AtendenteCaixa(hubOrfao);

        assertDoesNotThrow(() -> caixaSolto.enviarOrdemProducao("Mesa 10: 1x Batata"));
    }

    @Test
    public void testDecoratorMultiplosAdicionaisIguais() {
        ItemVenda burger = new HamburguerBase();
        burger = new AdicionalBacon(burger);
        burger = new AdicionalBacon(burger);

        assertEquals(35.0, burger.calcularPreco(), 0.001);
        assertTrue(burger.getDescricao().contains("Bacon Crocante + Bacon Crocante"));
    }

    @Test
    public void testSucessoProxyGerente() {
        ServicoEstoque estoquePermitido = new ProxyServicoEstoque("GERENTE");

        assertDoesNotThrow(() -> estoquePermitido.alterarEstoque(150),
                "O Proxy deveria permitir que o Gerente alterasse o estoque sem restrições.");
    }

    @Test
    public void testSucessoStrategyFreteFixo() {
        FreteStrategy fixo = new FreteFixoMoto();

        assertEquals(7.00, fixo.calcular(2.0), 0.001);
        assertEquals(7.00, fixo.calcular(50.0), 0.001);
    }

    @Test
    public void testReceiverProduzirBurgerSucesso() {
        CozinhaReceiver chapa = new CozinhaReceiver();
        assertEquals("Ocioso", chapa.getUltimoStatus(), "O Receiver deveria iniciar no estado 'Ocioso'.");
        chapa.producirBurger();
        assertEquals("Chapeando", chapa.getUltimoStatus(), 
            "Erro: O status deveria ter mudado para 'Chapeando'.");
    }

    @Test
    public void testReceiverDescartarBurgerSucesso() {
        CozinhaReceiver chapa = new CozinhaReceiver();
        chapa.descartarBurger();
        assertEquals("Cancelado", chapa.getUltimoStatus(), 
            "Erro: O status deveria ter mudado para 'Cancelado'.");
    }

    @Test
    public void testObserverAtualizarSucesso() {
        NotificadorPainelCliente painel = new NotificadorPainelCliente();
        assertEquals("", painel.getUltimoStatusRecebido(), "Deveria iniciar vazio.");
        painel.atualizar("PRONTO_PARA_RETIRADA");
        assertEquals("PRONTO_PARA_RETIRADA", painel.getUltimoStatusRecebido(),
            "O Observer deveria ter armazenado o status recebido na variável de estado.");
    }

    @Test
    public void testEstadoProntoGetNome() {
        EstadoPedido estado = new EstadoPronto();
        assertEquals("Pronto", estado.getNome());
    }

    @Test
    public void testEstadoProntoAvancarDeveLancarExcecao() {
        EstadoPedido estado = new EstadoPronto();
        PedidoContexto contextoMock = new PedidoContexto(); 
        IllegalStateException excecao = assertThrows(IllegalStateException.class, () -> {
            estado.avancar(contextoMock);
        }, "Deveria ter lançado uma exceção pois o estado Pronto não pode avançar.");

        assertTrue(excecao.getMessage().contains("O pedido já foi entregue"));
    }

    @Test
    public void testRealServicoEstoqueAlterarEstoqueSucesso() {
        RealServicoEstoque servico = new RealServicoEstoque();
        assertEquals(0, servico.getEstoqueAtual());
        servico.alterarEstoque(45);
        assertEquals(45, servico.getEstoqueAtual(), 
            "O estado simulado do banco de dados deveria registrar exatamente 45 itens.");
    }

    @Test
    public void testAtendenteCaixaReceberDirecionamentoSucesso() {
        CentralDeOperacoesMediator hub = new CentralDeOperacoesMediator();
        AtendenteCaixa caixa = new AtendenteCaixa(hub);
        caixa.receberDirecionamento("Cozinha sem pão brioche!");
        assertEquals("Cozinha sem pão brioche!", caixa.getUltimaMensagemRecebida(),
            "O Caixa deveria ter guardado internamente o aviso vindo do mediador.");
    }

    @Test
    public void testOperadorCozinhaReceberDirecionamentoSucesso() {
        CentralDeOperacoesMediator hub = new CentralDeOperacoesMediator();
        OperadorCozinha operador = new OperadorCozinha(hub);
        operador.receberDirecionamento("Preparar 1x X-Bacon imediatamente");
        assertEquals("Preparar 1x X-Bacon imediatamente", operador.getUltimaOrdemRecebida(),
            "O Operador da Cozinha deveria ter guardado a ordem recebida do Mediator na sua variável de estado.");
    }

    @Test
    @DisplayName("[28] Falha - Composite sem sub-elementos")
    public void test28() {
        assertEquals(0.0, new SecaoCardapioComposite("Vazia").getPreco(), 0.001);
    }

    @Test
    @DisplayName("[29] Falha - Iterator estouro físico de array")
    public void test29() {
        CustomIterator it = new ListaPedidos().obterIterator();
        it.next();
        it.next();
        it.next();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> it.next());
    }

    @Test
    public void testChainDescontosFluxoCompleto() {
        DescontoHandler cupom = new DescontoCupom();
        DescontoHandler fidelidade = new DescontoFidelidade();
        cupom.setProximo(fidelidade);
        double valorFinal = cupom.aplicarDesconto(60.0);
        assertEquals(47.5, valorFinal, 0.001, "O cálculo acumulado da esteira falhou.");
    }

    @Test
    public void testBridgeCanalSMSSucesso() {
        CanalSMS sms = new CanalSMS();
        sms.enviar("Seu burger está pronto!");
        assertEquals("Seu burger está pronto!", sms.getUltimaMensagemEnviada());
    }

    @Test
    public void testCompositeItemSimplesValores() {
        ItemSimples batata = new ItemSimples("Batata Frita", 12.50);
        
        assertEquals(12.50, batata.getPreco(), 0.001);
        assertEquals("Batata Frita", batata.getNome());
        assertDoesNotThrow(() -> batata.exibir());
    }

    @Test
    public void testFacadeFecharPedidoCompletoSucesso() {
        HamburgueriaFacade fachada = new HamburgueriaFacade();
        assertFalse(fachada.isTransacaoFinalizadaComSucesso());

        fachada.fecharPedidoCompleto("1x Combo Kids", 25.0);

        assertTrue(fachada.isTransacaoFinalizadaComSucesso(), 
            "A Facade falhou em concluir todas as etapas da transação unificada.");
    }

    @Test
    public void testPaoBriocheGetNome() {
        Pao pao = new PaoBrioche();
        assertEquals("Pão Brioche", pao.getNome(), 
            "O nome retornado pelo PaoBrioche está incorreto.");
    }

    @Test
    public void testPaoVeganoGetNome() {
        Pao pao = new PaoVegano();
        assertEquals("Pão de Fermentação Natural (Sem Leite)", pao.getNome(), 
            "O nome retornado pelo PaoVegano está incorreto.");
    }

    @Test
    public void testBurgerFuturoGetNome() {
        Proteina proteina = new BurgerFuturo();
        assertEquals("Hambúrguer de Planta", proteina.getNome(), 
            "O nome retornado pelo BurgerFuturo está incorreto.");
    }

    @Test
    public void testBurgerBovinoGetNome() {
        Proteina proteina = new BurgerBovino();
        assertEquals("Blend Bovino 200g", proteina.getNome(), 
            "O nome retornado pelo BurgerBovino está incorreto.");
    }

    @Test
    @DisplayName("[30] Falha - Mediator sem colaboradores plugados")
    public void test30() {
        assertDoesNotThrow(() -> new AtendenteCaixa(new CentralDeOperacoesMediator()).enviarOrdemProducao("Teste"));
    }

    @Test
    @DisplayName("[31] Falha - Decorator triplo idêntico acumulado")
    public void test31() {
        ItemVenda v = new AdicionalBacon(new AdicionalBacon(new AdicionalBacon(new HamburguerBase())));
        assertEquals(40.0, v.calcularPreco(), 0.001);
    }

    @Test
    @DisplayName("[Prototype] Deve forçar CloneNotSupportedException e cobrir o bloco catch")
    public void testPrototype_ForçarCatchException() {
        ItemCardapioPrototype prototipoSabotado = new ItemCardapioPrototype("X-Fake") {
            @Override
            public ItemCardapioPrototype clone() {
                try {
                    throw new CloneNotSupportedException("Simulação de falha de hardware/JVM");
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        assertThrows(RuntimeException.class, () -> {
            prototipoSabotado.clone();
        }, "O teste deveria ter capturado a RuntimeException gerada dentro do bloco catch.");
    }

    @Test
    @DisplayName("[32] Sucesso - Proxy liberação para Gerente")
    public void test32() {
        assertDoesNotThrow(() -> new ProxyServicoEstoque("GERENTE").alterarEstoque(5));
    }

    @Test
    @DisplayName("[33] Sucesso - Strategy frete fixo longo")
    public void test33() {
        assertEquals(7.0, new FreteFixoMoto().calcular(100.0), 0.001);
    }

    @Test
    @DisplayName("[34] Interpreter - Validação de Cupom Válido")
    public void test34() {
        assertTrue(new ExpressaoEAninhada(new ContemItemExpressao("BURGER"), new ContemItemExpressao("BATATA"))
                .interpretar("BURGER BATATA"));
    }

    @Test
    @DisplayName("[35] Visitor - Execução fiscal isolada")
    public void test35() {
        assertDoesNotThrow(() -> new PratoComida(50.0).aceitarVisitor(new ImpostoEstadualVisitor()));
    }

    @Test
    @DisplayName("[36] Mediator - Comunicação centralizada")
    public void test36() {
        CentralDeOperacoesMediator hub = new CentralDeOperacoesMediator();
        AtendenteCaixa cx = new AtendenteCaixa(hub);
        hub.setCaixa(cx);
        hub.setCozinha(new OperadorCozinha(hub));
        assertDoesNotThrow(() -> cx.enviarOrdemProducao("Mesa 1"));
    }

    @Test
    @DisplayName("[37] Memento - Gravação de múltiplos snapshots")
    public void test37() {
        OriginatorBurgerCustom o = new OriginatorBurgerCustom();
        CaretakerHistorico c = new CaretakerHistorico();
        o.setIngredientes("A");
        c.salvar(o);
        o.setIngredientes("B");
        c.salvar(o);
        o.setIngredientes("C");
        c.desfazer(o);
        assertEquals("B", o.getIngredientes());
    }

    @Test
    @DisplayName("[38] State - Avanço completo até o fim")
    public void test38() {
        PedidoContexto p = new PedidoContexto();
        p.proximoEstagio();
        p.proximoEstagio();
        assertEquals("Pronto", p.getEstado().getNome());
    }

    @Test
    @DisplayName("[39] Observer - Registro reativo múltiplo")
    public void test39() {
        PedidoContexto p = new PedidoContexto();
        p.registrarObserver(new NotificadorPainelCliente());
        assertDoesNotThrow(() -> p.proximoEstagio());
    }

    @Test
    @DisplayName("[40] Singleton - Registro consecutivo de caixa")
    public void test40() {
        GerenciadorCaixa.getInstance().registrarVenda(10.0);
        assertTrue(GerenciadorCaixa.getInstance().getSaldoDiario() > 0);
    }

    @Test
    @DisplayName("[41] Prototype - Adição isolada no clone")
    public void test41() {
        ItemCardapioPrototype c = RegistroPrototypes.getPrototype("X-Bacon").clone();
        c.addIngrediente("Molho");
        assertTrue(c.getIngredientes().contains("Molho"));
    }

    @Test
    @DisplayName("[42] Builder - Validação textual de queijo extra")
    public void test42() {
        assertTrue(new HamburguerCustomizado.Builder("A", "B").comQueijoExtra().build().toString()
                .contains("Queijo Extra"));
    }

    @Test
    @DisplayName("[43] Builder - Validação textual de molho")
    public void test43() {
        assertTrue(new HamburguerCustomizado.Builder("A", "B").comMolhoEspecial().build().toString()
                .contains("Molho Especial"));
    }

    @Test
    @DisplayName("[44] Composite - Exibição estrutural da seção")
    public void test44() {
        assertDoesNotThrow(() -> new SecaoCardapioComposite("A").exibir());
    }

    @Test
    @DisplayName("[45] Flyweight - Verificação de chaves distintas")
    public void test45() {
        assertNotSame(FlyweightFactory.getIngrediente("Cheddar"), FlyweightFactory.getIngrediente("Cebola"));
    }

    @Test
    @DisplayName("[46] Flyweight - Consistência de nomes")
    public void test46() {
        assertEquals("Cheddar", FlyweightFactory.getIngrediente("Cheddar").getNome());
    }

    @Test
    @DisplayName("[47] Decorator - Verificação de descrição base")
    public void test47() {
        assertTrue(new HamburguerBase().getDescricao().contains("Hambúrguer Clássico"));
    }

    @Test
    @DisplayName("[48] Chain - Encadeamento nulo ou final")
    public void test48() {
        assertEquals(50.0, new DescontoCupom().aplicarDesconto(50.0), 0.001);
    }

    @Test
    @DisplayName("[49] Interpreter - Rejeição de cupom incompleto")
    public void test49() {
        assertFalse(new ExpressaoEAninhada(new ContemItemExpressao("BURGER"), new ContemItemExpressao("BATATA"))
                .interpretar("Só BURGER"));
    }

    @Test
    @DisplayName("[50] Falha - Memento com pilha vazia de Ctrl+Z")
    public void test50() {
        assertDoesNotThrow(() -> new CaretakerHistorico().desfazer(new OriginatorBurgerCustom()));
    }

    
}
