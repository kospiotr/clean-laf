package pl.pkosmowski.laf.clear;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class LafCheckBoxUI extends MetalCheckBoxUI {
//  protected MiML miml;
  boolean oldOpaque;
  
  public static ComponentUI createUI( JComponent c) {
    return new LafCheckBoxUI();
  }

//  public void installListeners( AbstractButton b) {
//    super.installListeners( b);
//    
//    //b.addMouseListener( new MiML( b));
//  }
//  
//  protected void uninstallListeners( AbstractButton b) {
//    super.uninstallListeners( b);
//    
//    //b.removeMouseListener( miml);
//  }
  
  public void installDefaults( AbstractButton b) {
    super.installDefaults( b);

    oldOpaque = b.isOpaque();
    b.setOpaque( false);
    
    icon = LafIconFactory.getCheckBoxIcon();
  } 
  
  public void uninstallDefaults( AbstractButton b) {
    super.uninstallDefaults( b);
    
    b.setOpaque( oldOpaque);
    icon = MetalIconFactory.getCheckBoxIcon();
  }
  
  public synchronized void paint( Graphics g, JComponent c) {
    // Si esta dentro de una tabla o una lista, hay que pintarle el fondo de forma expresa, porque al ser transparente, el CellRendererPane no lo pinta 
    if ( oldOpaque ) {
      Dimension size = c.getSize();
      Object papi = c.getParent();
      
      // Esto esta aqui para resolver un bug descubierto por Marcelo J. Ruiz que ocurre dentro de Netbeans
      if ( papi != null ) {
        if ( papi.getClass() == CellRendererPane.class ) {
          g.setColor( c.getBackground());
          g.fillRect( 0,0, size.width, size.height);
        }
        else if ( papi instanceof JTable ) {
          g.setColor( ((JTable)papi).getSelectionBackground());
          g.fillRect( 0,0, size.width, size.height);
        }
        else if ( papi instanceof JList ) {
          g.setColor( ((JList)papi).getSelectionBackground());
          g.fillRect( 0,0, size.width, size.height);
        }
      }
    }
        
    super.paint( g, c);
  }
  
  
  protected void paintFocus( Graphics g, Rectangle t, Dimension d){
    LafUtils.paintFocus( g, 1, 1, d.width-2, d.height-2, 8,8, LafLookAndFeel.getFocusColor());
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

