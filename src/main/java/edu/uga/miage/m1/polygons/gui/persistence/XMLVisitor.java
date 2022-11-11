package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements SimpleFile {

    private String representation = null;

    public XMLVisitor() {
        representation = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root> <shapes> ";
    }

    @Override
    public void visit(SimpleShape shape) {
        String className = shape.getClass().getSimpleName().toLowerCase();

        if(className.equals("compoundshape")) {
            representation += String.format("<shape> <type>%s</type> <shapes> ", className);

            for (SimpleShape s : ((CompoundShape) shape).getShapes()) {
                this.visit(s);
            }

            representation += String.format("</shapes> </shape> ");
        } else {
            representation += String.format("<shape> <type>%s</type> <x>%d</x> <y>%d</y> </shape> ", shape.getClass().getSimpleName().toLowerCase(), shape.getX(), shape.getY());
        }
    }

    /**
     * @return the representation in JSon example for a Triangle:
     *
     *         <pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */
    public String getRepresentation() {
        return  representation.concat("</shapes> </root>");
    }
}
