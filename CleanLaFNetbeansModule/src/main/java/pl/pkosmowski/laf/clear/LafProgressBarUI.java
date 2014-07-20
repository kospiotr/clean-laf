package pl.pkosmowski.laf.clear;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class LafProgressBarUI extends BasicProgressBarUI {
	public static ComponentUI createUI( JComponent c) {
    return new LafProgressBarUI();
  }
	
  public void paintDeterminate( Graphics g, JComponent c) {
	  Graphics2D g2D = (Graphics2D)g;
	  
    Insets b = progressBar.getInsets();
    int largo = progressBar.getWidth() - (b.left + b.right);
	  int alto = progressBar.getHeight() - (b.top + b.bottom);
	  int len = getAmountFull(b, largo, alto);
    
    int xi = b.left;
    int yi = b.top;
    int xf = xi + largo;
    int yf = yi + alto;
    int xm = xi + len - 1;
    int ym = yf - len ;
    
    if ( progressBar.getOrientation() == JProgressBar.HORIZONTAL ) {
      g2D.setColor( progressBar.getForeground());
      g2D.fillRect( xi,yi, xm,yf);
      
      GradientPaint grad = new GradientPaint( xi,yi, LafUtils.getBrillo(), 
                                              xi,yf, LafUtils.getSombra());
      g2D.setPaint( grad);
      g2D.fillRect( xi,yi, xm,yf);
      
    	grad = new GradientPaint( xm+1,yi, LafUtils.getSombra(), 
                                xm+1,yf, LafUtils.getBrillo());
  		g2D.setPaint( grad);
      g2D.fillRect( xm+1,yi, xf,yf);
  	}
  	else {
      g2D.setColor( progressBar.getForeground());
      g2D.fillRect( xi,ym, xf,yf);
      
      GradientPaint grad = new GradientPaint( xi,yi, LafUtils.getSombra(), 
                                              xf,yi, LafUtils.getBrillo());
  		g2D.setPaint( grad);
      g2D.fillRect( xi,yi, xf,ym);
      
      grad = new GradientPaint( xi,ym, LafUtils.getBrillo(), 
                                xf,ym, LafUtils.getSombra());
      g2D.setPaint( grad);
      g2D.fillRect( xi,ym, xf,yf);
    }
    
    paintString(g, 0,0,0,0,0, b);
	}
	
	public void paintIndeterminate( Graphics g, JComponent c) {
	  Graphics2D g2D = (Graphics2D)g;
	  
	  Rectangle rec = new Rectangle();
    rec = getBox( rec);
    
    Insets b = progressBar.getInsets();
    int xi = b.left;
    int yi = b.top;
    int xf = c.getWidth() - b.right;
    int yf = c.getHeight() - b.bottom;
    
    g2D.setColor( progressBar.getForeground());
    g2D.fillRect( rec.x, rec.y, rec.width, rec.height);
    
    if ( progressBar.getOrientation() == JProgressBar.HORIZONTAL ) {
      GradientPaint grad = new GradientPaint( rec.x,rec.y, LafUtils.getBrillo(), 
                                              rec.x,rec.height, LafUtils.getSombra());
      g2D.setPaint( grad);
      g2D.fill( rec);
      
      grad = new GradientPaint( xi,yi, LafUtils.getSombra(), 
                                xi,yf, LafUtils.getBrillo());
  		g2D.setPaint( grad);
      g2D.fillRect( xi,yi, rec.x,yf);
      g2D.fillRect( rec.x + rec.width,yi, xf,yf);
    }
    else {
      GradientPaint grad = new GradientPaint( rec.x,rec.y, LafUtils.getBrillo(), 
                                              rec.width,rec.y, LafUtils.getSombra());
      g2D.setPaint( grad);
      g2D.fill( rec);

      
      grad = new GradientPaint( xi,yi, LafUtils.getSombra(), 
                                xf,yi, LafUtils.getBrillo());
  		g2D.setPaint( grad);
      g2D.fillRect( xi,yi, xf,rec.y);
      g2D.fillRect( xi,rec.y+rec.height, xf,yf);
    }
    
    paintString( g2D, 0,0,0,0, 0, b);
  }
  
  protected void paintString( Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
    if ( !progressBar.isStringPainted()) {
      return;
    }
    
    String text = progressBar.getString();
    
    Point point = getStringPlacement( g, text, b.left, b.top, 
                                               progressBar.getWidth() - b.left - b.right, 
                                               progressBar.getHeight() - b.top - b.bottom);
    g.setFont( progressBar.getFont().deriveFont( Font.BOLD));
    
    if ( progressBar.getOrientation() == JProgressBar.HORIZONTAL ) {
      if ( !progressBar.getComponentOrientation().isLeftToRight() ) {
        point.x += progressBar.getFontMetrics( g.getFont()).stringWidth( text);
      }
    }
    
    
    LafUtils.paintShadowTitle( g, text, point.x, point.y, Color.white, Color.black, 1, 
                                        LafUtils.FAT, progressBar.getOrientation());
  }
  
}