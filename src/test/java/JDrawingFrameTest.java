import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController;
import ShapePackage.shapes.ShapesEnum.Shapes;

class JDrawingFrameTest {
    JDrawingFrame jDrawFrame = new JDrawingFrame("test");
    ShapeController shapeController = new ShapeController();

/*
    @Test
    void testCreateForm () {
        SimpleShape shape = jdf.createShape(jdf.getShapes("circle"), 10, 40);
        assertEquals(10, shape.getX());
        assertEquals(40, shape.getY());
    }
 */

    @Test
    void testPanel() {
        assertNotNull(jDrawFrame.getPanel());
    }

    @Test
    void testEnumSahpes() {
        assertEquals(Shapes.CIRCLE,  shapeController.getShapes("circle"));
        assertEquals(Shapes.SQUARE,  shapeController.getShapes("square"));
        assertEquals(Shapes.TRIANGLE,  shapeController.getShapes("triangle"));
    }

    @Test
    void testToolBar() {
        JToolBar toolBar = jDrawFrame.getToolBar();
        assertNotNull(toolBar);
    }
}
