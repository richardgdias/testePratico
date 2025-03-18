/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.BO;

import br.com.testepratico.DAO.VendasDAO;
import br.com.testepratico.DTO.ClientesDTO;
import br.com.testepratico.DTO.ProdutosDTO;
import br.com.testepratico.DTO.ProdutosVendaDTO;
import br.com.testepratico.DTO.VendasDTO;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Richard
 */
public class VendasBO {

    /**
     * MÉTODO RESPONSÁVEL POR GERAR CÓDIGO PARA UMA NOVA VENDA NO BANCO DE DADOS
     *
     * @return String
     */
    public String gerarNovoCodigoVenda() {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.gerarNovoCodigoVenda();
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR UMA NOVA VENDA NO BANCO DE DADOS
     *
     * @param vDTO
     * @return boolean
     */
    public boolean inserirVenda(VendasDTO vDTO) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.inserirVenda(vDTO);
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR OS PRODUTOS DE UMA NOVA VENDA
     *
     * @param vDTO
     * @param codigoVenda
     * @return boolean
     */
    public boolean inserirProdutosVenda(VendasDTO vDTO, String codigoVenda) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.inserirProdutosVenda(vDTO, codigoVenda);
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR UMA VENDA JÁ EXISTENTE NO BANCO DE DADOS
     *
     * @param vDTO
     * @return boolean
     */
    public boolean alterarVenda(VendasDTO vDTO) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.alterarVenda(vDTO);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM ARRAYLIST PREENCHIDO COM OS DADOS DAS
     * VENDAS PARA PREENCHER A TABELA
     *
     * @param dado
     * @param tipo
     * @return ArrayList
     */
    public ArrayList preencherTabelaVendas(String dado, String tipo) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.preencherTabelaVendas(dado, tipo);
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR OS PRODUTOS DA VENDA PARA PREENCHER A
     * TABELA
     *
     * @param codigoVenda
     * @return ArrayList
     */
    public ArrayList preencherTabelaProdutosVenda(String codigoVenda) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.preencherTabelaProdutosVenda(codigoVenda);
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR UMA VENDA DO BANCO DE DADOS
     *
     * @param codigoVenda
     * @return boolean
     */
    public boolean excluirVenda(int codigoVenda) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.excluirVenda(codigoVenda);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM DTO PREENCHIDO COM OS DADOS DE UMA
     * VENDA DO BANCO DE DADOS
     *
     * @param dado
     * @param tipo
     * @return VendasDTO
     */
    public VendasDTO buscarDadosVenda(String dado, String tipo) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.buscarDadosVenda(dado, tipo);
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER UM ARRAY COM OS CLIENTES CADASTRADOS NO
     * BANCO DE DADOS
     *
     * @return ArrayList
     */
    public ArrayList carregarClientesCmb() {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.carregarClientesCmb();
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER UM ARRAY COM OS PRODUTOS CADASTRADOS NO
     * BANCO DE DADOS
     *
     * @return ArrayList
     */
    public ArrayList carregarProdutosCmb() {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.carregarProdutosCmb();
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR OS DADOS DO CLIENTE PELO CÓDIGO
     *
     * @param codigo
     * @return String
     */
    public String buscarNomeCliente(String codigo) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.buscarNomeCliente(codigo);
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR OS DADOS DO CLIENTE PELO NOME
     *
     * @param cliente
     * @return int
     */
    public int buscarCodigoCliente(String cliente) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.buscarCodigoCliente(cliente);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM DTO PREENCHIDO COM OS DADOS DE UM
     * PRODUTO DO BANCO DE DADOS PELA DESCRICAO DO PRODUTO
     *
     * @param dado
     * @param tipo
     * @return ProdutosDTO
     */
    public ProdutosDTO buscarDadosProduto(String dado, String tipo) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.buscarDadosProduto(dado, tipo);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR OS PRODUTOS DE UMA VENDA QUE ESTÁ SENDO
     * ALTERADA
     *
     * @param codigoVenda
     * @return ArrayList
     */
    public ArrayList<ProdutosVendaDTO> retornarProdutosVendaAlterada(int codigoVenda) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.retornarProdutosVendaAlterada(codigoVenda);
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR LIMITE DE COMPRA E DIA DE FECHAMENTO DA
     * FATURA DO CLIENTE
     *
     * @param codigoCliente
     * @return ClientesDTO
     */
    public ClientesDTO buscarDadosCliente(String codigoCliente) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.buscarDadosCliente(codigoCliente);
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR O VALOR TOTAL DAS COMPRAS REALIZADAS PELO
     * CLIENTE DENTRO DO PERÍODO DO DIA DA FATURA
     *
     * @param codigoCliente
     * @param dataAtual
     * @param dataAnterior
     * @return double
     */
    public double buscarValorComprasRealizadas(String codigoCliente, LocalDate dataAtual, LocalDate dataAnterior) {
        VendasDAO vDAO = new VendasDAO();
        return vDAO.buscarValorComprasRealizadas(codigoCliente, dataAtual, dataAnterior);
    }

}
