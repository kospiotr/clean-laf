package pl.pkosmowski.cleanlafnetbeansmodule;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import pl.pkosmowski.laf.clear.LafLookAndFeel;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {

        UIManager.getDefaults().clear();

        try {
            LookAndFeel laf = new LafLookAndFeel();
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
