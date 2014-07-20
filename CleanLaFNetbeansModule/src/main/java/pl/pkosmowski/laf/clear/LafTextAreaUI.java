package pl.pkosmowski.laf.clear;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nilo J. Gonzalez 2007
 */
public class LafTextAreaUI extends BasicTextAreaUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafTextAreaUI(c);
    }
    private boolean rollover = false;
    private boolean focus = false;
    private MiTextML miTextML;

    public LafTextAreaUI(JComponent c) {
        super();
    }

    @Override
    protected void installListeners() {
        super.installListeners();

        miTextML = new MiTextML();
        getComponent().addMouseListener(miTextML);
        getComponent().addFocusListener(miTextML);
    }

    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();

        getComponent().removeMouseListener(miTextML);
        getComponent().removeFocusListener(miTextML);
    }

    public boolean isFocus() {
        return focus;
    }

    public boolean isRollover() {
        return rollover;
    }

    @Override
    protected void paintBackground(Graphics g) {
        JTextComponent c = getComponent();
        Border bb = c.getBorder();
        if (bb != null && bb instanceof LafBorders.LafGenBorder) {
            g.setColor(getComponent().getBackground());

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 7, 7);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

            if (c.isEnabled() && c.isEditable()) {
                if (focus) {
                    LafUtils.paintFocus(g, 1, 1, c.getWidth() - 2, c.getHeight() - 2, 2, 2, LafLookAndFeel.getFocusColor());
                } else if (rollover) {
                    LafUtils.paintFocus(g, 1, 1, c.getWidth() - 2, c.getHeight() - 2, 2, 2, LafUtils.getColorAlfa(LafLookAndFeel.getFocusColor(), 150));
                }
            }
        } else {
            super.paintBackground(g);
        }
    }

  //////////////////////////
    class MiTextML extends MouseAdapter implements FocusListener {

        protected void refreshBorder() {
            if (getComponent().getParent() != null) {
                Component papi = getComponent();

                papi.getParent().repaint(papi.getX() - 5, papi.getY() - 5,
                        papi.getWidth() + 10, papi.getHeight() + 10);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            rollover = false;
            refreshBorder();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            rollover = true;
            refreshBorder();
        }

        @Override
        public void focusGained(FocusEvent e) {
            focus = true;
            refreshBorder();
        }

        @Override
        public void focusLost(FocusEvent e) {
            focus = false;
            refreshBorder();
        }
    }
}
