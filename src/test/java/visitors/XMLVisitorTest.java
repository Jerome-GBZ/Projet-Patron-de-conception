package visitors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

import org.junit.jupiter.api.Test;

class XMLVisitorTest {
    Circle c = new Circle(50, 200);
    Square s = new Square(100, 100);
    XMLVisitor visitor = new XMLVisitor();

    @Test
    void testVisitClasic() {
        assertNotNull(visitor);
        assertNotNull(c);
        visitor.visit(c);

        String expectedR = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root> <shapes> <shape type='circle'> <x>50</x> <y>200</y> </shape> </shapes> </root>";
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

        String expectedR = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root> <shapes> <shape type='compoundshape'> <shapes> <shape type='circle'> <x>50</x> <y>200</y> </shape> <shape type='square'> <x>100</x> <y>100</y> </shape> </shapes> </shape> </shapes> </root>";
        assertEquals(expectedR, visitor.getRepresentation());
    }
}