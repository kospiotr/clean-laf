package pl.pkosmowski.laf.clear;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

public class LafMenuUI extends BasicMenuUI {

    public static ComponentUI createUI(JComponent x) {
        return new LafMenuUI();
    }

    @Override
    public void update(Graphics g, JComponent c) {
        JMenu menu = (JMenu) c;
        if (menu.isTopLevelMenu()) {
            menu.setOpaque(false);

            ButtonModel model = menu.getModel();
            if (model.isArmed() || model.isSelected()) {
                g.setColor(LafLookAndFeel.getFocusColor());
                g.fillRoundRect(1, 1, c.getWidth() - 2, c.getHeight() - 3, 2, 2);
            }
        } else {
            menuItem.setBorderPainted(false);
            menuItem.setOpaque(false);
        }

        super.update(g, c);
    }

    @Override
    protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        LafUtils.pintaBarraMenu(g, menuItem, bgColor);
    }
}
