/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testepratico.UI;

import br.com.testepratico.Utils.funcoes;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Richard
 */
public final class FecharTela extends javax.swing.JDialog {

    public String Tela, CabecalhoTela, MensagemTela;
    private static String retorno;

    funcoes f = new funcoes();

    public FecharTela(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setUndecorated(true);
        initComponents();
        desempenho();
        teclas();
        f.botaoTransparente(TelaAviso);
        f.botaoTransparente(TelaConfirma);
    }

    public FecharTela() {
        this.setUndecorated(true);
        initComponents();
        desempenho();
        teclas();
        f.botaoTransparente(TelaAviso);
        f.botaoTransparente(TelaConfirma);
    }

    public void desempenho() {
        this.setLayout(null);
        jPanelPrincipal.setLocation(20, 50);
        lblTestePratico.setVisible(true);
        lblFundo.setVisible(true);
        this.getRootPane().setOpaque(true);
        this.getContentPane().setBackground(new Color(0, 0, 0, 0));
        this.setBackground(new Color(0, 0, 0, 0));
        this.setSize(440, 270);
    }

    public StyledDocument style(String texto) {
        StyledDocument document = null;
        try {
            StyleContext context = new StyleContext();
            document = new DefaultStyledDocument(context);
            Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
            document.insertString(document.getLength(), texto, style);

        } catch (BadLocationException ex) {
            Logger.getLogger(FecharTela.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;
    }

    public void teclas() {
        InputMap inputMapF1 = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        InputMap inputMapEnter = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        InputMap inputMapF2 = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMapF1.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "sim");
        inputMapF2.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "nao");
        inputMapEnter.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapF1);
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapEnter);
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMapF2);

        this.getRootPane().getActionMap().put("nao", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                retorno = "nao";
                dispose();
            }
        });

        this.getRootPane().getActionMap().put("sim", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                retorno = "sim";
                dispose();
            }
        });

        this.getRootPane().getActionMap().put("enter", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPrincipal = new javax.swing.JPanel();
        TelaAviso = new javax.swing.JPanel();
        lblLinha1 = new javax.swing.JLabel();
        lblLinha2 = new javax.swing.JLabel();
        lblCabecalhoAviso = new javax.swing.JLabel();
        lblIconeAviso = new javax.swing.JLabel();
        jspAviso = new javax.swing.JScrollPane();
        jtpAviso = new javax.swing.JTextPane();
        btnOk = new javax.swing.JButton();
        lblFundoAviso = new javax.swing.JLabel();
        TelaConfirma = new javax.swing.JPanel();
        lblLinha3 = new javax.swing.JLabel();
        lblLinha4 = new javax.swing.JLabel();
        lblCabecalhoConfirma = new javax.swing.JLabel();
        lblIconeConfirma = new javax.swing.JLabel();
        lblInterrogacao = new javax.swing.JLabel();
        jspConfirma = new javax.swing.JScrollPane();
        jtpConfirma = new javax.swing.JTextPane();
        btnSim = new javax.swing.JButton();
        btnNao = new javax.swing.JButton();
        lblTestePratico = new javax.swing.JLabel();
        lblFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelPrincipal.setLayout(new java.awt.CardLayout());

        TelaAviso.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLinha1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinha1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/linha.png"))); // NOI18N
        TelaAviso.add(lblLinha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 380, 5));

        lblLinha2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinha2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/linha.png"))); // NOI18N
        TelaAviso.add(lblLinha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 380, 5));

        lblCabecalhoAviso.setFont(new java.awt.Font("Open Sans Light", 1, 18)); // NOI18N
        lblCabecalhoAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TelaAviso.add(lblCabecalhoAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 13, 370, 30));

        lblIconeAviso.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblIconeAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconeAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/erro.png"))); // NOI18N
        TelaAviso.add(lblIconeAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 82, -1, -1));

        jspAviso.setBorder(null);
        jspAviso.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jtpAviso.setEditable(false);
        jtpAviso.setBackground(new java.awt.Color(240, 240, 240));
        jtpAviso.setBorder(null);
        jtpAviso.setFont(new java.awt.Font("Open Sans", 0, 19)); // NOI18N
        jspAviso.setViewportView(jtpAviso);

        TelaAviso.add(jspAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 290, 100));

        btnOk.setFont(new java.awt.Font("Open Sans", 1, 11)); // NOI18N
        btnOk.setForeground(new java.awt.Color(255, 255, 255));
        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/ok.png"))); // NOI18N
        btnOk.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOkMouseExited(evt);
            }
        });
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        TelaAviso.add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 150, 120, 40));
        TelaAviso.add(lblFundoAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 200));

        jPanelPrincipal.add(TelaAviso, "TelaAviso");

        TelaConfirma.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLinha3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinha3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/linha.png"))); // NOI18N
        TelaConfirma.add(lblLinha3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 380, 5));

        lblLinha4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLinha4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/linha.png"))); // NOI18N
        TelaConfirma.add(lblLinha4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 380, 5));

        lblCabecalhoConfirma.setFont(new java.awt.Font("Open Sans Light", 1, 18)); // NOI18N
        lblCabecalhoConfirma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TelaConfirma.add(lblCabecalhoConfirma, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 13, 360, 25));

        lblIconeConfirma.setFont(new java.awt.Font("Open Sans", 0, 22)); // NOI18N
        lblIconeConfirma.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconeConfirma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/Interrogação.png"))); // NOI18N
        TelaConfirma.add(lblIconeConfirma, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 82, -1, -1));

        lblInterrogacao.setFont(new java.awt.Font("Open Sans", 0, 20)); // NOI18N
        TelaConfirma.add(lblInterrogacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 66, 40, 70));

        jspConfirma.setBorder(null);
        jspConfirma.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jtpConfirma.setEditable(false);
        jtpConfirma.setBackground(new java.awt.Color(240, 240, 240));
        jtpConfirma.setBorder(null);
        jtpConfirma.setFont(new java.awt.Font("Open Sans", 0, 16)); // NOI18N
        jspConfirma.setViewportView(jtpConfirma);

        TelaConfirma.add(jspConfirma, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 290, 100));

        btnSim.setFont(new java.awt.Font("Open Sans", 1, 11)); // NOI18N
        btnSim.setForeground(new java.awt.Color(255, 255, 255));
        btnSim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/sim.png"))); // NOI18N
        btnSim.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnSimFocusLost(evt);
            }
        });
        btnSim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSimMouseExited(evt);
            }
        });
        btnSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimActionPerformed(evt);
            }
        });
        TelaConfirma.add(btnSim, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 150, 120, 40));

        btnNao.setFont(new java.awt.Font("Open Sans", 1, 11)); // NOI18N
        btnNao.setForeground(new java.awt.Color(255, 255, 255));
        btnNao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/nao.png"))); // NOI18N
        btnNao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnNaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnNaoFocusLost(evt);
            }
        });
        btnNao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNaoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNaoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNaoMouseExited(evt);
            }
        });
        btnNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNaoActionPerformed(evt);
            }
        });
        TelaConfirma.add(btnNao, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 150, 120, 40));

        jPanelPrincipal.add(TelaConfirma, "TelaConfirma");

        getContentPane().add(jPanelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 399, 198));

        lblTestePratico.setFont(new java.awt.Font("Open Sans Semibold", 0, 11)); // NOI18N
        lblTestePratico.setForeground(new java.awt.Color(255, 255, 255));
        lblTestePratico.setText("TESTE PRÁTICO - VR SOFTWARE");
        lblTestePratico.setToolTipText("");
        getContentPane().add(lblTestePratico, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 20, 380, 32));

        lblFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/Resources/bordaFecharTela440X270.png"))); // NOI18N
        getContentPane().add(lblFundo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 270));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        MensagemTela = MensagemTela.toUpperCase();
        switch (Tela) {
            case "Confirma": {
                lblCabecalhoConfirma.setText(CabecalhoTela);
                jtpConfirma.setDocument(style(MensagemTela));
                lblIconeConfirma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/Interrogação.png")));
                CardLayout card = (CardLayout) jPanelPrincipal.getLayout();
                card.show(jPanelPrincipal, "TelaConfirma");
                break;
            }
            case "Aviso": {
                lblCabecalhoAviso.setText(CabecalhoTela);
                jtpAviso.setDocument(style(MensagemTela));
                lblIconeAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/aviso.png")));
                CardLayout card = (CardLayout) jPanelPrincipal.getLayout();
                card.show(jPanelPrincipal, "TelaAviso");
                btnOk.requestFocusInWindow();
                btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/ok.png")));
                break;
            }
            case "Erro": {
                lblCabecalhoAviso.setText(CabecalhoTela);
                jtpAviso.setDocument(style(MensagemTela));
                lblIconeAviso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/erro.png")));
                CardLayout card = (CardLayout) jPanelPrincipal.getLayout();
                card.show(jPanelPrincipal, "TelaAviso");
                btnOk.requestFocusInWindow();
                btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/ok.png")));
                break;
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowActivated

    private void btnSimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMouseEntered
        btnSim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/sim_off.png")));
    }//GEN-LAST:event_btnSimMouseEntered

    private void btnSimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMouseExited
        btnSim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/sim.png")));
    }//GEN-LAST:event_btnSimMouseExited

    private void btnSimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMouseClicked
        retorno = "sim";
        dispose();
    }//GEN-LAST:event_btnSimMouseClicked

    private void btnNaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMouseClicked
        retorno = "nao";
        dispose();
    }//GEN-LAST:event_btnNaoMouseClicked

    private void btnNaoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMouseEntered
        btnNao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/nao_off.png")));
    }//GEN-LAST:event_btnNaoMouseEntered

    private void btnNaoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMouseExited
        btnNao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/nao.png")));
    }//GEN-LAST:event_btnNaoMouseExited

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnSimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSimFocusGained
        btnSim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/sim_off.png")));

        btnSim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (evt.getSource() == btnSim) {
                    if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                        btnNao.requestFocus();
                    }
                }
            }
        });

        btnSim.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (evt.getSource() == btnSim) {
                    if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                        retorno = "sim";
                        dispose();
                    }
                }
            }
        });
    }//GEN-LAST:event_btnSimFocusGained

    private void btnSimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSimFocusLost
        btnSim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/sim.png")));
    }//GEN-LAST:event_btnSimFocusLost

    private void btnNaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnNaoFocusGained
        btnNao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/nao_off.png")));

        btnNao.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (evt.getSource() == btnNao) {
                    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                        btnSim.requestFocus();
                    }
                }
            }
        });

        btnNao.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (evt.getSource() == btnNao) {
                    if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                        retorno = "nao";
                        dispose();
                    }
                }
            }
        });
    }//GEN-LAST:event_btnNaoFocusGained

    private void btnNaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnNaoFocusLost
        btnNao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/nao.png")));
    }//GEN-LAST:event_btnNaoFocusLost

    private void btnNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNaoActionPerformed
        retorno = "nao";
        dispose();
    }//GEN-LAST:event_btnNaoActionPerformed

    private void btnSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimActionPerformed
        retorno = "sim";
        dispose();
    }//GEN-LAST:event_btnSimActionPerformed

    private void btnOkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOkMouseEntered
        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/ok_off.png")));
    }//GEN-LAST:event_btnOkMouseEntered

    private void btnOkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOkMouseExited
        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/testepratico/resources/ok.png")));
    }//GEN-LAST:event_btnOkMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel TelaAviso;
    private javax.swing.JPanel TelaConfirma;
    private javax.swing.JButton btnNao;
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnSim;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JScrollPane jspAviso;
    private javax.swing.JScrollPane jspConfirma;
    private javax.swing.JTextPane jtpAviso;
    private javax.swing.JTextPane jtpConfirma;
    private javax.swing.JLabel lblCabecalhoAviso;
    private javax.swing.JLabel lblCabecalhoConfirma;
    private javax.swing.JLabel lblFundo;
    private javax.swing.JLabel lblFundoAviso;
    private javax.swing.JLabel lblIconeAviso;
    private javax.swing.JLabel lblIconeConfirma;
    private javax.swing.JLabel lblInterrogacao;
    private javax.swing.JLabel lblLinha1;
    private javax.swing.JLabel lblLinha2;
    private javax.swing.JLabel lblLinha3;
    private javax.swing.JLabel lblLinha4;
    private javax.swing.JLabel lblTestePratico;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the retorno
     */
    public String getRetorno() {
        return retorno;
    }

    /**
     * @param retorno the retorno to set
     */
    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }
}
