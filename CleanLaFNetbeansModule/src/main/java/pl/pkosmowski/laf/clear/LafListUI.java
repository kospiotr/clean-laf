package pl.pkosmowski.laf.clear;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class LafListUI extends BasicListUI {
  public LafListUI( JComponent list) {
    super();
  }
  
  public static ComponentUI createUI( JComponent list) {
    return new LafListUI( list);
  }
  
  protected void paintCell( Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer,
                            ListModel dataModel, ListSelectionModel selModel, int leadIndex) {
    
    rowBounds.x += 1;
    super.paintCell( g, row, rowBounds, cellRenderer, dataModel, selModel, leadIndex);
    rowBounds.x -= 1;
    
    if ( list.isSelectedIndex( row) ) {
      Color oldColor = g.getColor();
      
      g.translate( rowBounds.x, rowBounds.y);
      
      GradientPaint grad = new GradientPaint( 0,0, LafUtils.getBrillo(), 
                                              0,rowBounds.height, LafUtils.getSombra());
      Color bgColor = LafLookAndFeel.getMenuSelectedBackground();  
        
      Graphics2D g2D = (Graphics2D)g;
      g2D.setPaint( grad);
      g2D.fillRect( 0,0, rowBounds.width-1, rowBounds.height);
      
      g.setColor( bgColor.darker());
      g.drawRect( 0,0, rowBounds.width-1, rowBounds.height-1);
      
      g.translate( -rowBounds.x, -rowBounds.y);
      g.setColor( oldColor);
    }
  }
}
