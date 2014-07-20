package pl.pkosmowski.laf.clear;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class LafToolBarUI extends MetalToolBarUI {
  public LafToolBarUI() {
    super();
  }
  
	public static ComponentUI createUI( JComponent c) {
    return new LafToolBarUI();
  }
  
	protected Border createRolloverBorder() {
	  return LafBorders.getGenBorder();//.getRolloverButtonBorder();
  }
}
