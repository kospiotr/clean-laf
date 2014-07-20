package pl.pkosmowski.laf.clear;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class LafSpinnerUI extends BasicSpinnerUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafSpinnerUI();
    }
    protected boolean oldOpaque;

    @Override
    protected void installDefaults() {
        super.installDefaults();

        oldOpaque = spinner.isOpaque();
        spinner.setOpaque(false);
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();

        spinner.setOpaque(oldOpaque);
    }

    @Override
    protected Component createPreviousButton() {
        Component c = createArrowButton(SwingConstants.SOUTH);
        c.setName("Spinner.previousButton");
        installPreviousButtonListeners(c);

        return c;
    }

    @Override
    protected Component createNextButton() {
        Component c = createArrowButton(SwingConstants.NORTH);
        c.setName("Spinner.nextButton");
        installNextButtonListeners(c);

        return c;
    }

    private Component createArrowButton(int direction) {
        JButton b = new LafArrowButton(direction);

        b.setInheritsPopupMenu(true);

        return b;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        g.setColor(c.getBackground());
        g.fillRect(2, 3, c.getWidth() - 4, c.getHeight() - 6);
        g.drawLine(3, 2, c.getWidth() - 4, 2);
        g.drawLine(3, c.getHeight() - 3, c.getWidth() - 4, c.getHeight() - 3);

        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField text = ((JSpinner.DefaultEditor) editor).getTextField();
            text.setBackground(c.getBackground());
        }
    }

    /**
     * *************************************************************************************
     */
    class LafArrowButton extends JButton {

        private static final long serialVersionUID = 3031842923932443184L;

        private final int dir;

        LafArrowButton(int direction) {
            super();

            setRequestFocusEnabled(false);

            dir = direction;
            if (direction == SwingConstants.NORTH) {
                setIcon(UIManager.getIcon("Spinner.nextIcon"));
            } else {
                setIcon(UIManager.getIcon("Spinner.previousIcon"));
            }

            setOpaque(false);
        }

    // Esto esta aqui gracias a Christopher J. Huey, que no solo hizo pruebas y mas pruebas, hasta un nivel al que ni yo
        // ni nadie habia llegado, ��si no que encima el tio va y manda parches!! La verdad es que no se como agradecer 
        // estas cosas...
        // This is a fix from Christopher J. Huey 
        @Override
        public boolean isFocusTraversable() {
            return false;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(15, getIcon().getIconHeight());
        }

        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        @Override
        public void paint(Graphics g) {
            Icon icon = getIcon();

            int w = getWidth() - 1;
            int h = getHeight() - 1;

            int x = (w - icon.getIconWidth()) / 2;
            int y = (h - icon.getIconHeight()) / 2;

            int yf = y;

            Border bb = ((JComponent) getParent()).getBorder();
            if (bb != null) {
                if (dir == SwingConstants.NORTH) {
                    y += bb.getBorderInsets(this).top / 2;
                    yf = bb.getBorderInsets(this).top - 2;
                } else {
                    yf = getHeight() - bb.getBorderInsets(this).bottom;
                }

                w -= 3;
            }

            icon.paintIcon(this, g, x, y);

            if (dir == SwingConstants.NORTH) {
                g.setColor(LafUtils.getBrillo());
                g.drawLine(1, yf, 1, h);

                g.setColor(LafUtils.getSombra());
                g.drawLine(1, h, w, h);
                g.drawLine(0, yf, 0, h);
            } else {
                g.setColor(LafUtils.getBrillo());
                g.drawLine(1, 0, w, 0);
                g.drawLine(1, 0, 1, yf);

                g.setColor(LafUtils.getSombra());
                g.drawLine(0, 0, 0, yf);
            }
        }
    }
}
