package pl.pkosmowski.laf.clear;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalBorders;

public class LafBorders extends MetalBorders {

    private static Border butBorder;
    private static Border popupMenuBorder;
    private static Border rolloverButtonBorder;
    private static Border scrollPaneBorder;
    private static Border internalFrameBorder;
    private static Border menuBarBorder;
    private static Border toolBarBorder;
    private static Border cellFocusBorder;
    private static Border genBorder;
    private static Border genEmptyBorder;
    private static Border genThinBorder;
    private static Border genMenuBorder;
    private static Border genTextFieldBorder;
    private static Border genComboEditorBorder;
    private static Border genComboButtonBorder;
    private static Border genToolTipBorder;

    public static Border getCellFocusBorder() {
        if (cellFocusBorder == null) {
            cellFocusBorder = new LafCellFocusBorder();
        }
        return cellFocusBorder;
    }

    public static Border getToolTipBorder() {
        if (genToolTipBorder == null) {
            genToolTipBorder = new LafToolTipBorder();
        }
        return genToolTipBorder;
    }

    public static Border getInternalFrameBorder() {
        if (internalFrameBorder == null) {
            internalFrameBorder = new LafInternalFrameBorder();
        }
        return internalFrameBorder;
    }

    public static Border getPopupMenuBorder() {
        if (popupMenuBorder == null) {
            popupMenuBorder = new LafPopupMenuBorder();
        }
        return popupMenuBorder;
    }

    public static Border getButtonBorder() {
        if (butBorder == null) {
            butBorder = new BorderUIResource.CompoundBorderUIResource(new LafBorders.LafButtonBorder(),
                    new BasicBorders.MarginBorder());
        }
        return butBorder;
    }

    public static Border getRolloverButtonBorder() {
        if (rolloverButtonBorder == null) {
            rolloverButtonBorder = new LafRolloverButtonBorder();
        }
        return rolloverButtonBorder;
    }

    public static Border getScrollPaneBorder() {
        if (scrollPaneBorder == null) {
            scrollPaneBorder = new LafScrollPaneBorder();
        }
        return scrollPaneBorder;
    }

    public static Border getMenuBarBorder() {
        if (menuBarBorder == null) {
            menuBarBorder = new LafMenuBarBorder();
        }
        return menuBarBorder;
    }

    public static Border getToolBarBorder() {
        if (toolBarBorder == null) {
            toolBarBorder = new LafToolBarBorder();
        }
        return toolBarBorder;
    }

    public static Border getGenMenuBorder() {
        if (genMenuBorder == null) {
            genMenuBorder = new LafMenuBorder();
        }
        return genMenuBorder;
    }

    public static Border getComboEditorBorder() {
        if (genComboEditorBorder == null) {
            genComboEditorBorder = new LafComboEditorBorder();
        }
        return genComboEditorBorder;
    }

    public static Border getComboButtonBorder() {
        if (genComboButtonBorder == null) {
            genComboButtonBorder = new LafComboButtonBorder();
        }
        return genComboButtonBorder;
    }

    public static Border getGenBorder() {
        if (genBorder == null) {
            genBorder = new LafGenBorder();
        }
        return genBorder;
    }

    public static Border getEmptyGenBorder() {
        if (genEmptyBorder == null) {
            genEmptyBorder = new LafEmptyGenBorder();
        }
        return genEmptyBorder;
    }

    public static Border getThinGenBorder() {
        if (genThinBorder == null) {
            genThinBorder = new LafThinGenBorder();
        }
        return genThinBorder;
    }

    public static Border getTextFieldBorder() {
        if (genTextFieldBorder == null) {
            genTextFieldBorder = new LafTextFieldBorder();
        }
        return genTextFieldBorder;
    }

    public static class LafCellFocusBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = -7363292672160449136L;

        protected static Insets borderInsets = new Insets(1, 1, 1, 1);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Color col = LafUtils.getColorTercio(LafLookAndFeel.getControlTextColor(),
                    LafLookAndFeel.getFocusColor());
            g.setColor(col);
            g.drawRect(x, y, w - 1, h - 1);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafButtonBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = -2083885266582056467L;

        protected static Insets borderInsets = new Insets(0, 0, 0, 0);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            if (!((AbstractButton) c).isBorderPainted()) {
                return;
            }

            g.translate(x, y);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setColor(LafLookAndFeel.getControlDarkShadow());
            g2D.drawRoundRect(0, 0, w - 1, h - 1, 8, 8);

            if (c instanceof JButton) {
                JButton button = (JButton) c;
                //ButtonModel model = button.getModel();

                if (button.isDefaultButton()) {
                    g2D.setColor(LafLookAndFeel.getControlDarkShadow().darker());
                    g2D.drawRoundRect(1, 1, w - 3, h - 3, 7, 7);
                }
                /*else if ( model.isPressed() && model.isArmed() ) {
                 g.translate( x, y);
                 g.setColor( LafLookAndFeel.getControlDarkShadow() );
                 g.drawRoundRect( 0,0, w-1,h-1, 8,8);
                 }*/
            }

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafPopupMenuBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = -2083885266582056468L;

        protected static Insets borderInsets = new Insets(1, 1, 5, 5);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.translate(x, y);

            g.setColor(LafLookAndFeel.getControlDarkShadow());
            g.drawRect(0, 0, w - 5, h - 5);

            Icon icono = UIManager.getIcon("BorderPopupMenu.SombraEsqIcon");
            icono.paintIcon(c, g, w - 5, h - 5);

            icono = UIManager.getIcon("BorderPopupMenu.SombraUpIcon");
            icono.paintIcon(c, g, w - 5, 0);

            icono = UIManager.getIcon("BorderPopupMenu.SombraIzqIcon");
            icono.paintIcon(c, g, 0, h - 5);

            icono = UIManager.getIcon("BorderPopupMenu.SombraBajIcon");
            g.drawImage(((ImageIcon) icono).getImage(), 5, h - 5, w - 10, icono.getIconHeight(), null);

            icono = UIManager.getIcon("BorderPopupMenu.SombraDerIcon");
            g.drawImage(((ImageIcon) icono).getImage(), w - 5, 5, icono.getIconWidth(), h - 10, null);

            g.translate(-x, -y);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafToolTipBorder extends LafPopupMenuBorder implements UIResource {

        private static final long serialVersionUID = -7253367634568230481L;
    }

    public static class LafRolloverButtonBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = -2083885266582056469L;

        protected static Insets borderInsets = new Insets(3, 3, 3, 3);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            if (!((AbstractButton) c).isBorderPainted()) {
                return;
            }
            ButtonModel model = ((AbstractButton) c).getModel();

            if (model.isRollover()) { //&& !( model.isPressed() && !model.isArmed() ) ) {
                g.setColor(LafLookAndFeel.getControlDarkShadow());
                g.drawRoundRect(0, 0, w - 1, h - 1, 8, 8);

                RoundRectangle2D.Float boton = new RoundRectangle2D.Float();
                boton.x = 0;
                boton.y = 0;
                boton.width = c.getWidth();
                boton.height = c.getHeight();
                boton.arcwidth = 8;
                boton.archeight = 8;

                GradientPaint grad = null;
                if (model.isPressed()) {
                    grad = new GradientPaint(0, 0, LafUtils.getSombra(),
                            0, c.getHeight() / 2, LafUtils.getBrillo());
                } else {
                    grad = new GradientPaint(0, 0, LafUtils.getBrillo(),
                            0, c.getHeight(), LafUtils.getSombra());
                }

                Graphics2D g2D = (Graphics2D) g;

                g2D.setPaint(grad);
                g2D.fill(boton);
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafInternalFrameBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = -4691959764241705857L;

        private static final int grosor = 3;

        protected static Insets ins = new Insets(0, grosor, 5 + grosor, 5 + grosor);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.translate(x, y);

            Graphics2D g2D = (Graphics2D) g.create();

            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, LafUtils.getFrameOpacityFloat()));

            // Elegimos el color del borde
            Color colIzq, colDer;
            GradientPaint grad;
            if (((JInternalFrame) c).isSelected()) {
                grad = new GradientPaint(0, 0, LafLookAndFeel.getPrimaryControlDarkShadow(),
                        w, 0, LafLookAndFeel.getPrimaryControl());
                colIzq = LafLookAndFeel.getPrimaryControlDarkShadow();
                colDer = LafLookAndFeel.getPrimaryControl();
            } else {
                grad = new GradientPaint(0, 0, LafLookAndFeel.getControl(),
                        w, 0, LafLookAndFeel.getControlDarkShadow());
                colIzq = LafLookAndFeel.getControl();
                colDer = LafLookAndFeel.getControlDarkShadow();
            }

            g2D.setColor(colIzq);
            g2D.fillRect(0, 0, grosor, h - ins.bottom);

            g2D.setPaint(grad);
            g2D.fillRect(0, h - ins.bottom, w - ins.right + grosor, grosor);

            g2D.setColor(colDer);
            g2D.fillRect(w - ins.right, 0, grosor, h - ins.bottom);

            g2D.dispose();

            g.setColor(LafLookAndFeel.getControlDarkShadow());
            g.drawRect(0, 0, w - 5, h - 5);

            Icon icono = UIManager.getIcon("BorderPopupMenu.SombraEsqIcon");
            icono.paintIcon(c, g, w - 5, h - 5);

            icono = UIManager.getIcon("BorderPopupMenu.SombraUpIcon");
            icono.paintIcon(c, g, w - 5, 0);

            icono = UIManager.getIcon("BorderPopupMenu.SombraIzqIcon");
            icono.paintIcon(c, g, 0, h - 5);

            icono = UIManager.getIcon("BorderPopupMenu.SombraBajIcon");
            g.drawImage(((ImageIcon) icono).getImage(), 5, h - 5, w - 10, icono.getIconHeight(), null);

            icono = UIManager.getIcon("BorderPopupMenu.SombraDerIcon");
            g.drawImage(((ImageIcon) icono).getImage(), w - 5, 5, icono.getIconWidth(), h - 10, null);

            g.translate(-x, -y);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafMenuBarBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = 116001977502172752L;

        protected static Insets ins = new Insets(0, 2, 0, 10);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            /*
             Icon icono = UIManager.getIcon( "BordeGenSup");
             y += height-icono.getIconHeight();
             g.drawImage( ((ImageIcon)icono).getImage(), 0,y, width, icono.getIconHeight(), null);
             */
            g.setColor(LafUtils.getSombra());
            g.drawLine(0, height - 2, width, height - 2);
            g.setColor(LafUtils.getBrillo());
            g.drawLine(0, height - 1, width, height - 1);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafToolBarBorder extends LafMenuBarBorder implements SwingConstants, UIResource {

        private static final long serialVersionUID = 116002347502172752L;

        private static final int bumpWidth = 14;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Icon icon = null;
            int desp = 0;

            if (((JToolBar) c).isFloatable()) {
                if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                    icon = UIManager.getIcon("ScrollBar.verticalThumbIconImage");
                    desp = icon.getIconHeight();

                    for (int i = 0; i < 5; i++) {
                        icon.paintIcon(c, g, x + 1, y + 1 + (desp * i));
                    }
                } else {
                    icon = UIManager.getIcon("ScrollBar.horizontalThumbIconImage");
                    desp = icon.getIconWidth();

                    for (int i = 0; i < 5; i++) {
                        icon.paintIcon(c, g, x + 1 + (desp * i), y + 1);
                    }
                }
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new Insets(0, 0, 0, 0));
        }

        @Override
        public Insets getBorderInsets(Component c, Insets ins) {
            ins.top = ins.left = ins.bottom = ins.right = 3;

            if (((JToolBar) c).isFloatable()) {
                if (((JToolBar) c).getOrientation() == HORIZONTAL) {
                    if (c.getComponentOrientation().isLeftToRight()) {
                        ins.left += bumpWidth;
                    } else {
                        ins.right += bumpWidth;
                    }
                } else {// vertical
                    ins.top += bumpWidth;
                }
            }

            Insets margin = ((JToolBar) c).getMargin();

            if (margin != null) {
                ins.left += margin.left;
                ins.top += margin.top;
                ins.right += margin.right;
                ins.bottom += margin.bottom;
            }

            return ins;
        }
    }

    public static class LafGenBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = 116001977502172752L;

        protected static Insets ins = new Insets(3, 3, 2, 2);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            int wl = width - 8;
            int hl = height - 8;

            ImageIcon icono = (ImageIcon) UIManager.getIcon("BordeGenSup");
            // Esto esta aqui porque cuando se cambia de look and feel no siempre se cambia
            // el borde de los textfield, siguen teniendo el Lafborder y cuando van a cargar
            // el icono salta una nullpointerexception porque no lo encuentra 
            if (icono == null) {
                return;
            }

            g.translate(x, y);

            g.drawImage(icono.getImage(), 4, 0, wl, icono.getIconHeight(), null);

            icono = (ImageIcon) UIManager.getIcon("BordeGenInf");
            g.drawImage(icono.getImage(), 4, height - icono.getIconHeight(), wl, icono.getIconHeight(), null);

            icono = (ImageIcon) UIManager.getIcon("BordeGenDer");
            g.drawImage(icono.getImage(), width - icono.getIconWidth(), 4, icono.getIconWidth(), hl, null);

            icono = (ImageIcon) UIManager.getIcon("BordeGenIzq");
            g.drawImage(icono.getImage(), 0, 4, icono.getIconWidth(), hl, null);

            icono = (ImageIcon) UIManager.getIcon("BordeGenSupIzq");
            icono.paintIcon(c, g, 0, 0);
            icono = (ImageIcon) UIManager.getIcon("BordeGenInfIzq");
            icono.paintIcon(c, g, 0, height - icono.getIconHeight());
            icono = (ImageIcon) UIManager.getIcon("BordeGenSupDer");
            icono.paintIcon(c, g, width - icono.getIconWidth(), 0);
            icono = (ImageIcon) UIManager.getIcon("BordeGenInfDer");
            icono.paintIcon(c, g, width - icono.getIconWidth(), height - icono.getIconHeight());

            g.translate(-x, -y);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafEmptyGenBorder extends LafGenBorder implements UIResource {

        private static final long serialVersionUID = 116002377502172752L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        }
    }

    public static class LafThinGenBorder extends LafGenBorder implements UIResource {

        private static final long serialVersionUID = 116002982734987752L;

        protected static Insets ins = new Insets(1, 1, 1, 1);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(LafLookAndFeel.getControlDarkShadow());
            g.drawRect(x, y, width - 1, height - 1);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafTextFieldBorder extends LafGenBorder implements UIResource {

        private static final long serialVersionUID = -7253364063167310481L;

        protected static Insets ins = new Insets(5, 6, 5, 6);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            super.paintBorder(c, g, x + 2, y + 2, width - 4, height - 4);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafMenuBorder extends LafGenBorder implements UIResource {

        private static final long serialVersionUID = -7253364063167610481L;

        protected static Insets ins = new Insets(3, 3, 3, 3);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            JMenuItem b = (JMenuItem) c;
            ButtonModel model = b.getModel();

            if (model.isArmed() || model.isSelected()) {
                super.paintBorder(c, g, x, y, width, height - 2);
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafComboEditorBorder extends LafTextFieldBorder implements UIResource {

        private static final long serialVersionUID = -7253364063167610483L;
    }

    public static class LafComboButtonBorder extends LafButtonBorder {

        private static final long serialVersionUID = -7253364063167610483L;

        protected static Insets ins = new Insets(2, 2, 2, 2);

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            super.paintBorder(c, g, x + 2, y + 2, width - 4, height - 4);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return ins;
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            Insets tmpIns = getBorderInsets(c);

            insets.top = tmpIns.top;
            insets.left = tmpIns.left;
            insets.bottom = tmpIns.bottom;
            insets.right = tmpIns.right;

            return insets;
        }
    }

    public static class LafScrollPaneBorder extends AbstractBorder implements UIResource {

        private static final long serialVersionUID = -6416636693876853556L;

    }
}
