package ShapePackage.factories;

import ShapePackage.shapes.*;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;

public class ShapeFactory implements SimpleShapeFactory {
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
            case FIGMA:
                shape = new Figma(x, y);
                break;
            default:
                shape = null;
        }

        return shape;
    }
}