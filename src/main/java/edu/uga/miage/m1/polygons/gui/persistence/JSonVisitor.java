package edu.uga.miage.m1.polygons.gui.persistence;

import ShapePackage.shapes.CompoundShape;
import ShapePackage.shapes.SimpleShape;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements SimpleFile {

    private String representation = null;

    public JSonVisitor() {
        clear();
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

    @Override
    public String getRepresentation() {
        representation = representation.substring(0, representation.length()-2);
        return  representation.concat("] }");
    }

    @Override
    public void clear() {
        representation = "{ \"shapes\":[ ";
    }
}
