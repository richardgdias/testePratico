/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.BO;

import br.com.testepratico.DAO.ProdutosDAO;
import br.com.testepratico.DTO.ProdutosDTO;
import java.util.ArrayList;

/**
 *
 * @author Richard
 */
public class ProdutosBO {

    /**
     * MÉTODO RESPONSÁVEL POR GERAR CÓDIGO PARA UM NOVO PRODUTO NO BANCO DE
     * DADOS
     *
     * @return String
     */
    public String gerarNovoCodigoProduto() {
        ProdutosDAO pDAO = new ProdutosDAO();
        return pDAO.gerarNovoCodigoProduto();
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR UM NOVO PRODUTO NO BANCO DE DADOS
     *
     * @param pDTO
     * @return boolean
     */
    public boolean inserirProduto(ProdutosDTO pDTO) {
        ProdutosDAO pDAO = new ProdutosDAO();
        return pDAO.inserirProduto(pDTO);
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR UM PRORDUTO JÁ EXISTENTE NO BANCO DE DADOS
     *
     * @param pDTO
     * @return boolean
     */
    public boolean alterarProduto(ProdutosDTO pDTO) {
        ProdutosDAO pDAO = new ProdutosDAO();
        return pDAO.alterarProduto(pDTO);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM ARRAYLIST PREENCHIDO COM OS DADOS DE
     * PRODUTOS PARA PREENCHER A TABELA
     *
     * @param dado
     * @param tipo
     * @return ArrayList
     */
    public ArrayList preencherTabelaProdutos(String dado, String tipo) {
        ProdutosDAO pDAO = new ProdutosDAO();
        return pDAO.preencherTabelaProdutos(dado, tipo);
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR UM PRODUTO DO BANCO DE DADOS
     *
     * @param codigoProduto
     * @return boolean
     */
    public boolean excluirProduto(int codigoProduto) {
        ProdutosDAO pDAO = new ProdutosDAO();
        return pDAO.excluirProduto(codigoProduto);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM DTO PREENCHIDO COM OS DADOS DE UM
     * PRODUTO DO BANCO DE DADOS
     *
     * @param dado
     * @param tipo
     * @return ProdutosDTO
     */
    public ProdutosDTO buscarDadosProduto(String dado, String tipo) {
        ProdutosDAO pDAO = new ProdutosDAO();
        return pDAO.buscarDadosProduto(dado, tipo);
    }

}
