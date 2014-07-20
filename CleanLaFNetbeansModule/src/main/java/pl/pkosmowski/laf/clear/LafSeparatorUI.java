package pl.pkosmowski.laf.clear;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

public class LafSeparatorUI extends BasicSeparatorUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafSeparatorUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Dimension s = c.getSize();

        if (((JSeparator) c).getOrientation() == JSeparator.VERTICAL) {
            g.setColor(LafUtils.getSombra());
            g.drawLine(0, 0, 0, s.height);

            g.setColor(LafUtils.getBrillo());
            g.drawLine(1, 0, 1, s.height);
        } else // HORIZONTAL
        {
            g.setColor(LafUtils.getSombra());
            g.drawLine(0, 0, s.width, 0);

            g.setColor(LafUtils.getBrillo());
            g.drawLine(0, 1, s.width, 1);
        }
    }

}
