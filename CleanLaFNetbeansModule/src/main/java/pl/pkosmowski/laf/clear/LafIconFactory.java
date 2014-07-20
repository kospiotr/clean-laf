package pl.pkosmowski.laf.clear;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;

public class LafIconFactory implements Serializable {

    private static final long serialVersionUID = 7191199335114123414L;

    private static Icon checkBoxIcon;
    private static Icon radioButtonIcon;
    private static Icon checkBoxMenuItemIcon;
    private static Icon radioButtonMenuItemIcon;
    private static Icon sliderHorizIcon;
    private static Icon sliderVertIcon;
    private static Icon treeCollapsedIcon;
    private static Icon treeExpandedIcon;
    private static Icon bandaMenuItemIcon;
    private static Icon comboFlechaIcon;
    private static Icon scrollNorthIcon;
    private static Icon scrollSouthIcon;
    private static Icon scrollEastIcon;
    private static Icon scrollWestIcon;

    private static Icon frameCloseIcon;
    private static Icon frameMaxIcon;
    private static Icon frameMinIcon;
    private static Icon frameAltMaximizeIcon;

    private static Icon spinnerNextIcon;
    private static Icon spinnerPreviousIcon;

    public static Icon getSpinnerNextIcon() {
        if (spinnerNextIcon == null) {
            spinnerNextIcon = new SpinnerNextIcon();
        }
        return spinnerNextIcon;
    }

    public static Icon getSpinnerPreviousIcon() {
        if (spinnerPreviousIcon == null) {
            spinnerPreviousIcon = new SpinnerPreviousIcon();
        }
        return spinnerPreviousIcon;
    }

    public static Icon getFrameCloseIcon() {
        if (frameCloseIcon == null) {
            frameCloseIcon = new FrameGenericIcon("InternalFrame.NimCloseIcon",
                    "InternalFrame.NimCloseIconRoll",
                    "InternalFrame.NimCloseIconPush");
        }
        return frameCloseIcon;
    }

    public static Icon getFrameMaxIcon() {
        if (frameMaxIcon == null) {
            frameMaxIcon = new FrameGenericIcon("InternalFrame.NimMaxIcon",
                    "InternalFrame.NimMaxIconRoll",
                    "InternalFrame.NimMaxIconPush");
        }
        return frameMaxIcon;
    }

    public static Icon getFrameMinIcon() {
        if (frameMinIcon == null) {
            frameMinIcon = new FrameGenericIcon("InternalFrame.NimMinIcon",
                    "InternalFrame.NimMinIconRoll",
                    "InternalFrame.NimMinIconPush");
        }
        return frameMinIcon;
    }

    public static Icon getFrameAltMaximizeIcon() {
        if (frameAltMaximizeIcon == null) {
            frameAltMaximizeIcon = new FrameGenericIcon("InternalFrame.NimResizeIcon",
                    "InternalFrame.NimResizeIconRoll",
                    "InternalFrame.NimResizeIconPush");
        }
        return frameAltMaximizeIcon;
    }

    public static Icon getComboFlechaIcon() {
        if (comboFlechaIcon == null) {
            comboFlechaIcon = new ComboFlechaIcon();
        }
        return comboFlechaIcon;
    }

    public static Icon getBandaMenuItemIcon() {
        if (bandaMenuItemIcon == null) {
            bandaMenuItemIcon = new BandaMenuItemIcon();
        }
        return bandaMenuItemIcon;
    }

    public static Icon getCheckBoxIcon() {
        if (checkBoxIcon == null) {
            checkBoxIcon = new CheckBoxIcon();
        }
        return checkBoxIcon;
    }

    public static Icon getRadioButtonIcon() {
        if (radioButtonIcon == null) {
            radioButtonIcon = new RadioButtonIcon();
        }
        return radioButtonIcon;
    }

    public static Icon getCheckBoxMenuItemIcon() {
        if (checkBoxMenuItemIcon == null) {
            checkBoxMenuItemIcon = new CheckBoxMenuItemIcon();
        }
        return checkBoxMenuItemIcon;
    }

    public static Icon getRadioButtonMenuItemIcon() {
        if (radioButtonMenuItemIcon == null) {
            radioButtonMenuItemIcon = new RadioButtonMenuItemIcon();
        }
        return radioButtonMenuItemIcon;
    }

    public static Icon getSliderVerticalIcon() {
        if (sliderVertIcon == null) {
            sliderVertIcon = new SliderVerticalIcon();
        }
        return sliderVertIcon;
    }

    public static Icon getSliderHorizontalIcon() {
        if (sliderHorizIcon == null) {
            sliderHorizIcon = new SliderHorizontalIcon();
        }
        return sliderHorizIcon;
    }

    public static Icon getTreeCollapsedIcon() {
        if (treeCollapsedIcon == null) {
            treeCollapsedIcon = new TreeCollapsedIcon();
        }
        return treeCollapsedIcon;
    }

    public static Icon getTreeExpandedIcon() {
        if (treeExpandedIcon == null) {
            treeExpandedIcon = new TreeExpandedIcon();
        }
        return treeExpandedIcon;
    }

    public static Icon getScrollBarNorthButtonIcon() {
        if (scrollNorthIcon == null) {
            scrollNorthIcon = new ScrollBarNorthButtonIcon();
        }
        return scrollNorthIcon;
    }

    public static Icon getScrollBarSouthButtonIcon() {
        if (scrollSouthIcon == null) {
            scrollSouthIcon = new ScrollBarSouthButtonIcon();
        }
        return scrollSouthIcon;
    }

    public static Icon getScrollBarEastButtonIcon() {
        if (scrollEastIcon == null) {
            scrollEastIcon = new ScrollBarEastButtonIcon();
        }
        return scrollEastIcon;
    }

    public static Icon getScrollBarWestButtonIcon() {
        if (scrollWestIcon == null) {
            scrollWestIcon = new ScrollBarWestButtonIcon();
        }
        return scrollWestIcon;
    }

    /**
     * ***************************************************************************************
     */
    private static class ComboFlechaIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = -3071886619903027901L;

        private int w, h;

        ComboFlechaIcon() {
            w = 15;
            h = 15;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Icon icon = UIManager.getIcon("ComboBox.flechaIcon");
            w = icon.getIconWidth();
            h = icon.getIconHeight();

            icon.paintIcon(c, g, x, y);

            g.setColor(LafLookAndFeel.getFocusColor());
            g.drawLine(x + 2, y + 5, x + 7, y + 10);
            g.drawLine(x + 7, y + 10, x + 12, y + 5);
            g.drawLine(x + 2, y + 4, x + 7, y + 9);
            g.drawLine(x + 7, y + 9, x + 12, y + 4);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class ScrollBarNorthButtonIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = -3074532619903027901L;

        private int w, h;

        ScrollBarNorthButtonIcon() {
            w = 15;
            h = 15;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Icon icon = UIManager.getIcon("ScrollBar.northButtonIconImage");
            w = icon.getIconWidth();
            h = icon.getIconHeight();

            icon.paintIcon(c, g, x, y);

            g.setColor(LafLookAndFeel.getFocusColor());
            g.drawLine(x + 2, y + 8, x + 7, y + 3);
            g.drawLine(x + 7, y + 3, x + 12, y + 8);
            g.drawLine(x + 2, y + 9, x + 7, y + 4);
            g.drawLine(x + 7, y + 4, x + 12, y + 9);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class ScrollBarSouthButtonIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = -3074532619903027901L;

        private int w, h;

        ScrollBarSouthButtonIcon() {
            w = 15;
            h = 15;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Icon icon = UIManager.getIcon("ScrollBar.southButtonIconImage");
            w = icon.getIconWidth();
            h = icon.getIconHeight();

            icon.paintIcon(c, g, x, y);

            g.setColor(LafLookAndFeel.getFocusColor());
            g.drawLine(x + 2, y + 5, x + 7, y + 10);
            g.drawLine(x + 7, y + 10, x + 12, y + 5);
            g.drawLine(x + 2, y + 6, x + 7, y + 11);
            g.drawLine(x + 7, y + 11, x + 12, y + 6);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class ScrollBarEastButtonIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = -3074532619903027901L;

        private int w, h;

        ScrollBarEastButtonIcon() {
            w = 15;
            h = 15;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Icon icon = UIManager.getIcon("ScrollBar.eastButtonIconImage");
            w = icon.getIconWidth();
            h = icon.getIconHeight();

            icon.paintIcon(c, g, x, y);

            g.setColor(LafLookAndFeel.getFocusColor());
            g.drawLine(x + 5, y + 2, x + 10, y + 7);
            g.drawLine(x + 10, y + 7, x + 5, y + 12);
            g.drawLine(x + 6, y + 2, x + 11, y + 7);
            g.drawLine(x + 11, y + 7, x + 6, y + 12);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class ScrollBarWestButtonIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = -3074532619903027901L;

        private int w, h;

        ScrollBarWestButtonIcon() {
            w = 15;
            h = 15;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Icon icon = UIManager.getIcon("ScrollBar.westButtonIconImage");
            w = icon.getIconWidth();
            h = icon.getIconHeight();

            icon.paintIcon(c, g, x, y);

            g.setColor(LafLookAndFeel.getFocusColor());
            g.drawLine(x + 9, y + 2, x + 4, y + 7);
            g.drawLine(x + 4, y + 7, x + 9, y + 12);
            g.drawLine(x + 10, y + 2, x + 5, y + 7);
            g.drawLine(x + 5, y + 7, x + 10, y + 12);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class CheckBoxIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 7191199235214123414L;

        private final int w;
        private int h;

        CheckBoxIcon() {
            w = 21;
            h = 21;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JCheckBox b = (JCheckBox) c;
            ButtonModel model = b.getModel();

            boolean isEnabled = model.isEnabled();
            boolean isOn = model.isSelected() || model.isPressed();

            g.setColor(LafLookAndFeel.getControl());
            g.fillRect(x + 4, y + 3, 13, 15);
            g.drawLine(x + 3, y + 4, x + 3, y + 16);
            g.drawLine(x + 17, y + 4, x + 17, y + 16);

            Icon icono = UIManager.getIcon("CheckBox.iconBase");
            icono.paintIcon(c, g, x, y);

            if (isOn) {
                g.setColor(LafLookAndFeel.getFocusColor());
                g.fillRect(x + 4, y + 3, 13, 15);
                g.drawLine(x + 3, y + 4, x + 3, y + 16);
                g.drawLine(x + 17, y + 4, x + 17, y + 16);
            }

            if (model.isArmed() && isEnabled) {
                g.setColor(new Color(255, 255, 155, 127));
                g.fillRect(x + 5, y + 5, 11, 11);
            }

            if (isOn) {
                icono = UIManager.getIcon("CheckBox.iconTick");
                icono.paintIcon(c, g, x, y);
            }
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class RadioButtonIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 7191299335214123414L;
        private final int w;
        private int h;

        RadioButtonIcon() {
            w = 21;
            h = 21;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JRadioButton b = (JRadioButton) c;
            ButtonModel model = b.getModel();

            boolean isEnabled = model.isEnabled();
            boolean isOn = model.isSelected() || model.isPressed();

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setColor(LafLookAndFeel.getControl());
            g2D.fillOval(x + 3, y + 3, 15, 15);

            Icon icono = UIManager.getIcon("RadioButton.iconBase");
            icono.paintIcon(c, g, x, y);

            if (isOn) {
                g2D.setColor(LafLookAndFeel.getFocusColor());
                g2D.fillOval(x + 3, y + 3, 15, 15);
            }

            if (model.isArmed() && isEnabled) {
                g2D.setColor(new Color(255, 255, 155, 127));
                g2D.fillOval(x + 5, y + 5, 11, 11);
            }

            if (isOn) {
                icono = UIManager.getIcon("RadioButton.iconTick");
                icono.paintIcon(c, g, x, y);
            }

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class CheckBoxMenuItemIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 7291199335214123414L;

        private final int w;
        private int h;

        CheckBoxMenuItemIcon() {
            w = 21;
            h = 0;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JMenuItem b = (JMenuItem) c;
            ButtonModel model = b.getModel();

            x = 1;
            y = 0;
            boolean isEnabled = model.isEnabled();
            boolean isOn = model.isSelected() || model.isPressed();

            Icon icono = UIManager.getIcon("MenuCheckBox.iconBase");
            icono.paintIcon(c, g, x, y);

            if (!isEnabled) {
                g.setColor(new Color(0, 0, 0, 63));
                g.fillRect(x + 4, y + 3, 13, 15);
                g.drawLine(x + 3, y + 4, x + 3, y + 16);
                g.drawLine(x + 17, y + 4, x + 17, y + 16);
            } else if (isOn) {
                g.setColor(LafLookAndFeel.getFocusColor());
                g.fillRect(x + 4, y + 3, 13, 15);
                g.drawLine(x + 3, y + 4, x + 3, y + 16);
                g.drawLine(x + 17, y + 4, x + 17, y + 16);
            }

            if (model.isArmed() && isEnabled) {
                g.setColor(new Color(255, 255, 155, 127));
                g.fillRect(x + 5, y + 5, 11, 11);
            }

            if (isOn) {
                icono = UIManager.getIcon("MenuCheckBox.iconTick");
                icono.paintIcon(c, g, x, y);
            }

            g.setColor(LafUtils.getRolloverColor());
            g.fillRect(0, 0, 22, b.getHeight());

            g.setColor(LafUtils.getSombra());
            g.drawLine(22, 0, 22, b.getHeight());
            g.setColor(LafUtils.getBrillo());
            g.drawLine(23, 0, 23, b.getHeight());
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class BandaMenuItemIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 8191199335214123414L;

        private final int w;
        private int h;

        BandaMenuItemIcon() {
            w = 21;
            h = 0; // Esta chapuza es solo para que se pueda pintar como es debido en JDK 1.6
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JMenuItem b = (JMenuItem) c;

            g.setColor(LafUtils.getRolloverColor());
            g.fillRect(0, 0, 22, b.getHeight());

            g.setColor(LafUtils.getSombra());
            g.drawLine(22, 0, 22, b.getHeight());
            g.setColor(LafUtils.getBrillo());
            g.drawLine(23, 0, 23, b.getHeight());
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ***************************************************************************************
     */
    private static class RadioButtonMenuItemIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 8191199335214123414L;

        private final int w;
        private int h;

        RadioButtonMenuItemIcon() {
            w = 21;
            h = 0;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            JMenuItem b = (JMenuItem) c;
            ButtonModel model = b.getModel();

            x = 1;
            y = 0;
            boolean isEnabled = model.isEnabled();
            boolean isOn = model.isSelected() || model.isPressed();

            Icon icono = UIManager.getIcon("MenuRadioButton.iconBase");
            icono.paintIcon(c, g, x, y);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!isEnabled) {
                g2D.setColor(new Color(0, 0, 0, 63));
                g2D.fillOval(x + 3, y + 3, 15, 15);
            } else if (isOn) {
                g2D.setColor(LafLookAndFeel.getFocusColor());
                g2D.fillOval(x + 3, y + 3, 15, 15);
            }

            if (model.isArmed() && isEnabled) {
                g2D.setColor(new Color(255, 255, 155, 127));
                g2D.fillOval(x + 5, y + 5, 11, 11);
            }

            if (isOn) {
                icono = UIManager.getIcon("MenuRadioButton.iconTick");
                icono.paintIcon(c, g, x, y);
            }

            g.setColor(LafUtils.getRolloverColor());
            g.fillRect(0, 0, 22, b.getHeight());

            g.setColor(LafUtils.getSombra());
            g.drawLine(22, 0, 22, b.getHeight());
            g.setColor(LafUtils.getBrillo());
            g.drawLine(23, 0, 23, b.getHeight());

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class SliderVerticalIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 3191199335214123414L;

        private final int w;
        private int h;

        SliderVerticalIcon() {
            w = 21;
            h = 19;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (c.hasFocus()) {
                g2D.setColor(LafLookAndFeel.getFocusColor());
                g2D.fillOval(x + 1, y + 4, 17, 11);
            } else if (!c.isEnabled()) {
                g2D.setColor(Color.gray);
                g2D.fillOval(x + 1, y + 4, 17, 11);
            }

            Icon icono = UIManager.getIcon("Slider.verticalThumbIconImage");
            icono.paintIcon(c, g, x, y);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class SliderHorizontalIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 1191199335214123414L;

        private final int w;
        private int h;

        SliderHorizontalIcon() {
            w = 19;
            h = 21;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (c.hasFocus()) {
                g2D.setColor(LafLookAndFeel.getFocusColor());
                g2D.fillOval(x + 3, y + 2, 11, 17);
            } else if (!c.isEnabled()) {
                g2D.setColor(Color.gray);
                g2D.fillOval(x + 3, y + 2, 11, 17);
            }

            Icon icono = UIManager.getIcon("Slider.horizontalThumbIconImage");
            icono.paintIcon(c, g, x, y);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class TreeCollapsedIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 6191195335214123414L;

        private final int w;
        private int h;

        TreeCollapsedIcon() {
            w = 18;
            h = 18;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.translate(x, y);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setColor(LafLookAndFeel.getFocusColor());
            g2D.fillOval(2, 2, 14, 14);

            g2D.setColor(LafLookAndFeel.getBlack());
            g2D.drawLine(11, 11, 7, 7);
            g2D.drawLine(11, 11, 7, 11);
            g2D.drawLine(11, 11, 11, 7);

            Icon icono = UIManager.getIcon("Tree.PelotillaIcon");
            icono.paintIcon(c, g, 0, 0);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

            g.translate(-x, -y);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class TreeExpandedIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 7191199335214121114L;

        private final int w;
        private int h;

        TreeExpandedIcon() {
            w = 18;
            h = 18;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.translate(x, y);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2D.setColor(LafLookAndFeel.getFocusColor());
            g2D.fillOval(2, 2, 14, 14);

            g2D.setColor(LafLookAndFeel.getBlack());
            g2D.drawLine(10, 10, 6, 6);
            g2D.drawLine(6, 6, 6, 10);
            g2D.drawLine(6, 6, 10, 6);

            Icon icono = UIManager.getIcon("Tree.PelotillaIcon");
            icono.paintIcon(c, g, 0, 0);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

            g.translate(-x, -y);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class FrameGenericIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 1191199335214123414L;
        private final String sIcono;

        private String sIconoR, sIconoP;
        private final int w;
        private int h;

        FrameGenericIcon(String icon, String iconR, String iconP) {
            w = 20;
            h = 20;

            sIcono = icon;
            sIconoR = iconR;
            sIconoP = iconP;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            ButtonModel model = ((AbstractButton) c).getModel();

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Icon icono = null;
            if (model.isPressed()) {
                g2D.setColor(LafLookAndFeel.getFocusColor());
                g2D.fillRoundRect(x, y, w, h, 4, 4);
                icono = UIManager.getIcon(sIconoP);
            } else if (model.isRollover()) {
                icono = UIManager.getIcon(sIconoR);
            } else {
                icono = UIManager.getIcon(sIcono);
            }

            icono.paintIcon(c, g, x, y);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class SpinnerNextIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 8191199334213423414L;

        private final int w;
        private int h;

        SpinnerNextIcon() {
            w = 7;
            h = 5;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.translate(x, y);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!c.isEnabled()) {
                g2D.setColor(LafLookAndFeel.getInactiveControlTextColor());
            } else {
                ButtonModel mod = ((AbstractButton) c).getModel();
                if (mod.isPressed()) {
                    g2D.setColor(LafLookAndFeel.getFocusColor());
                } else {
                    g2D.setColor(LafLookAndFeel.getControlTextColor());
                }
            }

            g2D.drawLine(1, 3, 3, 1);
            g2D.drawLine(3, 1, 5, 3);
            g2D.drawLine(1, 4, 3, 2);
            g2D.drawLine(3, 2, 5, 4);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

            g.translate(-x, -y);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    /**
     * ************************************************************************************************************
     */
    private static class SpinnerPreviousIcon implements Icon, UIResource, Serializable {

        private static final long serialVersionUID = 8191199334213423414L;

        private final int w;
        private int h;

        SpinnerPreviousIcon() {
            w = 7;
            h = 5;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.translate(x, y);

            Graphics2D g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!c.isEnabled()) {
                g2D.setColor(LafLookAndFeel.getInactiveControlTextColor());
            } else {
                ButtonModel mod = ((AbstractButton) c).getModel();
                if (mod.isPressed()) {
                    g2D.setColor(LafLookAndFeel.getFocusColor());
                } else {
                    g2D.setColor(LafLookAndFeel.getControlTextColor());
                }
            }

            g2D.drawLine(1, 1, 3, 3);
            g2D.drawLine(3, 3, 5, 1);
            g2D.drawLine(1, 2, 3, 4);
            g2D.drawLine(3, 4, 5, 2);

            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

            g.translate(-x, -y);
        }

        @Override
        public int getIconWidth() {
            return w;
        }

        @Override
        public int getIconHeight() {
            return h;
        }
    }

    private LafIconFactory() {
    }
}
