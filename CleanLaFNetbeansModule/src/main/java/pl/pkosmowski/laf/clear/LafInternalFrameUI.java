package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class LafInternalFrameUI extends MetalInternalFrameUI {
  LafInternalFrameTitlePane titlePane;
  
  
  public LafInternalFrameUI( JInternalFrame arg0) {
    super( arg0);
  }

  public static ComponentUI createUI( JComponent c) {
    return new LafInternalFrameUI( (JInternalFrame)c);
  }

  protected JComponent createNorthPane( JInternalFrame w) {
    super.createNorthPane( w);
    
    titlePane = new LafInternalFrameTitlePane( w);
    return titlePane;
  }

  public void update( Graphics g, JComponent c) {
    paint( g, c);
  }
  
  
}
