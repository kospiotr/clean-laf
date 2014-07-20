package pl.pkosmowski.laf.clear;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.Element;
import javax.swing.text.View;


public class LafPasswordFieldUI extends LafTextFieldUI {
  public LafPasswordFieldUI( JComponent c) {
    super( c);
  }
  
	public static ComponentUI createUI( JComponent c) {
    return new LafPasswordFieldUI( c);
  }
  
  protected String getPropertyPrefix() {
    return "PasswordField";
  }


  public View create( Element elem) {
    return new LafPasswordView( elem);
  }
}
