import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class JDrawingFrameTest {
    JDrawingFrame jdf = new JDrawingFrame("test");

    @Test
    void test_CreateForm () {
        // SimpleShape shape = jdf.createShape(JDrawingFrame.Shapes.CIRCLE, 10, 40);
        //  assertEquals(10, shape.getX());
        // assertEquals(40, shape.getY());
    }
}
