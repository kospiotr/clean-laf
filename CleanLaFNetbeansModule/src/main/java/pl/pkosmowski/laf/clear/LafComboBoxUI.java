package pl.pkosmowski.laf.clear;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * 
 * @author Nilo J. Gonzalez 2007
 */
public class LafComboBoxUI extends MetalComboBoxUI {
  private boolean rollover = false;
  private boolean focus = false;
  private MiML miML;
  
  protected boolean oldOpaque;
  
  public static ComponentUI createUI( JComponent c) {
    return new LafComboBoxUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
   
    oldOpaque = comboBox.isOpaque();
    comboBox.setOpaque( false);
  }

  protected void uninstallDefaults() {
    super.uninstallDefaults();
    
    comboBox.setOpaque( oldOpaque);
  }

  protected  void installListeners() {
    super.installListeners();

    miML = new MiML();
    comboBox.addMouseListener( miML);
    comboBox.addFocusListener( miML);
  }
  
  protected  void uninstallListeners() {
    super.uninstallListeners();

    comboBox.removeMouseListener( miML);
    comboBox.removeFocusListener( miML);
  }
  
  public Dimension getMinimumSize( JComponent c) {
    Dimension dim = super.getMinimumSize( c);
    
    if ( comboBox.isEditable() ) {
      dim.height = editor.getPreferredSize().height - 2;
    }
    
    dim.width += 20;
    
    return dim;
  }
  
  protected ComboBoxEditor createEditor() {
    return new LafComboBoxEditor();
  }
  
  protected JButton createArrowButton() {
    return new LafComboBoxButton( comboBox, UIManager.getIcon( "ComboBox.buttonDownIcon"), 
                                     (comboBox.isEditable() ? true : false),
                                     currentValuePane, listBox);
  }
  
  public class LafComboBoxEditor extends MetalComboBoxEditor  {
    public LafComboBoxEditor() {
      super();
      editor.setBorder( LafBorders.getComboEditorBorder());
    }
  }
  
  private final class LafComboBoxButton extends MetalComboBoxButton {
    private static final long serialVersionUID = 1L;

    public LafComboBoxButton( JComboBox cb, Icon icon, boolean editable, CellRendererPane pane, JList list) {
      super( cb, icon, editable, pane, list);

      miML = new MiML();
      addMouseListener( miML);
      addFocusListener( miML);
    }
    
    /*
     * Este metodo se a�ade para limitar la espuesta de los eventos de raton a la zona del boton en los 
     * combos no editables.
     * en los combos no editables, TOOOOODO el combo es un boton, y como los botones capturan los eventos
     * de raton, por defecto no deja pasar ninguno a los posibles mouselisteners que se hayan registrado en 
     * el JComboBox. Estos no se llegan a lanzar nunca porque los captura este boton.
     * Para evitarlo, se sobreescribe el metodo contains, que es invocado cada vez que se genera un evento
     * de raton y devuelve true si el evento debe ser tratado y false en caso contrario, para que la nueva 
     * implementacion solo responda true en caso de que el raton se encuentre en el area del boton y no 
     * cuando se encuentra sobre la parte que tiene texto.  
     */
    public boolean contains( int x, int y) {
      boolean res = super.contains( x, y);
      
      if ( res && !iconOnly ) {
        if ( x < (getWidth() - comboIcon.getIconWidth() - getInsets().right - 5) ) {
          res = false;
        }
      }
      
      return res;
    }




    /*
     * Esto es triky. 
     * - Primero, le ponemos el borde que le viene bien segun sea
     * un boton o un cuadro de texto. No se puede hacer en el constructor porque cuando se construye el 
     * objeto aun no se sabe si es editable (se muestra un simple boton con bordes de boton) o no 
     * editable (todo es un boton, con bordes de textfield). Si es editable, pues borde de boton y a
     * correr, y si no, borde de editor y se ajustan algunas cosillas. Pero sobre todo, si es peque�o
     * se pone un borde que ocupa menos espacio. Es principalmente para que se pinten bien dentro de las
     * tablas
     * 
     * - Segundo, si es editable se pinta tal cual, pero si no lo es, hay que hacer transparente el
     * renderer de cada fila porque si no cada vez que se pulsa se rellena del color del foco y
     * queda feisimo. Ademas, como super.paintComponent pinta el foco *SIEMPRE* y a mi no me gusta
     * como pinta el foco, hay que copiar el metodo padre y comentar la parte del foco para que no lo
     * pinte, que es por lo que esta ahi el metodo paintLeches.
     * 
     * - Tercero, una vez pintado, hacemos un ajuste fino de lo que han pintado los JButton, que en
     * el caso de los cuadros editables consiste en borrar el sobrante de boton con el color del fondo
     * y tanto si es editable como si no, pintar el foco por el exterior del boton. 
     */
    public void paintComponent( Graphics g) {
      boolean canijo = false;
      
      if ( iconOnly ) {
        Border bb = LafBorders.getComboButtonBorder();
        Insets ins = bb.getBorderInsets( comboBox);
        
        // Si cabe todo, le ponemos un borde guay. Si no, pues le dejamos un borde cutrecillo
        if ( ( getSize().height < (comboIcon.getIconHeight() + ins.top + ins.bottom) )
              ||
             ( getSize().width  < (comboIcon.getIconWidth() + ins.left + ins.right) ) 
           ) {
          canijo = true;
          bb = LafBorders.getThinGenBorder();
        }
        
        setBorder( bb);
        setMargin( new Insets( 0,5,0,7));
      }
      else {
        Border bb = LafBorders.getComboEditorBorder();
        Insets ins = bb.getBorderInsets( comboBox);
        
        // Si cabe todo, le ponemos un borde guay. Si no, pues le dejamos un borde cutrecillo
        if ( getSize().height < (getFont().getSize() + ins.top + ins.bottom) ) {
          canijo = true;
          bb = LafBorders.getThinGenBorder();
        }
        
        setBorder( bb);
        setOpaque( false);
      }

      if ( !iconOnly && comboBox != null) {
        try {
          // Le pintamos el fondo
          g.setColor( getBackground());
          if ( !canijo ) {
            g.fillRect( 2,3, getWidth()-4, getHeight()-6);
          }
          else {
            g.fillRect( 0,0, getWidth(), getHeight());
          }
          g.drawLine( 3,2, getWidth()-4, 2);
          g.drawLine( 3,getHeight()-3, getWidth()-4, getHeight()-3);
          
          paintLeches( g);
        }
        catch ( Exception ex) {}
      }
      
      if ( iconOnly ) {
        RoundRectangle2D.Float boton = new RoundRectangle2D.Float(); 
        if ( canijo ) {
          boton.x = 0;
          boton.y = 0;
          boton.width = getWidth();
          boton.height = getHeight();
          boton.arcwidth = 1;
          boton.archeight = 1;
        }
        else {
          boton.x = 2;
          boton.y = 2;
          boton.width = getWidth() - 4;
          boton.height = getHeight() - 4;
          boton.arcwidth = 8;
          boton.archeight = 8;
        }
        
        setOpaque( false);
        paintLeches( g);
        
        ButtonModel mod = getModel();
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint grad = null;
        
        
        if ( mod.isPressed() || mod.isSelected() ) {
          grad = new GradientPaint( 0,0, LafUtils.getSombra(), 
                                    0,getHeight(), LafUtils.getBrillo());
          g2D.setPaint( grad);
          g2D.fill( boton);
        }
        else {
          grad = new GradientPaint( 0,0, LafUtils.getBrillo(), 
                                    0,getHeight(), LafUtils.getSombra());
          g2D.setPaint( grad);
          g2D.fill( boton);
        }
        
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
      }
      
      // Esto es para arreglar lo que deja el pintado por defecto
      if ( this.isEnabled() && !canijo ) {
        if ( focus ) {
          LafUtils.paintFocus( g, 1, 1, getWidth()-2, getHeight()-2, 4,4, 3, LafLookAndFeel.getFocusColor());
        }
        else if ( rollover ) {
          LafUtils.paintFocus( g, 1, 1, getWidth()-2, getHeight()-2, 4,4, 3, LafUtils.getColorAlfa( LafLookAndFeel.getFocusColor(), 150));
        }
      }
    }
    
    /*
     * Todo esto es porque la clase padre no pregunta si el focus se debe pintar cuando va a pintar el puto focus
     */
    protected void paintLeches( Graphics g ) {
      boolean leftToRight = comboBox.getComponentOrientation().isLeftToRight();

      if (ui != null) {
        Graphics scratchGraphics = (g == null) ? null : g.create();
        try {
          ui.update( scratchGraphics, this);
        }
        finally {
          scratchGraphics.dispose();
        }
      }

      Insets insets = getInsets();

      int width = getWidth() - (insets.left + insets.right);
      int height = getHeight() - (insets.top + insets.bottom);

      if ( height <= 0 || width <= 0 ) {
        return;
      }

      int left = insets.left;
      int top = insets.top;
      int right = left + (width - 1);
      int bottom = top + (height - 1);

      int iconWidth = 0;
      int iconLeft = (leftToRight) ? right : left;

      // Paint the icon
      if ( comboIcon != null ) {
        iconWidth = comboIcon.getIconWidth();
        int iconHeight = comboIcon.getIconHeight();
        int iconTop = 0;

        if ( iconOnly ) {
          iconLeft = (getWidth() / 2) - (iconWidth / 2);
          iconTop = (getHeight() / 2) - (iconHeight / 2);
        }
        else {
          if (leftToRight) {
            iconLeft = (left + (width - 1)) - iconWidth;
          }
          else {
            iconLeft = left;
          }
          iconTop = (top + ((bottom - top) / 2)) - (iconHeight / 2);
        }

        comboIcon.paintIcon( this, g, iconLeft, iconTop );

        // Se a�ade esto para pintar un limite al boton de despliegue
        if ( !iconOnly ) {
          g.setColor( LafUtils.getSombra());
          g.drawLine( iconLeft-5,6, iconLeft-5,getHeight()-6);

          g.setColor( LafUtils.getBrillo());
          g.drawLine( iconLeft-4,6, iconLeft-4,getHeight()-6);
        }

        // Paint the focus
        /* Esto queda comentado porque pinta el focus SIEMPRE, aunque yo no quiera...
        if ( comboBox.hasFocus()) {
              g.setColor( MetalLookAndFeel.getFocusColor() );
              g.drawRect( left - 1, top - 1, width + 3, height + 1 );
          }
         */
      }

      // Let the renderer paint
      if ( !iconOnly && comboBox != null ) {
        ListCellRenderer renderer = comboBox.getRenderer();
        Component c;
        boolean renderPressed = getModel().isPressed();
        c = renderer.getListCellRendererComponent( listBox, comboBox.getSelectedItem(), -1, renderPressed, false);
        c.setFont(rendererPane.getFont());

        if ( model.isArmed() && model.isPressed() ) {
          /*
          if ( isOpaque() ) {
            c.setBackground( UIManager.getColor( "Button.select"));
          }
          */
          c.setForeground( comboBox.getForeground());
          // Esto se pone para que el combo no se ponga amarillo al pulsarse
          c.setBackground( getBackground());
        }
        else if ( !comboBox.isEnabled() ) {
          if ( isOpaque() ) {
            c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
          }
          c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
        }
        else {
          c.setForeground( comboBox.getForeground());
          c.setBackground( comboBox.getBackground());
        }

        int cWidth = width - (insets.right + iconWidth);

        // Fix for 4238829: should lay out the JPanel.
        boolean shouldValidate = false;
        if (c instanceof JPanel)  {
          shouldValidate = true;
        }

        if (leftToRight) {
          rendererPane.paintComponent( g, c, this, left, top, cWidth, height, shouldValidate );
        }
        else {
          rendererPane.paintComponent( g, c, this, left + iconWidth, top, cWidth, height, shouldValidate );
        }
      }
    }
  }
  
  //////////////////////////
  
  public class MiML extends MouseAdapter implements FocusListener {
    protected void refresh() {
      if ( comboBox != null && comboBox.getParent() != null ) {
        comboBox.getParent().repaint( comboBox.getX()-5, comboBox.getY()-5, 
                                      comboBox.getWidth()+10, comboBox.getHeight()+10);
      }
    }
    
    public void mouseExited( MouseEvent e) {
      rollover = false;
      refresh();
    }
    
    public void mouseEntered( MouseEvent e) {
      rollover = true;
      refresh();
    }
    
    public void focusGained( FocusEvent e) {
      focus = true;
      refresh();
    }
      
    public void focusLost( FocusEvent e) {
      focus = false;
      refresh();
    }
  }
}
