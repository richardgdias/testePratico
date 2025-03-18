/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.testepratico.UI;

import br.com.testepratico.BO.ProdutosBO;
import br.com.testepratico.DTO.ProdutosDTO;
import br.com.testepratico.Utils.JTableRenderer;
import br.com.testepratico.Utils.ModeloTabela;
import br.com.testepratico.Utils.funcoes;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
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
public class Produtos extends javax.swing.JInternalFrame {

    funcoes f = new funcoes();
    String operacao = "";

    /**
     * Creates new form Produtos
     */
    public Produtos() {
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

        jspClientes.setBorder(null);
        tabelaClientes.setShowHorizontalLines(false);
        tabelaClientes.setShowGrid(false);
        tabelaClientes.setShowVerticalLines(false);

        definirNewCmb();
        definirTeclas();
        preencherTabelaProdutos("", "TODOS");

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
                f.comFoco(lblBordaPesquisarPor, jPanelManipular);
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.semFoco(lblBordaPesquisarPor, jPanelManipular);
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
                abrirTelaNovoProduto();
            }
        });

        //F7
        this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                salvarProduto();
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
     * MÉTODO RESPONSÁVEL POR PREENCHER A TABELA DE PRODUTOS
     *
     * @param dado
     * @param tipo
     */
    public void preencherTabelaProdutos(String dado, String tipo) {
        ProdutosBO pBO = new ProdutosBO();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "DESCRIÇÃO", "VALOR", "", ""};

        dados = pBO.preencherTabelaProdutos(dado, tipo);
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer ladoDireito = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tabelaClientes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        ladoDireito.setHorizontalAlignment(SwingConstants.RIGHT);

        tabelaClientes.setModel(modelo);

        //CODIGO
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabelaClientes.getColumnModel().getColumn(0).setCellRenderer(centralizado);

        //DESCRIÇÃO
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(600);

        //VALOR
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaClientes.getColumnModel().getColumn(2).setCellRenderer(centralizado);

        //EDITAR
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(25);

        //EXCLUIR
        tabelaClientes.getColumnModel().getColumn(4).setPreferredWidth(25);

        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setSelectionBackground(new Color(112, 112, 112));
        jspClientes.getViewport().setBackground(Color.WHITE);
        tabelaClientes.setRowHeight(25);

        ImageIcon imagem = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/lixeira.png"));
        ImageIcon imagem2 = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/seta-Des-devolver.png"));
        JTableRenderer renderer = new JTableRenderer(imagem);
        JTableRenderer renderer2 = new JTableRenderer(imagem2);
        tabelaClientes.getColumnModel().getColumn(3).setCellRenderer(renderer2);
        tabelaClientes.getColumnModel().getColumn(4).setCellRenderer(renderer);
    }

    /**
     * MÉTODO RESPONSÁVEL POR LIMPAR OS CAMPOS
     */
    public void limparCampos() {
        txtCodigoProduto.setText("");
        txtDescricaoProduto.setText("");
        txtValorProduto.setText(f.convMoeda("0"));
    }

    /**
     * MÉTODO RESPONSÁVEL POR SALVAR OS DADOS DO PRODUTO NO BANCO DE DADOS
     */
    public void salvarProduto() {
        ProdutosBO pBO = new ProdutosBO();
        ProdutosDTO pDTO = new ProdutosDTO();
        boolean retorno = true;

        if (!"".equals(txtDescricaoProduto.getText())) {
            pDTO.setCodigo(Integer.parseInt(txtCodigoProduto.getText()));
            pDTO.setDescricaoProduto(txtDescricaoProduto.getText());
            pDTO.setValorProduto(Double.parseDouble(f.convDouble(txtValorProduto.getText())));

            switch (operacao) {
                case "NOVO":
                    retorno = pBO.inserirProduto(pDTO);
                    break;
                case "ALTERAR":
                    retorno = pBO.alterarProduto(pDTO);
                    break;
            }

            if (retorno) {
                switch (operacao) {
                    case "NOVO":
                        f.aviso("PRODUTO REGISTRADO COM SUCESSO");
                        break;
                    case "ALTERAR":
                        f.aviso("PRODUTO ALTERADO COM SUCESSO");
                        break;
                }

                CardLayout card = (CardLayout) jPanelCard.getLayout();
                card.show(jPanelCard, "visualizar");

                preencherTabelaProdutos("", "TODOS");
            } else {
                f.aviso("ERRO AO TENTAR REGISTRAR PRODUTO");
            }
        } else {
            f.aviso("POR FAVOR, PREENCHA OS DADOS DO PRODUTO CORRETAMENTE");
            txtDescricaoProduto.requestFocus();
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR OS DADOS DO PRODUTO NO BANCO DE DADOS
     *
     * @param codigoProduto
     */
    public void alterarProduto(int codigoProduto) {
        ProdutosBO pBO = new ProdutosBO();
        ProdutosDTO pDTO = new ProdutosDTO();

        operacao = "ALTERAR";
        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "manipular");

        pDTO = pBO.buscarDadosProduto(String.valueOf(codigoProduto), "CÓDIGO");

        txtCodigoProduto.setText(String.valueOf(pDTO.getCodigo()));
        txtDescricaoProduto.setText(pDTO.getDescricaoProduto());
        txtValorProduto.setText(f.convMoeda(String.valueOf(pDTO.getValorProduto())));

        txtDescricaoProduto.requestFocus();
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR O PRODUTO DO BANCO DE DADOS
     *
     * @param codigoProduto
     */
    public void excluirProduto(int codigoProduto) {
        if (("sim").equals(f.telaResposta("DESEJA REALMENTE EXCLUIR ESTE PRODUTO?"))) {
            ProdutosBO pBO = new ProdutosBO();
            boolean retorno = pBO.excluirProduto(codigoProduto);

            if (retorno) {
                preencherTabelaProdutos("", "TODOS");
            } else {
                f.aviso("ERRO AO TENTAR EXCLUIR PRODUTO");
            }
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR ABRIR TELA PARA CADASTRAR NOVO PRODUTO
     */
    public void abrirTelaNovoProduto() {
        ProdutosBO pBO = new ProdutosBO();

        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "manipular");

        limparCampos();
        txtCodigoProduto.setText(pBO.gerarNovoCodigoProduto());
        txtDescricaoProduto.requestFocus();
        operacao = "NOVO";
    }

    /**
     * MÉTODO RESPONSÁVEL POR CANCELAR A TELA DE NOVO PRODUTO E RETORNAR PARA
     * TELA INCIAL
     */
    public void cancelar() {
        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "visualizar");
        preencherTabelaProdutos("", "TODOS");
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
        lblPesquisar = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        lblBordaPesquisar = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        jspClientes = new javax.swing.JScrollPane();
        tabelaClientes = new javax.swing.JTable();
        lblBordaTabelaClientes = new javax.swing.JLabel();
        cmbPesquisarPor = new javax.swing.JComboBox<>();
        lblPesquisarPor = new javax.swing.JLabel();
        lblBordaPesquisarPor = new javax.swing.JLabel();
        jPanelManipular = new javax.swing.JPanel();
        lblDescricaoProduto = new javax.swing.JLabel();
        txtDescricaoProduto = new javax.swing.JFormattedTextField();
        lblBordaDescricaoProduto = new javax.swing.JLabel();
        lblCodigoProduto = new javax.swing.JLabel();
        txtCodigoProduto = new javax.swing.JFormattedTextField();
        lblBordaCodigoProduto = new javax.swing.JLabel();
        lblValorProduto = new javax.swing.JLabel();
        txtValorProduto = new javax.swing.JFormattedTextField();
        lblBordaValorProduto = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

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

        lblPesquisar.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblPesquisar.setForeground(new java.awt.Color(154, 154, 154));
        lblPesquisar.setText("Pesquisar");
        jPanelVisualizar.add(lblPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 3, -1, -1));

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
        jPanelVisualizar.add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 19, 400, 18));

        lblBordaPesquisar.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaPesquisar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaPesquisar.setOpaque(true);
        jPanelVisualizar.add(lblBordaPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 0, 410, 40));

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

        jspClientes.setBorder(null);

        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaClientesMouseClicked(evt);
            }
        });
        jspClientes.setViewportView(tabelaClientes);

        jPanelVisualizar.add(jspClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, 780, 450));

        lblBordaTabelaClientes.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaTabelaClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaTabelaClientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaTabelaClientes.setOpaque(true);
        jPanelVisualizar.add(lblBordaTabelaClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, 790, 460));

        cmbPesquisarPor.setEditable(true);
        cmbPesquisarPor.setFont(new java.awt.Font("Open Sans", 0, 12)); // NOI18N
        cmbPesquisarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOME", "CÓDIGO" }));
        jPanelVisualizar.add(cmbPesquisarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 90, 18));

        lblPesquisarPor.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblPesquisarPor.setForeground(new java.awt.Color(154, 154, 154));
        lblPesquisarPor.setText("Pesquisar Por");
        jPanelVisualizar.add(lblPesquisarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, -1, -1));

        lblBordaPesquisarPor.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaPesquisarPor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaPesquisarPor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaPesquisarPor.setOpaque(true);
        jPanelVisualizar.add(lblBordaPesquisarPor, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 100, 40));

        jPanelCard.add(jPanelVisualizar, "visualizar");

        jPanelManipular.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDescricaoProduto.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblDescricaoProduto.setForeground(new java.awt.Color(154, 154, 154));
        lblDescricaoProduto.setText("Descrição Produto");
        jPanelManipular.add(lblDescricaoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 3, -1, -1));

        txtDescricaoProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDescricaoProduto.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtDescricaoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescricaoProdutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescricaoProdutoFocusLost(evt);
            }
        });
        jPanelManipular.add(txtDescricaoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 19, 560, 18));

        lblBordaDescricaoProduto.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaDescricaoProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaDescricaoProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaDescricaoProduto.setOpaque(true);
        jPanelManipular.add(lblBordaDescricaoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 0, 570, 40));

        lblCodigoProduto.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblCodigoProduto.setForeground(new java.awt.Color(154, 154, 154));
        lblCodigoProduto.setText("Código Produto");
        jPanelManipular.add(lblCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, -1, -1));

        txtCodigoProduto.setEditable(false);
        txtCodigoProduto.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCodigoProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoProduto.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtCodigoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoProdutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoProdutoFocusLost(evt);
            }
        });
        jPanelManipular.add(txtCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 90, 18));

        lblBordaCodigoProduto.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaCodigoProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaCodigoProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaCodigoProduto.setOpaque(true);
        jPanelManipular.add(lblBordaCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 100, 40));

        lblValorProduto.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblValorProduto.setForeground(new java.awt.Color(154, 154, 154));
        lblValorProduto.setText("Valor Produto");
        jPanelManipular.add(lblValorProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 3, -1, -1));

        txtValorProduto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtValorProduto.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtValorProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorProdutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorProdutoFocusLost(evt);
            }
        });
        txtValorProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtValorProdutoKeyTyped(evt);
            }
        });
        jPanelManipular.add(txtValorProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 19, 90, 18));

        lblBordaValorProduto.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaValorProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaValorProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaValorProduto.setOpaque(true);
        jPanelManipular.add(lblBordaValorProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 0, 100, 40));

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
        jPanelManipular.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 480, 120, 40));

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
        jPanelManipular.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 480, 120, 40));

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

    private void txtCodigoProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProdutoFocusGained
        f.comFoco(lblBordaCodigoProduto, jPanelManipular);
    }//GEN-LAST:event_txtCodigoProdutoFocusGained

    private void txtCodigoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoProdutoFocusLost
        f.semFoco(lblBordaCodigoProduto, jPanelManipular);
    }//GEN-LAST:event_txtCodigoProdutoFocusLost

    private void txtValorProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorProdutoFocusGained
        f.comFoco(lblBordaValorProduto, jPanelManipular);
    }//GEN-LAST:event_txtValorProdutoFocusGained

    private void txtValorProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorProdutoFocusLost
        f.semFoco(lblBordaValorProduto, jPanelManipular);
        txtValorProduto.setText(f.convMoeda(f.convDouble(txtValorProduto.getText())));
    }//GEN-LAST:event_txtValorProdutoFocusLost

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/salvar_off.png")));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/salvar.png")));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarProduto();
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
        abrirTelaNovoProduto();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtValorProdutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorProdutoKeyTyped
        String caracteres = "1234567890,";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtValorProdutoKeyTyped

    private void tabelaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClientesMouseClicked
        int linha = tabelaClientes.getSelectedRow();
        int codigoProduto = Integer.parseInt(tabelaClientes.getValueAt(linha, 0).toString());

        switch (tabelaClientes.getSelectedColumn()) {
            case 3:
                alterarProduto(codigoProduto);
                break;
            case 4:
                excluirProduto(codigoProduto);
                break;
        }
    }//GEN-LAST:event_tabelaClientesMouseClicked

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        preencherTabelaProdutos(txtPesquisar.getText(), cmbPesquisarPor.getSelectedItem().toString());
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void txtPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusGained
        f.comFoco(lblBordaPesquisar, jPanelVisualizar);
    }//GEN-LAST:event_txtPesquisarFocusGained

    private void txtPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusLost
        f.semFoco(lblBordaPesquisar, jPanelVisualizar);
    }//GEN-LAST:event_txtPesquisarFocusLost

    private void txtDescricaoProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescricaoProdutoFocusGained
        f.comFoco(lblBordaDescricaoProduto, jPanelManipular);
    }//GEN-LAST:event_txtDescricaoProdutoFocusGained

    private void txtDescricaoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescricaoProdutoFocusLost
        f.semFoco(lblBordaDescricaoProduto, jPanelManipular);
    }//GEN-LAST:event_txtDescricaoProdutoFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbPesquisarPor;
    private javax.swing.JPanel jPanelCard;
    private javax.swing.JPanel jPanelManipular;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JPanel jPanelVisualizar;
    private javax.swing.JScrollPane jspClientes;
    private javax.swing.JLabel lblBordaCodigoProduto;
    private javax.swing.JLabel lblBordaDescricaoProduto;
    private javax.swing.JLabel lblBordaPesquisar;
    private javax.swing.JLabel lblBordaPesquisarPor;
    private javax.swing.JLabel lblBordaTabelaClientes;
    private javax.swing.JLabel lblBordaValorProduto;
    private javax.swing.JLabel lblCodigoProduto;
    private javax.swing.JLabel lblDescricaoProduto;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblPesquisarPor;
    private javax.swing.JLabel lblValorProduto;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JFormattedTextField txtCodigoProduto;
    private javax.swing.JFormattedTextField txtDescricaoProduto;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JFormattedTextField txtValorProduto;
    // End of variables declaration//GEN-END:variables
}
