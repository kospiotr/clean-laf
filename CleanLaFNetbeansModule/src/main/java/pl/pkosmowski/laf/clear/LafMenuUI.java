package pl.pkosmowski.laf.clear;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

public class LafMenuUI extends BasicMenuUI {
  public static ComponentUI createUI( JComponent x) {
    return new LafMenuUI();
  }

  public void update( Graphics g, JComponent c) {
    JMenu menu = (JMenu)c;
    if ( menu.isTopLevelMenu() ) {
      menu.setOpaque( false);
      
      ButtonModel model = menu.getModel();
      if ( model.isArmed() || model.isSelected() ) {
        g.setColor( LafLookAndFeel.getFocusColor());
        g.fillRoundRect( 1,1, c.getWidth()-2, c.getHeight()-3, 2,2);
      }
    }
    else {
      menuItem.setBorderPainted( false);
      menuItem.setOpaque( false);
    }
    
    super.update( g, c);
  }
 
  protected void paintBackground( Graphics g, JMenuItem menuItem, Color bgColor) {
    LafUtils.pintaBarraMenu( g, menuItem, bgColor);
  }
}
