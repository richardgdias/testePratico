/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testepratico.Utils;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Richard
 */
public class JTableRenderer extends DefaultTableCellRenderer {
    public JTableRenderer(Object value){
                if (value instanceof ImageIcon) {
                    if (value != null) {
                        ImageIcon d = (ImageIcon) value;
                        setIcon(d);
                       setHorizontalAlignment(SwingConstants.CENTER);
                    } else {
                        setText("");
                        setIcon(null);
                    }
                } else {
                    super.setValue(value);
                }
                
                
                
    }
}
