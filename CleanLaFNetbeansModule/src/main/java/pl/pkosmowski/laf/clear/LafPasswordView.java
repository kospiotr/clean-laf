package pl.pkosmowski.laf.clear;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import javax.swing.JPasswordField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;
import javax.swing.text.Position;

public class LafPasswordView extends PasswordView {

    protected static int ancho = 9;
    protected static int hueco = 3;

    public LafPasswordView(Element elem) {
        super(elem);
    }

    @Override
    protected int drawEchoCharacter(Graphics g, int x, int y, char c) {
        int w = getFontMetrics().charWidth(c);
        w = (w < ancho ? ancho : w);
        int h = (getContainer().getHeight() - ancho) / 2;

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.fillOval(x, h + 1, w, w);

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

        return x + w + hueco;
    }

    @Override
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        Container c = getContainer();
        if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            if (!f.echoCharIsSet()) {
                return super.modelToView(pos, a, b);
            }

            char echoChar = f.getEchoChar();
            int w = f.getFontMetrics(f.getFont()).charWidth(echoChar);
            w = (w < ancho ? ancho : w) + hueco;

            Rectangle alloc = adjustAllocation(a).getBounds();
            int dx = (pos - getStartOffset()) * w;
            alloc.x += dx - 2;
            if (alloc.x <= 5) {
                alloc.x = 6;
            }
            alloc.width = 1;

            return alloc;
        }

        return null;
    }

    @Override
    public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias) {
        bias[0] = Position.Bias.Forward;
        int n = 0;
        Container c = getContainer();
        if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            if (!f.echoCharIsSet()) {
                return super.viewToModel(fx, fy, a, bias);
            }

            char echoChar = f.getEchoChar();
            int w = f.getFontMetrics(f.getFont()).charWidth(echoChar);
            w = (w < ancho ? ancho : w) + hueco;

            a = adjustAllocation(a);
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a.getBounds();
            n = ((int) fx - alloc.x) / w;
            if (n < 0) {
                n = 0;
            } else if (n > (getStartOffset() + getDocument().getLength())) {
                n = getDocument().getLength() - getStartOffset();
            }
        }

        return getStartOffset() + n;
    }
}
