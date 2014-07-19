package pl.pkosmowski.cleanlafnetbeansmodule;

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.neilcsmith.praxis.laf.PraxisLookAndFeel;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        System.setProperty("netbeans.ps.hideSingleExpansion", "true");

        try {
            EventQueue.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    UIManager.getDefaults().clear();
                    ClassLoader cl = Lookup.getDefault().lookup(ClassLoader.class);
                    UIManager.put("ClassLoader", cl);

                    try {
                        LookAndFeel laf = new PraxisLookAndFeel();
                        UIManager.setLookAndFeel(laf);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Exceptions.printStackTrace(ex);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
            });
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InvocationTargetException ex) {
            Exceptions.printStackTrace(ex);
        }

        WindowManager.getDefault().invokeWhenUIReady(new Runnable() {

            @Override
            public void run() {
                WindowManager wm = WindowManager.getDefault();
                TopComponent tc = wm.findTopComponent("projectTabLogical_tc");
                tc.setIcon(ImageUtilities.loadImage("org/netbeans/modules/project/ui/resources/projectTab.png", true));
                tc = wm.findTopComponent("CommonPalette");
                tc.setIcon(ImageUtilities.loadImage("org/netbeans/modules/palette/resources/palette.png", true));
            }
        });
    }

}
