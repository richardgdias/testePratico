/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.BO;

import br.com.testepratico.DAO.ClientesDAO;
import br.com.testepratico.DTO.ClientesDTO;
import java.util.ArrayList;

/**
 *
 * @author Richard
 */
public class ClientesBO {

    /**
     * MÉTODO RESPONSÁVEL POR GERAR CÓDIGO PARA UM NOVO CLIENTE NO BANCO DE
     * DADOS
     *
     * @return String
     */
    public String gerarNovoCodigoCliente() {
        ClientesDAO cDAO = new ClientesDAO();
        return cDAO.gerarNovoCodigoCliente();
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR UM NOVO CLIENTE NO BANCO DE DADOS
     *
     * @param cDTO
     * @return boolean
     */
    public boolean inserirCliente(ClientesDTO cDTO) {
        ClientesDAO cDAO = new ClientesDAO();
        return cDAO.inserirCliente(cDTO);
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR UM CLIENTE JÁ EXISTENTE NO BANCO DE DADOS
     *
     * @param cDTO
     * @return boolean
     */
    public boolean alterarCliente(ClientesDTO cDTO) {
        ClientesDAO cDAO = new ClientesDAO();
        return cDAO.alterarCliente(cDTO);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM ARRAYLIST PREENCHIDO COM OS DADOS DE
     * CLIENTES PARA PREENCHER A TABELA
     *
     * @param dado
     * @param tipo
     * @return ArrayList
     */
    public ArrayList preencherTabelaClientes(String dado, String tipo) {
        ClientesDAO cDAO = new ClientesDAO();
        return cDAO.preencherTabelaClientes(dado, tipo);
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR UM CLIENTE DO BANCO DE DADOS
     *
     * @param codigoCliente
     * @return boolean
     */
    public boolean excluirCliente(int codigoCliente) {
        ClientesDAO cDAO = new ClientesDAO();
        return cDAO.excluirCliente(codigoCliente);
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM DTO PREENCHIDO COM OS DADOS DE UM
     * CLIENTE DO BANCO DE DADOS
     *
     * @param dado
     * @param tipo
     * @return ClientesDTO
     */
    public ClientesDTO buscarDadosCliente(String dado, String tipo) {
        ClientesDAO cDAO = new ClientesDAO();
        return cDAO.buscarDadosCliente(dado, tipo);
    }

}
