/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import br.com.testepratico.Utils.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class LimparBanco {

    /**
     * MÉTODO RESPONSÁVEL POR LIMPAR AS TABELAS DE CLIENTES
     */
    public void limparTabelaClientes() {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";

        try {
            conexao.abrir();

            sql = "TRUNCATE TABLE clientes;";
            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LimparBanco.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
    }
    
    /**
     * MÉTODO RESPONSÁVEL POR LIMPAR AS TABELAS DE PRODUTOS
     */
    public void limparTabelaProdutos() {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";

        try {
            conexao.abrir();

            sql = "TRUNCATE TABLE produtos;";
            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LimparBanco.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
    }
    
    /**
     * MÉTODO RESPONSÁVEL POR LIMPAR AS TABELAS DE VENDAS
     */
    public void limparTabelaVendas() {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";

        try {
            conexao.abrir();

            sql = "TRUNCATE TABLE vendas;";
            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();

            sql = "TRUNCATE TABLE produtos_venda;";
            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LimparBanco.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
    }

}
