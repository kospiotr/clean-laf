package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class LafPopupMenuSeparatorUI extends MetalPopupMenuSeparatorUI {
  public static ComponentUI createUI( JComponent c) {
    return new LafPopupMenuSeparatorUI();
  }

  public void paint( Graphics g, JComponent c) {
    Dimension s = c.getSize();

    g.setColor( LafUtils.getSombra());
    g.drawLine( 1, 0, s.width-1, 0);
    g.setColor( LafUtils.getBrillo());
    g.drawLine( 1, 1, s.width-1, 1);
  }

  public Dimension getPreferredSize( JComponent c ) { 
    return new Dimension( 0, 2);
  }
}
