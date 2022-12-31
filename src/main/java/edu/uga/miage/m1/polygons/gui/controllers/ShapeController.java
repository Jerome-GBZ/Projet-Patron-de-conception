package edu.uga.miage.m1.polygons.gui.controllers;

import java.util.List;

import ShapePackage.factories.ShapeFactory;
import ShapePackage.shapes.SimpleShape;
import ShapePackage.shapes.ShapesEnum.Shapes;

public class ShapeController {
    private ShapeFactory shapeFac = new ShapeFactory();

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
