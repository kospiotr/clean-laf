package pl.pkosmowski.laf.clear;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;

/**
 * 
 * @author Nilo J. Gonzalez 2007
 * @author Mark Senne
 */
public class LafScrollPaneUI extends BasicScrollPaneUI {
  
  protected boolean oldOpaque;
  
  public static ComponentUI createUI( JComponent x) {
    return new LafScrollPaneUI();
  }
  
  public void installUI( JComponent c) {
    super.installUI(c);

    // Esto es gracias a Mark Senne
    JScrollPane sp = (JScrollPane)c;
    if ( sp.getHorizontalScrollBar() != null ) {
      sp.getHorizontalScrollBar().putClientProperty( MetalScrollBarUI.FREE_STANDING_PROP, Boolean.FALSE);
    }
    if ( sp.getVerticalScrollBar() != null ) {
      sp.getVerticalScrollBar().putClientProperty( MetalScrollBarUI.FREE_STANDING_PROP, Boolean.FALSE);
    }

    oldOpaque = sp.isOpaque();
    sp.setOpaque( false);
    
    // Si el componente alojado tiene borde, hay que quitarselo para que no quede
    // feo.
    Component cc = sp.getViewport().getView();
    if ( cc != null ) {
      try {
        JComponent ccc = (JComponent)cc;
        
        // Esto esta aqui para solucionar un bug descubierto por Michael Flor
        if ( ccc.getBorder() != null && ccc.getBorder() instanceof LafBorders.LafGenBorder ) {
          ccc.setBorder( null);
        }
      }
      catch ( Exception ex) {
        System.out.println( ex);
        ex.printStackTrace();
      }
    }
  }
  
  public void uninstallUI( JComponent c) {
    super.uninstallUI( c);
    
    c.setOpaque( oldOpaque);
  }
  
  public void paint( Graphics g, JComponent c) {
    JScrollPane sp = (JScrollPane)c;
    
    // Esto es para solucionar un bug encontrado por Fabian Voith que consiste en que cuando se cambia el 
    // tama�o del JScrollPane se reevalua el tama�o necesario, lo que puede provocar un relayaut si ha cambiado
    // el tam�o de lo que contiene, por ejemplo, si se despliegan unos nodos en un arbol...
    // Para solucionarlo se guarda el tama�o del scrollpane al empezar a pintar y se pone al terminar.
    // Gracias a Fabian Voith.
    Dimension dim = sp.getSize();
    
    if ( sp.getViewportBorder() != null ) {
      // Parece que el popup de los combos contiene un scrollpane que contiene un panel,
      // asi que hay que asegurarse de que si papi es un combo no se pinta el borde
      // para que el combo no quede "raro"
      Component cc = c.getParent();
      while ( cc != null ) {
        if ( cc.toString().startsWith( "javax.swing.plaf.basic.BasicComboPopup")) {
          sp.setViewportBorder( null);
          break;
        }
        cc = cc.getParent();
      }

      // Ahora vamos a ver si el componente alojado dentro del scrollpane tiene un 
      // borde y se lo quitamos. Esto tambien se hace en installUI, pero hay que
      // repetirlo en cada repintado porque si se cambia el tema no se vuelve a
      // llamar el metodo principal.
      cc = sp.getViewport().getView();
      if ( cc != null ) {
        try {
          JComponent ccc = (JComponent)cc;

          Border bb = sp.getViewportBorder();
          if ( bb != null && bb instanceof LafBorders.LafGenBorder ) {
            int x = bb.getBorderInsets( sp).left + bb.getBorderInsets( sp).right - 1;
            
            g.setColor( ccc.getBackground());
            
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g.fillRoundRect( 0,0, x+cc.getWidth(), c.getHeight(), 7,7);
            
            g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
      
            // Aqui se vuelve a poner el tama�o que tenia al principio, por el bug ese descubierto por Fabian Voith.
            sp.setPreferredSize( dim);
          }
          
          // Esto esta aqui para solucionar un bug descubierto por Michael Flor
          if ( ccc.getBorder() != null && ccc.getBorder() instanceof LafBorders.LafGenBorder ) {
            ccc.setBorder( null);
          }
        }
        catch ( Exception ex) { /*si no se puede, pues no se puede...*/}
      }
    }
    
    super.paint( g, c);
  }
}
