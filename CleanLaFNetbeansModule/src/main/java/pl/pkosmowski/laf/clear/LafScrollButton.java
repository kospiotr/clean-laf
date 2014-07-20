package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.metal.*;

public class LafScrollButton extends MetalScrollButton {
  private static final long serialVersionUID = 1L;
  
  public LafScrollButton( int direction, int width, boolean freeStanding) {
    super( direction, width+1, freeStanding);
  }

  public void paint( Graphics g) {
    Rectangle rec = new Rectangle( 0,0, getWidth(),getHeight());
    
    Graphics2D g2D = (Graphics2D)g;
    GradientPaint grad = null;
    
    if (getDirection() == SwingConstants.EAST || getDirection() == SwingConstants.WEST) {
      if ( getModel().isPressed() || getModel().isSelected() ) {
        grad = new GradientPaint( 0,0, LafUtils.getSombra(), 
                                  0,rec.height, LafUtils.getBrillo());
      }
      else {
        grad = new GradientPaint( 0,0, LafUtils.getBrillo(), 
                                  0,rec.height, LafUtils.getSombra());
      }
    } 
    else {
      if ( getModel().isPressed() || getModel().isSelected() ) {
        grad = new GradientPaint( 0,0, LafUtils.getSombra(), 
                                  rec.width,0, LafUtils.getBrillo());
      }
      else {
        grad = new GradientPaint( 0,0, LafUtils.getBrillo(), 
                                  rec.width,0, LafUtils.getSombra());
      }
    }
    
    g2D.setColor( LafLookAndFeel.getControl());
    g2D.fillRect( rec.x, rec.y, rec.width, rec.height);
    
    g2D.setPaint( grad);
    g2D.fillRect( rec.x, rec.y, rec.width, rec.height);
    
    if ( getModel().isRollover() ) {
      g2D.setColor( LafUtils.getRolloverColor());
      g2D.fillRect( rec.x, rec.y, rec.width, rec.height);
    }

    g2D.setColor( LafLookAndFeel.getControlDarkShadow());
    g2D.drawRect( rec.x, rec.y, rec.width-1, rec.height-1);
    
    Icon icon = null;
    switch ( getDirection() ) {
      case SwingConstants.EAST :  icon = UIManager.getIcon( "ScrollBar.eastButtonIcon"); break;
      case SwingConstants.WEST :  icon = UIManager.getIcon( "ScrollBar.westButtonIcon"); break;
      case SwingConstants.NORTH : icon = UIManager.getIcon( "ScrollBar.northButtonIcon"); break;
      case SwingConstants.SOUTH : icon = UIManager.getIcon( "ScrollBar.southButtonIcon"); break;
    }
    icon.paintIcon( this, g2D, rec.x, rec.y);
  }
}
