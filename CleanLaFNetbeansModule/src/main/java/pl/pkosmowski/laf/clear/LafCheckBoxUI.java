package pl.pkosmowski.laf.clear;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalCheckBoxUI;
import javax.swing.plaf.metal.MetalIconFactory;

public class LafCheckBoxUI extends MetalCheckBoxUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafCheckBoxUI();
    }
    boolean oldOpaque;

    @Override
    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        oldOpaque = b.isOpaque();
        b.setOpaque(false);

        icon = LafIconFactory.getCheckBoxIcon();
    }

    @Override
    public void uninstallDefaults(AbstractButton b) {
        super.uninstallDefaults(b);

        b.setOpaque(oldOpaque);
        icon = MetalIconFactory.getCheckBoxIcon();
    }

    @Override
    public synchronized void paint(Graphics g, JComponent c) {
        // Si esta dentro de una tabla o una lista, hay que pintarle el fondo de forma expresa, porque al ser transparente, el CellRendererPane no lo pinta
        if (oldOpaque) {
            Dimension size = c.getSize();
            Object papi = c.getParent();

            // Esto esta aqui para resolver un bug descubierto por Marcelo J. Ruiz que ocurre dentro de Netbeans
            if (papi != null) {
                if (papi.getClass() == CellRendererPane.class) {
                    g.setColor(c.getBackground());
                    g.fillRect(0, 0, size.width, size.height);
                } else if (papi instanceof JTable) {
                    g.setColor(((JTable) papi).getSelectionBackground());
                    g.fillRect(0, 0, size.width, size.height);
                } else if (papi instanceof JList) {
                    g.setColor(((JList) papi).getSelectionBackground());
                    g.fillRect(0, 0, size.width, size.height);
                }
            }
        }

        super.paint(g, c);
    }

    @Override
    protected void paintFocus(Graphics g, Rectangle t, Dimension d) {
        LafUtils.paintFocus(g, 1, 1, d.width - 2, d.height - 2, 8, 8, LafLookAndFeel.getFocusColor());
    }
    /////////////////////////////////////
//  public class MiML extends MouseInputAdapter {
//    private AbstractButton papi;
//    
//    MiML( AbstractButton b) {
//      papi = b;
//    }
//    
//    void refresh() {
//      papi.getParent().repaint( papi.getX()-5, papi.getY()-5, 
//                                papi.getWidth()+10, papi.getHeight()+10);
//    }
//    
//    public void  mouseEntered( MouseEvent e) {
//      papi.getModel().setRollover( true);
//      refresh();
//    }
//
//    public void  mouseExited( MouseEvent e) {
//      papi.getModel().setRollover( false);
//      refresh();
//    }
//
//    public void mousePressed( MouseEvent e) {
//      refresh();
//    }
//    
//    public void mouseClicked( MouseEvent e) {
//      refresh();
//    }
//  }

}
