package pl.pkosmowski.laf.clear;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.text.JTextComponent;

/**
 * 
 * @author Nilo J. Gonzalez 2007
 */
public class LafTextAreaUI extends BasicTextAreaUI {
  private boolean rollover = false;
  private boolean focus = false;
  private MiTextML miTextML;
  
  public LafTextAreaUI( JComponent c) {
    super();
  }
  
  public static ComponentUI createUI( JComponent c) {
    return new LafTextAreaUI( c);
  }
  
  protected  void installListeners() {
    super.installListeners();

    miTextML = new MiTextML();
    getComponent().addMouseListener( miTextML);
    getComponent().addFocusListener( miTextML);
  }
  
  protected  void uninstallListeners() {
    super.uninstallListeners();

    getComponent().removeMouseListener( miTextML);
    getComponent().removeFocusListener( miTextML);
  }
  
  public boolean isFocus() {
    return focus;
  }

  public boolean isRollover() {
    return rollover;
  }
  protected void paintBackground( Graphics g) {
    JTextComponent c = (JTextComponent)getComponent();

    Border bb = c.getBorder();
    
    if ( bb != null && bb instanceof LafBorders.LafGenBorder ) {
      g.setColor( getComponent().getBackground());
      
      Graphics2D g2d = (Graphics2D)g;
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      g.fillRoundRect( 2,2, c.getWidth()-4, c.getHeight()-4, 7,7);
      
      g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
      
      if ( c.isEnabled() && c.isEditable() ) {
        if ( focus ) {
          LafUtils.paintFocus( g, 1,1, c.getWidth()-2, c.getHeight()-2, 2, 2, LafLookAndFeel.getFocusColor());
        }
        else if ( rollover ) {
          LafUtils.paintFocus( g, 1,1, c.getWidth()-2, c.getHeight()-2, 2, 2, LafUtils.getColorAlfa( LafLookAndFeel.getFocusColor(), 150));
        }
      }
    }
    else {
      super.paintBackground( g);
    }

  }
  
  //////////////////////////
  
  class MiTextML extends MouseAdapter implements FocusListener {
    protected void refreshBorder() {
      if ( getComponent().getParent() != null ) {
        Component papi = getComponent();

        papi.getParent().repaint( papi.getX()-5, papi.getY()-5, 
                                  papi.getWidth()+10, papi.getHeight()+10);
      }
    }
    
    public void mouseExited( MouseEvent e) {
      rollover = false;
      refreshBorder();
    }
    
    public void mouseEntered( MouseEvent e) {
      rollover = true;
      refreshBorder();
    }
    
    public void focusGained( FocusEvent e) {
      focus = true;
      refreshBorder();
    }
      
    public void focusLost( FocusEvent e) {
      focus = false;
      refreshBorder();
    }
  }
}
