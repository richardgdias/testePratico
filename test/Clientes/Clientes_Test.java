/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import Utils.ConexaoTest;
import Utils.LimparBanco;
import br.com.testepratico.BO.ClientesBO;
import br.com.testepratico.DTO.ClientesDTO;
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
public class Clientes_Test {

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
    public void a_testGerarNovoCodigoCliente() {
        ClientesBO cBO = new ClientesBO();
        LimparBanco limparTabela = new LimparBanco();

        //limpa a tabela 
        limparTabela.limparTabelaClientes();

        /* ========== Execução ========== */
        String result = cBO.gerarNovoCodigoCliente();

        /* ========== Verificação ========== */
        assertThat(result, is("1"));
    }

    @Test
    public void b_testInserirCliente() {
        ClientesDTO cDTO = new ClientesDTO();
        ClientesBO cBO = new ClientesBO();

        //preenche os dados do teste para inserção
        cDTO.setCodigo(1);
        cDTO.setNome("TESTE 1");
        cDTO.setLimiteCompra(1000);
        cDTO.setDiaFechamentoFatura(30);

        /* ========== Execução ========== */
        boolean result = cBO.inserirCliente(cDTO);

        /* ========== Verificação ========== */
        assertTrue(result);
    }

    @Test
    public void c_testAlterarCliente() {
        ClientesDTO cDTO = new ClientesDTO();
        ClientesBO cBO = new ClientesBO();

        //preenche os dados do teste para inserção
        cDTO.setCodigo(1);
        cDTO.setNome("TESTE 2");
        cDTO.setLimiteCompra(2000);
        cDTO.setDiaFechamentoFatura(31);

        /* ========== Execução ========== */
        boolean result = cBO.alterarCliente(cDTO);

        /* ========== Verificação ========== */
        assertTrue(result);
    }
    
    @Test
    public void d_testPreencherTabelaClientes() {
        ClientesBO cBO = new ClientesBO();
        String dado = "";
        String tipo = "TODOS";

        /* ========== Execução ========== */
        ArrayList result = cBO.preencherTabelaClientes(dado, tipo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }
    
    @Test
    public void e_testBuscarDadosCliente() {
        ClientesBO cBO = new ClientesBO();
        String dado = "1";
        String tipo = "CÓDIGO";

        /* ========== Execução ========== */
        ClientesDTO result = cBO.buscarDadosCliente(dado, tipo);
        /* ========== Verificação ========== */
        assertNotNull(result);
    }
    
    @Test
    public void f_testExcluirCliente() {
        ClientesBO cBO = new ClientesBO();
        int codigoCliente = 1;

        /* ========== Execução ========== */
        boolean result = cBO.excluirCliente(codigoCliente);

        /* ========== Verificação ========== */
        assertTrue(result);
    }

}
