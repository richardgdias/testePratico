/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.DAO;

import br.com.testepratico.DTO.ClientesDTO;
import br.com.testepratico.DTO.ProdutosDTO;
import br.com.testepratico.DTO.ProdutosVendaDTO;
import br.com.testepratico.DTO.VendasDTO;
import br.com.testepratico.Utils.Conexao;
import br.com.testepratico.Utils.funcoes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class VendasDAO {

    funcoes f = new funcoes();

    /**
     * MÉTODO RESPONSÁVEL POR GERAR CÓDIGO PARA UMA NOVA VENDA NO BANCO DE DADOS
     *
     * @return String
     */
    public String gerarNovoCodigoVenda() {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String codigo = "";

        try {
            conexao.abrir();

            sql = "SELECT MAX(codigo) AS codigo FROM vendas";

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                codigo = String.valueOf(rs.getInt("codigo") + 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return codigo;
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR UMA NOVA VENDA NO BANCO DE DADOS
     *
     * @param vDTO
     * @return boolean
     */
    public boolean inserirVenda(VendasDTO vDTO) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "INSERT INTO vendas (codigo, codigo_cliente, nome_cliente, valor_total, data_compra) "
                    + "VALUES (?,?,?,?,?)";

            String data = f.StringEmData(f.pegarDataString());

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataUtil = formato.parse(data);
            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setInt(1, vDTO.getCodigo());
            pstmt.setInt(2, vDTO.getCodigoCliente());
            pstmt.setString(3, vDTO.getNomeCliente());
            pstmt.setDouble(4, vDTO.getValorTotal());
            pstmt.setDate(5, dataSql);
            pstmt.executeUpdate();
        } catch (SQLException | ParseException ex) {
            sucesso = false;
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR OS PRODUTOS DA NOVA VENDA
     *
     * @param vDTO
     * @param codigoVenda
     * @return boolean
     */
    public boolean inserirProdutosVenda(VendasDTO vDTO, String codigoVenda) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "DELETE FROM produtos_venda WHERE codigo_venda = " + codigoVenda;

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();

            sql = "INSERT INTO produtos_venda (codigo_venda, codigo_produto, descricao_produto, "
                    + "valor_produto, quantidade, valor_total) VALUES (?,?,?,?,?,?)";

            pstmt = conexao.conn.prepareStatement(sql);

            for (ProdutosVendaDTO produtosArray : vDTO.getArrayProdutos()) {
                pstmt.setInt(1, produtosArray.getCodigoVenda());
                pstmt.setInt(2, produtosArray.getCodigoProduto());
                pstmt.setString(3, produtosArray.getDescricaoProduto());
                pstmt.setDouble(4, produtosArray.getValorProduto());
                pstmt.setInt(5, produtosArray.getQuantidade());
                pstmt.setDouble(6, produtosArray.getValorTotal());
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR UMA VENDA JÁ EXISTENTE NO BANCO DE DADOS
     *
     * @param vDTO
     * @return boolean
     */
    public boolean alterarVenda(VendasDTO vDTO) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "UPDATE vendas SET codigo_cliente=?, nome_cliente=?, "
                    + "valor_total=?, data_compra=? WHERE codigo=?";

            String data = f.StringEmData(f.pegarDataString());

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataUtil = formato.parse(data);
            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setInt(1, vDTO.getCodigo());
            pstmt.setString(2, vDTO.getNomeCliente());
            pstmt.setDouble(3, vDTO.getValorTotal());
            pstmt.setDate(4, dataSql);
            pstmt.setInt(5, vDTO.getCodigo());
            pstmt.executeUpdate();
        } catch (SQLException | ParseException ex) {
            sucesso = false;
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM ARRAYLIST PREENCHIDO COM OS DADOS DA
     * VENDA PARA PREENCHER A TABELA
     *
     * @param dado
     * @param tipo
     * @return ArrayList
     */
    public ArrayList preencherTabelaVendas(String dado, String tipo) {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList dados = new ArrayList();

        try {
            conexao.abrir();

            switch (tipo) {
                case "CÓDIGO VENDA":
                    sql = "SELECT codigo, codigo_cliente, nome_cliente, valor_total, data_compra FROM vendas "
                            + "WHERE codigo::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "CÓDIGO PRODUTO":
                    sql = "SELECT DISTINCT v.codigo, v.codigo_cliente, v.nome_cliente, v.valor_total, v.data_compra FROM vendas v "
                            + "INNER JOIN produtos_venda pv ON pv.codigo_venda = v.codigo "
                            + "WHERE codigo_produto::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "CÓDIGO CLIENTE":
                    sql = "SELECT DISTINCT codigo, codigo_cliente, nome_cliente, valor_total, data_compra FROM vendas "
                            + "WHERE codigo_cliente::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "DESCRIÇÃO PRODUTO":
                    sql = "SELECT DISTINCT v.codigo, v.codigo_cliente, v.nome_cliente, v.valor_total, v.data_compra FROM vendas v "
                            + "INNER JOIN produtos_venda pv ON pv.codigo_venda = v.codigo "
                            + "WHERE pv.descricao_produto LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "NOME CLIENTE":
                    sql = "SELECT DISTINCT codigo, codigo_cliente, nome_cliente, valor_total, data_compra FROM vendas "
                            + "WHERE nome_cliente LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "TODAS":
                    sql = "SELECT codigo, codigo_cliente, nome_cliente, valor_total, data_compra FROM vendas";
                    break;
            }

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    dados.add(new Object[]{
                        rs.getInt("codigo"),
                        rs.getInt("codigo_cliente"),
                        rs.getString("nome_cliente"),
                        f.convMoeda(String.valueOf(rs.getDouble("valor_total"))),
                        f.DataEmString(rs.getString("data_compra")),
                        "",
                        ""
                    });
                } while (rs.next());
            } else {
                dados.add(new Object[]{"", "", "", "", "", "", ""});
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return dados;
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR OS PRODUTOS DA VENDA PARA PREENCHER A
     * TABELA
     *
     * @param codigoVenda
     * @return ArrayList
     */
    public ArrayList preencherTabelaProdutosVenda(String codigoVenda) {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList dados = new ArrayList();

        try {
            conexao.abrir();

            sql = "SELECT codigo_produto, descricao_produto, valor_produto, "
                    + "quantidade, valor_total FROM produtos_venda "
                    + "WHERE codigo_venda = " + codigoVenda;

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    dados.add(new Object[]{
                        rs.getInt("codigo_produto"),
                        rs.getString("descricao_produto"),
                        f.convMoeda(String.valueOf(rs.getDouble("valor_produto"))),
                        rs.getInt("quantidade"),
                        f.convMoeda(String.valueOf(rs.getDouble("valor_total")))
                    });
                } while (rs.next());
            } else {
                dados.add(new Object[]{"", "", "", "", ""});
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return dados;
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR UMA VENDA DO BANCO DE DADOS
     *
     * @param codigoVenda
     * @return boolean
     */
    public boolean excluirVenda(int codigoVenda) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        String sql = "";
        boolean sucesso = true;

        try {
            conexao.abrir();

            sql = "DELETE FROM vendas WHERE codigo = " + codigoVenda;

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();

            sql = "DELETE FROM produtos_venda WHERE codigo_venda = " + codigoVenda;

            pstmt = conexao.conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            sucesso = false;
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return sucesso;
    }

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR UM DTO PREENCHIDO COM OS DADOS DE UMA
     * VENDA DO BANCO DE DADOS
     *
     * @param dado
     * @param tipo
     * @return ClientesDTO
     */
    public VendasDTO buscarDadosVenda(String dado, String tipo) {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        VendasDTO vDTO = new VendasDTO();

        try {
            conexao.abrir();

            switch (tipo) {
                case "CÓDIGO":
                    sql = "SELECT codigo, codigo_cliente, nome_cliente, valor_total, data_compra "
                            + "FROM vendas "
                            + "WHERE codigo::text LIKE '" + dado + "%' ORDER BY codigo";
                    break;
                case "CÓDIGO CLIENTE":
                    sql = "SELECT codigo, codigo_cliente, nome_cliente, valor_total, data_compra "
                            + "FROM vendas "
                            + "WHERE codigo_cliente::text LIKE '" + dado + "%' ORDER BY codigo_cliente";
                    break;
                case "NOME CLIENTE":
                    sql = "SELECT codigo, codigo_cliente, nome_cliente, valor_total, data_compra "
                            + "FROM vendas "
                            + "WHERE nome_cliente LIKE '" + dado + "%' ORDER BY nome_cliente";
                    break;
            }

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vDTO.setCodigo(rs.getInt("codigo"));
                vDTO.setCodigoCliente(rs.getInt("codigo_cliente"));
                vDTO.setNomeCliente(rs.getString("nome_cliente"));
                vDTO.setValorTotal(rs.getDouble("valor_total"));
                vDTO.setDataCompra(rs.getString("data_compra"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return vDTO;
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER UM ARRAY COM OS CLIENTES CADASTRADOS NO
     * BANCO DE DADOS
     *
     * @return ArrayList
     */
    public ArrayList carregarClientesCmb() {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList clientes = new ArrayList();
        String sql = "";

        try {
            conexao.abrir();

            sql = "SELECT nome FROM clientes ORDER BY nome";

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    clientes.add(new Object[]{
                        rs.getString("nome")
                    });
                } while (rs.next());
            } else {
                clientes.add(new Object[]{""});
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return clientes;
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER UM ARRAY COM OS PRODUTOS CADASTRADOS NO
     * BANCO DE DADOS
     *
     * @return ArrayList
     */
    public ArrayList carregarProdutosCmb() {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList clientes = new ArrayList();
        String sql = "";

        try {
            conexao.abrir();

            sql = "SELECT descricao FROM produtos ORDER BY descricao";

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                do {
                    clientes.add(new Object[]{
                        rs.getString("descricao")
                    });
                } while (rs.next());
            } else {
                clientes.add(new Object[]{""});
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return clientes;
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR OS DADOS DO CLIENTE PELO CÓDIGO
     *
     * @param codigo
     * @return String
     */
    public String buscarNomeCliente(String codigo) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String nome = "";
        String sql = "";

        try {
            conexao.abrir();

            sql = "SELECT nome FROM clientes WHERE codigo = " + codigo;

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                nome = rs.getString("nome");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return nome;
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR OS DADOS DO CLIENTE PELO NOME
     *
     * @param cliente
     * @return int
     */
    public int buscarCodigoCliente(String cliente) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int codigo = 0;
        String sql = "";

        try {
            conexao.abrir();

            sql = "SELECT codigo FROM clientes WHERE nome = '" + cliente + "'";

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                codigo = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return codigo;
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
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ProdutosDTO pDTO = new ProdutosDTO();

        try {
            conexao.abrir();

            switch (tipo) {
                case "descricao":
                    sql = "SELECT * FROM produtos WHERE descricao = '" + dado + "'";
                    break;
                case "codigo":
                    sql = "SELECT * FROM produtos WHERE codigo = " + dado;
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

    /**
     * MÉTODO RESPONSÁVEL POR RETORNAR OS PRODUTOS DE UMA VENDA QUE ESTÁ SENDO
     * ALTERADA
     *
     * @param codigoVenda
     * @return produtosAdicionados
     */
    public ArrayList<ProdutosVendaDTO> retornarProdutosVendaAlterada(int codigoVenda) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        ArrayList<ProdutosVendaDTO> produtosAdicionados = new ArrayList();
        ProdutosVendaDTO os4ProdutosDTO = null;

        try {
            conexao.abrir();

            sql = "SELECT codigo_produto, descricao_produto, valor_produto, "
                    + "quantidade, codigo_venda, valor_total "
                    + "FROM produtos_venda WHERE codigo_venda = " + codigoVenda;

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                do {
                    os4ProdutosDTO = new ProdutosVendaDTO();
                    os4ProdutosDTO.setCodigoProduto(rs.getInt("codigo_produto"));
                    os4ProdutosDTO.setDescricaoProduto(rs.getString("descricao_produto"));
                    os4ProdutosDTO.setValorProduto(rs.getDouble("valor_produto"));
                    os4ProdutosDTO.setQuantidade(rs.getInt("quantidade"));
                    os4ProdutosDTO.setCodigoVenda(rs.getInt("codigo_venda"));
                    os4ProdutosDTO.setValorTotal(rs.getDouble("valor_total"));

                    produtosAdicionados.add(os4ProdutosDTO);
                } while (rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return produtosAdicionados;
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR LIMITE DE COMPRA E DIA DE FECHAMENTO DA
     * FATURA DO CLIENTE
     *
     * @param codigoCliente
     * @return ClientesDTO
     */
    public ClientesDTO buscarDadosCliente(String codigoCliente) {
        String sql = "";
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ClientesDTO cDTO = new ClientesDTO();

        try {
            conexao.abrir();

            sql = "SELECT limite_compra, dia_fechamento_fatura FROM clientes "
                    + "WHERE codigo = " + codigoCliente;

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cDTO.setLimiteCompra(rs.getDouble("limite_compra"));
                cDTO.setDiaFechamentoFatura(rs.getInt("dia_fechamento_fatura"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return cDTO;
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR O VALOR TOTAL DAS COMPRAS REALIZADAS PELO
     * CLIENTE DENTRO DO PERÍODO DO DIA DA FATURA
     *
     * @param codigoCliente
     * @param dataAtual
     * @param dataAnterior
     * @return
     */
    public double buscarValorComprasRealizadas(String codigoCliente, LocalDate dataAtual, LocalDate dataAnterior) {
        Conexao conexao = new Conexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double valor = 0;
        String sql = "";

        try {
            conexao.abrir();

            sql = "SELECT SUM(v.valor_total) AS valorTotal FROM vendas v "
                    + "INNER JOIN clientes c ON c.codigo = v.codigo_cliente "
                    + "WHERE v.codigo_cliente = " + codigoCliente + " "
                    + "AND v.data_compra BETWEEN '" + dataAnterior + "' AND '" + dataAtual + "'";

            pstmt = conexao.conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                valor = rs.getDouble("valorTotal");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.fechar();
        }
        return valor;
    }

}
