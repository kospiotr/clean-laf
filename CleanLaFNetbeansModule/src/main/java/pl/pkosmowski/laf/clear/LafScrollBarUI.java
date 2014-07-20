package pl.pkosmowski.laf.clear;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class LafScrollBarUI extends MetalScrollBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafScrollBarUI();
    }
    private boolean clicked;
    private boolean rollOver;

    @Override
    protected TrackListener createTrackListener() {
        return new MiML(this);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        decreaseButton = new LafScrollButton(orientation, scrollBarWidth, isFreeStanding);
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        increaseButton = new LafScrollButton(orientation, scrollBarWidth, isFreeStanding);
        return increaseButton;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Color thumbColor = UIManager.getColor("ScrollBar.thumb");
        Color thumbShadow = UIManager.getColor("ScrollBar.thumbShadow");

        g.translate(thumbBounds.x, thumbBounds.y);

        g.setColor(thumbColor);
        g.fillRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 1);

        g.setColor((rollOver ? thumbShadow.darker() : thumbShadow));
        g.drawRect(0, 0, thumbBounds.width - 1, thumbBounds.height - 1);

        Icon icDecor = null;
        if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            icDecor = UIManager.getIcon("ScrollBar.horizontalThumbIconImage");
        } else {
            icDecor = UIManager.getIcon("ScrollBar.verticalThumbIconImage");
        }

        int w = icDecor.getIconWidth();
        int h = icDecor.getIconHeight();
        int x = (thumbBounds.width - w) / 2;
        int y = (thumbBounds.height - h) / 2;

        if (((scrollbar.getOrientation() == JScrollBar.HORIZONTAL) && (thumbBounds.width >= w))
                || ((scrollbar.getOrientation() == JScrollBar.VERTICAL) && (thumbBounds.height >= h))) {
            icDecor.paintIcon(c, g, x, y);
        }

        g.translate(-thumbBounds.x, -thumbBounds.y);

        Graphics2D g2D = (Graphics2D) g;
        GradientPaint grad = null;

        Color colA, colB;
        if (clicked) {
            colA = LafUtils.getSombra();
            colB = LafUtils.getBrillo();
        } else {
            colA = LafUtils.getBrillo();
            colB = LafUtils.getSombra();
        }

        if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            grad = new GradientPaint(thumbBounds.x, thumbBounds.y, colA,
                    thumbBounds.x, thumbBounds.height, colB);
        } else {
            grad = new GradientPaint(thumbBounds.x, thumbBounds.y, colA,
                    thumbBounds.width, thumbBounds.y, colB);
            /*
             ImageIcon icSombra = (ImageIcon)UIManager.getIcon( "BordeGenSup");
             g.drawImage( icSombra.getImage(), thumbBounds.x,thumbBounds.y+thumbBounds.height,
             thumbBounds.width, icSombra.getIconHeight(), null);
             */
        }

        g2D.setPaint(grad);
        g2D.fill(thumbBounds);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2D = (Graphics2D) g;
        GradientPaint grad = null;

        if (scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
            grad = new GradientPaint(trackBounds.x, trackBounds.y, LafUtils.getSombra(),
                    trackBounds.x, trackBounds.y + trackBounds.height, LafUtils.getBrillo());
        } else {
            grad = new GradientPaint(trackBounds.x, trackBounds.y, LafUtils.getSombra(),
                    trackBounds.x + trackBounds.width, trackBounds.y, LafUtils.getBrillo());
        }

        g2D.setPaint(grad);
        g2D.fill(trackBounds);
    }

/////////////////////////////////////
    public class MiML extends MetalScrollBarUI.TrackListener {

        LafScrollBarUI papi;

        public MiML(LafScrollBarUI papi) {
            this.papi = papi;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);

            papi.rollOver = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);

            papi.rollOver = false;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            papi.clicked = true;
            scrollbar.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);

            papi.clicked = false;
            scrollbar.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);

            if (papi.rollOver && !thumbRect.contains(e.getX(), e.getY())) {
                rollOver = false;
                scrollbar.repaint();
            } else if (!papi.rollOver && thumbRect.contains(e.getX(), e.getY())) {
                papi.rollOver = true;
                scrollbar.repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);

            if (papi.rollOver && !thumbRect.contains(e.getX(), e.getY())) {
                rollOver = false;
                scrollbar.repaint();
            } else if (!papi.rollOver && thumbRect.contains(e.getX(), e.getY())) {
                papi.rollOver = true;
                scrollbar.repaint();
            }
        }
    }
}
