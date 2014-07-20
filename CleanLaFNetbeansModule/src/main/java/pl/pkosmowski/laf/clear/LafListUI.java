package pl.pkosmowski.laf.clear;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

public class LafListUI extends BasicListUI {

    public static ComponentUI createUI(JComponent list) {
        return new LafListUI(list);
    }

    public LafListUI(JComponent list) {
        super();
    }

    @Override
    protected void paintCell(Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer,
            ListModel dataModel, ListSelectionModel selModel, int leadIndex) {

        rowBounds.x += 1;
        super.paintCell(g, row, rowBounds, cellRenderer, dataModel, selModel, leadIndex);
        rowBounds.x -= 1;

        if (list.isSelectedIndex(row)) {
            Color oldColor = g.getColor();

            g.translate(rowBounds.x, rowBounds.y);

            GradientPaint grad = new GradientPaint(0, 0, LafUtils.getBrillo(),
                    0, rowBounds.height, LafUtils.getSombra());
            Color bgColor = LafLookAndFeel.getMenuSelectedBackground();

            Graphics2D g2D = (Graphics2D) g;
            g2D.setPaint(grad);
            g2D.fillRect(0, 0, rowBounds.width - 1, rowBounds.height);

            g.setColor(bgColor.darker());
            g.drawRect(0, 0, rowBounds.width - 1, rowBounds.height - 1);

            g.translate(-rowBounds.x, -rowBounds.y);
            g.setColor(oldColor);
        }
    }
}
