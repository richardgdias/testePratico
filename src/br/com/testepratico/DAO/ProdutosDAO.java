/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.DAO;

import br.com.testepratico.DTO.ProdutosDTO;
import br.com.testepratico.Utils.Conexao;
import br.com.testepratico.Utils.funcoes;
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
public class ProdutosDAO {

    /**
     * MÉTODO RESPONSÁVEL POR GERAR CÓDIGO PARA UM NOVO PRODUTO NO BANCO DE
     * DADOS
     *
     * @return String
     */
    public String gerarNovoCodigoProduto() {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String codigo = "";

        try {
            conexao.abrir();

            sql = "SELECT MAX(codigo) AS codigo FROM produtos";

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
     * MÉTODO RESPONSÁVEL POR INSERIR UM NOVO PRODUTO NO BANCO DE DADOS
     *
     * @param pDTO
     * @return boolean
     */
    public boolean inserirProduto(ProdutosDTO pDTO) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "INSERT INTO produtos (descricao, valor) "
                    + "VALUES (?,?)";

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setString(1, pDTO.getDescricaoProduto());
            pstmt.setDouble(2, pDTO.getValorProduto());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR UM PRORDUTO JÁ EXISTENTE NO BANCO DE DADOS
     *
     * @param pDTO
     * @return boolean
     */
    public boolean alterarProduto(ProdutosDTO pDTO) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "UPDATE produtos SET descricao=?, valor=? "
                    + "WHERE codigo=?";

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setString(1, pDTO.getDescricaoProduto());
            pstmt.setDouble(2, pDTO.getValorProduto());
            pstmt.setInt(3, pDTO.getCodigo());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
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
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList dados = new ArrayList();
        funcoes f = new funcoes();

        try {
            conexao.abrir();

            switch (tipo) {
                case "CÓDIGO":
                    sql = "SELECT codigo, descricao, valor FROM produtos "
                            + "WHERE codigo::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "NOME":
                    sql = "SELECT codigo, descricao, valor FROM produtos "
                            + "WHERE descricao LIKE '" + dado + "%' ORDER BY descricao";
                    break;
                case "TODOS":
                    sql = "SELECT codigo, descricao, valor FROM produtos";
                    break;
            }

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    dados.add(new Object[]{
                        rs.getInt("codigo"),
                        rs.getString("descricao"),
                        f.convMoeda(String.valueOf(rs.getDouble("valor"))),
                        "",
                        ""
                    });
                } while (rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return dados;
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR UM PRODUTO DO BANCO DE DADOS
     *
     * @param codigoProduto
     * @return boolean
     */
    public boolean excluirProduto(int codigoProduto) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "DELETE FROM produtos WHERE codigo = " + codigoProduto;

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
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
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ProdutosDTO pDTO = new ProdutosDTO();

        try {
            conexao.abrir();

            switch (tipo) {
                case "CÓDIGO":
                    sql = "SELECT codigo, descricao, valor FROM produtos "
                            + "WHERE codigo::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "NOME":
                    sql = "SELECT codigo, descricao, valor FROM produtos "
                            + "WHERE descricao LIKE '" + dado + "%' ORDER BY descricao";
                    break;
            }

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pDTO.setCodigo(rs.getInt("codigo"));
                pDTO.setDescricaoProduto(rs.getString("descricao"));
                pDTO.setValorProduto(rs.getDouble("valor"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return pDTO;
    }

}
