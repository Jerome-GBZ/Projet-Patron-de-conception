package commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ShapePackage.shapes.Circle;
import ShapePackage.shapes.SimpleShape;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.controllers.ShapeController;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;

class ShapeControllerTest {
    ShapeController shapeC = new ShapeController();

    @Test
    void testGetSquare() {
        assertEquals(Shapes.SQUARE, shapeC.getShapes("square".toUpperCase()));
    }

    @Test
    void testGetCircle() {
        assertEquals(Shapes.CIRCLE, shapeC.getShapes("circle".toUpperCase()));
    }

    @Test
    void testGetTriangle() {
        assertEquals(Shapes.TRIANGLE, shapeC.getShapes("triangle".toUpperCase()));
    }

    @Test
    void testGetCompoundShape() {
        assertEquals(Shapes.COMPOUNDSHAPE, shapeC.getShapes("compoundshape".toUpperCase()));
    }

    @Test
    void testClickOnShape() {
        List<SimpleShape> shapesList = new ArrayList<>();
        Circle c = new Circle(0, 0);
        shapesList.add(c);

        assertEquals(c, shapeC.shapeIsSelect(shapesList, 0, 0));
    }

    @Test
    void testClickNoShape() {
        List<SimpleShape> shapesList = new ArrayList<>();
        Circle c = new Circle(0, 0);
        shapesList.add(c);

        assertEquals(null, shapeC.shapeIsSelect(shapesList, 100, 0));
    }

    @Test
    void testClickNoShapeListEmpty() {
        List<SimpleShape> shapesList = new ArrayList<>();
        assertEquals(null, shapeC.shapeIsSelect(shapesList, 100, 0));
    }

    @Test
    void testCreateShape() {
        List<SimpleShape> shapesList = new ArrayList<>();
        shapesList.add(shapeC.createSimpleShape(Shapes.CIRCLE, 0, 0));

        assertEquals(1, shapesList.size());
    }
}

