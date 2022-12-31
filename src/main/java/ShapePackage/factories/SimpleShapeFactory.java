package ShapePackage.factories;

import ShapePackage.shapes.SimpleShape;
import ShapePackage.shapes.ShapesEnum.Shapes;

public interface SimpleShapeFactory {
    SimpleShape createSimpleShape(Shapes type, int x, int y);
}