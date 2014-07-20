package pl.pkosmowski.laf.clear;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolBarUI;

public class LafToolBarUI extends MetalToolBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafToolBarUI();
    }

    public LafToolBarUI() {
        super();
    }

    @Override
    protected Border createRolloverBorder() {
        return LafBorders.getGenBorder();//.getRolloverButtonBorder();
    }
}
