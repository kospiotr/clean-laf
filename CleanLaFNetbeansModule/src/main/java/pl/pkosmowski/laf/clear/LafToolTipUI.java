package pl.pkosmowski.laf.clear;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolTipUI;

public class LafToolTipUI extends MetalToolTipUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafToolTipUI(c);
    }
    protected JToolTip tooltip;

    public LafToolTipUI(JComponent c) {
        super();

        tooltip = (JToolTip) c;
        tooltip.setOpaque(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        int w = tooltip.getWidth();
        int h = tooltip.getHeight();

        Border bb = tooltip.getBorder();
        if (bb != null) {
            w -= bb.getBorderInsets(tooltip).right;
            h -= bb.getBorderInsets(tooltip).bottom;
        }

        g.setColor(tooltip.getBackground());
        g.fillRect(0, 0, w, h);

        super.paint(g, c);
    }

}
