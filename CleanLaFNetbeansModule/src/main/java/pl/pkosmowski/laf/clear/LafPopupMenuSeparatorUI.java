package pl.pkosmowski.laf.clear;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;

public class LafPopupMenuSeparatorUI extends MetalPopupMenuSeparatorUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafPopupMenuSeparatorUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Dimension s = c.getSize();

        g.setColor(LafUtils.getSombra());
        g.drawLine(1, 0, s.width - 1, 0);
        g.setColor(LafUtils.getBrillo());
        g.drawLine(1, 1, s.width - 1, 1);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(0, 2);
    }
}
