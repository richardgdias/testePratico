/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vendas;

import Utils.ConexaoTest;
import Utils.LimparBanco;
import br.com.testepratico.BO.VendasBO;
import br.com.testepratico.DTO.ClientesDTO;
import br.com.testepratico.DTO.ProdutosDTO;
import br.com.testepratico.DTO.ProdutosVendaDTO;
import br.com.testepratico.DTO.VendasDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Richard
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Vendas_Test {

    @Before
    public void setUp() {
        ConexaoTest conn = new ConexaoTest();

        /* ========== Montagem do cenário ========== */
        //define as informações de acesso ao banco
        conn.abrirConexao();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void a_testGerarNovoCodigoVenda() {
        VendasBO vBO = new VendasBO();
        LimparBanco limparTabela = new LimparBanco();

        //limpa a tabela 
        limparTabela.limparTabelaVendas();

        /* ========== Execução ========== */
        String result = vBO.gerarNovoCodigoVenda();

        /* ========== Verificação ========== */
        assertThat(result, is("1"));
    }

    @Test
    public void b_testInserirVenda() {
        VendasDTO vDTO = new VendasDTO();
        VendasBO vBO = new VendasBO();

        //preenche os dados do teste para inserção
        vDTO.setCodigo(1);
        vDTO.setCodigoCliente(1);
        vDTO.setNomeCliente("TESTE 1");
        vDTO.setValorTotal(1500);
        vDTO.setDataCompra("2025-03-18");

        /* ========== Execução ========== */
        boolean result = vBO.inserirVenda(vDTO);

        /* ========== Verificação ========== */
        assertTrue(result);
    }

    @Test
    public void c_testInserirProdutosVenda() {
        VendasDTO vDTO = new VendasDTO();
        VendasBO vBO = new VendasBO();
        ArrayList<ProdutosVendaDTO> produtos = new ArrayList();
        ProdutosVendaDTO pvDTO = new ProdutosVendaDTO();

        //preenche os dados do teste para inserção
        pvDTO.setCodigoProduto(1);
        pvDTO.setDescricaoProduto("PRODUTO TESTE 1");
        pvDTO.setValorProduto(1500);
        pvDTO.setQuantidade(1);
        pvDTO.setValorTotal(1500);
        pvDTO.setCodigoVenda(1);

        produtos.add(pvDTO);
        vDTO.setArrayProdutos(produtos);

        /* ========== Execução ========== */
        boolean result = vBO.inserirProdutosVenda(vDTO, "1");

        /* ========== Verificação ========== */
        assertTrue(result);
    }

    @Test
    public void d_testAlterarVenda() {
        VendasDTO vDTO = new VendasDTO();
        VendasBO vBO = new VendasBO();

        //preenche os dados do teste para inserção
        vDTO.setCodigo(1);
        vDTO.setCodigoCliente(1);
        vDTO.setNomeCliente("TESTE 2");
        vDTO.setValorTotal(1500);
        vDTO.setDataCompra("2025-03-18");

        /* ========== Execução ========== */
        boolean result = vBO.alterarVenda(vDTO);

        /* ========== Verificação ========== */
        assertTrue(result);
    }

    @Test
    public void e_testPreencherTabelaVendas() {
        VendasBO vBO = new VendasBO();
        String dado = "";
        String tipo = "TODAS";
        String dataInicial = "2025-03-01";
        String dataFinal = "2025-03-18";

        /* ========== Execução ========== */
        ArrayList result = vBO.preencherTabelaVendas(dado, tipo, dataInicial, dataFinal);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void f_testPreencherTabelaProdutosVenda() {
        VendasBO vBO = new VendasBO();
        String codigoVenda = "1";

        /* ========== Execução ========== */
        ArrayList result = vBO.preencherTabelaProdutosVenda(codigoVenda);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void g_testBuscarDadosVenda() {
        VendasBO vBO = new VendasBO();
        String dado = "1";
        String tipo = "CÓDIGO";

        /* ========== Execução ========== */
        VendasDTO result = vBO.buscarDadosVenda(dado, tipo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void h_testCarregarClientesCmb() {
        VendasBO vBO = new VendasBO();

        /* ========== Execução ========== */
        ArrayList result = vBO.carregarClientesCmb();
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void i_testCarregarProdutosCmb() {
        VendasBO vBO = new VendasBO();

        /* ========== Execução ========== */
        ArrayList result = vBO.carregarProdutosCmb();
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void j_testBuscarNomeCliente() {
        VendasBO vBO = new VendasBO();
        String codigo = "1";

        /* ========== Execução ========== */
        String result = vBO.buscarNomeCliente(codigo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void k_testBuscarCodigoCliente() {
        VendasBO vBO = new VendasBO();
        String cliente = "TESTE 2";

        /* ========== Execução ========== */
        int result = vBO.buscarCodigoCliente(cliente);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void l_testBuscarDadosProduto() {
        VendasBO vBO = new VendasBO();
        String dado = "1";
        String tipo = "CÓDIGO";

        /* ========== Execução ========== */
        ProdutosDTO result = vBO.buscarDadosProduto(dado, tipo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void m_testRetornarProdutosVendaAlterada() {
        VendasBO vBO = new VendasBO();
        int codigoVenda = 1;

        /* ========== Execução ========== */
        ArrayList<ProdutosVendaDTO> result = vBO.retornarProdutosVendaAlterada(codigoVenda);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void n_testBuscarDadosCliente() {
        VendasBO vBO = new VendasBO();
        String codigoCliente = "1";

        /* ========== Execução ========== */
        ClientesDTO result = vBO.buscarDadosCliente(codigoCliente);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }

    @Test
    public void o_testBuscarValorComprasRealizadas() {
        VendasBO vBO = new VendasBO();
        String codigoCliente = "1";
        LocalDate dataCompleta = LocalDate.of(2025, 03, 18);
        LocalDate dataAnterior = dataCompleta.minusMonths(1);

        /* ========== Execução ========== */
        double result = vBO.buscarValorComprasRealizadas(codigoCliente, dataCompleta, dataAnterior);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }
    
    @Test
    public void f_testExcluirVenda() {
        VendasBO vBO = new VendasBO();
        int codigoVenda = 1;

        /* ========== Execução ========== */
        boolean result = vBO.excluirVenda(codigoVenda);

        /* ========== Verificação ========== */
        assertTrue(result);
    }

}
