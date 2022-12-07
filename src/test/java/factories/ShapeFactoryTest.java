package factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import ShapePackage.factories.ShapeFactory;
import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;

class ShapeFactoryTest {
    ShapeFactory shapeFac = new ShapeFactory();

    @Test
    void testConstructorShapeFactory() {
        assertNotNull(shapeFac);
    }

    @Test
    void testConstrucCircle() {
        SimpleShape s = shapeFac.createSimpleShape(Shapes.CIRCLE, 0, 0);
        assertNotNull(s);
    }

    @Test
    void testConstrucSquare() {
        SimpleShape s = shapeFac.createSimpleShape(Shapes.SQUARE, 0, 0);
        assertNotNull(s);
    }

    @Test
    void testConstrucTriangle() {
        SimpleShape s = shapeFac.createSimpleShape(Shapes.TRIANGLE, 0, 0);
        assertNotNull(s);
    }

    @Test
    void testConstrucCompoundShape() {
        SimpleShape s = shapeFac.createSimpleShape(Shapes.COMPOUNDSHAPE, 0, 0);
        assertNull(s);
    }
}
