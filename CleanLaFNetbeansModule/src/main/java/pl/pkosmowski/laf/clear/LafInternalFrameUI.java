package pl.pkosmowski.laf.clear;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalInternalFrameUI;

public class LafInternalFrameUI extends MetalInternalFrameUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafInternalFrameUI((JInternalFrame) c);
    }
    LafInternalFrameTitlePane titlePane;

    public LafInternalFrameUI(JInternalFrame arg0) {
        super(arg0);
    }

    @Override
    protected JComponent createNorthPane(JInternalFrame w) {
        super.createNorthPane(w);

        titlePane = new LafInternalFrameTitlePane(w);
        return titlePane;
    }

    @Override
    public void update(Graphics g, JComponent c) {
        paint(g, c);
    }

}
