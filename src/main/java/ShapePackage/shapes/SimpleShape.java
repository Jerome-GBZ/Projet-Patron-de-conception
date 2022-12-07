package ShapePackage.shapes;

import java.awt.Graphics2D;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface SimpleShape {

    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     */
    void draw(Graphics2D g2, float width);

    int getX();

    int getY();

    void moveTo(int x, int y);

    boolean clickedOnShape(int x, int y);

    boolean add(SimpleShape shape);
}
