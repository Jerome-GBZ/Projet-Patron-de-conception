package edu.uga.miage.m1.polygons.gui.factories;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.factories.ShapeFactory.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public interface SimpleShapeFactory {
    SimpleShape createSimpleShape(Shapes type, int x, int y);

    Shapes getShapes(String type);

    SimpleShape shapeIsSelect(List<SimpleShape> shapesList, int x, int y);
}