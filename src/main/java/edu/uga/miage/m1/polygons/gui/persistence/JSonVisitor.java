package edu.uga.miage.m1.polygons.gui.Persistence;

import edu.uga.miage.m1.polygons.gui.Shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.Shapes.SimpleShape;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements SimpleFile {

    private String representation = null;

    public JSonVisitor() {
        representation = "{ \"shapes\":[ ";
    }

    @Override
    public void visit(SimpleShape shape) {
        String className = shape.getClass().getSimpleName().toLowerCase();

        if(className.equals("compoundshape")) {
            representation += String.format("{ \"type\": \"%s\", \"shapes\": [", className);

            for (SimpleShape s : ((CompoundShape) shape).getShapes()) {
                this.visit(s);
            }

            representation += "]}, ";
        } else {
            representation += String.format("{ \"type\": \"%s\", \"x\": %d, \"y\": %d }, ", className, shape.getX(), shape.getY());
        }
    }

    /**
     * @return the representation in JSon example for a Circle
     */
    public String getRepresentation() {
        representation = representation.substring(0, representation.length()-2);
        return  representation.concat("] }");
    }
}
