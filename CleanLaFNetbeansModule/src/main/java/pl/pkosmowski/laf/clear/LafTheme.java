package pl.pkosmowski.laf.clear;


import java.awt.*;
import java.io.*;
import java.util.*;
import java.net.*;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.DefaultMetalTheme;


/**
 * Define un <I>tema</I> de color para el LafLookAndFeel.
 * En realidad, valen para cualquier Look&Feel que herede de MetalLookAndFeel.<BR>
 * Se usa asi:
 * <PRE>
 * LafLookAndFeel LafLF = new LafLookAndFeel();
 * LafLF.setCurrentTheme( new LafTheme());
 * UIManager.setLookAndFeel(LafLF);
 * </PRE>
 * Con esto se pone un color gris oscuro. Tambien define temas partiendo de un color base, modificando
 * los valores primarios.
 * <PRE>
 * LafLookAndFeel LafLF = new LafLookAndFeel();
 * LafLF.setCurrentTheme( new LafTheme( <I>unColor</I>));
 * UIManager.setLookAndFeel(LafLF);
 * </PRE>
 * o partiendo de dos colores base, uno para los valores primarios y otro para los secundarios.
 * <PRE>
 * LafLookAndFeel LafLF = new LafLookAndFeel();
 * LafLF.setCurrentTheme( new LafTheme( <I>unColorPrimario</I>, <I>unColorSecundario</I>));
 * UIManager.setLookAndFeel(LafLF);
 * </PRE>
 * Para entender como va el temita de los colores, puede ayudar mucho consultar esta pagina:
 * <a href='http://java.sun.com/products/jlf/ed1/dg/higg.htm'>http://java.sun.com/products/jlf/ed1/dg/higg.htm</a> 
 */
public class LafTheme extends DefaultMetalTheme {
  public static final int DEFAULT_MENU_OPACITY = 195;
  public static final int DEFAULT_FRAME_OPACITY = 180;
  
  // primarios
  private ColorUIResource primary1 = new ColorUIResource( 229, 189, 0);

  private ColorUIResource primary2 = new ColorUIResource( 239, 199, 0);

  private ColorUIResource primary3 = new ColorUIResource( 249, 209, 0);

  // secondarios
  private ColorUIResource secondary1 = new ColorUIResource( 217, 215, 173);

  private ColorUIResource secondary2 = new ColorUIResource( 227, 225, 183);

  private ColorUIResource secondary3 = new ColorUIResource( 237, 235, 193);
  
  private ColorUIResource black = new ColorUIResource( 0, 0, 0);
  
  private ColorUIResource white = new ColorUIResource( 255, 255, 255);
  
  // la fuente
  private FontUIResource font = new FontUIResource( "SansSerif", Font.PLAIN, 12);
  private FontUIResource boldFont = new FontUIResource( "SansSerif", Font.BOLD, 12);
  
  // la opacidadMenu de los menus
  private int opacidadMenu = DEFAULT_MENU_OPACITY;

  //la opacidadMenu de los InternalFrames
  private int opacidadFrame = DEFAULT_FRAME_OPACITY;
  
  public LafTheme() {
    super();
  }

  /**
   * Este constructor crea un tema partiendo de un fichero de tema **COMPLETO** situado en la ruta marcada por el parametro nomFich.
   * Si no se encuentra en el sistema de ficheros, se busca en al classpath. Si el fichero de tema esta incompleto, salta
   * una excepcion NumberFormatException con el valor que ha dado el problema 
   * @param nomFich el nombre del fichero
   */
  public LafTheme( String nomFich) {
    super();
    
    Properties props = new Properties();
    InputStream res = null;
    
    try {
      res = new FileInputStream( nomFich);  // Primero, se carga el fichero
    }
    catch ( Exception ex) {
      nomFich = "/" + nomFich;
      res = this.getClass().getResourceAsStream( nomFich);   // Si no hay fichero, se busca en el classpath/jar
    }
    
    if ( res != null ) {
      try {
        props.load( res);
        res.close();
        initFromProps( props);     
      }
      catch ( Exception ex) {
        ex.printStackTrace();
        return;  // Si no esta en ningun sitio, esto dara una excepcion y deja los colores por defecto
      }
    }
  }
  
  /**
   * Este constructor crea un tema partiendo de un fichero de tema **COMPLETO** situado en la URL apuntada por el parametro url. 
   * Si el fichero de tema esta incompleto, salta
   * una excepcion NumberFormatException con el valor que ha dado el problema 
   * @param url la url del tema
   */
  public LafTheme( URL url) {
    super();
    
    Properties props = new Properties();
    InputStream res = null;
    
    try {
      URLConnection con = url.openConnection();
      res = con.getInputStream();
      props.load( res);
      res.close();
      initFromProps( props);
    }
    catch ( Exception ex) {
      return;  // Si no esta en ningun sitio, esto dara una excepcion y deja los colores por defecto
    }  
  }
  
  private void initFromProps( Properties props) {
    setPrimary1( Color.decode( props.getProperty( "Laflf.p1")));                                                                 
    setPrimary2( Color.decode( props.getProperty( "Laflf.p2")));                                                                 
    setPrimary3( Color.decode( props.getProperty( "Laflf.p3")));                                                                 
                                                                     
    setSecondary1( Color.decode( props.getProperty( "Laflf.s1")));                                                                 
    setSecondary2( Color.decode( props.getProperty( "Laflf.s2")));                                                                 
    setSecondary3( Color.decode( props.getProperty( "Laflf.s3")));                                                                 
                                                                     
    setWhite( Color.decode( props.getProperty( "Laflf.w")));                                                                 
    setBlack( Color.decode( props.getProperty( "Laflf.b")));                                                                 
                                                                         
    setMenuOpacity( Integer.parseInt( props.getProperty( "Laflf.menuOpacity")));                                                                 
    setFrameOpacity( Integer.parseInt( props.getProperty( "Laflf.frameOpacity")));                                                                 

    if ( props.getProperty( "Laflf.font") != null ) {
      setFont( Font.decode( props.getProperty( "Laflf.font")));
    }
  }
  
  /**
   * Este constructor recibe por parametro el color que se desea utilizar como color principal de "fondo".
   * Es el color que se usara como fondo de los botones, dialogos, menus... El resto de los colores de fondo
   * se calculan oscureciendo este en diversa medida. 
   * @param base Color el color de fondo.
   */
  public LafTheme( Color base) {
    super();
    
    setPrimary( base);
  }
  
  /**
   * Este constructor recibe por parametro los colores que se desea utilizar.
   * Base es el color que se usara como fondo de los botones, dialogos, menus... y prim es el color que se usara para
   * los objetos seleccionados. En palabras de Sun, Prim es el color que da "personalidad" al tema...
   * El resto de los colores  se calculan oscureciendo estos en diversa medida. 
   * @param prim Color el color a usar en las selecciones.
   * @param base Color el color de fondo.
   */
  public LafTheme( Color prim, Color sec) {
    super();
    
    setPrimary( prim);
    setSecondary( sec);
  }

  public String getName() { 
    return "Laf Theme"; 
  }


  protected ColorUIResource getPrimary1() { 
    return primary1; 
  }
  protected ColorUIResource getPrimary2() { 
    return primary2; 
  }
  protected ColorUIResource getPrimary3() { 
    return primary3; 
  }

  protected ColorUIResource getSecondary1() { 
    return secondary1; 
  }
  protected ColorUIResource getSecondary2() { 
    return secondary2; 
  }
  protected ColorUIResource getSecondary3() { 
    return secondary3; 
  }

  protected ColorUIResource getBlack() { 
    return black; 
  }
  
  protected ColorUIResource getWhite() { 
    return white; 
  }
  
  public void setPrimary( Color selection) {
    int r = selection.getRed();
    int g = selection.getGreen();
    int b = selection.getBlue();
    
    primary1 = new ColorUIResource( new Color( (r>20 ? r-20 : 0), (g>20 ? g-20 : 0), (b>20 ? b-20 : 0)));
    primary2 = new ColorUIResource( new Color( (r>10 ? r-10 : 0), (g>10 ? g-10 : 0), (b>10 ? b-10 : 0)));
    primary3 = new ColorUIResource( selection);
  }
  
  public void setSecondary( Color background) {
    int r = background.getRed();
    int g = background.getGreen();
    int b = background.getBlue();
    
    secondary1 = new ColorUIResource( new Color( (r>20 ? r-20 : 0), (g>20 ? g-20 : 0), (b>20 ? b-20 : 0)));
    secondary2 = new ColorUIResource( new Color( (r>10 ? r-10 : 0), (g>10 ? g-10 : 0), (b>10 ? b-10 : 0)));
    secondary3 = new ColorUIResource( background);
  }
  
  public void setPrimary1( Color col) { 
    primary1 = new ColorUIResource( col); 
  }
  public void setPrimary2( Color col) { 
    primary2 = new ColorUIResource( col); 
  }
  public void setPrimary3( Color col) { 
    primary3 = new ColorUIResource( col); 
  }

  public void setSecondary1( Color col) { 
    secondary1 = new ColorUIResource( col); 
  }
  public void setSecondary2( Color col) { 
    secondary2 = new ColorUIResource( col); 
  }
  public void setSecondary3( Color col) { 
    secondary3 = new ColorUIResource( col); 
  }
  
  public void setBlack( Color col) { 
    black = new ColorUIResource( col); 
  }
  public void setWhite( Color col) { 
    white = new ColorUIResource( col); 
  }
  
  public void setOpacity( int val) {
    setMenuOpacity( val);
  }
  
  public int getOpacity() {
    return getMenuOpacity();
  }
  
  public void setMenuOpacity( int val) {
    if ( val < 0 || val > 255 ) throw new NumberFormatException( "MenuOpacity out of range [0,255]: " + val);
    opacidadMenu = val;
  }
  
  public int getMenuOpacity() {
    return opacidadMenu;
  }
  
  public void setFrameOpacity( int val) {
    if ( val < 0 || val > 255 ) throw new NumberFormatException( "MenuOpacity out of range [0,255]: " + val);
    opacidadFrame = val;
  }
  
  public int getFrameOpacity() {
    return opacidadFrame;
  }
  
  public void setFont( Font ff) {
    font = new FontUIResource( ff);
    boldFont = new FontUIResource( ff.deriveFont( Font.BOLD));
  }
  
  public FontUIResource	getControlTextFont() {
  	return font;
  }
  
  public FontUIResource	getMenuTextFont() {
  	return font;
  }
  
  public FontUIResource getSubTextFont() {
  	return font;
  } 
           	
  public FontUIResource	getSystemTextFont()  {
  	return boldFont;
  }
           	
  public FontUIResource	getUserTextFont()  {
  	return font;
  }
           	
  public FontUIResource	getWindowTitleFont() {
  	return boldFont;
  }
  
  public String toString() {
    StringBuffer cad = new StringBuffer();
    
    cad.append( "Laflf.p1=" + encode( primary1) + "\n");
    cad.append( "Laflf.p2=" + encode( primary2) + "\n");
    cad.append( "Laflf.p3=" + encode( primary3) + "\n");
    cad.append( "Laflf.s1=" + encode( secondary1) + "\n");
    cad.append( "Laflf.s2=" + encode( secondary2) + "\n");
    cad.append( "Laflf.s3=" + encode( secondary3) + "\n");
    
    cad.append( "Laflf.w=" + encode( white) + "\n");
    cad.append( "Laflf.b=" + encode( black) + "\n");
    cad.append( "Laflf.menuOpacity=" + opacidadMenu + "\n");
    cad.append( "Laflf.frameOpacity=" + opacidadFrame + "\n");
    
    cad.append( "Laflf.font=" + encode( font) + "\n");
    
    return cad.toString();
  }
  
  protected String encode( Font ff) {
    StringBuilder res = new StringBuilder();
    
    res.append( ff.getName() + "-");
    
    if ( ff.isPlain() ) {
      res.append( "PLAIN-");
    }
    else if ( ff.isBold() && ff.isItalic() ) {
      res.append( "BOLDITALIC-");
    }
    else if ( ff.isBold() ) {
      res.append( "BOLD-");
    }
    else if ( ff.isItalic() ) {
      res.append( "ITALIC-");
    }
    
    res.append( ff.getSize());
    
    return res.toString();
  }
  
  protected String encode( Color col) {
    String r = Integer.toHexString( col.getRed()).toUpperCase();
    String g = Integer.toHexString( col.getGreen()).toUpperCase();
    String b = Integer.toHexString( col.getBlue()).toUpperCase();
    
    
    return "#" + ( r.length() == 1 ? "0" + r : r )
               + ( g.length() == 1 ? "0" + g : g )
               + ( b.length() == 1 ? "0" + b : b );
  }
}
