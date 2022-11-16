import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.factory.ShapeFactory;

class JDrawingFrameTest {
    JDrawingFrame jDrawFrame = new JDrawingFrame("test");
    ShapeFactory shapeFac = new ShapeFactory();

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
        assertEquals(ShapeFactory.Shapes.CIRCLE,  shapeFac.getShapes("circle"));
        assertEquals(ShapeFactory.Shapes.SQUARE,  shapeFac.getShapes("square"));
        assertEquals(ShapeFactory.Shapes.TRIANGLE,  shapeFac.getShapes("triangle"));
    }

    @Test
    void testToolBar() {
        JToolBar toolBar = jDrawFrame.getToolBar();
        assertNotNull(toolBar);
    }
}
