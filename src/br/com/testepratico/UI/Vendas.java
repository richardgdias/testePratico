/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.testepratico.UI;

import br.com.testepratico.BO.VendasBO;
import br.com.testepratico.DTO.ClientesDTO;
import br.com.testepratico.DTO.ProdutosDTO;
import br.com.testepratico.DTO.ProdutosVendaDTO;
import br.com.testepratico.DTO.VendasDTO;
import br.com.testepratico.Utils.JTableRenderer;
import br.com.testepratico.Utils.ModeloTabela;
import br.com.testepratico.Utils.funcoes;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_TAB;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Richard
 */
public class Vendas extends javax.swing.JInternalFrame {

    funcoes f = new funcoes();
    String operacao = "";
    ArrayList<ProdutosVendaDTO> produtosAdicionados = new ArrayList();
    LocalDate proximaDataCompra;
    double valorDisponivel;

    /**
     * Creates new form Clientes
     */
    public Vendas() {
        initComponents();
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXECUTAR AS CONFIGURAÇÕES INICIAIS
     */
    public void configuracoesIniciais() {
        f.botaoTransparente(jPanelVisualizar);
        f.botaoTransparente(jPanelManipular);

        f.selectAll(jPanelVisualizar);
        f.selectAll(jPanelManipular);

        f.upperCase(jPanelVisualizar);
        f.upperCase(jPanelManipular);

        jspVendas.setBorder(null);
        tabelaVendas.setShowHorizontalLines(false);
        tabelaVendas.setShowGrid(false);
        tabelaVendas.setShowVerticalLines(false);

        jspProdutosVendaVisualizar.setBorder(null);
        tabelaProdutosVendaVisualizar.setShowHorizontalLines(false);
        tabelaProdutosVendaVisualizar.setShowGrid(false);
        tabelaProdutosVendaVisualizar.setShowVerticalLines(false);

        jspTabelaProdutosVendaManipular.setBorder(null);
        tabelaProdutosVendaManipular.setShowHorizontalLines(false);
        tabelaProdutosVendaManipular.setShowGrid(false);
        tabelaProdutosVendaManipular.setShowVerticalLines(false);

        String dataAtual = f.pegarDataString();
        int mes = Integer.parseInt(dataAtual.substring(3, 5));
        String diaUm = f.pegarPrimeiroDiaMes(mes - 1);

        txtDataInicial.setText(diaUm);
        txtDataFinal.setText(dataAtual);

        definirNewCmb();
        definirTeclas();
        preencherCmb();
        preencherTabelaVendas("", "TODAS", txtDataInicial.getText(), txtDataFinal.getText());
        preencherTabelaProdutosVisualizar("0");
        preencherTabelaProdutosManipular();
        calcularTotalProdutosManipulados();

        txtPesquisar.requestFocus();
    }

    /**
     * MODIFICA PROPRIEDADES GRÁFICAS DO COMBOBOX. REALIZA A SOBREPOSIÇÃO DOS
     * SEUS EVENTOS
     */
    public void definirNewCmb() {
        cmbPesquisarPor.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        cmbPesquisarPor.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.comFoco(lblBordaPesquisarPor, jPanelVisualizar);
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.semFoco(lblBordaPesquisarPor, jPanelVisualizar);
            }
        });

        cmbNomeCliente.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        cmbNomeCliente.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.comFoco(lblBordaNomeCliente, jPanelManipular);
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.semFoco(lblBordaNomeCliente, jPanelManipular);
            }
        });

        cmbNomeCliente.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == VK_ENTER || evt.getKeyCode() == VK_TAB) {
                    txtCodigoProduto.requestFocus();
                }
            }
        });

        cmbDescricaoProduto.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        cmbDescricaoProduto.getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.comFoco(lblBordaDescricaoProduto, jPanelManipular);
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.semFoco(lblBordaDescricaoProduto, jPanelManipular);
            }
        });

        cmbDescricaoProduto.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == VK_ENTER || evt.getKeyCode() == VK_TAB) {
                    txtQuantidade.requestFocus();
                }
            }
        });
    }

    /**
     * MÉTODO RESPONSÁVEL POR CRIAR OS LISTENERS DAS TECLAS DE ATALHO
     */
    public void definirTeclas() {
        InputMap inputMapFechar = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        InputMap inputMapNovo = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        InputMap inputMapSalvar = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        InputMap inputMapCancelar = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMapFechar.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "fechar");
        inputMapNovo.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
        inputMapSalvar.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "salvar");
        inputMapCancelar.put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "cancelar");

        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapFechar);
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapNovo);
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapSalvar);
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapCancelar);

        //F1
        this.getRootPane().getActionMap().put("novo", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                abrirTelaNovaVenda();
            }
        });

        //F7
        this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                salvarVenda();
            }
        });

        //F8
        this.getRootPane().getActionMap().put("cancelar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                cancelar();
            }
        });

        //ESC
        this.getRootPane().getActionMap().put("fechar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER OS COMBOBOX COM OS CLIENTES E PRODUTOS
     * CADASTRADOS
     */
    public void preencherCmb() {
        VendasBO vBO = new VendasBO();

        //CLIENTES
        ArrayList arrayClientes = vBO.carregarClientesCmb();

        cmbNomeCliente.removeAllItems();
        cmbNomeCliente.addItem("");
        for (Object clientes : arrayClientes) {
            Object[] cliente = (Object[]) clientes;
            cmbNomeCliente.addItem(String.valueOf(cliente[0]));
        }

        //PRODUTOS
        ArrayList arrayProdutos = vBO.carregarProdutosCmb();

        cmbDescricaoProduto.removeAllItems();
        cmbDescricaoProduto.addItem("");
        for (Object produtos : arrayProdutos) {
            Object[] produto = (Object[]) produtos;
            cmbDescricaoProduto.addItem(String.valueOf(produto[0]));
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER A TABELA DE VENDAS
     *
     * @param dado
     * @param tipo
     * @param dataInicial
     * @param dataFinal
     */
    public void preencherTabelaVendas(String dado, String tipo, String dataInicial, String dataFinal) {
        VendasBO vBO = new VendasBO();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "CÓDIGO CLIENTE", "CLIENTE", "TOTAL", "DATA COMPRA", "", ""};

        dados = vBO.preencherTabelaVendas(dado, tipo, dataInicial, dataFinal);
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer ladoDireito = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tabelaVendas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        ladoDireito.setHorizontalAlignment(SwingConstants.RIGHT);

        tabelaVendas.setModel(modelo);

        //CODIGO
        tabelaVendas.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabelaVendas.getColumnModel().getColumn(0).setCellRenderer(centralizado);

        //CODIGO CLIENTE
        tabelaVendas.getColumnModel().getColumn(1).setMaxWidth(0);
        tabelaVendas.getColumnModel().getColumn(1).setMinWidth(0);
        tabelaVendas.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        tabelaVendas.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);

        //CLIENTE
        tabelaVendas.getColumnModel().getColumn(2).setPreferredWidth(600);

        //TOTAL
        tabelaVendas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaVendas.getColumnModel().getColumn(3).setCellRenderer(ladoDireito);

        //DATA COMPRA
        tabelaVendas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaVendas.getColumnModel().getColumn(4).setCellRenderer(centralizado);

        //EDITAR
        tabelaVendas.getColumnModel().getColumn(5).setPreferredWidth(25);

        //EXCLUIR
        tabelaVendas.getColumnModel().getColumn(6).setPreferredWidth(25);

        tabelaVendas.getTableHeader().setReorderingAllowed(false);
        tabelaVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVendas.setSelectionBackground(new Color(112, 112, 112));
        jspVendas.getViewport().setBackground(Color.WHITE);
        tabelaVendas.setRowHeight(25);

        if (!"".equals(tabelaVendas.getValueAt(0, 0).toString())) {
            ImageIcon imagem = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/lixeira.png"));
            ImageIcon imagem2 = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/seta-Des-devolver.png"));
            JTableRenderer renderer = new JTableRenderer(imagem);
            JTableRenderer renderer2 = new JTableRenderer(imagem2);
            tabelaVendas.getColumnModel().getColumn(5).setCellRenderer(renderer2);
            tabelaVendas.getColumnModel().getColumn(6).setCellRenderer(renderer);
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER A TABELA DE PRODUTOS DA TELA DE
     * VISUALIZAÇÃO DA VENDA
     *
     * @param codigoVenda
     */
    public void preencherTabelaProdutosVisualizar(String codigoVenda) {
        VendasBO vBO = new VendasBO();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "DESCRIÇÃO PRODUTO", "VALOR", "QTDE", "TOTAL"};

        dados = vBO.preencherTabelaProdutosVenda(codigoVenda);
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer ladoDireito = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tabelaProdutosVendaVisualizar.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        ladoDireito.setHorizontalAlignment(SwingConstants.RIGHT);

        tabelaProdutosVendaVisualizar.setModel(modelo);

        //CODIGO
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(0).setPreferredWidth(100);

        //DESCRIÇÃO
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(1).setPreferredWidth(600);

        //VALOR
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(2).setCellRenderer(ladoDireito);

        //QTDE
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(3).setCellRenderer(centralizado);

        //TOTAL
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaProdutosVendaVisualizar.getColumnModel().getColumn(4).setCellRenderer(ladoDireito);

        tabelaProdutosVendaVisualizar.getTableHeader().setReorderingAllowed(false);
        tabelaProdutosVendaVisualizar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutosVendaVisualizar.setSelectionBackground(new Color(112, 112, 112));
        jspProdutosVendaVisualizar.getViewport().setBackground(Color.WHITE);
        tabelaProdutosVendaVisualizar.setRowHeight(25);
    }

    /**
     * MÉTODO RESPONSÁVEL POR PREENCHER A TABELA DE PRODUTOS DA TELA DE
     * MANIPULAÇÃO DA VENDA
     */
    public void preencherTabelaProdutosManipular() {
        String[] colunas = new String[]{"CÓDIGO", "DESCRIÇÃO PRODUTO", "VALOR", "QTDE", "", "", "CODIGO VENDA", "TOTAL"};

        ModeloTabela modelo = new ModeloTabela(tratarProdutosTabelaManipulacao(), colunas);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer ladoDireito = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tabelaProdutosVendaManipular.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        ladoDireito.setHorizontalAlignment(SwingConstants.RIGHT);

        tabelaProdutosVendaManipular.setModel(modelo);

        //CODIGO
        tabelaProdutosVendaManipular.getColumnModel().getColumn(0).setPreferredWidth(100);

        //DESCRIÇÃO
        tabelaProdutosVendaManipular.getColumnModel().getColumn(1).setPreferredWidth(600);

        //VALOR
        tabelaProdutosVendaManipular.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaProdutosVendaManipular.getColumnModel().getColumn(2).setCellRenderer(ladoDireito);

        //QTDE
        tabelaProdutosVendaManipular.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaProdutosVendaManipular.getColumnModel().getColumn(3).setCellRenderer(centralizado);

        //EDITAR
        tabelaProdutosVendaManipular.getColumnModel().getColumn(4).setPreferredWidth(25);

        //EXCLUIR
        tabelaProdutosVendaManipular.getColumnModel().getColumn(5).setPreferredWidth(25);

        //CÓDIGO DA VENDA
        tabelaProdutosVendaManipular.getColumnModel().getColumn(6).setMaxWidth(0);
        tabelaProdutosVendaManipular.getColumnModel().getColumn(6).setMinWidth(0);
        tabelaProdutosVendaManipular.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        tabelaProdutosVendaManipular.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);

        //TOTAL
        tabelaProdutosVendaManipular.getColumnModel().getColumn(7).setMaxWidth(0);
        tabelaProdutosVendaManipular.getColumnModel().getColumn(7).setMinWidth(0);
        tabelaProdutosVendaManipular.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
        tabelaProdutosVendaManipular.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);

        tabelaProdutosVendaManipular.getTableHeader().setReorderingAllowed(false);
        tabelaProdutosVendaManipular.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutosVendaManipular.setSelectionBackground(new Color(112, 112, 112));
        jspTabelaProdutosVendaManipular.getViewport().setBackground(Color.WHITE);
        tabelaProdutosVendaManipular.setRowHeight(25);

        ImageIcon imagem = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/lixeira.png"));
        ImageIcon imagem2 = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/seta-Des-devolver.png"));
        JTableRenderer renderer = new JTableRenderer(imagem);
        JTableRenderer renderer2 = new JTableRenderer(imagem2);
        tabelaProdutosVendaManipular.getColumnModel().getColumn(4).setCellRenderer(renderer2);
        tabelaProdutosVendaManipular.getColumnModel().getColumn(5).setCellRenderer(renderer);
    }

    /**
     * MÉTODO RESPONSÁVEL POR TRATAR O ARRAY PARA PREENCHER A TABELA DE PRODUTOS
     *
     * @return ArrayList
     */
    public ArrayList tratarProdutosTabelaManipulacao() {
        ArrayList produtos = new ArrayList();

        if (produtosAdicionados != null) {
            for (ProdutosVendaDTO item : produtosAdicionados) {
                produtos.add(new Object[]{
                    item.getCodigoProduto(),
                    item.getDescricaoProduto(),
                    f.convMoeda(String.valueOf(item.getValorProduto())),
                    item.getQuantidade(),
                    "",
                    "",
                    item.getCodigoVenda(),
                    item.getValorTotal()
                });
            }
        } else {
            produtos.add(new Object[]{"", "", "", "", "", "", "", ""});
        }

        return produtos;
    }

    /**
     * MÉTODO RESPONSÁVEL POR LIMPAR OS CAMPOS
     */
    public void limparCampos() {
        txtCodigoVenda.setText("");
        txtCodigoCliente.setText("");
        cmbNomeCliente.setSelectedItem("");
        txtValor.setText(f.convMoeda("0"));
        txtCodigoProduto.setText("");
        cmbDescricaoProduto.setSelectedItem("");
        txtQuantidade.setText("");
    }

    /**
     * MÉTODO RESPONSÁVEL POR SALVAR OS DADOS DO CLIENTE NO BANCO DE DADOS
     */
    public void salvarVenda() {
        VendasDTO vDTO = new VendasDTO();
        VendasBO vBO = new VendasBO();
        boolean retorno = true;
        double total = 0;

        if (!"".equals(cmbNomeCliente.getSelectedItem().toString()) && !"".equals(txtCodigoCliente.getText())) {
            for (int i = 0; i < tabelaProdutosVendaManipular.getRowCount(); i++) {
                double valor = Double.parseDouble(f.convDouble(tabelaProdutosVendaManipular.getValueAt(i, 2).toString()));
                double qtde = Double.parseDouble(f.convDouble(tabelaProdutosVendaManipular.getValueAt(i, 3).toString()));

                total += valor * qtde;
            }

            if (verificarCreditoCliente(total)) {
                vDTO.setCodigo(Integer.parseInt(txtCodigoVenda.getText()));
                vDTO.setCodigoCliente(Integer.parseInt(txtCodigoCliente.getText()));
                vDTO.setNomeCliente(cmbNomeCliente.getSelectedItem().toString());
                vDTO.setArrayProdutos(produtosAdicionados);
                vDTO.setValorTotal(total);

                switch (operacao) {
                    case "NOVO":
                        retorno = vBO.inserirVenda(vDTO);

                        if (retorno) {
                            retorno = vBO.inserirProdutosVenda(vDTO, txtCodigoVenda.getText());
                        }
                        break;
                    case "ALTERAR":
                        retorno = vBO.alterarVenda(vDTO);

                        if (retorno) {
                            retorno = vBO.inserirProdutosVenda(vDTO, txtCodigoVenda.getText());
                        }
                        break;
                }

                if (retorno) {
                    switch (operacao) {
                        case "NOVO":
                            f.aviso("VENDA REGISTRADA COM SUCESSO");
                            break;
                        case "ALTERAR":
                            f.aviso("VENDA ALTERADA COM SUCESSO");
                            break;
                    }

                    CardLayout card = (CardLayout) jPanelCard.getLayout();
                    card.show(jPanelCard, "visualizar");

                    preencherTabelaVendas("", "TODAS", txtDataInicial.getText(), txtDataFinal.getText());
                    preencherTabelaProdutosVisualizar("0");
                    cmbPesquisarPor.requestFocus();
                    cmbPesquisarPor.setSelectedItem("TODAS");
                } else {
                    f.aviso("ERRO AO TENTAR REGISTRAR VENDA");
                }
            } else {
                f.aviso("COMPRA EXCEDEU O LIMITE DE CRÉDITO");
                f.aviso("VALOR DISPONÍVEL: " + f.convMoeda(String.valueOf(valorDisponivel))
                        + "\nDATA PRÓXIMO FECHAMENTO: " + f.DataEmString(String.valueOf(proximaDataCompra)));
            }
        } else {
            f.aviso("POR FAVOR, PREENCHA OS DADOS DO CLIENTE CORRETAMENTE");
            cmbNomeCliente.requestFocus();
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR VERIFICAR O CRÉDITO DO CLIENTE ANTES DE FAZER UMA
     * VENDA
     *
     * @param valorCompra
     * @return boolean
     */
    public boolean verificarCreditoCliente(double valorCompra) {
        VendasBO vBO = new VendasBO();
        ClientesDTO cDTO = new ClientesDTO();
        LocalDate dataAtual = LocalDate.now();

        cDTO = vBO.buscarDadosCliente(txtCodigoCliente.getText());

        int dia = cDTO.getDiaFechamentoFatura();
        int ano = dataAtual.getYear();
        Month mes = dataAtual.getMonth();

        //montando data do fechamento do cliente
        LocalDate dataCompleta = LocalDate.of(ano, mes, dia);
        LocalDate dataAnterior = dataCompleta.minusMonths(1);

        /**
         * verifica se a data de fechamento do cliente já passou nesse mes se já
         * passou, adiciona + 1 mês na data
         */
        boolean jaPassou = dataCompleta.isBefore(dataAtual)
                && dataCompleta.getMonthValue() == dataAtual.getMonthValue()
                && dataCompleta.getYear() == dataAtual.getYear();

        if (jaPassou) {
            proximaDataCompra = dataCompleta.plusMonths(1);
        } else {
            proximaDataCompra = dataCompleta;
        }

        double valorComprasRealizadas = vBO.buscarValorComprasRealizadas(txtCodigoCliente.getText(), dataCompleta, dataAnterior);
        double limiteDisponivel = cDTO.getLimiteCompra() - valorComprasRealizadas;
        double resultado = valorComprasRealizadas + valorCompra;
        valorDisponivel = limiteDisponivel;

        return resultado < cDTO.getLimiteCompra();
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR OS DADOS DA VENDA NO BANCO DE DADOS
     *
     * @param codigoVenda
     */
    public void alterarVenda(int codigoVenda) {
        VendasBO vBO = new VendasBO();
        VendasDTO vDTO = new VendasDTO();

        operacao = "ALTERAR";
        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "manipular");

        vDTO = vBO.buscarDadosVenda(String.valueOf(codigoVenda), "CÓDIGO");
        produtosAdicionados = vBO.retornarProdutosVendaAlterada(codigoVenda);

        txtCodigoVenda.setText(String.valueOf(vDTO.getCodigo()));
        txtCodigoCliente.setText(String.valueOf(vDTO.getCodigoCliente()));
        cmbNomeCliente.setSelectedItem(String.valueOf(vDTO.getNomeCliente()));

        preencherTabelaProdutosManipular();
        calcularTotalProdutosManipulados();

        txtCodigoCliente.requestFocus();
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR A VENDA DO BANCO DE DADOS
     *
     * @param codigoVenda
     */
    public void excluirVenda(int codigoVenda) {
        if (("sim").equals(f.telaResposta("DESEJA REALMENTE EXCLUIR ESTA VENDA?"))) {
            VendasBO vBO = new VendasBO();
            boolean retorno = vBO.excluirVenda(codigoVenda);

            if (retorno) {
                preencherTabelaVendas("", "TODAS", txtDataInicial.getText(), txtDataFinal.getText());
                preencherTabelaProdutosVisualizar("0");
            } else {
                f.aviso("ERRO AO TENTAR EXCLUIR VENDA");
            }
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR CANCELAR A TELA DE NOVA VENDA E RETORNAR PARA TELA
     * INCIAL
     */
    public void cancelar() {
        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "visualizar");
        preencherTabelaVendas("", "TODAS", txtDataInicial.getText(), txtDataFinal.getText());
        preencherTabelaProdutosVisualizar("0");
        cmbPesquisarPor.requestFocus();
        cmbPesquisarPor.setSelectedItem("TODAS");
    }

    /**
     * MÉTODO RESPONSÁVEL POR ABRIR TELA PARA CADASTRAR NOVA VENDA
     */
    public void abrirTelaNovaVenda() {
        VendasBO vBO = new VendasBO();

        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "manipular");

        limparCampos();
        produtosAdicionados.clear();
        preencherTabelaProdutosManipular();
        txtCodigoVenda.setText(vBO.gerarNovoCodigoVenda());
        txtCodigoCliente.requestFocus();
        operacao = "NOVO";
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR AS INFORMAÇÕES DE UM CLIENTE
     *
     * @param dado
     * @param tipo
     */
    public void buscarCliente(String dado, String tipo) {
        VendasBO vBO = new VendasBO();

        switch (tipo) {
            case "codigo":
                cmbNomeCliente.setSelectedItem(vBO.buscarNomeCliente(dado));
                break;
            case "nome":
                txtCodigoCliente.setText(String.valueOf(vBO.buscarCodigoCliente(cmbNomeCliente.getSelectedItem().toString())));
                break;
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR BUSCAR AS INFORMAÇÕES DE UM PRODUTO
     *
     * @param dado
     * @param tipo
     */
    public void buscarProduto(String dado, String tipo) {
        VendasBO vBO = new VendasBO();
        ProdutosDTO pDTO = new ProdutosDTO();

        switch (tipo) {
            case "codigo":
                pDTO = vBO.buscarDadosProduto(dado, tipo);
                break;
            case "descricao":
                pDTO = vBO.buscarDadosProduto(dado, tipo);
                break;
        }

        if (String.valueOf(pDTO.getCodigo()) != null && !String.valueOf(pDTO.getCodigo()).equals("")) {
            txtCodigoProduto.setText(String.valueOf(pDTO.getCodigo()));
            cmbDescricaoProduto.setSelectedItem(pDTO.getDescricaoProduto());
            txtValor.setText(f.convMoeda(String.valueOf(pDTO.getValorProduto())));
            txtQuantidade.requestFocus();
        } else {
            f.aviso("PRODUTO NÃO ENCONTRADO");
            txtCodigoProduto.requestFocus();
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR CALCULAR OS VALORES TOTAIS DO BOLETO
     */
    public void calcularTotalProdutosManipulados() {
        double totalBoleto = 0;

        for (int i = 0; i < tabelaProdutosVendaManipular.getRowCount(); i++) {
            double valor = Double.parseDouble(f.convDouble(tabelaProdutosVendaManipular.getValueAt(i, 2).toString()));
            double qtde = Double.parseDouble(f.convDouble(tabelaProdutosVendaManipular.getValueAt(i, 3).toString()));

            totalBoleto += valor * qtde;
        }

        lblTotal.setText("TOTAL: " + f.convMoeda(String.valueOf(totalBoleto)));
        lblQtde.setText("QTDE: " + tabelaProdutosVendaManipular.getRowCount());
    }

    /**
     * MÉTODO RESPONSÁVEL POR INSERIR NOVO PRODUTO NA TABELA DA VENDA
     */
    public void inserirProduto() {
        if (verificarProduto()) {
            ProdutosVendaDTO pvDTO = new ProdutosVendaDTO();

            pvDTO.setCodigoProduto(Integer.parseInt(txtCodigoProduto.getText()));
            pvDTO.setDescricaoProduto(cmbDescricaoProduto.getSelectedItem().toString());
            pvDTO.setValorProduto(Double.parseDouble(f.convDouble(txtValor.getText())));
            pvDTO.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            pvDTO.setValorTotal(Integer.parseInt(txtQuantidade.getText()) * Double.parseDouble(f.convDouble(txtValor.getText())));
            pvDTO.setCodigoVenda(Integer.parseInt(txtCodigoVenda.getText()));

            produtosAdicionados.add(pvDTO);
            preencherTabelaProdutosManipular();
            calcularTotalProdutosManipulados();

            txtCodigoProduto.setText("");
            cmbDescricaoProduto.setSelectedItem("");
            txtValor.setText(f.convMoeda("0"));
            txtQuantidade.setText("");
        } else {
            f.aviso("PRODUTO JÁ ADICIONADO, NÃO É POSSÍVEL ADICIONADO NOVAMENTE");
            cmbDescricaoProduto.requestFocus();
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR VERIFICAR SE O PRODUTO QUE ESTÁ SENDO ADICIONADO
     * JÁ EXISTE NA TABELA
     *
     * @return boolean
     */
    public boolean verificarProduto() {
        int aux = 0;

        for (ProdutosVendaDTO item : produtosAdicionados) {
            if (Integer.parseInt(txtCodigoProduto.getText()) == item.getCodigoProduto()) {
                aux++;
            }
        }

        return aux == 0;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPrincipal = new javax.swing.JPanel();
        jPanelCard = new javax.swing.JPanel();
        jPanelVisualizar = new javax.swing.JPanel();
        lblVendas = new javax.swing.JLabel();
        lblBordaVendas = new javax.swing.JLabel();
        lblPesquisar = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        lblBordaPesquisar = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        jspVendas = new javax.swing.JScrollPane();
        tabelaVendas = new javax.swing.JTable();
        lblBordaTabelaClientes = new javax.swing.JLabel();
        cmbPesquisarPor = new javax.swing.JComboBox<>();
        lblPesquisarPor = new javax.swing.JLabel();
        lblBordaPesquisarPor = new javax.swing.JLabel();
        jspProdutosVendaVisualizar = new javax.swing.JScrollPane();
        tabelaProdutosVendaVisualizar = new javax.swing.JTable();
        lblBordaTabelaClientes1 = new javax.swing.JLabel();
        lblProdutosVenda = new javax.swing.JLabel();
        lblBordaProdutosVenda = new javax.swing.JLabel();
        lblDataInicial = new javax.swing.JLabel();
        txtDataInicial = new javax.swing.JFormattedTextField();
        lblBordaDataInicial = new javax.swing.JLabel();
        lblDataFinal = new javax.swing.JLabel();
        txtDataFinal = new javax.swing.JFormattedTextField();
        lblBordaDataFinal = new javax.swing.JLabel();
        jPanelManipular = new javax.swing.JPanel();
        lblNomeCliente = new javax.swing.JLabel();
        cmbNomeCliente = new javax.swing.JComboBox<>();
        lblBordaNomeCliente = new javax.swing.JLabel();
        lblCodigoVenda = new javax.swing.JLabel();
        txtCodigoVenda = new javax.swing.JFormattedTextField();
        lblBordaCodigoVenda = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblProdutos = new javax.swing.JLabel();
        lblBordaProdutos = new javax.swing.JLabel();
        jspTabelaProdutosVendaManipular = new javax.swing.JScrollPane();
        tabelaProdutosVendaManipular = new javax.swing.JTable();
        lblBordaTabelaClientes2 = new javax.swing.JLabel();
        lblCodigoCliente = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JFormattedTextField();
        lblBordaCodigoCliente = new javax.swing.JLabel();
        lblDescricaoProduto = new javax.swing.JLabel();
        cmbDescricaoProduto = new javax.swing.JComboBox<>();
        lblBordaDescricaoProduto = new javax.swing.JLabel();
        lblCodigoProduto = new javax.swing.JLabel();
        txtCodigoProduto = new javax.swing.JFormattedTextField();
        lblBordaCodigoProduto = new javax.swing.JLabel();
        lblQuantidade = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JFormattedTextField();
        lblBordaQuantidade = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        txtValor = new javax.swing.JFormattedTextField();
        lblBordaValor = new javax.swing.JLabel();
        lblQtde = new javax.swing.JLabel();
        lblBordaQtde = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblBordaTotal = new javax.swing.JLabel();

        setBorder(null);
        setMaximumSize(new java.awt.Dimension(801, 554));
        setMinimumSize(new java.awt.Dimension(801, 554));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelCard.setLayout(new java.awt.CardLayout());

        jPanelVisualizar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVendas.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblVendas.setForeground(new java.awt.Color(255, 255, 255));
        lblVendas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVendas.setText("VENDAS");
        jPanelVisualizar.add(lblVendas, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 55, 215, 25));

        lblBordaVendas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBordaVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/abaConferencia.png"))); // NOI18N
        jPanelVisualizar.add(lblBordaVendas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 245, 32));

        lblPesquisar.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblPesquisar.setForeground(new java.awt.Color(154, 154, 154));
        lblPesquisar.setText("Pesquisar");
        jPanelVisualizar.add(lblPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 3, -1, -1));

        txtPesquisar.setBorder(null);
        txtPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesquisarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesquisarFocusLost(evt);
            }
        });
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });
        jPanelVisualizar.add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 19, 225, 18));

        lblBordaPesquisar.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaPesquisar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaPesquisar.setOpaque(true);
        jPanelVisualizar.add(lblBordaPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 0, 235, 40));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/novo.png"))); // NOI18N
        btnNovo.setBorder(null);
        btnNovo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNovoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNovoMouseExited(evt);
            }
        });
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanelVisualizar.add(btnNovo, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 130, 40));

        jspVendas.setBorder(null);

        tabelaVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelaVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaVendasMouseClicked(evt);
            }
        });
        jspVendas.setViewportView(tabelaVendas);

        jPanelVisualizar.add(jspVendas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 780, 220));

        lblBordaTabelaClientes.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaTabelaClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaTabelaClientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaTabelaClientes.setOpaque(true);
        jPanelVisualizar.add(lblBordaTabelaClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 80, 790, 230));

        cmbPesquisarPor.setEditable(true);
        cmbPesquisarPor.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        cmbPesquisarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "CÓDIGO VENDA", "CÓDIGO PRODUTO", "CÓDIGO CLIENTE", "DESCRIÇÃO PRODUTO", "NOME CLIENTE" }));
        jPanelVisualizar.add(cmbPesquisarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 160, 18));

        lblPesquisarPor.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblPesquisarPor.setForeground(new java.awt.Color(154, 154, 154));
        lblPesquisarPor.setText("Pesquisar Por");
        jPanelVisualizar.add(lblPesquisarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, -1, -1));

        lblBordaPesquisarPor.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaPesquisarPor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaPesquisarPor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaPesquisarPor.setOpaque(true);
        jPanelVisualizar.add(lblBordaPesquisarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 170, 40));

        jspProdutosVendaVisualizar.setBorder(null);

        tabelaProdutosVendaVisualizar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jspProdutosVendaVisualizar.setViewportView(tabelaProdutosVendaVisualizar);

        jPanelVisualizar.add(jspProdutosVendaVisualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 355, 780, 160));

        lblBordaTabelaClientes1.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaTabelaClientes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaTabelaClientes1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaTabelaClientes1.setOpaque(true);
        jPanelVisualizar.add(lblBordaTabelaClientes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 350, 790, 170));

        lblProdutosVenda.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblProdutosVenda.setForeground(new java.awt.Color(255, 255, 255));
        lblProdutosVenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProdutosVenda.setText("PRODUTOS DA VENDA");
        jPanelVisualizar.add(lblProdutosVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 325, 215, 25));

        lblBordaProdutosVenda.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBordaProdutosVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/abaConferencia.png"))); // NOI18N
        jPanelVisualizar.add(lblBordaProdutosVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 245, 32));

        lblDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        lblDataInicial.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblDataInicial.setForeground(new java.awt.Color(158, 158, 158));
        lblDataInicial.setText("Data Inicial");
        lblDataInicial.setOpaque(true);
        jPanelVisualizar.add(lblDataInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 3, 80, 15));

        txtDataInicial.setBorder(null);
        try {
            txtDataInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataInicialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataInicialFocusLost(evt);
            }
        });
        txtDataInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataInicialMouseClicked(evt);
            }
        });
        jPanelVisualizar.add(txtDataInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 19, 100, 18));

        lblBordaDataInicial.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaDataInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaDataInicial.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaDataInicial.setOpaque(true);
        jPanelVisualizar.add(lblBordaDataInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 110, 40));

        lblDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        lblDataFinal.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblDataFinal.setForeground(new java.awt.Color(158, 158, 158));
        lblDataFinal.setText("Data Final");
        lblDataFinal.setOpaque(true);
        jPanelVisualizar.add(lblDataFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 3, 80, 15));

        txtDataFinal.setBorder(null);
        try {
            txtDataFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataFinal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataFinalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataFinalFocusLost(evt);
            }
        });
        txtDataFinal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDataFinalMouseClicked(evt);
            }
        });
        jPanelVisualizar.add(txtDataFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 19, 100, 18));

        lblBordaDataFinal.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaDataFinal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaDataFinal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaDataFinal.setOpaque(true);
        jPanelVisualizar.add(lblBordaDataFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 110, 40));

        jPanelCard.add(jPanelVisualizar, "visualizar");

        jPanelManipular.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNomeCliente.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblNomeCliente.setForeground(new java.awt.Color(154, 154, 154));
        lblNomeCliente.setText("Nome Cliente");
        jPanelManipular.add(lblNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 3, 100, -1));

        cmbNomeCliente.setEditable(true);
        cmbNomeCliente.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        cmbNomeCliente.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbNomeClientePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanelManipular.add(cmbNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 19, 570, 18));

        lblBordaNomeCliente.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaNomeCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaNomeCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaNomeCliente.setOpaque(true);
        jPanelManipular.add(lblBordaNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 0, 580, 40));

        lblCodigoVenda.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblCodigoVenda.setForeground(new java.awt.Color(154, 154, 154));
        lblCodigoVenda.setText("Código Venda");
        jPanelManipular.add(lblCodigoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, -1, -1));

        txtCodigoVenda.setEditable(false);
        txtCodigoVenda.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoVenda.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCodigoVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoVenda.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtCodigoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoVendaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoVendaFocusLost(evt);
            }
        });
        jPanelManipular.add(txtCodigoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 90, 18));

        lblBordaCodigoVenda.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaCodigoVenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaCodigoVenda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaCodigoVenda.setOpaque(true);
        jPanelManipular.add(lblBordaCodigoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 100, 40));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/salvar.png"))); // NOI18N
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarMouseExited(evt);
            }
        });
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanelManipular.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 485, 120, 40));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/cancelar.png"))); // NOI18N
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanelManipular.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 485, 120, 40));

        lblProdutos.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblProdutos.setForeground(new java.awt.Color(255, 255, 255));
        lblProdutos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProdutos.setText("PRODUTOS");
        jPanelManipular.add(lblProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 105, 215, 25));

        lblBordaProdutos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBordaProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/abaConferencia.png"))); // NOI18N
        jPanelManipular.add(lblBordaProdutos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 245, 32));

        jspTabelaProdutosVendaManipular.setBorder(null);

        tabelaProdutosVendaManipular.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelaProdutosVendaManipular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProdutosVendaManipularMouseClicked(evt);
            }
        });
        jspTabelaProdutosVendaManipular.setViewportView(tabelaProdutosVendaManipular);

        jPanelManipular.add(jspTabelaProdutosVendaManipular, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 135, 780, 340));

        lblBordaTabelaClientes2.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaTabelaClientes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaTabelaClientes2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaTabelaClientes2.setOpaque(true);
        jPanelManipular.add(lblBordaTabelaClientes2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 790, 350));

        lblCodigoCliente.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblCodigoCliente.setForeground(new java.awt.Color(154, 154, 154));
        lblCodigoCliente.setText("Código Cliente");
        jPanelManipular.add(lblCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 3, -1, -1));

        txtCodigoCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCodigoCliente.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtCodigoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoClienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoClienteFocusLost(evt);
            }
        });
        txtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoClienteKeyTyped(evt);
            }
        });
        jPanelManipular.add(txtCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 19, 90, 18));

        lblBordaCodigoCliente.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaCodigoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaCodigoCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaCodigoCliente.setOpaque(true);
        jPanelManipular.add(lblBordaCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 100, 40));

        lblDescricaoProduto.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblDescricaoProduto.setForeground(new java.awt.Color(154, 154, 154));
        lblDescricaoProduto.setText("Descrição Produto");
        jPanelManipular.add(lblDescricaoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 53, 100, -1));

        cmbDescricaoProduto.setEditable(true);
        cmbDescricaoProduto.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        cmbDescricaoProduto.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbDescricaoProdutoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanelManipular.add(cmbDescricaoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 69, 465, 18));

        lblBordaDescricaoProduto.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaDescricaoProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaDescricaoProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaDescricaoProduto.setOpaque(true);
        jPanelManipular.add(lblBordaDescricaoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 475, 40));

        lblCodigoProduto.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblCodigoProduto.setForeground(new java.awt.Color(154, 154, 154));
        lblCodigoProduto.setText("Código Produto");
        jPanelManipular.add(lblCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, -1, -1));

        txtCodigoProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCodigoProduto.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtCodigoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoProdutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoProdutoFocusLost(evt);
            }
        });
        txtCodigoProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoProdutoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProdutoKeyTyped(evt);
            }
        });
        jPanelManipular.add(txtCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 69, 90, 18));

        lblBordaCodigoProduto.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaCodigoProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaCodigoProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaCodigoProduto.setOpaque(true);
        jPanelManipular.add(lblBordaCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, 100, 40));

        lblQuantidade.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblQuantidade.setForeground(new java.awt.Color(154, 154, 154));
        lblQuantidade.setText("Quantidade");
        jPanelManipular.add(lblQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 53, -1, -1));

        txtQuantidade.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtQuantidade.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusLost(evt);
            }
        });
        txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantidadeKeyPressed(evt);
            }
        });
        jPanelManipular.add(txtQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 69, 90, 18));

        lblBordaQuantidade.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaQuantidade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaQuantidade.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaQuantidade.setOpaque(true);
        jPanelManipular.add(lblBordaQuantidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 50, 100, 40));

        lblValor.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblValor.setForeground(new java.awt.Color(154, 154, 154));
        lblValor.setText("Valor");
        jPanelManipular.add(lblValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 53, -1, -1));

        txtValor.setEditable(false);
        txtValor.setBackground(new java.awt.Color(255, 255, 255));
        txtValor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtValor.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        jPanelManipular.add(txtValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(595, 69, 90, 18));

        lblBordaValor.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaValor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaValor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaValor.setOpaque(true);
        jPanelManipular.add(lblBordaValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 100, 40));

        lblQtde.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblQtde.setForeground(new java.awt.Color(255, 255, 255));
        lblQtde.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelManipular.add(lblQtde, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 482, 215, 25));

        lblBordaQtde.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBordaQtde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/abaConferencia.png"))); // NOI18N
        jPanelManipular.add(lblBordaQtde, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 480, 245, 32));

        lblTotal.setFont(new java.awt.Font("Open Sans", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanelManipular.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(263, 482, 215, 25));

        lblBordaTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBordaTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/abaConferencia.png"))); // NOI18N
        jPanelManipular.add(lblBordaTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 480, 245, 32));

        jPanelCard.add(jPanelManipular, "manipular");

        jPanelPrincipal.add(jPanelCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 530));

        getContentPane().add(jPanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseEntered
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/novo_off.png")));
    }//GEN-LAST:event_btnNovoMouseEntered

    private void btnNovoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNovoMouseExited
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/novo.png")));
    }//GEN-LAST:event_btnNovoMouseExited

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        configuracoesIniciais();
    }//GEN-LAST:event_formInternalFrameActivated

    private void txtCodigoVendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoVendaFocusGained
        f.comFoco(lblBordaCodigoVenda, jPanelManipular);
    }//GEN-LAST:event_txtCodigoVendaFocusGained

    private void txtCodigoVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoVendaFocusLost
        f.semFoco(lblBordaCodigoVenda, jPanelManipular);
    }//GEN-LAST:event_txtCodigoVendaFocusLost

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/salvar_off.png")));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/salvar.png")));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarVenda();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/cancelar_off.png")));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/cancelar.png")));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        abrirTelaNovaVenda();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        preencherTabelaVendas(txtPesquisar.getText(), cmbPesquisarPor.getSelectedItem().toString(), txtDataInicial.getText(), txtDataFinal.getText());
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void txtPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusGained
        f.comFoco(lblBordaPesquisar, jPanelVisualizar);
    }//GEN-LAST:event_txtPesquisarFocusGained

    private void txtPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusLost
        f.semFoco(lblBordaPesquisar, jPanelVisualizar);
    }//GEN-LAST:event_txtPesquisarFocusLost

    private void cmbNomeClientePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbNomeClientePopupMenuWillBecomeInvisible
        if (!"".equals(cmbNomeCliente.getSelectedItem().toString())) {
            buscarCliente(cmbNomeCliente.getSelectedItem().toString(), "nome");
        }
    }//GEN-LAST:event_cmbNomeClientePopupMenuWillBecomeInvisible

    private void txtCodigoClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoClienteFocusGained
        f.comFoco(lblBordaCodigoCliente, jPanelManipular);
    }//GEN-LAST:event_txtCodigoClienteFocusGained

    private void txtCodigoClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoClienteFocusLost
        f.semFoco(lblBordaCodigoCliente, jPanelManipular);
    }//GEN-LAST:event_txtCodigoClienteFocusLost

    private void txtCodigoProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProdutoFocusGained
        f.comFoco(lblBordaCodigoProduto, jPanelManipular);
    }//GEN-LAST:event_txtCodigoProdutoFocusGained

    private void txtCodigoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProdutoFocusLost
        f.semFoco(lblBordaCodigoProduto, jPanelManipular);
    }//GEN-LAST:event_txtCodigoProdutoFocusLost

    private void txtQuantidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusGained
        f.comFoco(lblBordaQuantidade, jPanelManipular);
    }//GEN-LAST:event_txtQuantidadeFocusGained

    private void txtQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusLost
        f.semFoco(lblBordaQuantidade, jPanelManipular);
    }//GEN-LAST:event_txtQuantidadeFocusLost

    private void txtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusGained
        f.comFoco(lblBordaValor, jPanelManipular);
    }//GEN-LAST:event_txtValorFocusGained

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
        f.semFoco(lblBordaValor, jPanelManipular);
    }//GEN-LAST:event_txtValorFocusLost

    private void txtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoCliente.getText())) {
                buscarCliente(txtCodigoCliente.getText(), "codigo");
                cmbNomeCliente.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoClienteKeyPressed

    private void txtCodigoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyTyped
        String caracteres = "1234567890";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoClienteKeyTyped

    private void cmbDescricaoProdutoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbDescricaoProdutoPopupMenuWillBecomeInvisible
        if (!"".equals(cmbDescricaoProduto.getSelectedItem().toString())) {
            buscarProduto(cmbDescricaoProduto.getSelectedItem().toString(), "descricao");
        }
    }//GEN-LAST:event_cmbDescricaoProdutoPopupMenuWillBecomeInvisible

    private void txtCodigoProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProdutoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoProduto.getText())) {
                buscarProduto(txtCodigoProduto.getText(), "codigo");
            }
        }
    }//GEN-LAST:event_txtCodigoProdutoKeyPressed

    private void txtCodigoProdutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProdutoKeyTyped
        String caracteres = "1234567890";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoProdutoKeyTyped

    private void txtQuantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantidadeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_TAB) {
            boolean validaInserir = false;

            if (!txtCodigoProduto.getText().equals("")) {
                if (!cmbDescricaoProduto.getSelectedItem().toString().equals("")) {
                    if (!txtQuantidade.getText().equals("")) {
                        validaInserir = true;
                    } else {
                        f.aviso("É NECESSÁRIO UMA QTDE");
                        txtQuantidade.requestFocusInWindow();
                    }
                } else {
                    f.aviso("É NECESSÁRIO UMA DESCRIÇÃO");
                    cmbDescricaoProduto.requestFocusInWindow();
                }
            } else {
                f.aviso("É NECESSÁRIO UM CÓDIGO");
                txtCodigoProduto.requestFocusInWindow();
            }

            if (validaInserir) {
                inserirProduto();
            }
        }
    }//GEN-LAST:event_txtQuantidadeKeyPressed

    private void tabelaProdutosVendaManipularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutosVendaManipularMouseClicked
        int linha = tabelaProdutosVendaManipular.getSelectedRow();

        if (tabelaProdutosVendaManipular.getSelectedColumn() == 5) {
            produtosAdicionados.remove(linha);

            preencherTabelaProdutosManipular();
            calcularTotalProdutosManipulados();
            txtCodigoProduto.requestFocusInWindow();
        } else if (tabelaProdutosVendaManipular.getSelectedColumn() == 4) {
            txtCodigoProduto.setText(tabelaProdutosVendaManipular.getValueAt(linha, 0).toString());
            cmbDescricaoProduto.setSelectedItem(tabelaProdutosVendaManipular.getValueAt(linha, 1).toString());
            txtValor.setText(tabelaProdutosVendaManipular.getValueAt(linha, 2).toString());
            txtQuantidade.setText(tabelaProdutosVendaManipular.getValueAt(linha, 3).toString());

            produtosAdicionados.remove(linha);

            preencherTabelaProdutosManipular();
            calcularTotalProdutosManipulados();
            txtQuantidade.requestFocusInWindow();
        }
    }//GEN-LAST:event_tabelaProdutosVendaManipularMouseClicked

    private void tabelaVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaVendasMouseClicked
        int linha = tabelaVendas.getSelectedRow();
        int codigoVenda = Integer.parseInt(tabelaVendas.getValueAt(linha, 0).toString());

        if (tabelaVendas.getSelectedColumn() == 5) {
            alterarVenda(codigoVenda);
        } else if (tabelaVendas.getSelectedColumn() == 6) {
            excluirVenda(codigoVenda);
        }

        preencherTabelaProdutosVisualizar(String.valueOf(codigoVenda));
    }//GEN-LAST:event_tabelaVendasMouseClicked

    private void txtDataInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataInicialFocusGained
        f.comFoco(lblBordaDataInicial, jPanelVisualizar);
    }//GEN-LAST:event_txtDataInicialFocusGained

    private void txtDataInicialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataInicialFocusLost
        f.semFoco(lblBordaDataInicial, jPanelVisualizar);
    }//GEN-LAST:event_txtDataInicialFocusLost

    private void txtDataInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataInicialMouseClicked
        f.comFoco(lblBordaDataInicial, jPanelVisualizar);

        Calendario c = new Calendario(null, true);
        LocalDate hoje = LocalDate.now();

        try {
            c.dataInicial = txtDataInicial.getText();
            c.setVisible(true);
            txtDataInicial.setText(c.getData());
        } catch (NullPointerException ex) {
            txtDataInicial.setText(f.DataEmString(hoje.toString()));
        } finally {
            f.semFoco(lblBordaDataInicial, jPanelVisualizar);
        }
        
        preencherTabelaVendas(txtPesquisar.getText(), cmbPesquisarPor.getSelectedItem().toString(), txtDataInicial.getText(), txtDataFinal.getText());
    }//GEN-LAST:event_txtDataInicialMouseClicked

    private void txtDataFinalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFinalFocusGained
        f.comFoco(lblBordaDataFinal, jPanelVisualizar);
    }//GEN-LAST:event_txtDataFinalFocusGained

    private void txtDataFinalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFinalFocusLost
        f.semFoco(lblBordaDataFinal, jPanelVisualizar);
    }//GEN-LAST:event_txtDataFinalFocusLost

    private void txtDataFinalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDataFinalMouseClicked
        f.comFoco(lblBordaDataFinal, jPanelVisualizar);

        Calendario c = new Calendario(null, true);
        LocalDate hoje = LocalDate.now();

        try {
            c.dataInicial = txtDataFinal.getText();
            c.setVisible(true);
            txtDataFinal.setText(c.getData());
        } catch (NullPointerException ex) {
            txtDataFinal.setText(f.DataEmString(hoje.toString()));
        } finally {
            f.semFoco(lblBordaDataFinal, jPanelVisualizar);
        }
        
        preencherTabelaVendas(txtPesquisar.getText(), cmbPesquisarPor.getSelectedItem().toString(), txtDataInicial.getText(), txtDataFinal.getText());
    }//GEN-LAST:event_txtDataFinalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbDescricaoProduto;
    private javax.swing.JComboBox<String> cmbNomeCliente;
    private javax.swing.JComboBox<String> cmbPesquisarPor;
    private javax.swing.JPanel jPanelCard;
    private javax.swing.JPanel jPanelManipular;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JPanel jPanelVisualizar;
    private javax.swing.JScrollPane jspProdutosVendaVisualizar;
    private javax.swing.JScrollPane jspTabelaProdutosVendaManipular;
    private javax.swing.JScrollPane jspVendas;
    private javax.swing.JLabel lblBordaCodigoCliente;
    private javax.swing.JLabel lblBordaCodigoProduto;
    private javax.swing.JLabel lblBordaCodigoVenda;
    private javax.swing.JLabel lblBordaDataFinal;
    private javax.swing.JLabel lblBordaDataInicial;
    private javax.swing.JLabel lblBordaDescricaoProduto;
    private javax.swing.JLabel lblBordaNomeCliente;
    private javax.swing.JLabel lblBordaPesquisar;
    private javax.swing.JLabel lblBordaPesquisarPor;
    private javax.swing.JLabel lblBordaProdutos;
    private javax.swing.JLabel lblBordaProdutosVenda;
    private javax.swing.JLabel lblBordaQtde;
    private javax.swing.JLabel lblBordaQuantidade;
    private javax.swing.JLabel lblBordaTabelaClientes;
    private javax.swing.JLabel lblBordaTabelaClientes1;
    private javax.swing.JLabel lblBordaTabelaClientes2;
    private javax.swing.JLabel lblBordaTotal;
    private javax.swing.JLabel lblBordaValor;
    private javax.swing.JLabel lblBordaVendas;
    private javax.swing.JLabel lblCodigoCliente;
    private javax.swing.JLabel lblCodigoProduto;
    private javax.swing.JLabel lblCodigoVenda;
    private javax.swing.JLabel lblDataFinal;
    private javax.swing.JLabel lblDataInicial;
    private javax.swing.JLabel lblDescricaoProduto;
    private javax.swing.JLabel lblNomeCliente;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblPesquisarPor;
    private javax.swing.JLabel lblProdutos;
    private javax.swing.JLabel lblProdutosVenda;
    private javax.swing.JLabel lblQtde;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblValor;
    private javax.swing.JLabel lblVendas;
    private javax.swing.JTable tabelaProdutosVendaManipular;
    private javax.swing.JTable tabelaProdutosVendaVisualizar;
    private javax.swing.JTable tabelaVendas;
    private javax.swing.JFormattedTextField txtCodigoCliente;
    private javax.swing.JFormattedTextField txtCodigoProduto;
    private javax.swing.JFormattedTextField txtCodigoVenda;
    private javax.swing.JFormattedTextField txtDataFinal;
    private javax.swing.JFormattedTextField txtDataInicial;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JFormattedTextField txtQuantidade;
    private javax.swing.JFormattedTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
