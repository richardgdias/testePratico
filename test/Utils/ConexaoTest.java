/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class ConexaoTest {

    public Statement stm;
    public ResultSet rs;
    private final String driver = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost:5432/testePratico";
    private final String usuario = "postgres";
    private final String senha = "123321abc";
    public Connection conn;

    public void abrirConexao() {
        try {
            System.setProperty("jdbc.Drivers", driver); //setando a propriedade do driver de conexao
            conn = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fecharConexao() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
