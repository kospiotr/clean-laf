package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class LafSeparatorUI extends BasicSeparatorUI {
  public static ComponentUI createUI( JComponent c) {
    return new LafSeparatorUI();
  }

  public void paint( Graphics g, JComponent c) {
    Dimension s = c.getSize();

    if ( ((JSeparator)c).getOrientation() == JSeparator.VERTICAL ) {
      g.setColor( LafUtils.getSombra());
      g.drawLine( 0, 0, 0, s.height);

      g.setColor( LafUtils.getBrillo());
      g.drawLine( 1, 0, 1, s.height);
    }
    else // HORIZONTAL
    {
      g.setColor( LafUtils.getSombra());
      g.drawLine( 0, 0, s.width, 0);

      g.setColor( LafUtils.getBrillo());
      g.drawLine( 0, 1, s.width, 1);
    }
  }

}
