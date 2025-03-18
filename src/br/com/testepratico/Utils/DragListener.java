/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.testepratico.Utils;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Richard
 */
public final class DragListener extends MouseInputAdapter {

    public static void add(Frame frame) {
        if (!frame.isUndecorated()) {
            //throw new IllegalArgumentException("Frame is not undecorated!");
        }
        DragListener listener = new DragListener();
        frame.addMouseListener(listener);
        frame.addMouseMotionListener(listener);
    }

    private Point location;
    private MouseEvent pressed;

    public void mousePressed(MouseEvent me) {
        pressed = me;
    }

    public void mouseDragged(MouseEvent me) {
        Component component = me.getComponent();
        location = component.getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();
        component.setLocation(x, y);
    }

    private DragListener() {
    }
}
