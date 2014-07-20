package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;


public class LafMenuBarUI extends BasicMenuBarUI {
  public static ComponentUI createUI( JComponent x) {
	  return new LafMenuBarUI();
  }

  public void paint( Graphics g, JComponent c) {
    super.paint( g, c);
    
    /*
    GradientPaint grad = new GradientPaint( 0,0, LafUtils.getBrillo(), 
                                            0,c.getBounds().height, LafUtils.getSombra());

    Graphics2D g2D = (Graphics2D)g;
    g2D.setPaint( grad);
    g2D.fillRect( 0,0, c.getBounds().width-1, c.getBounds().height-1);
    */
  }
}

