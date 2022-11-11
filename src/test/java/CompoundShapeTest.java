import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

class CompoundShapeTest {
    Circle c = new Circle(0, 0);
    Square s = new Square(50, 100);
    List<SimpleShape> list = new ArrayList<>();

    @Test
    void test_moveTo() {
        list.add(c);
        list.add(s);
        CompoundShape cs = new CompoundShape(list);

        assertEquals(2, cs.getShapes().size());
    }
}