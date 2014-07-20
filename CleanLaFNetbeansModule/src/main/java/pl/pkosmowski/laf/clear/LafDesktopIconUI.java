package pl.pkosmowski.laf.clear;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopIconUI;

public class LafDesktopIconUI extends BasicDesktopIconUI {

    public static ComponentUI createUI(JComponent c) {
        return new LafDesktopIconUI();
    }
    boolean hasFocus;

    private final int width = UIManager.getInt("LafDesktopIcon.width");
    private final int height = UIManager.getInt("LafDesktopIcon.height");

    private final int bigWidth = UIManager.getInt("LafDesktopIconBig.width");
    private final int bigHeight = UIManager.getInt("LafDesktopIconBig.height");

    private final HackML hackML;
    private Icon resizeIcon, antIcon;

    public LafDesktopIconUI() {
        super();

        hackML = new HackML();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();

        LookAndFeel.uninstallBorder(desktopIcon);
    }

    @Override
    protected void installComponents() {
    }

    @Override
    protected void uninstallComponents() {
    }

    @Override
    protected void installListeners() {
        super.installListeners();

        if (frame != null) {
            desktopIcon.addMouseListener(hackML);
            desktopIcon.addMouseMotionListener(hackML);
        }
    }

    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();

        desktopIcon.removeMouseListener(hackML);
        desktopIcon.removeMouseMotionListener(hackML);
    }

    @Override
    public void update(Graphics g, JComponent c) {
        paint(g, c);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (frame.getFrameIcon() != antIcon) {
            antIcon = frame.getFrameIcon();
            resizeIcon = LafUtils.reescala(antIcon, bigWidth, bigHeight);
        }
        String title = frame.getTitle();

        int x = 0;
        if (resizeIcon != null) {
            x = (width - resizeIcon.getIconWidth()) / 2;
            resizeIcon.paintIcon(c, g, x, 2);
        }

        g.setFont(UIManager.getFont("DesktopIcon.font"));
        FontMetrics fm = g.getFontMetrics();

        if (hasFocus) {
            int y = 0;
            String auxTit = getTitle(title, fm, width - 10);    // Los anglos se mearan de risa al ver el nombre de esta variable...
            while (auxTit.length() > 0) {
                if (auxTit.endsWith("...")) {
                    auxTit = auxTit.substring(0, auxTit.length() - 3);
                }

                Rectangle2D rect = fm.getStringBounds(auxTit, g);
                x = (int) (width - rect.getWidth()) / 2;
                y += rect.getHeight();

                LafUtils.paintShadowTitleFat(g, auxTit, x, y, Color.white);

                title = title.substring(auxTit.length());
                auxTit = getTitle(title, fm, width - 10);
            }
        } else {
            title = getTitle(title, fm, width - 10);
            Rectangle2D rect = fm.getStringBounds(title, g);
            x = (int) (width - rect.getWidth()) / 2;
            LafUtils.paintShadowTitleFat(g, title, x, height - LafUtils.MATRIX_FAT, Color.white);
        }
    }

    protected String getTitle(String title, FontMetrics fm, int len) {
        if (title == null || title.isEmpty()) {
            return "";
        }
        int lTit = fm.stringWidth(title);
        if (lTit <= len) {
            return title;
        }
        int lPuntos = fm.stringWidth("...");
        if (len - lPuntos <= 0) {
            return "";
        }
        int i = 1;
        do {
            String aux = title.substring(0, i++) + "...";
            lPuntos = fm.stringWidth(aux);
        } while (lPuntos < len);
        return title.substring(0, i - 1) + "...";
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return getMinimumSize(c);
    }

    @Override
    public Dimension getMaximumSize(JComponent c) {
        return getMinimumSize(c);
    }

    @Override
    public Dimension getMinimumSize(JComponent c) {
        return new Dimension(width, height);
    }

    //******************************+
    private class HackML extends MouseInputAdapter {

        @Override
        public void mouseReleased(MouseEvent ev) {
            dodo(ev);
        }

        @Override
        public void mousePressed(MouseEvent ev) {
            dodo(ev);
        }

        @Override
        public void mouseExited(MouseEvent ev) {
            hasFocus = false;
            dodo(ev);
        }

        @Override
        public void mouseEntered(MouseEvent ev) {
            hasFocus = true;
            dodo(ev);
        }

        @Override
        public void mouseDragged(MouseEvent ev) {
            dodo(ev);
        }

        void dodo(MouseEvent ev) {
            if (desktopIcon != null) {
                desktopIcon.getDesktopPane().updateUI();
            }
        }
    }
}
