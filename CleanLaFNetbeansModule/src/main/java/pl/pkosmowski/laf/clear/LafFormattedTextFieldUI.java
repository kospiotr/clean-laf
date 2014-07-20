package pl.pkosmowski.laf.clear;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;


 public class LafFormattedTextFieldUI extends LafTextFieldUI {
  public LafFormattedTextFieldUI( JComponent c) {
    super( c);
  }
  
	public static ComponentUI createUI( JComponent c) {
    return new LafFormattedTextFieldUI( c);
  }
}
