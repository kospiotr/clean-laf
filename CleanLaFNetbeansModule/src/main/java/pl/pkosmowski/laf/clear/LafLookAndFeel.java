/**
 * The main class for the Laf Look&Feel.
 *
 * To use this Look&Feel, simply include these two lines into your code:<br>
 * <code>
 * LafLookAndFeel LafLF = new LafLookAndFeel();
 * UIManager.setLookAndFeel( LafLF);
 * </code>
 * You can change the default theme color in two ways.
 * 
 * You can create theme and change de colours with this lines:<br>
 *   <code>
 *   LafLookAndFeel LafLF = new LafLookAndFeel();
 *   LafTheme nt = new LafTheme();
 *   nt.setX( Color);
 *   ....
 *   nt.setX( Color);
 *   LafLF.setCurrentTheme( nt);
 *   UIManager.setLookAndFeel( LafLF);
 *   <code><br>
 * This way is good if you can change the sources and you want the program works *only* with LafLF. 
 *
 * If you don't have the sources you can change the theme color including same properties in your command line and the look and feel
 * will do its best... This couldn't work if the application changes the system properties, but, well, if you don't have the sources...<br>
 * For example:<br>
 *   <code>java -DLaflf.selection=0x00cc00 XXX.YOUR.APP.XXX</code> will colour with green the selected widgets<br>
 *   <code>java java -DLaflf.s1=0xdde8ee -DLaflf.s2=0xb7daec -DLaflf.s3=0x74bfe6 XXX.YOUR.APP.XXX</code> will colour with blue the background of the widgets<br>
 * The values are in the tipical HTML format (0xRRGGBB) with the red, green and blue values encoded in hexadecimal format.<br> 
 * These are the admited properties:
 * <ul>   
 * <li>Laflf.selection: this is the selection color</li>
 * <li>Laflf.background: this is the background color</li>
 * <li>Laflf.p1: this is the primary1 color (�Don't you understand? Patience?</li>
 * <li>Laflf.p2: this is the primary2 color</li>
 * <li>Laflf.p3: this is the primary3 color</li>
 * <li>Laflf.s1: this is the secondary1 color</li>
 * <li>Laflf.s2: this is the secondary2 color</li>
 * <li>Laflf.s3: this is the secondary3 color</li>
 * <li>Laflf.b: this is the black color</li>
 * <li>Laflf.w: this is the white color</li>
 * <li>Laflf.menuOpacity: this is the menu opacity</li>
 * <li>Laflf.frameOpacity: this is the frame opacity</li>
 * </ul>
 * �Primary color? �Secondary? �What the...? Cool. <a href='http://java.sun.com/products/jlf/ed1/dg/higg.htm#62001'>Here</a> you can learn what 
 * i'm talking about. Swing applications have only 8 colors, named PrimaryX, SecondaryX, White and Black, and <a href='http://java.sun.com/products/jlf/ed1/dg/higg.htm#62001'>here</a>
 * you hava a table with the who-is-who.<br>
 * You don't need to write all the values, you only must write those values you want to change. There are two shorthand properties, selection and background.
 * If you write Laflf.selection or Laflf.background the LafLF will calculate the colors around (darker and lighter) your choose.<br>
 * If Laflf.selection is writen, pX, sX, b and w are ignored. 
 * Ahh!! One more thing. 0xRRGGBB is equal #RRGGBB. 
 * @see LafTheme
 * @see http://java.sun.com/products/jlf/ed1/dg/higg.htm#62001
 * @author Nilo J. Gonzalez 
 */
 
package pl.pkosmowski.laf.clear;

import java.awt.*;
import java.awt.image.Kernel;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

import java.util.*;

public class LafLookAndFeel extends MetalLookAndFeel {
  private static final long serialVersionUID = 7191199335214123414L;
  
  String fichTheme = "";
  public static final String ICO_PATH = "/pl/pkosmowski/laf/clear/icons/";
  
  protected static MetalTheme theme;
  
  public LafLookAndFeel() {
    super();
    
    LafTheme nt = new LafTheme( "LafThemeFile.theme");
    
    String p1, p2, p3, s1, s2, s3, selection, background, w, b, opMenu, opFrame;
    
    
    // Vamos a ver si han puesto por linea de comandos un fichero de tema...
    
    // Este codigo esta aqui gracias a Fritz Elfert, que descubrio que esto cascaba miserablemente
    // cuando se usaba en un applet.
    String nomFich = null;
    String nomURL = null;
    try {
      nomFich = System.getProperty( "Laflf.themeFile");
      nomURL = System.getProperty( "Laflf.themeURL");
    } 
    catch ( Exception ex) {
      // If used in an applet, this could throw a SecurityException.
    }
    
    // ... o tenemos que tirar del fichero por defecto
    nomFich = ( nomFich == null ? "LafThemeFile.theme" : nomFich);

    
    try {
      // Primero, vamos a ver si lo sacamos de una url
      if ( nomURL != null ) {
        try {
          LafTheme ntt = new LafTheme( new java.net.URL( nomURL));  // Esto funcionara si el fichero esta en la URL y ademas esta bien
          nt = ntt;
        }
        catch ( Exception ex) {
          System.err.println( nomURL + " theme file is wrong or doesn't exist...");
          System.err.println( ex);
        }
      }
      else {
        try {
          LafTheme ntt = new LafTheme( nomFich);  // Esto funcionara si el fichero esta en la URL y ademas esta bien
          nt = ntt;
        }
        catch ( Exception ex) {
          System.err.println( nomFich + " theme file is wrong or doesn't exist...");
          System.err.println( ex);
        }
      }
      
      fichTheme = nomFich;
    }
    catch ( Exception ex) {    // Si no se puede leer el fichero o el fichero esta malamente,
      nt = new LafTheme( "LafThemeFile.theme");  // no le hacemos ni caso.
    }
    
    try {
      // Ahora vamos a ver si se expecifican los colores por linea de comandos.
      selection = System.getProperty( "Laflf.selection");
      background = System.getProperty( "Laflf.background");
      
      p1 = System.getProperty( "Laflf.p1");
      p2 = System.getProperty( "Laflf.p2");
      p3 = System.getProperty( "Laflf.p3");
      
      s1 = System.getProperty( "Laflf.s1");
      s2 = System.getProperty( "Laflf.s2");
      s3 = System.getProperty( "Laflf.s3");
      
      w = System.getProperty( "Laflf.w");
      b = System.getProperty( "Laflf.b");
      
      opMenu = System.getProperty( "Laflf.menuOpacity");
      opFrame = System.getProperty(  "Laflf.frameOpacity");
        
      nt = LafUtils.iniCustomColors( nt, selection, background, p1, p2, p3, s1, s2, s3, w, b, opMenu, opFrame);
    }
    catch ( Exception ex ) {
      // Este codigo esta aqui gracias a Fritz Elfert, que descubrio que esto cascaba miserablemente
      // cuando se usaba en un applet. Un gran tipo Fritz...
      if ( fichTheme.length() == 0 ) {
        nt = new LafTheme( "LafThemeFile.theme");
      }
    }
    
    setCurrentTheme( nt);
    
    float[] elements = new float[LafUtils.MATRIX_FAT*LafUtils.MATRIX_FAT];
    for ( int i = 0; i < elements.length; i++ ) {
      elements[i] = 0.1f;
    }
    int mid = LafUtils.MATRIX_FAT/2+1;
    elements[mid*mid] = .2f;
    
    LafUtils.kernelFat = new Kernel( LafUtils.MATRIX_FAT,LafUtils.MATRIX_FAT, elements);
    
    elements = new float[LafUtils.MATRIX_THIN*LafUtils.MATRIX_THIN];
    for ( int i = 0; i < elements.length; i++ ) {
      elements[i] = 0.1f;
    }
    mid = LafUtils.MATRIX_THIN/2+1;
    elements[mid*mid] = .2f;
    
    LafUtils.kernelThin = new Kernel( LafUtils.MATRIX_THIN,LafUtils.MATRIX_THIN, elements);
  }

  /*
   * Este codigo esta aqui para copiar los atajos de teclado de look and feel del sistema.
   * Principalmente sirve para que los usuarios de Macs puedan hacer el copy-paste usando
   * las teclas a las que estan acostumbrados.
   * Lo hizo Norbert Seekircher
   */
  public void initialize() {
    try {
      LookAndFeel laf = (LookAndFeel)Class.forName( UIManager.getSystemLookAndFeelClassName()).newInstance();
      UIDefaults systemDefaults = laf.getDefaults();
      Enumeration keys = systemDefaults.keys();
      String key;

      while ( keys.hasMoreElements() ) {
        key = keys.nextElement().toString();

        if ( key.contains( "InputMap") ) {
          UIManager.getDefaults().put( key, systemDefaults.get( key));
        }
      }
    } 
    catch ( Exception ex) {
      //ex.printStackTrace();
    }
  }

  
  public String getID() {
    return "Laf";
  }

  public String getName() {
    return "Laf";
  }

  public String getDescription() {
    return "Look and Feel Laf, by Nilo J. Gonzalez 2005-2007";
  }

  public boolean isNativeLookAndFeel() {
    return false;
  }

  public boolean isSupportedLookAndFeel() {
    return true;
  }
  
  /**
   * Este metodo devuelve false porque para dar bordes como es debido a la ventana principal hay que
   * fusilarse la clase MetalRootPaneUI enterita porque la mayoria de sus metodos son privados...
   * Ademas, no es mala idea que la decoracion de la ventana principal la ponga el sistema operativo
   * para que sea igual que todas (y si tiene transparencias, mejor)
   */
  public boolean getSupportsWindowDecorations() {
    return false;
  }

  /* Esta mierda es debida a que quiero que esto funcione en la version 1.4 de Java y ademas, 
     que el sitio adecuado para dejar la transparencia de los menus es el theme. Bueno, pues
     en la version 1.4 eso no se puede hacer, porque la funcion getCurrentTheme es privada de
     MetalLookAndFeel, asi que no hay manera de saber que tema se esta usando y por tanto no
     se puede recuperar la opacidad (ni ninugna otra caracteristica de los temas que no sea 
     estandar). Asi que hay que replicar la funcion setCurrentTheme aqui, guardar el tema en 
     una variable local y devolverlo despues en la funcion getOpacity
  */
  public static void setCurrentTheme( MetalTheme t) {
    MetalLookAndFeel.setCurrentTheme( t);
    
    theme = t;
    LafUtils.rollColor = null;
  }
  
  protected void initClassDefaults( UIDefaults table) {
    super.initClassDefaults( table);
    
    table.put( "ButtonUI", "pl.pkosmowski.laf.clear.LafButtonUI");
    table.put( "ToggleButtonUI", "pl.pkosmowski.laf.clear.LafToggleButtonUI");
    table.put( "TextFieldUI", "pl.pkosmowski.laf.clear.LafTextFieldUI");
    table.put( "TextAreaUI", "pl.pkosmowski.laf.clear.LafTextAreaUI");
    table.put( "PasswordFieldUI", "pl.pkosmowski.laf.clear.LafPasswordFieldUI");
    table.put( "CheckBoxUI", "pl.pkosmowski.laf.clear.LafCheckBoxUI");
    table.put( "RadioButtonUI", "pl.pkosmowski.laf.clear.LafRadioButtonUI");
    table.put( "FormattedTextFieldUI", "pl.pkosmowski.laf.clear.LafFormattedTextFieldUI");
    table.put( "SliderUI", "pl.pkosmowski.laf.clear.LafSliderUI");
    table.put( "SpinnerUI", "pl.pkosmowski.laf.clear.LafSpinnerUI");
    
    table.put( "ListUI", "pl.pkosmowski.laf.clear.LafListUI");
    table.put( "ComboBoxUI", "pl.pkosmowski.laf.clear.LafComboBoxUI");
    table.put( "ScrollBarUI", "pl.pkosmowski.laf.clear.LafScrollBarUI");
    table.put( "ToolBarUI", "pl.pkosmowski.laf.clear.LafToolBarUI");
    table.put( "ProgressBarUI", "pl.pkosmowski.laf.clear.LafProgressBarUI");
    table.put( "ScrollPaneUI", "pl.pkosmowski.laf.clear.LafScrollPaneUI");
    
    table.put( "TabbedPaneUI", "pl.pkosmowski.laf.clear.LafTabbedPaneUI");
    table.put( "TableHeaderUI", "pl.pkosmowski.laf.clear.LafTableHeaderUI");
    table.put( "SplitPaneUI", "pl.pkosmowski.laf.clear.LafSplitPaneUI");
    
    table.put( "InternalFrameUI", "pl.pkosmowski.laf.clear.LafInternalFrameUI");
    table.put( "DesktopIconUI", "pl.pkosmowski.laf.clear.LafDesktopIconUI");
    
    table.put( "ToolTipUI", "pl.pkosmowski.laf.clear.LafToolTipUI");
    
    // Todo esto, es para sacar un triste menu    
    table.put( "MenuBarUI", "pl.pkosmowski.laf.clear.LafMenuBarUI");
    table.put( "MenuUI", "pl.pkosmowski.laf.clear.LafMenuUI");
    table.put( "SeparatorUI", "pl.pkosmowski.laf.clear.LafSeparatorUI");
    table.put( "PopupMenuUI", "pl.pkosmowski.laf.clear.LafPopupMenuUI");
    table.put( "PopupMenuSeparatorUI", "pl.pkosmowski.laf.clear.LafPopupMenuSeparatorUI");
    table.put( "MenuItemUI", "pl.pkosmowski.laf.clear.LafMenuItemUI");
    table.put( "CheckBoxMenuItemUI", "pl.pkosmowski.laf.clear.LafCheckBoxMenuItemUI");
    table.put( "RadioButtonMenuItemUI", "pl.pkosmowski.laf.clear.LafRadioButtonMenuItemUI");

    /*
    for( Enumeration en = table.keys(); en.hasMoreElements(); ) {
      System.out.println( "[" + en.nextElement() + "]");
    }
    */
  }

  protected void initSystemColorDefaults( UIDefaults table) {
    super.initSystemColorDefaults( table);
    
    // Esto es para que todo lo que este seleccionado tenga el mismo color.
    table.put( "textHighlight", getMenuSelectedBackground());
    
    // Y esto, para que se vean bien los textos inactivados.
    table.put( "textInactiveText", getInactiveSystemTextColor().darker());
    
    /*
    for( Enumeration en = table.keys(); en.hasMoreElements(); ) {
      System.out.println( "[" + (String)en.nextElement() + "]");
    }
    */
  }


  protected void initComponentDefaults( UIDefaults table) {
    super.initComponentDefaults( table);

    try {
      ColorUIResource cFore = (ColorUIResource)table.get( "MenuItem.disabledForeground");
      ColorUIResource cBack = (ColorUIResource)table.get( "MenuItem.foreground");
      
      ColorUIResource col = LafUtils.getColorTercio( cBack, cFore);
      table.put(  "MenuItem.disabledForeground", col);
      table.put(  "Label.disabledForeground", col);
      table.put(  "CheckBoxMenuItem.disabledForeground", col);
      table.put(  "Menu.disabledForeground", col);
      table.put(  "RadioButtonMenuItem.disabledForeground", col);
      table.put(  "ComboBox.disabledForeground", col);
      table.put(  "Button.disabledText", col);
      table.put(  "ToggleButton.disabledText", col);
      table.put(  "CheckBox.disabledText", col);
      table.put(  "RadioButton.disabledText", col);
      
      ColorUIResource col2 = LafUtils.getColorTercio(LafLookAndFeel.getWhite(),
                                                         (Color)table.get( "TextField.inactiveBackground"));
      table.put( "TextField.inactiveBackground", col2);
    }
    catch ( Exception ex) {
      ex.printStackTrace();
    }
    
    table.put( "MenuBar.border", LafBorders.getMenuBarBorder());

    Font fontMenu = ((Font)table.get( "Menu.font")).deriveFont( Font.BOLD);
    //table.put( "Menu.font", fontMenu);
    //table.put( "MenuItem.font", fontMenu);
    //table.put( "PopupMenu.font", fontMenu);
    //table.put( "RadioButtonMenuItem.font", fontMenu);
    //table.put( "CheckBoxMenuItem.font", fontMenu);
    table.put( "MenuItem.acceleratorFont", fontMenu);
    table.put( "RadioButtonMenuItem.acceleratorFont", fontMenu);
    table.put( "CheckBoxMenuItem.acceleratorFont", fontMenu);
    
    ColorUIResource colAcce = LafUtils.getColorTercio( (ColorUIResource)table.get( "MenuItem.foreground"),
                                                          (ColorUIResource)table.get( "MenuItem.acceleratorForeground")
                                                        );

    table.put( "MenuItem.acceleratorForeground", colAcce);
    table.put( "RadioButtonMenuItem.acceleratorForeground", colAcce);
    table.put( "CheckBoxMenuItem.acceleratorForeground", colAcce);
    
    // Para la sombra de los popupmenus
    table.put( "BorderPopupMenu.SombraBajIcon", LafUtils.loadRes( ICO_PATH + "SombraMenuBajo.png"));
    table.put( "BorderPopupMenu.SombraDerIcon", LafUtils.loadRes( ICO_PATH + "SombraMenuDer.png"));
    table.put( "BorderPopupMenu.SombraEsqIcon", LafUtils.loadRes( ICO_PATH + "SombraMenuEsq.png"));
    table.put( "BorderPopupMenu.SombraUpIcon", LafUtils.loadRes( ICO_PATH + "SombraMenuUp.png"));
    table.put( "BorderPopupMenu.SombraIzqIcon", LafUtils.loadRes( ICO_PATH + "SombraMenuIzq.png"));
    
    // Para el JTree
    table.put( "Tree.collapsedIcon", LafIconFactory.getTreeCollapsedIcon());
    table.put( "Tree.expandedIcon", LafIconFactory.getTreeExpandedIcon());
    table.put( "Tree.closedIcon", LafUtils.loadRes( ICO_PATH + "TreeDirCerrado.png"));
    table.put( "Tree.openIcon", LafUtils.loadRes( ICO_PATH + "TreeDirAbierto.png"));
    table.put( "Tree.leafIcon", LafUtils.loadRes( ICO_PATH + "TreeFicheroIcon.png"));
    table.put( "Tree.PelotillaIcon", LafUtils.loadRes( ICO_PATH + "TreePelotilla.png"));
    
    // Los dialogos de ficheros
    table.put( "FileView.directoryIcon", LafUtils.loadRes( ICO_PATH + "DialogDirCerrado.png"));
    table.put( "FileView.fileIcon", LafUtils.loadRes( ICO_PATH + "DialogFicheroIcon.png"));
    table.put( "FileView.floppyDriveIcon", LafUtils.loadRes( ICO_PATH + "DialogFloppyIcon.png"));
    table.put( "FileView.hardDriveIcon", LafUtils.loadRes( ICO_PATH + "DialogHDIcon.png"));
    table.put( "FileChooser.newFolderIcon", LafUtils.loadRes( ICO_PATH + "DialogNewDir.png"));
    table.put( "FileChooser.homeFolderIcon", LafUtils.loadRes( ICO_PATH + "DialogHome.png"));
    table.put( "FileChooser.upFolderIcon", LafUtils.loadRes( ICO_PATH + "DialogDirParriba.png"));
    table.put( "FileChooser.detailsViewIcon", LafUtils.loadRes( ICO_PATH + "DialogDetails.png"));
    table.put( "FileChooser.listViewIcon", LafUtils.loadRes( ICO_PATH + "DialogList.png"));
    
    // Para los muchos CheckBox y RadioButtons
    table.put( "CheckBoxMenuItem.checkIcon", LafIconFactory.getCheckBoxMenuItemIcon());
    table.put( "RadioButtonMenuItem.checkIcon", LafIconFactory.getRadioButtonMenuItemIcon());
    
    // La flechica de los combos...
    table.put( "ComboBox.flechaIcon", LafUtils.loadRes( ICO_PATH + "ComboButtonDown.png"));
    table.put( "ComboBox.buttonDownIcon", LafIconFactory.getComboFlechaIcon());
    
    // Los iconos de los menus
    table.put( "Menu.checkIcon", LafIconFactory.getBandaMenuItemIcon());
    table.put( "MenuItem.checkIcon", LafIconFactory.getBandaMenuItemIcon());
    table.put( "MenuCheckBox.iconBase", LafUtils.loadRes( ICO_PATH + "MenuCheckBoxBase.png"));
    table.put( "MenuCheckBox.iconTick", LafUtils.loadRes( ICO_PATH + "MenuCheckBoxTick.png"));
    table.put( "MenuRadioButton.iconBase", LafUtils.loadRes( ICO_PATH + "MenuRadioBase.png"));
    table.put( "MenuRadioButton.iconTick", LafUtils.loadRes( ICO_PATH + "MenuRadioTick.png"));
    table.put( "CheckBox.iconBase", LafUtils.loadRes( ICO_PATH + "CheckBoxBase.png"));
    table.put( "CheckBox.iconTick", LafUtils.loadRes( ICO_PATH + "CheckBoxTick.png"));
    table.put( "RadioButton.iconBase", LafUtils.loadRes( ICO_PATH + "RadioButtonBase.png"));
    table.put( "RadioButton.iconTick", LafUtils.loadRes( ICO_PATH + "RadioButtonTick.png"));
    
    // Iconos para los borders generales
    table.put( "BordeGenSup", LafUtils.loadRes( ICO_PATH + "BordeGenSup.png"));
    table.put( "BordeGenSupDer", LafUtils.loadRes( ICO_PATH + "BordeGenSupDer.png"));
    table.put( "BordeGenDer", LafUtils.loadRes( ICO_PATH + "BordeGenDer.png"));
    table.put( "BordeGenInfDer", LafUtils.loadRes( ICO_PATH + "BordeGenInfDer.png"));
    table.put( "BordeGenInf", LafUtils.loadRes( ICO_PATH + "BordeGenInf.png"));
    table.put( "BordeGenInfIzq", LafUtils.loadRes( ICO_PATH + "BordeGenInfIzq.png"));
    table.put( "BordeGenIzq", LafUtils.loadRes( ICO_PATH + "BordeGenIzq.png"));
    table.put( "BordeGenSupIzq", LafUtils.loadRes( ICO_PATH + "BordeGenSupIzq.png"));
    
    // Bordes generales
    table.put( "List.border", LafBorders.getGenBorder());
    table.put( "ScrollPane.viewportBorder", LafBorders.getGenBorder());
    table.put( "Menu.border", LafBorders.getGenMenuBorder());
    table.put( "ToolBar.border", LafBorders.getToolBarBorder());
    table.put( "TextField.border", LafBorders.getTextFieldBorder());
    table.put( "TextArea.border", LafBorders.getTextFieldBorder());
    table.put( "FormattedTextField.border", LafBorders.getTextFieldBorder());
    table.put( "PasswordField.border", LafBorders.getTextFieldBorder());
    table.put( "ToolTip.border", LafBorders.getToolTipBorder());
    
    table.put( "Table.focusCellHighlightBorder", LafBorders.getCellFocusBorder());
    
    // Esto realmente no es necesario porque no se sobrecarga la clase ScrollPaneUI, pero si no se sobrecarga
    // el borde de ScrollPane, NetBeans 5.5 se queda tieso cuando cierras todas las pesta�as del panel principal... 
    table.put( "ScrollPane.border", LafBorders.getScrollPaneBorder());
    
    // Para los ToolTips
    ColorUIResource col2 = LafUtils.getColorTercio(LafLookAndFeel.getFocusColor(),
                                                      (Color)table.get( "TextField.inactiveBackground"));
    table.put( "ToolTip.background", col2);
    table.put( "ToolTip.font", ((Font)table.get( "Menu.font")));
    
    // Para los Spinners
    table.put( "Spinner.editorBorderPainted", new Boolean( false));
    table.put( "Spinner.border", LafBorders.getTextFieldBorder());
    table.put( "Spinner.arrowButtonBorder", BorderFactory.createEmptyBorder());
    table.put( "Spinner.nextIcon", LafIconFactory.getSpinnerNextIcon());
    table.put( "Spinner.previousIcon", LafIconFactory.getSpinnerPreviousIcon());
    
    // Los iconillos de los dialogos
    table.put( "OptionPane.errorIcon", LafUtils.loadRes( ICO_PATH + "Error.png"));
    table.put( "OptionPane.informationIcon", LafUtils.loadRes( ICO_PATH + "Inform.png"));
    table.put( "OptionPane.warningIcon", LafUtils.loadRes( ICO_PATH + "Warn.png"));
    table.put( "OptionPane.questionIcon", LafUtils.loadRes( ICO_PATH + "Question.png"));
    
    // Para el JSlider
    table.put( "Slider.horizontalThumbIcon", LafIconFactory.getSliderHorizontalIcon());
    table.put( "Slider.verticalThumbIcon", LafIconFactory.getSliderVerticalIcon());
    table.put( "Slider.horizontalThumbIconImage", LafUtils.loadRes( ICO_PATH + "HorizontalThumbIconImage.png"));
    table.put( "Slider.verticalThumbIconImage", LafUtils.loadRes( ICO_PATH + "VerticalThumbIconImage.png"));
    
    // Para las scrollbars
    table.put( "ScrollBar.horizontalThumbIconImage", LafUtils.loadRes( ICO_PATH + "HorizontalScrollIconImage.png"));
    table.put( "ScrollBar.verticalThumbIconImage", LafUtils.loadRes( ICO_PATH + "VerticalScrollIconImage.png"));
    table.put( "ScrollBar.northButtonIconImage", LafUtils.loadRes( ICO_PATH + "ScrollBarNorthButtonIconImage.png"));
    table.put( "ScrollBar.southButtonIconImage", LafUtils.loadRes( ICO_PATH + "ScrollBarSouthButtonIconImage.png"));
    table.put( "ScrollBar.eastButtonIconImage", LafUtils.loadRes( ICO_PATH + "ScrollBarEastButtonIconImage.png"));
    table.put( "ScrollBar.westButtonIconImage", LafUtils.loadRes( ICO_PATH + "ScrollBarWestButtonIconImage.png"));
    table.put( "ScrollBar.northButtonIcon", LafIconFactory.getScrollBarNorthButtonIcon());
    table.put( "ScrollBar.southButtonIcon", LafIconFactory.getScrollBarSouthButtonIcon());
    table.put( "ScrollBar.eastButtonIcon", LafIconFactory.getScrollBarEastButtonIcon());
    table.put( "ScrollBar.westButtonIcon", LafIconFactory.getScrollBarWestButtonIcon());
    
    // Margenes de los botones
    table.put( "Button.margin", new InsetsUIResource( 5,14, 5,14));
    table.put( "ToggleButton.margin", new InsetsUIResource( 5,14, 5,14));
    
    // Para los InternalFrames y sus iconillos
    table.put( "Desktop.background", table.get( "MenuItem.background"));
    table.put( "InternalFrame.border", LafBorders.getInternalFrameBorder());
    
    table.put( "InternalFrame.NimCloseIcon", LafUtils.loadRes( ICO_PATH + "FrameClose.png"));
    table.put( "InternalFrame.NimCloseIconRoll", LafUtils.loadRes( ICO_PATH + "FrameCloseRoll.png"));
    table.put( "InternalFrame.NimCloseIconPush", LafUtils.loadRes( ICO_PATH + "FrameClosePush.png"));
    
    table.put( "InternalFrame.NimMaxIcon", LafUtils.loadRes( ICO_PATH + "FrameMaximiza.png"));
    table.put( "InternalFrame.NimMaxIconRoll", LafUtils.loadRes( ICO_PATH + "FrameMaximizaRoll.png"));
    table.put( "InternalFrame.NimMaxIconPush", LafUtils.loadRes( ICO_PATH + "FrameMaximizaPush.png"));
    
    table.put( "InternalFrame.NimMinIcon", LafUtils.loadRes( ICO_PATH + "FrameMinimiza.png"));
    table.put( "InternalFrame.NimMinIconRoll", LafUtils.loadRes( ICO_PATH + "FrameMinimizaRoll.png"));
    table.put( "InternalFrame.NimMinIconPush", LafUtils.loadRes( ICO_PATH + "FrameMinimizaPush.png"));
    
    table.put( "InternalFrame.NimResizeIcon", LafUtils.loadRes( ICO_PATH + "FrameResize.png"));
    table.put( "InternalFrame.NimResizeIconRoll", LafUtils.loadRes( ICO_PATH + "FrameResizeRoll.png"));
    table.put( "InternalFrame.NimResizeIconPush", LafUtils.loadRes( ICO_PATH + "FrameResizePush.png"));
    
    table.put( "InternalFrame.closeIcon", LafIconFactory.getFrameCloseIcon());
    table.put( "InternalFrame.minimizeIcon", LafIconFactory.getFrameAltMaximizeIcon());
    table.put( "InternalFrame.maximizeIcon", LafIconFactory.getFrameMaxIcon());
    table.put( "InternalFrame.iconifyIcon", LafIconFactory.getFrameMinIcon());
    table.put( "InternalFrame.icon", LafUtils.loadRes( ICO_PATH + "Frame.png"));
    table.put( "LafInternalFrameIconLit.width", new Integer( 20));
    table.put( "LafInternalFrameIconLit.height", new Integer( 20));
    
    Font fontIcon = ((Font)table.get( "InternalFrame.titleFont")).deriveFont( Font.BOLD);
    table.put( "DesktopIcon.font", fontIcon);
    table.put( "LafDesktopIcon.width", new Integer( 80));
    table.put( "LafDesktopIcon.height", new Integer( 60));
    table.put( "LafDesktopIconBig.width", new Integer( 48));
    table.put( "LafDesktopIconBig.height", new Integer( 48));
    
    // Esto no se usa dentro del codigo de Laf LaF, pero SWTSwing y el plugin EoS de Eclipse si lo usan
    table.put( "InternalFrame.activeTitleBackground", getMenuSelectedBackground());
    table.put( "InternalFrame.activeTitleGradient", getMenuSelectedBackground().darker());
    table.put( "InternalFrame.inactiveTitleBackground", getMenuBackground().brighter());
    table.put( "InternalFrame.inactiveTitleGradient", getMenuBackground().darker());
    
    //Esto es solo para saber que hay en la tabla
    /*
    for( Enumeration en = table.keys(); en.hasMoreElements(); ) {
      System.out.println( "[" + en.nextElement() + "]");
    }
    */
  }
}