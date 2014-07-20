package pl.pkosmowski.laf.clear;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.*;

public class LafSliderUI extends MetalSliderUI {
	public LafSliderUI() {
    super();
	}
	
	public static ComponentUI createUI( JComponent c) {
    return new LafSliderUI();
  }

  protected int getThumbOverhang() {
    return super.getThumbOverhang() + ( slider.getOrientation() == JSlider.HORIZONTAL ? 0 : 2 );
  }
}

