package pl.pkosmowski.laf.clear;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class LafMenuItemUI extends BasicMenuItemUI {

    public static ComponentUI createUI(JComponent x) {
        return new LafMenuItemUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        menuItem.setBorderPainted(false);
        menuItem.setOpaque(false);

        defaultTextIconGap = 3;
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();

        menuItem.setOpaque(true);
    }

    @Override
    protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        LafUtils.pintaBarraMenu(g, menuItem, bgColor);
    }
}
