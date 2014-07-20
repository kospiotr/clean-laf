package pl.pkosmowski.laf.clear;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.event.*;

public class LafButtonUI extends MetalButtonUI {

    protected MiListener miml;
  //static private LafButtonUI ui;

    protected boolean oldOpaque;

    public static ComponentUI createUI(JComponent c) {
        return new LafButtonUI();
        /*if ( ui == null ) {
         ui = new LafButtonUI();
         }
    
         return ui;
         */
    }

    public void installDefaults(AbstractButton button) {
        super.installDefaults(button);

        button.setBorder(LafBorders.getButtonBorder());

        selectColor = LafLookAndFeel.getFocusColor();
    }

    public void unsinstallDefaults(AbstractButton button) {
        super.uninstallDefaults(button);

        button.setBorder(MetalBorders.getButtonBorder());
    }

    public void installListeners(AbstractButton b) {
        super.installListeners(b);

        miml = new MiListener(b);
        b.addMouseListener(miml);
        b.addPropertyChangeListener(miml);
        b.addFocusListener(miml);
    }

    protected void uninstallListeners(AbstractButton b) {
        b.removeMouseListener(miml);
        b.removePropertyChangeListener(miml);
        b.removeFocusListener(miml);
    }

    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        if (!oldOpaque) {
            return;
        }

        if (b.isContentAreaFilled()) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(LafUtils.getColorAlfa(selectColor, 100));
            RoundRectangle2D.Float boton = hazBoton(b);
            g2D.fill(boton);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }
    }

    protected void paintFocus(Graphics g, AbstractButton b,
            Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        if (!b.isFocusPainted() || !oldOpaque) {
            return;
        }
        if (b.getParent() instanceof JToolBar) {
            return;  // No se pinta el foco cuando estamos en una barra
        }

        LafUtils.paintFocus(g, 3, 3, b.getWidth() - 6, b.getHeight() - 6, 2, 2, LafLookAndFeel.getFocusColor());
    }

    public void update(Graphics g, JComponent c) {
        oldOpaque = c.isOpaque();

        if (c.getParent() instanceof JToolBar) {
            super.update(g, c);
        } else {
            c.setOpaque(false);
            super.update(g, c);
            c.setOpaque(oldOpaque);
        }
    }

    public void paint(Graphics g, JComponent c) {
        ButtonModel mod = ((AbstractButton) c).getModel();

        if (oldOpaque) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D.Float boton = hazBoton(c);

            // This line is a fix from Ross Judson
            g2D.clip(boton);

            g2D.setColor(LafLookAndFeel.getControl());
            g2D.fill(boton);

            if (c.getParent() instanceof JToolBar) {
                if (mod.isRollover() || mod.isPressed() || mod.isSelected()) {
                    c.setBorder(LafBorders.getGenBorder());
                } else {
                    c.setBorder(LafBorders.getEmptyGenBorder());
                }

                if (mod.isPressed() || mod.isSelected()) {
                    g2D.setColor(LafLookAndFeel.getFocusColor());
                    g2D.fill(boton);
                }
            } else {
                GradientPaint grad = null;

                if (mod.isPressed() || mod.isSelected()) {
                    grad = new GradientPaint(0, 0, LafUtils.getSombra(),
                            0, c.getHeight(), LafUtils.getBrillo());
                } else {
                    grad = new GradientPaint(0, 0, LafUtils.getBrillo(),
                            0, c.getHeight(), LafUtils.getSombra());
                }

                g2D.setPaint(grad);
                g2D.fill(boton);

                if (mod.isRollover()) {
                    g2D.setColor(LafUtils.getRolloverColor());
                    g2D.fill(boton);
                }
            }

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        super.paint(g, c);
    }

    private RoundRectangle2D.Float hazBoton(JComponent c) {
        RoundRectangle2D.Float boton = new RoundRectangle2D.Float();
        boton.x = 0;
        boton.y = 0;
        boton.width = c.getWidth();
        boton.height = c.getHeight();
        boton.arcwidth = 8;
        boton.archeight = 8;

        return boton;
    }

  /////////////////////////////////////
    public class MiListener extends MouseInputAdapter implements PropertyChangeListener, FocusListener {

        private AbstractButton papi;

        MiListener(AbstractButton b) {
            papi = b;
        }

        public void refresh() {
            if (papi != null && papi.getParent() != null) {
                papi.getParent().repaint(papi.getX() - 5, papi.getY() - 5,
                        papi.getWidth() + 10, papi.getHeight() + 10);
            }
        }

        public void mouseEntered(MouseEvent e) {
            papi.getModel().setRollover(true);
            refresh();
        }

        public void mouseExited(MouseEvent e) {
            papi.getModel().setRollover(false);
            refresh();
        }

        public void mousePressed(MouseEvent e) {
            papi.getModel().setRollover(false);
            refresh();
        }

        public void mouseReleased(MouseEvent e) {
            papi.getModel().setRollover(false);
            refresh();
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("enabled")) {
                refresh();
            }
        }

        public void focusGained(FocusEvent e) {
            refresh();
        }

        public void focusLost(FocusEvent e) {
            refresh();
        }
    }
}
