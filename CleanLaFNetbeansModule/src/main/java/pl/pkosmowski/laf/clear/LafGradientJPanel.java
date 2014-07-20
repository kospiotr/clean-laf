package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;

/**
 * Solo para hacer pruebas...
 * 
 * @author Nilo J. Gonzalez 2007
 */
class LafGradientJPanel extends JPanel implements SwingConstants {
  private static final long serialVersionUID = 3064942006323344159L;

  protected int direction;
  protected Color colIni, colFin;
  
  public LafGradientJPanel() {
    super();
    
    init();
  }

  public LafGradientJPanel( boolean isDoubleBuffered) {
    super( isDoubleBuffered);
    
    init();
  }

  public LafGradientJPanel( LayoutManager layout, boolean isDoubleBuffered) {
    super( layout, isDoubleBuffered);
    
    init();
  }

  public LafGradientJPanel( LayoutManager layout) {
    super( layout);
    
    init();
  }

  protected void init() {
    direction = VERTICAL;
    colIni = LafLookAndFeel.getControl();
    colFin = colIni.darker();
  }
  
  public void setGradientDirection( int dir) {
    direction = dir;
  }
  
  public void setGradientColors( Color ini, Color fin) {
    colIni = ini;
    colFin = fin;
  }
  
  protected void paintComponent( Graphics g) {
    Graphics2D g2D = (Graphics2D)g;
    GradientPaint grad = null;
    
    if ( direction == HORIZONTAL ) {
      grad = new GradientPaint( 0,0, colIni, 
                                getWidth(),0, colFin);
    }
    else {
      grad = new GradientPaint( 0,0, colIni, 
                                0,getHeight(), colFin);
    }
    
    g2D.setPaint( grad);
    g2D.fillRect( 0,0, getWidth(), getHeight());
  }
}
