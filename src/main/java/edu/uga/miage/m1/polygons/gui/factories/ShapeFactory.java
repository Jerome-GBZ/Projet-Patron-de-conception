package edu.uga.miage.m1.polygons.gui.factories;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.*;

public class ShapeFactory {
    public enum Shapes {
        SQUARE,
        TRIANGLE,
        CIRCLE,
        COMPOUNDSHAPE
    }

    public SimpleShape createSimpleShape(Shapes type, int x, int y) {
        SimpleShape shape;

        switch(type) {
            case TRIANGLE:
                shape = new Triangle(x, y);
                break;
            case CIRCLE:
                shape = new Circle(x, y);
                break;
            case SQUARE:
                shape = new Square(x, y);
                break;
            default:
                shape = null;
        }

        return shape;
    }

    public Shapes getShapes(String type) {
        return Shapes.valueOf(type.toUpperCase());
    }

    public SimpleShape shapeIsSelect(List<SimpleShape> shapesList, int x, int y) {
        int i = shapesList.size() - 1;

        while (i > -1 && !shapesList.get(i).clickedOnShape(x, y)) { i--; }

        return i > -1 ? shapesList.get(i) : null;
    }
}