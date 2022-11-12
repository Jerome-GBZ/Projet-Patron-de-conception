import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

import org.junit.jupiter.api.Test;

class JSonVisitorTest {
    Circle c = new Circle(50, 200);
    Square s = new Square(100, 100);
    JSonVisitor visitor = new JSonVisitor();

    @Test
    void testVisitClasic() {
        assertNotNull(visitor);
        assertNotNull(c);
        visitor.visit(c);

        String expectedR = "{ \"shapes\":[ { \"type\": \"circle\", \"x\": 50, \"y\": 200 }] }";
        assertEquals(expectedR, visitor.getRepresentation());
    }

    @Test
    void testVisitGroup() {
        assertNotNull(visitor);
        assertNotNull(c);
        assertNotNull(s);

        List<SimpleShape> list = new ArrayList<>();
        list.add(c);
        list.add(s);
        CompoundShape cs = new CompoundShape(list);

        visitor.visit(cs);

        String expectedR = "{ \"shapes\":[ { \"type\": \"compoundshape\", \"shapes\": [{ \"type\": \"circle\", \"x\": 50, \"y\": 200 }, { \"type\": \"square\", \"x\": 100, \"y\": 100 }, ]}] }";
        assertEquals(expectedR, visitor.getRepresentation());
    }
}