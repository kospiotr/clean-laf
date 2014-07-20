package pl.pkosmowski.laf.clear;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.plaf.metal.MetalButtonUI;

public class LafButtonUI extends MetalButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafButtonUI();
        /*if ( ui == null ) {
         ui = new LafButtonUI();
         }
        
         return ui;
         */
    }

    protected MiListener miml;
    //static private LafButtonUI ui;

    protected boolean oldOpaque;

    @Override
    public void installDefaults(AbstractButton button) {
        super.installDefaults(button);

        button.setBorder(LafBorders.getButtonBorder());

        selectColor = LafLookAndFeel.getFocusColor();
    }

    public void unsinstallDefaults(AbstractButton button) {
        super.uninstallDefaults(button);

        button.setBorder(MetalBorders.getButtonBorder());
    }

    @Override
    public void installListeners(AbstractButton b) {
        super.installListeners(b);

        miml = new MiListener(b);
        b.addMouseListener(miml);
        b.addPropertyChangeListener(miml);
        b.addFocusListener(miml);
    }

    @Override
    protected void uninstallListeners(AbstractButton b) {
        b.removeMouseListener(miml);
        b.removePropertyChangeListener(miml);
        b.removeFocusListener(miml);
    }

    @Override
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

    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        if (!b.isFocusPainted() || !oldOpaque) {
            return;
        }
        if (b.getParent() instanceof JToolBar) {
            return;  // No se pinta el foco cuando estamos en una barra
        }

        LafUtils.paintFocus(g, 3, 3, b.getWidth() - 6, b.getHeight() - 6, 2, 2, LafLookAndFeel.getFocusColor());
    }

    @Override
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

    @Override
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

        private final AbstractButton papi;

        MiListener(AbstractButton b) {
            papi = b;
        }

        public void refresh() {
            if (papi != null && papi.getParent() != null) {
                papi.getParent().repaint(papi.getX() - 5, papi.getY() - 5,
                        papi.getWidth() + 10, papi.getHeight() + 10);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            papi.getModel().setRollover(true);
            refresh();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            papi.getModel().setRollover(false);
            refresh();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            papi.getModel().setRollover(false);
            refresh();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            papi.getModel().setRollover(false);
            refresh();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("enabled")) {
                refresh();
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            refresh();
        }

        @Override
        public void focusLost(FocusEvent e) {
            refresh();
        }
    }
}
