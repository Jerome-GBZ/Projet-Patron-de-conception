package ShapePackage.factories;

import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;

public interface SimpleShapeFactory {
    SimpleShape createSimpleShape(Shapes type, int x, int y);
}