/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.DAO;

import br.com.testepratico.DTO.ClientesDTO;
import br.com.testepratico.Utils.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class ClientesDAO {

    /**
     * MÉTODO RESPONSÁVEL POR GERAR CÓDIGO PARA UM NOVO CLIENTE NO BANCO DE
     * DADOS
     *
     * @return String
     */
    public String gerarNovoCodigoCliente() {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String codigo = "";

        try {
            conexao.abrir();

            sql = "SELECT MAX(codigo) AS codigo FROM clientes";

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                codigo = String.valueOf(rs.getInt("codigo") + 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return codigo;
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR UM NOVO CLIENTE NO BANCO DE DADOS
     *
     * @param cDTO
     * @return boolean
     */
    public boolean inserirCliente(ClientesDTO cDTO) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "INSERT INTO clientes (nome, limite_compra, dia_fechamento_fatura) "
                    + "VALUES (?,?,?)";

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setString(1, cDTO.getNome());
            pstmt.setDouble(2, cDTO.getLimiteCompra());
            pstmt.setInt(3, cDTO.getDiaFechamentoFatura());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR UM CLIENTE JÁ EXISTENTE NO BANCO DE DADOS
     *
     * @param cDTO
     * @return boolean
     */
    public boolean alterarCliente(ClientesDTO cDTO) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "UPDATE clientes SET nome=?, limite_compra=?, "
                    + "dia_fechamento_fatura=? WHERE codigo=?";

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setString(1, cDTO.getNome());
            pstmt.setDouble(2, cDTO.getLimiteCompra());
            pstmt.setInt(3, cDTO.getDiaFechamentoFatura());
            pstmt.setInt(4, cDTO.getCodigo());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
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
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList dados = new ArrayList();

        try {
            conexao.abrir();

            switch (tipo) {
                case "CÓDIGO":
                    sql = "SELECT codigo, nome FROM clientes "
                            + "WHERE codigo::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "NOME":
                    sql = "SELECT codigo, nome FROM clientes "
                            + "WHERE nome LIKE '" + dado + "%' ORDER BY nome";
                    break;
                case "TODOS":
                    sql = "SELECT codigo, nome FROM clientes";
                    break;
            }

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    dados.add(new Object[]{
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        "",
                        ""
                    });
                } while (rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return dados;
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR UM CLIENTE DO BANCO DE DADOS
     *
     * @param codigoCliente
     * @return boolean
     */
    public boolean excluirCliente(int codigoCliente) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "DELETE FROM clientes WHERE codigo = " + codigoCliente;

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
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
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ClientesDTO cDTO = new ClientesDTO();

        try {
            conexao.abrir();

            switch (tipo) {
                case "CÓDIGO":
                    sql = "SELECT codigo, nome, limite_compra, dia_fechamento_fatura "
                            + "FROM clientes "
                            + "WHERE codigo::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "NOME":
                    sql = "SELECT codigo, nome, limite_compra, dia_fechamento_fatura "
                            + "FROM clientes "
                            + "WHERE nome LIKE '" + dado + "%' ORDER BY nome";
                    break;
            }

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cDTO.setCodigo(rs.getInt("codigo"));
                cDTO.setNome(rs.getString("nome"));
                cDTO.setLimiteCompra(rs.getDouble("limite_compra"));
                cDTO.setDiaFechamentoFatura(rs.getInt("dia_fechamento_fatura"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return cDTO;
    }

}
