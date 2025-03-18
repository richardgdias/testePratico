/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.testepratico.UI;

import br.com.testepratico.BO.ClientesBO;
import br.com.testepratico.DTO.ClientesDTO;
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
public class Clientes extends javax.swing.JInternalFrame {

    funcoes f = new funcoes();
    String operacao = "";

    /**
     * Creates new form Clientes
     */
    public Clientes() {
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
        preencherTabelaClientes("", "TODOS");

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
                abrirTelaNovoCliente();
            }
        });

        //F7
        this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                salvarCliente();
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
     * MÉTODO RESPONSÁVEL POR PREENCHER A TABELA DE CLIENTES
     *
     * @param dado
     * @param tipo
     */
    public void preencherTabelaClientes(String dado, String tipo) {
        ClientesBO cBO = new ClientesBO();
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "NOME", "", ""};

        dados = cBO.preencherTabelaClientes(dado, tipo);
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer ladoDireito = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tabelaClientes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        ladoDireito.setHorizontalAlignment(SwingConstants.RIGHT);

        tabelaClientes.setModel(modelo);

        //CODIGO
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaClientes.getColumnModel().getColumn(0).setCellRenderer(centralizado);

        //NOME CLIENTE
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(600);

        //EDITAR
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(25);

        //EXCLUIR
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(25);

        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.setSelectionBackground(new Color(112, 112, 112));
        jspClientes.getViewport().setBackground(Color.WHITE);
        tabelaClientes.setRowHeight(25);

        ImageIcon imagem = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/lixeira.png"));
        ImageIcon imagem2 = new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/seta-Des-devolver.png"));
        JTableRenderer renderer = new JTableRenderer(imagem);
        JTableRenderer renderer2 = new JTableRenderer(imagem2);
        tabelaClientes.getColumnModel().getColumn(2).setCellRenderer(renderer2);
        tabelaClientes.getColumnModel().getColumn(3).setCellRenderer(renderer);
    }

    /**
     * MÉTODO RESPONSÁVEL POR LIMPAR OS CAMPOS
     */
    public void limparCampos() {
        txtCodigoCliente.setText("");
        txtNomeCliente.setText("");
        txtLimiteCompra.setText(f.convMoeda("0"));
        txtDiaFechamentoFatura.setText("0");
    }

    /**
     * MÉTODO RESPONSÁVEL POR SALVAR OS DADOS DO CLIENTE NO BANCO DE DADOS
     */
    public void salvarCliente() {
        ClientesDTO cDTO = new ClientesDTO();
        ClientesBO cBO = new ClientesBO();
        boolean retorno = true;

        if (!"".equals(txtNomeCliente.getText())) {
            if (Integer.parseInt(txtDiaFechamentoFatura.getText()) != 0 && Integer.parseInt(txtDiaFechamentoFatura.getText()) < 32) {
                cDTO.setCodigo(Integer.parseInt(txtCodigoCliente.getText()));
                cDTO.setNome(txtNomeCliente.getText());
                cDTO.setLimiteCompra(Double.parseDouble(f.convDouble(txtLimiteCompra.getText())));
                cDTO.setDiaFechamentoFatura(Integer.parseInt(txtDiaFechamentoFatura.getText()));

                switch (operacao) {
                    case "NOVO":
                        retorno = cBO.inserirCliente(cDTO);
                        break;
                    case "ALTERAR":
                        retorno = cBO.alterarCliente(cDTO);
                        break;
                }

                if (retorno) {
                    switch (operacao) {
                        case "NOVO":
                            f.aviso("CLIENTE REGISTRADO COM SUCESSO");
                            break;
                        case "ALTERAR":
                            f.aviso("CLIENTE ALTERADO COM SUCESSO");
                            break;
                    }

                    CardLayout card = (CardLayout) jPanelCard.getLayout();
                    card.show(jPanelCard, "visualizar");

                    preencherTabelaClientes("", "TODOS");
                } else {
                    f.aviso("ERRO AO TENTAR REGISTRAR CLIENTE");
                }
            } else {
                f.aviso("VALOR INVÁLIDO PARA DIA DE FECHAMENTO DA FATURA");
                txtDiaFechamentoFatura.requestFocus();
            }
        } else {
            f.aviso("POR FAVOR, PREENCHA OS DADOS DO CLIENTE CORRETAMENTE");
            txtNomeCliente.requestFocus();
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR ALTERAR OS DADOS DO CLIENTE NO BANCO DE DADOS
     *
     * @param codigoCliente
     */
    public void alterarCliente(int codigoCliente) {
        ClientesBO cBO = new ClientesBO();
        ClientesDTO cDTO = new ClientesDTO();

        operacao = "ALTERAR";
        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "manipular");

        cDTO = cBO.buscarDadosCliente(String.valueOf(codigoCliente), "CÓDIGO");

        txtCodigoCliente.setText(String.valueOf(cDTO.getCodigo()));
        txtNomeCliente.setText(cDTO.getNome());
        txtLimiteCompra.setText(f.convMoeda(String.valueOf(cDTO.getLimiteCompra())));
        txtDiaFechamentoFatura.setText(String.valueOf(cDTO.getDiaFechamentoFatura()));

        txtNomeCliente.requestFocus();
    }

    /**
     * MÉTODO RESPONSÁVEL POR EXCLUIR O CLIENTE DO BANCO DE DADOS
     *
     * @param codigoCliente
     */
    public void excluirCliente(int codigoCliente) {
        if (("sim").equals(f.telaResposta("DESEJA REALMENTE EXCLUIR ESTE CLIENTE?"))) {
            ClientesBO cBO = new ClientesBO();
            boolean retorno = cBO.excluirCliente(codigoCliente);

            if (retorno) {
                preencherTabelaClientes("", "TODOS");
            } else {
                f.aviso("ERRO AO TENTAR EXCLUIR CLIENTE");
            }
        }
    }

    /**
     * MÉTODO RESPONSÁVEL POR CANCELAR A TELA DE NOVO PRODUTO E RETORNAR PARA
     * TELA INCIAL
     */
    public void cancelar() {
        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "visualizar");
        preencherTabelaClientes("", "TODOS");
    }

    /**
     * MÉTODO RESPONSÁVEL POR ABRIR TELA PARA CADASTRAR NOVO CLIENTE
     */
    public void abrirTelaNovoCliente() {
        ClientesBO cBO = new ClientesBO();

        CardLayout card = (CardLayout) jPanelCard.getLayout();
        card.show(jPanelCard, "manipular");

        limparCampos();
        txtCodigoCliente.setText(cBO.gerarNovoCodigoCliente());
        txtNomeCliente.requestFocus();
        operacao = "NOVO";
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
        lblNomeCliente = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JFormattedTextField();
        lblBordaNomeCliente = new javax.swing.JLabel();
        lblCodigoCliente = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JFormattedTextField();
        lblBordaCodigoCliente = new javax.swing.JLabel();
        lblLimiteCompra = new javax.swing.JLabel();
        txtLimiteCompra = new javax.swing.JFormattedTextField();
        lblBordaLimiteCompra = new javax.swing.JLabel();
        lblDiaFechamentoFatura = new javax.swing.JLabel();
        txtDiaFechamentoFatura = new javax.swing.JFormattedTextField();
        lblBordaDiaFechamentoFatura = new javax.swing.JLabel();
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

        lblNomeCliente.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblNomeCliente.setForeground(new java.awt.Color(154, 154, 154));
        lblNomeCliente.setText("Nome Cliente");
        jPanelManipular.add(lblNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 3, -1, -1));

        txtNomeCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNomeCliente.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtNomeCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomeClienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeClienteFocusLost(evt);
            }
        });
        jPanelManipular.add(txtNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 19, 670, 18));

        lblBordaNomeCliente.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaNomeCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaNomeCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaNomeCliente.setOpaque(true);
        jPanelManipular.add(lblBordaNomeCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 0, 680, 40));

        lblCodigoCliente.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblCodigoCliente.setForeground(new java.awt.Color(154, 154, 154));
        lblCodigoCliente.setText("Código Cliente");
        jPanelManipular.add(lblCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, -1, -1));

        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtCodigoCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoCliente.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtCodigoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoClienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoClienteFocusLost(evt);
            }
        });
        jPanelManipular.add(txtCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 19, 90, 18));

        lblBordaCodigoCliente.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaCodigoCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaCodigoCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaCodigoCliente.setOpaque(true);
        jPanelManipular.add(lblBordaCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 0, 100, 40));

        lblLimiteCompra.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblLimiteCompra.setForeground(new java.awt.Color(154, 154, 154));
        lblLimiteCompra.setText("Limite de Compra");
        jPanelManipular.add(lblLimiteCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, -1, -1));

        txtLimiteCompra.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtLimiteCompra.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtLimiteCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLimiteCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLimiteCompraFocusLost(evt);
            }
        });
        txtLimiteCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLimiteCompraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLimiteCompraKeyTyped(evt);
            }
        });
        jPanelManipular.add(txtLimiteCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 69, 160, 18));

        lblBordaLimiteCompra.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaLimiteCompra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaLimiteCompra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaLimiteCompra.setOpaque(true);
        jPanelManipular.add(lblBordaLimiteCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 50, 170, 40));

        lblDiaFechamentoFatura.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblDiaFechamentoFatura.setForeground(new java.awt.Color(154, 154, 154));
        lblDiaFechamentoFatura.setText("Dia de Fechamento da Fatura");
        jPanelManipular.add(lblDiaFechamentoFatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 53, -1, -1));

        txtDiaFechamentoFatura.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDiaFechamentoFatura.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        txtDiaFechamentoFatura.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiaFechamentoFaturaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiaFechamentoFaturaFocusLost(evt);
            }
        });
        txtDiaFechamentoFatura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiaFechamentoFaturaKeyTyped(evt);
            }
        });
        jPanelManipular.add(txtDiaFechamentoFatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 69, 160, 18));

        lblBordaDiaFechamentoFatura.setBackground(new java.awt.Color(255, 255, 255));
        lblBordaDiaFechamentoFatura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBordaDiaFechamentoFatura.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        lblBordaDiaFechamentoFatura.setOpaque(true);
        jPanelManipular.add(lblBordaDiaFechamentoFatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 50, 170, 40));

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

    private void txtCodigoClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoClienteFocusGained
        f.comFoco(lblBordaCodigoCliente, jPanelManipular);
    }//GEN-LAST:event_txtCodigoClienteFocusGained

    private void txtCodigoClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoClienteFocusLost
        f.semFoco(lblBordaCodigoCliente, jPanelManipular);
    }//GEN-LAST:event_txtCodigoClienteFocusLost

    private void txtLimiteCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLimiteCompraFocusGained
        f.comFoco(lblBordaLimiteCompra, jPanelManipular);
    }//GEN-LAST:event_txtLimiteCompraFocusGained

    private void txtLimiteCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLimiteCompraFocusLost
        f.semFoco(lblBordaLimiteCompra, jPanelManipular);
        txtLimiteCompra.setText(f.convMoeda(f.convDouble(txtLimiteCompra.getText())));
    }//GEN-LAST:event_txtLimiteCompraFocusLost

    private void txtDiaFechamentoFaturaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaFechamentoFaturaFocusGained
        f.comFoco(lblBordaDiaFechamentoFatura, jPanelManipular);
    }//GEN-LAST:event_txtDiaFechamentoFaturaFocusGained

    private void txtDiaFechamentoFaturaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaFechamentoFaturaFocusLost
        f.semFoco(lblBordaDiaFechamentoFatura, jPanelManipular);
        String dia = txtDiaFechamentoFatura.getText();
        
        if (dia.matches("\\d") && dia.length() == 1) { 
            dia = "0" + dia;
        }
        
        txtDiaFechamentoFatura.setText(dia);
    }//GEN-LAST:event_txtDiaFechamentoFaturaFocusLost

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/salvar_off.png")));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/salvar.png")));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        salvarCliente();
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
        abrirTelaNovoCliente();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtDiaFechamentoFaturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiaFechamentoFaturaKeyTyped
        String caracteres = "1234567890";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiaFechamentoFaturaKeyTyped

    private void txtLimiteCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteCompraKeyTyped
        String caracteres = "1234567890,";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtLimiteCompraKeyTyped

    private void tabelaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClientesMouseClicked
        int linha = tabelaClientes.getSelectedRow();
        int codigoCliente = Integer.parseInt(tabelaClientes.getValueAt(linha, 0).toString());

        switch (tabelaClientes.getSelectedColumn()) {
            case 2:
                alterarCliente(codigoCliente);
                break;
            case 3:
                excluirCliente(codigoCliente);
                break;
        }
    }//GEN-LAST:event_tabelaClientesMouseClicked

    private void txtLimiteCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLimiteCompraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDiaFechamentoFatura.requestFocus();
        }
    }//GEN-LAST:event_txtLimiteCompraKeyPressed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        preencherTabelaClientes(txtPesquisar.getText(), cmbPesquisarPor.getSelectedItem().toString());
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void txtPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusGained
        f.comFoco(lblBordaPesquisar, jPanelVisualizar);
    }//GEN-LAST:event_txtPesquisarFocusGained

    private void txtPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusLost
        f.semFoco(lblBordaPesquisar, jPanelVisualizar);
    }//GEN-LAST:event_txtPesquisarFocusLost

    private void txtNomeClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeClienteFocusGained
        f.comFoco(lblBordaNomeCliente, jPanelManipular);
    }//GEN-LAST:event_txtNomeClienteFocusGained

    private void txtNomeClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeClienteFocusLost
        f.semFoco(lblBordaNomeCliente, jPanelManipular);
    }//GEN-LAST:event_txtNomeClienteFocusLost


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
    private javax.swing.JLabel lblBordaCodigoCliente;
    private javax.swing.JLabel lblBordaDiaFechamentoFatura;
    private javax.swing.JLabel lblBordaLimiteCompra;
    private javax.swing.JLabel lblBordaNomeCliente;
    private javax.swing.JLabel lblBordaPesquisar;
    private javax.swing.JLabel lblBordaPesquisarPor;
    private javax.swing.JLabel lblBordaTabelaClientes;
    private javax.swing.JLabel lblCodigoCliente;
    private javax.swing.JLabel lblDiaFechamentoFatura;
    private javax.swing.JLabel lblLimiteCompra;
    private javax.swing.JLabel lblNomeCliente;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblPesquisarPor;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JFormattedTextField txtCodigoCliente;
    private javax.swing.JFormattedTextField txtDiaFechamentoFatura;
    private javax.swing.JFormattedTextField txtLimiteCompra;
    private javax.swing.JFormattedTextField txtNomeCliente;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
