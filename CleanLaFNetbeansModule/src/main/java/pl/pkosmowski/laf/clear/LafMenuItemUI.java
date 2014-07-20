package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.*;

public class LafMenuItemUI extends BasicMenuItemUI {
  public static ComponentUI createUI( JComponent x) {
    return new LafMenuItemUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
    
    menuItem.setBorderPainted( false);
    menuItem.setOpaque( false);
    
    defaultTextIconGap = 3;
  }
  
  protected void uninstallDefaults() {
    super.uninstallDefaults();
    
    menuItem.setOpaque( true);
  }
  
  protected void paintBackground( Graphics g, JMenuItem menuItem, Color bgColor) {
    LafUtils.pintaBarraMenu( g, menuItem, bgColor);
  }
}
