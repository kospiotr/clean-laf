package pl.pkosmowski.laf.clear;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.LookAndFeel;
import javax.swing.Popup;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

public class LafPopupMenuUI extends BasicPopupMenuUI {

    private static Pantalla pantallas[] = null;

    private static Kernel kernel = null;
    private static final int MATRIX = 3;

    public static ComponentUI createUI(JComponent c) {
        if (pantallas == null) {
            try {
                GraphicsDevice[] gda = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

                pantallas = new Pantalla[gda.length];

                // El mundo es una mierda....
                LafPopupMenuUI dummy = new LafPopupMenuUI();

                for (int i = 0; i < gda.length; i++) {
                    pantallas[i] = dummy.new Pantalla(new Robot(gda[i]), gda[i].getDefaultConfiguration().getBounds());
                }
            } catch (AWTException ex) {
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            }
        }
        if (kernel == null) {
            float[] elements = new float[MATRIX * MATRIX];
            for (int i = 0; i < elements.length; i++) {
                elements[i] = .1f;
            }
            int mid = MATRIX / 2 + 1;
            elements[mid * mid] = .2f;

            kernel = new Kernel(MATRIX, MATRIX, elements);
        }
        return new LafPopupMenuUI();
    }
    private BufferedImage fondo = null;
    private BufferedImage blurFondo = null;
    private MiPL mipl;

    @Override
    public void installDefaults() {
        super.installDefaults();

        popupMenu.setBorder(LafBorders.getPopupMenuBorder());
        popupMenu.setOpaque(false);
    }

    @Override
    public void uninstallDefaults() {
        super.uninstallDefaults();

        LookAndFeel.installBorder(popupMenu, "PopupMenu.border");
        popupMenu.setOpaque(true);
    }

    @Override
    public void installListeners() {
        super.installListeners();

        mipl = new MiPL(popupMenu);
        popupMenu.addPopupMenuListener(mipl);
    }

    @Override
    public void uninstallListeners() {
        super.uninstallListeners();

        popupMenu.removePopupMenuListener(mipl);
    }

    @Override
    public void update(Graphics g, JComponent c) {
        if (blurFondo != null) {
            g.drawImage(blurFondo, 0, 0, null);
        }

        if (LafUtils.getMenuOpacity() > 5) {
            Color cFondo = new Color(c.getBackground().getRed(),
                    c.getBackground().getGreen(),
                    c.getBackground().getBlue(),
                    LafUtils.getMenuOpacity());
            g.setColor(cFondo);
            g.fillRect(0, 0, c.getWidth() - 4, c.getHeight() - 4);
        }
    }

    /**
     * Este metodo esta aqui solo para **MINIMIZAR** el problema de usar la
     * clase ROBOT. Esta clase tiene ciertas restricciones de seguridad (a parte
     * de que en el JDK de alguna distro de Linux de esas que van de guays el
     * programa nativo que hace el trabajo se instala sin permisos de ejecucion)
     * que obligan a que el jar tenga que ir firmado al usarse en applets. Este
     * metodo hace la llamada a la clase robot para capturar el fondo, y si
     * salta una excepcion (en realidad cualquier cosa pues se captura
     * Throwable), se devuelve una imagen tan transparente como se le pida. Esto
     * se cargara el efecto de blur (blurrear algo liso es liso) de los menus,
     * pero al menos habra cierta transparencia y pintara una buena sombra si no
     * se firma el applet o se usa una distro chapucera. Por cierto, si el menu
     * se sale de la ventana tendra un fondo opaco, y por tanto no habra
     * transparencia y la sombra quedara fatal
     *
     * @param pop
     * @param rect
     * @param transparencia
     * @return
     */
    protected BufferedImage pillaFondo(JPopupMenu pop, Rectangle rect, int transparencia) {
        BufferedImage img = null;
        try {
            Robot robot = null;
            for (Pantalla pantalla : pantallas) {
                if (pantalla.rect.contains(rect.x, rect.y)) {
                    robot = pantalla.robot;
                    rect.x -= pantalla.rect.x;
                    rect.y -= pantalla.rect.y;
                }
            }
            // Si llega aqui y ninguna pantalla sirve, robot=null y dara un NullPointerExcption, entrara en el catch y mostrara un
            // fondo transparente sin blur igual que en los applets.
            img = robot.createScreenCapture(rect);
        } catch (Throwable ex) {
            img = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            g.setColor(LafUtils.getColorAlfa(pop.getBackground(), transparencia));
            g.fillRect(0, 0, rect.width, rect.height);
            g.dispose();
        }
        return img;
    }

    @Override
    public Popup getPopup(JPopupMenu pop, int x, int y) {
        Dimension dim = pop.getPreferredSize();

        Rectangle rect = new Rectangle(x, y, dim.width, dim.height);
        fondo = pillaFondo(pop, rect, 0);

        if (LafUtils.getMenuOpacity() > 250) {
            blurFondo = fondo;
        } else {
            Rectangle rectAmp = new Rectangle(x - MATRIX, y - MATRIX, dim.width + 2 * MATRIX, dim.height + 2 * MATRIX);

            BufferedImage clearFondo = pillaFondo(pop, rectAmp, LafUtils.getMenuOpacity());

            blurFondo = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
            BufferedImage tempFondo = clearFondo.getSubimage(0, 0, clearFondo.getWidth(), clearFondo.getHeight());

            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            cop.filter(clearFondo, tempFondo);              // A ditorsionar
            cop.filter(tempFondo, clearFondo);              // A ditorsionar, otra vez
            cop.filter(clearFondo, tempFondo);              // A ditorsionar, y otra mas

            Graphics g = blurFondo.getGraphics();
            g.drawImage(fondo, 0, 0, null);
            g.drawImage(tempFondo.getSubimage(MATRIX, MATRIX, dim.width - 5, dim.height - 5),
                    0, 0, null);
        }

        return super.getPopup(pop, x, y);
    }

    /////////////////////////////////
    private class MiPL implements PopupMenuListener {

        JPopupMenu papi;

        MiPL(JPopupMenu pop) {
            papi = pop;
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent ev) {
            if (fondo == null) {
                return;
            }

            Graphics g = papi.getRootPane().getGraphics();

            Point p = papi.getLocationOnScreen();
            Point r = papi.getRootPane().getLocationOnScreen();

            g.drawImage(fondo, p.x - r.x, p.y - r.y, null);
            fondo = null;
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent ev) {
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent ev) {
        }
    }

    class Pantalla {

        Robot robot;
        Rectangle rect;

        Pantalla(Robot rob, Rectangle r) {
            robot = rob;
            rect = r;
        }
    }
}
