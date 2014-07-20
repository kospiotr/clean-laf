package pl.pkosmowski.laf.clear;

import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public class LafSplitPaneDivider extends BasicSplitPaneDivider {

    private static final long serialVersionUID = 1L;

    public LafSplitPaneDivider(BasicSplitPaneUI p) {
        super(p);
    }

    @Override
    protected JButton createRightOneTouchButton() {
        JButton b = new Boton(Boton.DER, super.splitPane, BasicSplitPaneDivider.ONE_TOUCH_SIZE);
        Boolean boo = ((Boolean) UIManager.get("SplitPane.oneTouchButtonsOpaque"));
        if (boo != null) {
            b.setOpaque(boo);
        }

        return b;
    }

    @Override
    protected JButton createLeftOneTouchButton() {
        JButton b = new Boton(Boton.IZQ, super.splitPane, BasicSplitPaneDivider.ONE_TOUCH_SIZE);
        Boolean boo = ((Boolean) UIManager.get("SplitPane.oneTouchButtonsOpaque"));
        if (boo != null) {
            b.setOpaque(boo);
        }

        return b;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;
        GradientPaint grad = null;
        if (super.splitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
            grad = new GradientPaint(0, 0, LafUtils.getBrillo(),
                    0, getHeight(), LafUtils.getSombra());
        } else {
            grad = new GradientPaint(0, 0, LafUtils.getBrillo(),
                    getWidth(), 0, LafUtils.getSombra());
        }

        g2D.setPaint(grad);
        g2D.fillRect(0, 0, getWidth(), getHeight());
    }

    protected class Boton extends JButton {

        private static final long serialVersionUID = 1L;

        public static final int IZQ = 0;
        public static final int DER = 1;

        private final JSplitPane splitPane;
        private final int ots;
        private int dir;

        Boton(int dir, JSplitPane sp, int ots) {
            this.dir = dir;
            splitPane = sp;
            this.ots = ots;

            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            setFocusPainted(false);
            setBorderPainted(false);
            setRequestFocusEnabled(false);
            setOpaque(false);
        }

        @Override
        public void setBorder(Border border) {
        }

        @Override
        public void paint(Graphics g) {
            if (splitPane != null) {
                int blocksize = Math.min(getDividerSize(), ots);

                g.setColor(LafLookAndFeel.getFocusColor());

                int[] xs = new int[3];
                int[] ys = new int[3];

                if (orientation == JSplitPane.VERTICAL_SPLIT && dir == DER) {
                    xs = new int[]{0, blocksize / 2, blocksize};
                    ys = new int[]{0, blocksize, 0};
                } else if (orientation == JSplitPane.VERTICAL_SPLIT && dir == IZQ) {
                    xs = new int[]{0, blocksize / 2, blocksize};
                    ys = new int[]{blocksize, 0, blocksize};
                } else if (orientation == JSplitPane.HORIZONTAL_SPLIT && dir == DER) {
                    xs = new int[]{0, 0, blocksize};
                    ys = new int[]{0, blocksize, blocksize / 2};
                } else if (orientation == JSplitPane.HORIZONTAL_SPLIT && dir == IZQ) {
                    //g.drawRect( blocksize-1,0, 2,blocksize);
                    xs = new int[]{0, blocksize, blocksize};
                    ys = new int[]{blocksize / 2, 0, blocksize};
                }

                g.fillPolygon(xs, ys, 3);
                g.setColor(LafLookAndFeel.getFocusColor().darker());
                g.drawPolygon(xs, ys, 3);
            }
        }

        @Override
        public boolean isFocusable() {
            return false;
        }
    }
}
