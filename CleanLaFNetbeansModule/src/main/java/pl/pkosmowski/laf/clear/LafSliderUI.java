package pl.pkosmowski.laf.clear;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalSliderUI;

public class LafSliderUI extends MetalSliderUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafSliderUI();
    }

    public LafSliderUI() {
        super();
    }

    @Override
    protected int getThumbOverhang() {
        return super.getThumbOverhang() + (slider.getOrientation() == JSlider.HORIZONTAL ? 0 : 2);
    }
}
