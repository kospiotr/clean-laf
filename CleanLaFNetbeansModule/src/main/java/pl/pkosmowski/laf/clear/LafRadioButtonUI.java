package pl.pkosmowski.laf.clear;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class LafRadioButtonUI extends MetalRadioButtonUI {
  //protected MiML miml;
  boolean oldOpaque;
  
  public void installDefaults( AbstractButton b) {
    super.installDefaults( b);

    oldOpaque = b.isOpaque();
    b.setOpaque( false);
    
    icon = LafIconFactory.getRadioButtonIcon();
  }  
  
  public void uninstallDefaults( AbstractButton b) {
    super.uninstallDefaults( b);
    
    b.setOpaque( oldOpaque);
    icon = MetalIconFactory.getRadioButtonIcon();
  }
  
//  public void installListeners( AbstractButton b) {
//    super.installListeners( b);
//    
//    //miml = new MiML( b);
//    //b.addMouseListener( miml);
//  }
  
  public static ComponentUI createUI( JComponent c) {
    return new LafRadioButtonUI();
  }
  
//  public synchronized void paint( Graphics g, JComponent c) {
//    super.paint( g, c);
//    
//    //ButtonModel abs = ((JRadioButton)c).getModel();
//    //if ( !c.hasFocus() && abs.isRollover() ) {
//      // No queda del todo bien...
//      //LafUtils.paintFocus( g, 1, 1, c.getWidth()-2, c.getHeight()-2, 8,8, LafUtils.getColorAlfa( LafLookAndFeel.getFocusColor(), 150));
//    //}
//  }
  
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
//  }  
}
