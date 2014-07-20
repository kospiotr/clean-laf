package pl.pkosmowski.laf.clear;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.plaf.metal.MetalRadioButtonUI;

public class LafRadioButtonUI extends MetalRadioButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafRadioButtonUI();
    }
    boolean oldOpaque;

    @Override
    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        oldOpaque = b.isOpaque();
        b.setOpaque(false);

        icon = LafIconFactory.getRadioButtonIcon();
    }

    @Override
    public void uninstallDefaults(AbstractButton b) {
        super.uninstallDefaults(b);

        b.setOpaque(oldOpaque);
        icon = MetalIconFactory.getRadioButtonIcon();
    }

    @Override
    protected void paintFocus(Graphics g, Rectangle t, Dimension d) {
        LafUtils.paintFocus(g, 1, 1, d.width - 2, d.height - 2, 8, 8, LafLookAndFeel.getFocusColor());
    }
}
