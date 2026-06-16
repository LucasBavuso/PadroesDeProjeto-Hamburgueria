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
    @DisplayName("[Singleton] Deve garantir instância única do Caixa")
    public void testSingletonCaixa() {
        GerenciadorCaixa c1 = GerenciadorCaixa.getInstance();
        GerenciadorCaixa c2 = GerenciadorCaixa.getInstance();
        assertSame(c1, c2, "Singleton falhou: instâncias diferentes na memória.");
        
        c1.registrarVenda(50.0);
        assertEquals(50.0, c2.getSaldoDiario(), 0.001);
    }

    @Test
    @DisplayName("[Abstract Factory] Deve criar famílias de insumos Tradicionais e Veganos")
    public void testAbstractFactoryInsumos() {
        FabricaInsumos trad = new FabricaTradicional();
        assertTrue(trad.criarPao() instanceof PaoBrioche);
        assertTrue(trad.criarProteina() instanceof BurgerBovino);

        FabricaInsumos veg = new FabricaVegana();
        assertTrue(veg.criarPao() instanceof PaoVegano);
        assertTrue(veg.criarProteina() instanceof BurgerFuturo);
    }

    @Test
    @DisplayName("[Prototype] Deve clonar receitas isolando as listas de ingredientes")
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
    @DisplayName("[Builder] Deve construir hambúrguer customizado com os opcionais corretos")
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
    @DisplayName("[Factory Method] Deve fabricar combos específicos via subclasses")
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
    @DisplayName("[Adapter] Deve adaptar objeto JSON externo do iFood para a interface nativa")
    public void testAdapterIFood() {
        PedidoIFoodExterno apiExterna = new PedidoIFoodExterno();
        PedidoNativo adaptador = new IFoodAdapter(apiExterna);
        
        assertTrue(adaptador.getDescricaoPedido().contains("Convertido"));
        assertTrue(adaptador.getDescricaoPedido().contains("iFood JSON"));
    }

    @Test
    @DisplayName("[Bridge] Deve desacoplar e permitir combinar entregas e canais")
    public void testBridgeEntregaNotificacao() {
        assertDoesNotThrow(() -> {
            Entrega entrega1 = new EntregaDelivery(new CanalWhatsApp());
            entrega1.despachar();

            Entrega entrega2 = new EntregaDelivery(new CanalSMS());
            entrega2.despachar();
        });
    }

    @Test
    @DisplayName("[Composite] Deve somar preços recursivamente em estruturas de cardápio")
    public void testCompositeCardapio() {
        SecaoCardapioComposite comboFamilia = new SecaoCardapioComposite("Combo Família");
        comboFamilia.adicionar(new ItemSimples("Hambúrguer", 25.0));
        comboFamilia.adicionar(new ItemSimples("Batata", 10.0));
        comboFamilia.adicionar(new ItemSimples("Refrigerante", 5.0));

        assertEquals(40.0, comboFamilia.getPreco(), 0.001);
        assertDoesNotThrow(() -> comboFamilia.exibir());
    }

    @Test
    @DisplayName("[Decorator] Deve acumular preços e descrições de múltiplos adicionais")
    public void testDecoratorAdicionais() {
        ItemVenda pedido = new HamburguerBase(); 
        pedido = new AdicionalBacon(pedido);    
        
        assertEquals(30.0, pedido.calcularPreco(), 0.001);
        assertTrue(pedido.getDescricao().contains("Hambúrguer Clássico"));
        assertTrue(pedido.getDescricao().contains("Bacon Crocante"));
    }

    @Test
    @DisplayName("[Flyweight] Deve compartilhar a mesma instância de metadados na memória")
    public void testFlyweightCompartilhamento() {
        DetalhesIngredienteFlyweight fly1 = FlyweightFactory.getIngrediente("Cheddar");
        DetalhesIngredienteFlyweight fly2 = FlyweightFactory.getIngrediente("Cheddar");
        DetalhesIngredienteFlyweight fly3 = FlyweightFactory.getIngrediente("Cebola");

        assertSame(fly1, fly2, "Flyweight Factory falhou: recriou instância idêntica.");
        assertNotSame(fly1, fly3);
        assertEquals("Cheddar", fly1.getNome());
    }

    @Test
    @DisplayName("[Proxy] Deve permitir alteração de estoque para GERENTE e barrar para cargos comuns")
    public void testProxySeguranca() {
        ProxyServicoEstoque proxyFuncionario = new ProxyServicoEstoque("FUNCIONARIO");
        assertDoesNotThrow(() -> proxyFuncionario.alterarEstoque(10));

        ProxyServicoEstoque proxyGerente = new ProxyServicoEstoque("GERENTE");
        assertDoesNotThrow(() -> proxyGerente.alterarEstoque(5));
    }

    @Test
    @DisplayName("[Facade] Deve rodar o fluxo completo de checkout em uma única chamada simplificada")
    public void testFacadeCheckout() {
        HamburgueriaFacade fachada = new HamburgueriaFacade();
        assertDoesNotThrow(() -> fachada.fecharPedidoCompleto("1x Artesanal", 35.0));
    }

    @Test
    @DisplayName("[Strategy] Deve alternar os algoritmos de frete (Fixo vs Dinâmico)")
    public void testStrategyFrete() {
        FreteStrategy fixo = new FreteFixoMoto();
        assertEquals(7.00, fixo.calcular(10.0), 0.001);

        FreteStrategy chuva = new FreteDinamicoChuva();
        assertEquals(14.00, chuva.calcular(4.0), 0.001); 
    }

    @Test
    @DisplayName("[Chain of Responsibility] Deve processar descontos de forma sequencial na esteira")
    public void testChainResponsibilityDescontos() {
        DescontoHandler c1 = new DescontoCupom();
        DescontoHandler c2 = new DescontoFidelidade();
        c1.setProximo(c2);

        double valorFinal = c1.aplicarDesconto(60.0);
        assertEquals(47.5, valorFinal, 0.001);
    }

    @Test
    @DisplayName("[Template Method] Deve garantir a execução estruturada do esqueleto de preparo")
    public void testTemplateMethodPreparo() {
        PreparadorHamburguerTemplate cozinha = new PreparadorXBacon();
        assertDoesNotThrow(() -> cozinha.prepararComboFinal());
    }

    @Test
    @DisplayName("[State & Observer] Deve gerenciar o ciclo de vida do status e notificar os ouvintes reativamente")
    public void testStateEObserverPedido() {
        PedidoContexto pedido = new PedidoContexto();
        NotificadorPainelCliente painel = new NotificadorPainelCliente();
        pedido.registrarObserver(painel);

        assertEquals("Recebido", pedido.getEstado().getNome());

        pedido.proximoEstagio();
        assertEquals("Preparando", pedido.getEstado().getNome());

        pedido.proximoEstagio();
        assertEquals("Pronto", pedido.getEstado().getNome());
        
        assertDoesNotThrow(() -> pedido.proximoEstagio());
    }

    @Test
    @DisplayName("[Command] Deve encapsular comandos da cozinha e permitir reversão (Undo)")
    public void testCommandCozinha() {
        CozinhaReceiver chapa = new CozinhaReceiver();
        ComandaCommand comando = new PrepararPedidoCommand(chapa);

        assertDoesNotThrow(() -> comando.execute());
        assertDoesNotThrow(() -> comando.undo()); 
    }

    @Test
    @DisplayName("[Memento] Deve salvar pontos estáveis de customização e permitir desfazer (Ctrl+Z)")
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
    @DisplayName("[Iterator] Deve ler coleções encapsuladas sem expor a estrutura interna")
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
    @DisplayName("[Mediator] Deve centralizar a troca de mensagens evitando acoplamento direto")
    public void testMediatorCentral() {
        CentralDeOperacoesMediator hub = new CentralDeOperacoesMediator();
        AtendenteCaixa caixa = new AtendenteCaixa(hub);
        OperadorCozinha cozinha = new OperadorCozinha(hub);

        hub.setCaixa(caixa);
        hub.setCozinha(cozinha);

        assertDoesNotThrow(() -> caixa.enviarOrdemProducao("Mesa 02: 1x Combo Brabo"));
    }

    @Test
    @DisplayName("[Interpreter] Deve processar sintaxe textual para validar regras de carrinho")
    public void testInterpreterCupons() {
        ExpressaoCupom expressaoRegra = new ExpressaoEAninhada(
                new ContemItemExpressao("BURGER"), 
                new ContemItemExpressao("BATATA")
        );

        assertTrue(expressaoRegra.interpretar("No carrinho tem BURGER, REFRIGERANTE e BATATA"));
        assertFalse(expressaoRegra.interpretar("No carrinho tem BURGER e REFRIGERANTE")); // Falta batata
    }

    @Test
    @DisplayName("[Visitor] Deve rodar lógicas contábeis/fiscais externas sobre as entidades de venda")
    public void testVisitorImpostos() {
        PratoComida itemRefeicao = new PratoComida(50.00);
        ImpostoEstadualVisitor fiscal = new ImpostoEstadualVisitor();

        assertEquals(50.00, itemRefeicao.getPreco());
        assertDoesNotThrow(() -> itemRefeicao.aceitarVisitor(fiscal));
    }

    @Test
    @DisplayName("[Falha - Command] Deve garantir resiliência ao tentar reverter (undo) sem execução prévia")
    public void testFalhaCommandUndoSemExecute() {
        CozinhaReceiver chapa = new CozinhaReceiver();
        ComandaCommand comando = new PrepararPedidoCommand(chapa);

        assertDoesNotThrow(() -> comando.undo(), 
            "O rollback do comando falhou ao lidar com um estado sem execução prévia.");
    }

    @Test
    @DisplayName("[Falha - Builder] Deve aceitar strings vazias ou nulas se o sistema não possuir travas estritas")
    public void testFalhaBuilderValoresVazios() {
        HamburguerCustomizado burgerFantasma = new HamburguerCustomizado.Builder(null, "")
                .build();

        assertNotNull(burgerFantasma);
        assertTrue(burgerFantasma.toString().contains("null"));
    }

    @Test
    @DisplayName("[Falha - Strategy] Deve lidar corretamente com distância zero ou negativa no cálculo de frete")
    public void testFalhaStrategyValoresInvalidos() {
        FreteStrategy chuva = new FreteDinamicoChuva();

        assertEquals(0.0, chuva.calcular(0.0), 0.001);

        assertEquals(-7.0, chuva.calcular(-2.0), 0.001);
    }

    @Test
    @DisplayName("[Falha - Composite] Deve retornar preço zero para uma seção vazia do cardápio")
    public void testFalhaCompositeSemFilhos() {
        SecaoCardapioComposite secaoVazia = new SecaoCardapioComposite("Bebidas Alcoólicas");
        
        assertEquals(0.0, secaoVazia.getPreco(), 0.001, 
            "Uma seção vazia do cardápio deveria registrar preço total igual a zero.");
        assertDoesNotThrow(() -> secaoVazia.exibir());
    }

    @Test
    @DisplayName("[Falha - Iterator] Deve lançar erro ou retornar falso se avançar além do limite da coleção")
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
    @DisplayName("[Falha - Mediator] Deve ignorar mensagens caso os colaboradores não estejam registrados no Hub")
    public void testFalhaMediatorColaboradoresDesconectados() {
        CentralDeOperacoesMediator hubOrfao = new CentralDeOperacoesMediator();
        AtendenteCaixa caixaSolto = new AtendenteCaixa(hubOrfao);

        assertDoesNotThrow(() -> caixaSolto.enviarOrdemProducao("Mesa 10: 1x Batata"));
    }

    @Test
    @DisplayName("[Falha - Decorator] Deve manter o comportamento correto mesmo se empilharmos múltiplos decorators idênticos")
    public void testDecoratorMultiplosAdicionaisIguais() {
        ItemVenda burger = new HamburguerBase(); 
        burger = new AdicionalBacon(burger);    
        burger = new AdicionalBacon(burger);    

        assertEquals(35.0, burger.calcularPreco(), 0.001);
        assertTrue(burger.getDescricao().contains("Bacon Crocante + Bacon Crocante"));
    }

    @Test
    @DisplayName("[Sucesso - Proxy] Deve permitir alteração de estoque quando o usuário for um GERENTE legítimo")
    public void testSucessoProxyGerente() {
        ServicoEstoque estoquePermitido = new ProxyServicoEstoque("GERENTE");
        
        assertDoesNotThrow(() -> estoquePermitido.alterarEstoque(150),
            "O Proxy deveria permitir que o Gerente alterasse o estoque sem restrições.");
    }

    @Test
    @DisplayName("[Sucesso - Strategy] Deve calcular o valor correto do frete fixo de moto independente da distância")
    public void testSucessoStrategyFreteFixo() {
        FreteStrategy fixo = new FreteFixoMoto();
        
        assertEquals(7.00, fixo.calcular(2.0), 0.001);
        assertEquals(7.00, fixo.calcular(50.0), 0.001);
    }
}

