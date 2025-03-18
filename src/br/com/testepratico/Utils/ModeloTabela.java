/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testepratico.Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Richard
 */
public class ModeloTabela extends AbstractTableModel {

    private ArrayList linhas = null;
    private String[] colunas = null;

    public ModeloTabela(ArrayList lin, String[] col) {
        setLinhas(lin);
        setColunas(col);
    }

    public ArrayList getLinhas() {
        return linhas;
    }

    public void setLinhas(ArrayList dados) {
        linhas = dados;
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] nomes) {
        colunas = nomes;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public String getColumnName(int numCol) {
        return colunas[numCol];
    }

    @Override
    public Object getValueAt(int numLin, int numCol) {
        Object[] linha = (Object[]) getLinhas().get(numLin);
        return linha[numCol];
    }

    public void removeRow(int row) {
        getLinhas().remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void limpaTabela() {
        int size = getRowCount();
        getLinhas().clear();
        fireTableRowsDeleted(0, size);
    }

    public void newRenderer(JTable jtable, String condicao, int tam, String tab1, String tab2) {
        jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int style;
                Color cl;
                funcoes f = new funcoes();

                if (condicao.equals(jtable.getValueAt(row, 0))) {
                    if (isSelected) {
                        c.setFont(new Font("OpenSans", Font.BOLD, tam + 1));
                        c.setForeground(Color.white);
                        c.setBackground(new Color(112, 112, 112));
                    } else {
                        c.setFont(new Font("OpenSans", Font.PLAIN, tam + 1));
                        c.setForeground(Color.BLACK);

                        cl = new Color(236, 236, 236);
                        c.setBackground(cl);
                    }
                    table.setFont(new Font("OpenSans", Font.BOLD, tam + 1));
                    label.setHorizontalAlignment(JLabel.CENTER);

                    style = Font.BOLD;
                    c.setFont(new Font("OpenSans", style, tam));
                } else {
                    if (isSelected) {
                        c.setFont(new Font("OpenSans", Font.BOLD, 12));
                        c.setForeground(Color.white);
                        c.setBackground(new Color(112, 112, 112));
                    } else {
                        c.setFont(new Font("OpenSans", Font.PLAIN, 12));
                        c.setForeground(Color.BLACK);

                        cl = new Color(255, 255, 255);
                        c.setBackground(cl);
                        jtable.setBackground(cl);

                    }
                    table.setFont(new Font("OpenSans", Font.PLAIN, tam + 1));

                    label.setHorizontalAlignment(JLabel.CENTER);
                    style = Font.PLAIN;
                    c.setFont(new Font("OpenSans", style, 12));

                    if ("conferencia".equals(tab1)) {
                        if (condicao.equals(jtable.getValueAt(row, 7))) {
                            if (column == 7) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                c.setForeground(Color.RED);
                            }
                        } else {
                            if (column == 7) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                c.setForeground(Color.BLUE);
                            }
                        }
                    }

                    if ("roteiro".equals(tab1)) {
                        if (condicao.equals(jtable.getValueAt(row, 7))) {
                            if (column == 7) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 12));
                                c.setForeground(Color.RED);
                            }
                        } else {
                            if (column == 7) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 12));
                                c.setForeground(Color.BLUE);
                            }
                        }
                    }

                    if ("OSM9".equals(tab1)) {
                        c.setFont(new Font("Open Sans Semibold", 0, 11));
                        if (condicao.equals(jtable.getValueAt(row, 8))) {
                            if (column == 8) {
                                c.setFont(new Font("Open Sans Semibold", 0, 11));
                                c.setForeground(Color.RED);
                            }
                        }
                    }

                    if ("OSM13".equals(tab1)) {
                        if (condicao.equals(jtable.getValueAt(row, 8))) {
                            if (column == 8) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                c.setForeground(Color.RED);
                            }

                        } else {
                            if (column == 8) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                c.setForeground(Color.GREEN);
                            }
                        }
                    }

                    if ("lead".equals(tab1)) {
                        if (jtable.getValueAt(row, 9).equals("VERDE")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            c.setForeground(new Color(0, 204, 51));
                        } else if (jtable.getValueAt(row, 9).equals("VERMELHO")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            c.setForeground(new Color(255, 51, 51));
                        } else if (jtable.getValueAt(row, 9).equals("LARANJA")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            c.setForeground(new Color(255, 153, 0));
                        } else if (jtable.getValueAt(row, 9).equals("CINZA")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            c.setForeground(new Color(153, 153, 153));
                        }
                    }

                    if ("estoque".equals(tab1)) {
                        if (Double.parseDouble(jtable.getValueAt(row, 3).toString()) <= 0) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 13));
                            c.setForeground(new Color(255, 51, 51));
                        }

                        if (column == 1) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                    }

                    if ("buscarProdutos".equals(tab1)) {
                        if (Double.parseDouble(f.convDouble(jtable.getValueAt(row, 6).toString())) <= 0) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 13));
                            c.setForeground(new Color(255, 51, 51));
                        }

                        if (column == 1) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                    }

                    if ("buscarProdutos2".equals(tab1)) {
                        if (Double.parseDouble(f.convDouble(jtable.getValueAt(row, 6).toString())) <= 0) {
                            c.setFont(new Font("Open Sans", Font.PLAIN, 13));
                        }

                        if (column == 1) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                    }

                    if ("estoqueGrade".equals(tab1)) {
                        if (f.convQtdeDouble(jtable.getValueAt(row, 5).toString()) <= 0) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 13));
                            c.setForeground(new Color(255, 51, 51));
                        }

                        if (column == 4 || column == 8 || column == 9 || column == 10) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

                    if ("crediarioParcelas".equals(tab1)) {
                        if (jtable.getValueAt(row, 10).toString().equals("false")) {
                            if (column == 4) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 13));
                                c.setForeground(new Color(0, 204, 51));
                            }

                        } else {
                            if (column == 4) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 13));
                                c.setForeground(new Color(255, 51, 51));
                            }
                        }

                        if (column == 6 || column == 7 || column == 8 || column == 9) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

                    if ("historicoPagamentos".equals(tab1)) {
                        if (jtable.getValueAt(row, 6).toString().equals("false")) {
                            if (column == 5) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 13));
                                c.setForeground(new Color(0, 204, 51));
                            }
                        } else {
                            if (column == 5) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 13));
                                c.setForeground(new Color(255, 51, 51));
                            }
                        }

                        if (column == 0 || column == 1 || column == 2 || column == 3) {
                            setHorizontalAlignment(SwingConstants.CENTER);
                        }

                        if (column == 4) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

                    if ("cobrancas".equals(tab1)) {
                        if (jtable.getValueAt(row, 6).toString().equals("VENCIDA")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            setForeground(Color.RED);
                            if (column == 3) {
                                setHorizontalAlignment(SwingConstants.RIGHT);
                            } else if (column == 1 || column == 5) {
                                setHorizontalAlignment(SwingConstants.LEFT);
                            } else {
                                setHorizontalAlignment(SwingConstants.CENTER);
                            }
                        } else if (jtable.getValueAt(row, 6).toString().equals("Á VENCER")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            setForeground(Color.ORANGE);
                            if (column == 3) {
                                setHorizontalAlignment(SwingConstants.RIGHT);
                            } else if (column == 1 || column == 5) {
                                setHorizontalAlignment(SwingConstants.LEFT);
                            } else {
                                setHorizontalAlignment(SwingConstants.CENTER);
                            }
                        } else if (jtable.getValueAt(row, 6).toString().equals("VENCE HOJE")) {
                            c.setFont(new Font("Open Sans", Font.BOLD, 11));
                            c.setForeground(new Color(253, 160, 23));
                            if (column == 3) {
                                setHorizontalAlignment(SwingConstants.RIGHT);
                            } else if (column == 1 || column == 5) {
                                setHorizontalAlignment(SwingConstants.LEFT);
                            } else {
                                setHorizontalAlignment(SwingConstants.CENTER);
                            }
                        }
                    }

                    if ("situacoes".equals(tab1)) {
                        if (jtable.getValueAt(row, 5) != null) {
                            if (jtable.getValueAt(row, 5).toString().equals("VERDE")) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(0, 255, 0));
                            } else if (jtable.getValueAt(row, 5).toString().equals("AZUL")) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(0, 0, 255));
                            } else if (jtable.getValueAt(row, 5).toString().equals("VERMELHO")) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                c.setForeground(new Color(255, 0, 0));
                            } else if (jtable.getValueAt(row, 5).toString().equals("LARANJA")) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(255, 127, 39));
                            } else if (jtable.getValueAt(row, 5).toString().equals("AMARELO")) {
                                c.setFont(new Font("Open Sans", Font.BOLD, 11));
                                c.setForeground(new Color(240, 200, 0));
                            }
                        }
                    }

                    if ("os1".equals(tab1)) {
                        if (jtable.getValueAt(row, 11) != null && jtable.getValueAt(row, 11).toString().equals("VERDE")) {
                            if (column == 8) {
                                setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(0, 255, 0));
                            }
                        } else if (jtable.getValueAt(row, 11) != null && jtable.getValueAt(row, 11).toString().equals("AZUL")) {
                            if (column == 8) {
                                setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(0, 0, 255));
                            }
                        } else if (jtable.getValueAt(row, 11) != null && jtable.getValueAt(row, 11).toString().equals("VERMELHO")) {
                            if (column == 8) {
                                setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(255, 0, 0));
                            }
                        } else if (jtable.getValueAt(row, 11) != null && jtable.getValueAt(row, 11).toString().equals("LARANJA")) {
                            if (column == 8) {
                                setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(255, 127, 39));
                            }
                        } else if (jtable.getValueAt(row, 11) != null && jtable.getValueAt(row, 11).toString().equals("AMARELO")) {
                            if (column == 8) {
                                setFont(new Font("Open Sans", Font.BOLD, 11));
                                setForeground(new Color(240, 200, 0));
                            }
                        }

                        if (column == 0 || column == 1 || column == 2 || column == 8 || column == 9) {
                            setHorizontalAlignment(SwingConstants.CENTER);
                        }

                        if (column == 5 || column == 6 || column == 7) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

//----------------------------------------------------------------------------------------------------------------------------------------------------------
// Parametros para alinhar campos específicos da JTable
//----------------------------------------------------------------------------------------------------------------------------------------------------------
                    if (null != tab1) {
                        switch (tab1) {
                            case "estoqueListagem":
                                if (column == 1) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 3 || column == 4 || column == 5 || column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "estoqueDetalhado":
                                if (!"".equals(tab2)) {
                                    if (column == 1) {
                                        setHorizontalAlignment(SwingConstants.LEFT);
                                    }
                                    if (column == 4 || column == 5 || column == 6) {
                                        setHorizontalAlignment(SwingConstants.RIGHT);
                                    }
                                } else {
                                    if (column == 1 || column == 2 || column == 3) {
                                        setHorizontalAlignment(SwingConstants.LEFT);
                                    }
                                    if (column == 5 || column == 6 || column == 7) {
                                        setHorizontalAlignment(SwingConstants.RIGHT);
                                    }
                                }
                                break;
                            case "entradaNota":
                                if (column == 3) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 5 || column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "qtdePecasQtdeMax":
                                if (column == 1) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 4 || column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "centroDeCustos":
                                if (column == 1) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 3) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 5) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                break;
                            case "venda":
                                if (column == 2 || column == 3 || column == 4) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 7) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "prod":
                            case "serv":
                            case "prodServ":
                            case "prodVend":
                                if (column == 1) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 3 || column == 4 || column == 5 || column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "prodMarcaFornec":
                                if (column == 1 || column == 2 || column == 3) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 3 || column == 5) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "cfe":
                                if (column == 5) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "orcamento":
                                if (column == 0) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 4) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "os":
                                if (column == 3) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 4 || column == 5 || column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                            case "os2":
                                if (column == 5 || column == 6 || column == 7) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                if (column == 2 || column == 3) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                            case "os3":
                                if (column == 8) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "OSM9":
                                if (column == 9) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                if (column == 4 || column == 5 || column == 6 || column == 7) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                break;
                            case "buscarProdutos":
                                if (column == 3 || column == 4 || column == 5 || column == 15 || column == 16) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                if (column == 0 || column == 1 || column == 12 || column == 13 || column == 14) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 2 || column == 6 || column == 7 || column == 8 || column == 9 || column == 10 || column == 11 || column == 17) {
                                    setHorizontalAlignment(SwingConstants.CENTER);
                                }
                                break;
                            case "nota":
                                if (null != tab2) {
                                    switch (tab2) {
                                        case "todas":
                                            if (column == 1 || column == 5) {
                                                setHorizontalAlignment(SwingConstants.LEFT);
                                            }
                                            if (column == 4) {
                                                setHorizontalAlignment(SwingConstants.RIGHT);
                                            }
                                            break;
                                        case "pendEnv":
                                            if (column == 1) {
                                                setHorizontalAlignment(SwingConstants.LEFT);
                                            }
                                            if (column == 4) {
                                                setHorizontalAlignment(SwingConstants.RIGHT);
                                            }
                                            break;
                                        case "outras":
                                            if (column == 1) {
                                                setHorizontalAlignment(SwingConstants.LEFT);
                                            }
                                            if (column == 5) {
                                                setHorizontalAlignment(SwingConstants.RIGHT);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case "financeiroVendas":
                                if (column == 2) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                if (column == 3 || column == 4 || column == 5 || column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "vendasComLucro":
                                if (column == 2 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8 || column == 9) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "contas":
                                if (column == 5) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                if (column == 1 || column == 2) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                break;
                            case "ContasAPagarXReceber":
                                if (column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8 || column == 9 || column == 10) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                }
                                break;
                            case "ContasAReceber":
                                if (column == 7) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                } else if (column == 4 || column == 5 || column == 10 || column == 12) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                break;
                            case "ContasRecebidas":
                                if (column == 11 || column == 12) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                } else if (column == 3 || column == 4 || column == 13 || column == 16) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                break;
                            case "roteiro":
                                if (column == 5) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                }
                                break;
                            case "OSM13":
                                if (column == 6) {
                                    setHorizontalAlignment(SwingConstants.RIGHT);
                                } else if (column == 3) {
                                    setHorizontalAlignment(SwingConstants.LEFT);
                                } else if (column == 9) {
                                    if (condicao.equals(jtable.getValueAt(row, 8))) {
                                        ImageIcon imagem = new javax.swing.ImageIcon(getClass().getResource("/br/com/odbo/resources/seta-devolver.png"));
                                        setIcon(imagem);
                                    } else {
                                        ImageIcon imagem = new javax.swing.ImageIcon(getClass().getResource("/br/com/odbo/resources/seta-Des-devolver.png"));
                                        setIcon(imagem);
                                    }
                                    if (jtable.getValueAt(row, 8).equals("")) {
                                        setIcon(null);
                                    }

                                } else {
                                    setIcon(null);
                                    setHorizontalAlignment(SwingConstants.CENTER);
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    if ("condicional".equals(tab1)) {
                        if (column == 0) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                    }

                    if ("condicional2".equals(tab2)) {
                        if (column == 1) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                        if (column == 2) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                        if (column == 4) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

                    if ("crediariosClientes".equals(tab1)) {
                        if (column == 1) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                        if (column == 2) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

                    if ("crediariosSimplificado".equals(tab1)) {
                        if (column == 1) {
                            setHorizontalAlignment(SwingConstants.LEFT);
                        }
                        if (column == 3) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }

                    if ("crediariosParcelas".equals(tab2)) {
                        if (column == 4 || column == 5) {
                            setHorizontalAlignment(SwingConstants.RIGHT);
                        }
                    }
//----------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------------------------
                }
                return c;
            }
        });
    }

    public void params(String tab1, String tab2, JTable jtable) {
        jtable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if ("condicional".equals(tab1)) {
                    if (column == 2) {
                        setHorizontalAlignment(SwingConstants.RIGHT);
                    }
                    if (column == 4) {
                        setHorizontalAlignment(SwingConstants.RIGHT);
                    }
                }
                return c;
            }
        });
    }

}
