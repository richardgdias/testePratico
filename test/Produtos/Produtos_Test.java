/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Produtos;

import Utils.ConexaoTest;
import Utils.LimparBanco;
import br.com.testepratico.BO.ProdutosBO;
import br.com.testepratico.DTO.ProdutosDTO;
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
public class Produtos_Test {
    
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
    public void a_testGerarNovoCodigoProduto() {
        ProdutosBO pBO = new ProdutosBO();
        LimparBanco limparTabela = new LimparBanco();

        //limpa a tabela 
        limparTabela.limparTabelaProdutos();

        /* ========== Execução ========== */
        String result = pBO.gerarNovoCodigoProduto();

        /* ========== Verificação ========== */
        assertThat(result, is("1"));
    }

    @Test
    public void b_testInserirProduto() {
        ProdutosDTO pDTO = new ProdutosDTO();
        ProdutosBO pBO = new ProdutosBO();

        //preenche os dados do teste para inserção
        pDTO.setCodigo(1);
        pDTO.setDescricaoProduto("PRODUTO TESTE 1");
        pDTO.setValorProduto(150);

        /* ========== Execução ========== */
        boolean result = pBO.inserirProduto(pDTO);

        /* ========== Verificação ========== */
        assertTrue(result);
    }

    @Test
    public void c_testAlterarProduto() {
        ProdutosDTO pDTO = new ProdutosDTO();
        ProdutosBO pBO = new ProdutosBO();

        //preenche os dados do teste para inserção
        pDTO.setCodigo(1);
        pDTO.setDescricaoProduto("PRODUTO TESTE 2");
        pDTO.setValorProduto(250);

        /* ========== Execução ========== */
        boolean result = pBO.alterarProduto(pDTO);

        /* ========== Verificação ========== */
        assertTrue(result);
    }
    
    @Test
    public void d_testPreencherTabelaProdutos() {
        ProdutosBO pBO = new ProdutosBO();
        String dado = "";
        String tipo = "TODOS";

        /* ========== Execução ========== */
        ArrayList result = pBO.preencherTabelaProdutos(dado, tipo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }
    
    @Test
    public void e_testBuscarDadosProduto() {
        ProdutosBO pBO = new ProdutosBO();
        String dado = "1";
        String tipo = "CÓDIGO";

        /* ========== Execução ========== */
        ProdutosDTO result = pBO.buscarDadosProduto(dado, tipo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }
    
    @Test
    public void f_testExcluirProduto() {
        ProdutosBO pBO = new ProdutosBO();
        int codigoProduto = 1;

        /* ========== Execução ========== */
        boolean result = pBO.excluirProduto(codigoProduto);

        /* ========== Verificação ========== */
        assertTrue(result);
    }
    
}
