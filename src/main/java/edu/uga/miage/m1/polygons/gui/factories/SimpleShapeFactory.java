package edu.uga.miage.m1.polygons.gui.factories;

import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public interface SimpleShapeFactory {
    SimpleShape createSimpleShape(Shapes type, int x, int y);
}