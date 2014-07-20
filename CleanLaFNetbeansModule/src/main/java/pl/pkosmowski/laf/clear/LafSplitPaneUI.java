package pl.pkosmowski.laf.clear;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.metal.MetalSplitPaneUI;

public class LafSplitPaneUI extends MetalSplitPaneUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafSplitPaneUI();
    }

    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new LafSplitPaneDivider(this);
    }
}
