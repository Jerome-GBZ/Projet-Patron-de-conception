package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements SimpleFile {

    private String representation = null;

    public XMLVisitor() {
        representation = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root> <shapes> ";
    }

    public void visit(SimpleShape shape) {
        String className = shape.getClass().getSimpleName().toLowerCase();

        if(className.equals("compoundshape")) {
            representation += String.format("<shape type='%s'> <shapes> ", className);

            for (SimpleShape s : ((CompoundShape) shape).getShapes()) {
                this.visit(s);
            }

            representation += "</shapes> </shape> ";
        } else {
            representation += String.format("<shape type='%s'> <x>%d</x> <y>%d</y> </shape> ", shape.getClass().getSimpleName().toLowerCase(), shape.getX(), shape.getY());
        }
    }

    /**
     * @return the representation in JSon example for a Triangle:
     */
    public String getRepresentation() {
        return  representation.concat("</shapes> </root>");
    }
}
