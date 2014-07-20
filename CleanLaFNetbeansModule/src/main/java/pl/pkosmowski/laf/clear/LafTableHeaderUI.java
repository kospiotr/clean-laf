package pl.pkosmowski.laf.clear;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;

public class LafTableHeaderUI extends BasicTableHeaderUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafTableHeaderUI();
    }

    public LafTableHeaderUI() {
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        g.translate(3, 0);

        super.paint(g, c);

        g.translate(-3, 0);

        if (!c.isOpaque()) {
            return;
        }

        Graphics2D g2D = (Graphics2D) g;

        GradientPaint grad = new GradientPaint(0, 0, LafUtils.getBrillo(),
                0, c.getHeight(), LafUtils.getSombra());

        g2D.setPaint(grad);
        g2D.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
