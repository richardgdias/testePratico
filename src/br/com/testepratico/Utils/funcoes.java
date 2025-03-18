/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testepratico.Utils;

import br.com.testepratico.UI.FecharTela;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author Richard
 */
public class funcoes {

    public void centralizarFormulario(Component pai, Component filho) {
        int larguraPai, alturaPai, larguraFilho, alturaFilho;
        int novoX, novoY;

        larguraPai = pai.getWidth();
        alturaPai = pai.getHeight();

        larguraFilho = filho.getWidth();
        alturaFilho = filho.getHeight();

        novoX = (larguraPai - larguraFilho) / 2;
        novoY = (alturaPai - alturaFilho) / 2;

        filho.getParent().setLayout(new GridBagLayout());
        filho.setLocation(novoX, novoY);
        filho.repaint();
    }

    public void centralizarFormularioX(Component pai, Component filho) {
        int larguraPai, alturaPai, larguraFilho, alturaFilho;
        int novoX, novoY;

        larguraPai = pai.getWidth();
        alturaPai = pai.getHeight();

        larguraFilho = filho.getWidth();

        novoX = (larguraPai - larguraFilho) / 2;
        novoY = filho.getY();

        filho.getParent().setLayout(new GridBagLayout());

        filho.setLocation(novoX, novoY);

        filho.repaint();
    }

    public void botaoTransparente(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                ((JButton) component).setContentAreaFilled(false);
                ((JButton) component).setBackground(new Color(0, 0, 0, 0));
                ((JButton) component).setFocusPainted(false);
            }
            if (component instanceof JToggleButton) {
                ((JToggleButton) component).setContentAreaFilled(false);
                ((JToggleButton) component).setBackground(new Color(0, 0, 0, 0));
                ((JToggleButton) component).setFocusPainted(false);
            }
        }
    }

    public void limparCampos(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            }
            if (component instanceof JComboBox) {
                ((JComboBox) component).setSelectedItem("");
            }
            if (component instanceof JFormattedTextField) {
                ((JFormattedTextField) component).setValue(null);
            }
            if (component instanceof JTextArea) {
                ((JTextArea) component).setText("");
            }
        }
    }

    public String pegarDataString() {
        Calendar c = Calendar.getInstance();
        String dia, mes, ano;
        String data = null;

        dia = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        mes = String.valueOf(c.get(Calendar.MONTH));
        mes = String.valueOf(Integer.valueOf(mes) + 1);
        ano = String.valueOf(c.get(Calendar.YEAR));

        if (dia.length() == 1) {
            dia = "0" + dia;
        }

        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        data = dia + "/" + mes + "/" + ano;

        return data;
    }

    //converte string em date
    public String StringEmData(String data) {
        //aaaa-mm-dd        
        String dataMysql;

        if (data == null || data.equals("") || data.equals("  /  /    ")) {
            dataMysql = null;
        } else {
            data = data.replace(" ", "");
            String dia = data.substring(0, 2);
            String mes = data.substring(3, 5);
            String ano = data.substring(6);
            dataMysql = ano + "-" + mes + "-" + dia;
        }

        return dataMysql;
    }

    //convert date em string
    public String DataEmString(String data) {
        //AAAA  MM  DD
        //1234  56  78
        String novaData;
        if (data == null || data.equals("null") || data.equals("")) {
            novaData = null;
        } else {
            data.replace("-", "");
            String ano = data.substring(0, 4);
            String mes = data.substring(5, 7);
            String dia = data.substring(8);
            novaData = dia + "/" + mes + "/" + ano;
        }
        return novaData;
    }

    public String pegarHora() {
        String hora;

        hora = String.valueOf(java.time.LocalTime.now()).substring(0, 5);

        return hora;
    }

    //RETORNA QTDE EM DOUBLE PARA SALVAR NO BANCO
    public double convQtdeDouble(String valor) {
        double valorFormatado = 0;

        try {
            valorFormatado = Double.parseDouble(valor.replace(",", "."));
        } catch (NumberFormatException e) {
            valorFormatado = 0;
            Logger.getLogger(funcoes.class.getName()).log(Level.SEVERE, null, e);
        }

        return valorFormatado;
    }

    //converter moeda para double
    public String convDouble(String vs) {
        String nvs = "0.00";

        try {
            nvs = vs.replace("R$", "").replace(".", "").replace(",", ".").replace(" ", "");
        } catch (Exception ex) {
            Logger.getLogger(funcoes.class.getName()).log(Level.SEVERE, null, ex);
            nvs = "0.00";
        }

        return nvs;
    }

    public String telaResposta(String msg) {
        FecharTela t = new FecharTela(null, true);
        String resposta = "nao";
        t.CabecalhoTela = "AVISO";
        t.MensagemTela = msg;
        t.Tela = "Confirma";
        t.setLocationRelativeTo(null);
        t.setVisible(true);
        if (t.getRetorno() != null) {
            resposta = t.getRetorno();
        }
        return resposta;
    }
    
    public void comFoco(JLabel label, Container container) {
        label.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(109, 182, 214), 2, true));
        container.repaint();
        container.revalidate();
    }

    public void semFoco(JLabel label, Container container) {
        label.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        container.repaint();
        container.revalidate();
    }
    
    public void passaCamposComEnter(JPanel painel) {
        HashSet conj = new HashSet(painel.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        painel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
    }
    
    public void erro(String erro) {
        FecharTela t = new FecharTela(null, true);
        t.CabecalhoTela = "ERRO";
        t.MensagemTela = erro;
        t.Tela = "Erro";
        t.setLocationRelativeTo(null);
        t.setVisible(true);
    }

    public void aviso(String msg) {
        FecharTela t = new FecharTela(null, true);
        t.CabecalhoTela = "AVISO";
        t.MensagemTela = msg;
        t.Tela = "Aviso";
        t.setLocationRelativeTo(null);
        t.setVisible(true);
    }
    
    //converter double para moeda
    public String convMoeda(String vs) {
        String nvs;
        BigDecimal valor;
        String valorFormatado;
        DecimalFormat dfPrecos = new DecimalFormat("#,##0.00");

        try {
            if (vs != null) {
                nvs = vs.replace("R$", "").replace(" ", "").replace(".", ",").replace(",", ".");
                valor = new BigDecimal(nvs);
                valorFormatado = "R$ " + dfPrecos.format(valor);
            } else {
                valorFormatado = "R$ " + dfPrecos.format(0);
            }
        } catch (Exception ex) {
            valorFormatado = "R$ " + dfPrecos.format(0);
            Logger.getLogger(funcoes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return valorFormatado;
    }
    
    public void selectAll(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(final FocusEvent e) {
                        ((JTextField) component).selectAll();
                    }
                });
            } else if (component instanceof JComboBox) {
                ((JComboBox) component).addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(final FocusEvent e) {
                        ((JComboBox) component).getEditor().selectAll();
                    }
                });
            }
        }
    }
    
    public void upperCase(Container container) {
        Component components[] = container.getComponents();

        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setDocument(new UpperCaseDocument());
            } else if (component instanceof JTextArea) {
                ((JTextArea) component).setDocument(new UpperCaseDocument());
            } else if (component instanceof JFormattedTextField) {
                ((JFormattedTextField) component).setDocument(new UpperCaseDocument());
            }
        }
    }
    
    public String pegarPrimeiroDiaMes(int mes) {
        Calendar cal = Calendar.getInstance();
        String data;

        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        data = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());

        return data;
    }

}
