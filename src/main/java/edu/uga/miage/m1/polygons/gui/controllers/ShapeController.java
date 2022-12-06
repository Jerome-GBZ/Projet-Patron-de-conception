package edu.uga.miage.m1.polygons.gui.controllers;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.factories.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class ShapeController {
    private ShapeFactory shapeFac = new ShapeFactory();

    public enum Shapes {
        SQUARE,
        TRIANGLE,
        CIRCLE,
        FIGMA,
        COMPOUNDSHAPE
    }

    public Shapes getShapes(String type) {
        return Shapes.valueOf(type.toUpperCase());
    }

    public SimpleShape shapeIsSelect(List<SimpleShape> shapesList, int x, int y) {
        int i = shapesList.size() - 1;

        while (i > -1 && !shapesList.get(i).clickedOnShape(x, y)) { i--; }

        return i > -1 ? shapesList.get(i) : null;
    }

    public SimpleShape createSimpleShape(Shapes type, int x, int y) {
        return shapeFac.createSimpleShape(type, x, y);
    }
}
