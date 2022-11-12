import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class JDrawingFrameTest {
    JDrawingFrame jdf = new JDrawingFrame("test");

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
        assertNotNull(jdf.getPanel());
    }

    @Test
    void testEnumSahpes() {
        assertEquals(JDrawingFrame.Shapes.CIRCLE,  jdf.getShapes("circle"));
        assertEquals(JDrawingFrame.Shapes.SQUARE,  jdf.getShapes("square"));
        assertEquals(JDrawingFrame.Shapes.TRIANGLE,  jdf.getShapes("triangle"));
    }

    @Test
    void testToolBar() {
        JToolBar toolBar = jdf.getToolBar();
        assertNotNull(toolBar);
    }
}
