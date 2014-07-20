package pl.pkosmowski.laf.clear;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

public class LafTextFieldUI extends BasicTextFieldUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafTextFieldUI(c);
    }
    private boolean rollover = false;
    private boolean focus = false;
    private MiTextML miTextML;

    protected boolean oldOpaque, canijo;

    LafTextFieldUI(JComponent c) {
        super();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        oldOpaque = getComponent().isOpaque();
        getComponent().setOpaque(false);
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();

        getComponent().setOpaque(oldOpaque);
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
    protected void paintSafely(Graphics g) {
        paintFocus(g);

        paintTodo(g);

        super.paintSafely(g);
    }

    protected void paintTodo(Graphics g) {
        JTextComponent c = getComponent();

        Border bb = c.getBorder();

        if (bb != null && bb instanceof LafBorders.LafGenBorder) {
            Insets ins = LafBorders.getTextFieldBorder().getBorderInsets(c);

            // Si cabe todo, le ponemos un borde guay. Si no, pues le dejamos un borde cutrecillo
            if (c.getSize().height + 2 < (c.getFont().getSize() + ins.top + ins.bottom)) {
                c.setBorder(LafBorders.getThinGenBorder());
                canijo = true;
            } else {
                c.setBorder(LafBorders.getTextFieldBorder());
                canijo = false;
            }

            if (!c.isEditable() || !c.isEnabled()) {
                g.setColor(UIManager.getColor("TextField.inactiveBackground"));
            } else {
                g.setColor(c.getBackground());
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 7, 7);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        } else {
            super.paintBackground(g);
        }
    }

    @Override
    protected void paintBackground(Graphics g) {
    }

    protected void paintFocus(Graphics g) {
        JTextComponent c = getComponent();

        if (c.isEnabled() && c.isEditable() && !canijo) {
            if (focus) {
                LafUtils.paintFocus(g, 1, 1, c.getWidth() - 2, c.getHeight() - 2, 4, 4, 3, LafLookAndFeel.getFocusColor());
            } else if (rollover) {
                LafUtils.paintFocus(g, 1, 1, c.getWidth() - 2, c.getHeight() - 2, 4, 4, 3, LafUtils.getColorAlfa(LafLookAndFeel.getFocusColor(), 150));
            }
        }
    }

  //////////////////////////
    class MiTextML extends MouseAdapter implements FocusListener {

        protected void refresh() {
            if (getComponent().getParent() != null) {
                Component papi = getComponent();

                papi.getParent().repaint(papi.getX() - 5, papi.getY() - 5,
                        papi.getWidth() + 10, papi.getHeight() + 10);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            rollover = false;
            refresh();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            rollover = true;
            refresh();
        }

        @Override
        public void focusGained(FocusEvent e) {
            focus = true;
            refresh();
        }

        @Override
        public void focusLost(FocusEvent e) {
            focus = false;
            refresh();
        }
    }
}
