/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package br.com.testepratico.Utils;

import br.com.testepratico.UI.TelaPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Richard
 */
public class Principal {
    
    public static TelaPrincipal principal = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            abrirTelaPrincipal();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * MÉTODO RESPONSÁVEL POR CHAMAR A TELA PRINCIPAL
     */
    public static void abrirTelaPrincipal() {
        principal = new TelaPrincipal();
        DragListener.add(principal);
        principal.setLocationRelativeTo(null);
        principal.requestFocusInWindow();
        principal.toFront();
        principal.setVisible(true);
    }
    
}
