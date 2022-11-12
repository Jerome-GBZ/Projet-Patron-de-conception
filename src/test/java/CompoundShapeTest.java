import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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


        List<SimpleShape> groupList = cs.getShapes();
        int x = 200;
        int y = 50;
        int translationX = x - groupList.get(0).getX();
        int translationY = y - groupList.get(0).getY();

        int resCX = c.getX() + translationX;
        int resCY = c.getY() + translationY;
        int resSX = s.getX() + translationX;
        int resSY = s.getY() + translationY;

        assertEquals(2, cs.getShapes().size());

        cs.moveTo(x, y);

        assertEquals(resCX, c.getX());
        assertEquals(resCY, c.getY());

        assertEquals(resSX, s.getX());
        assertEquals(resSY, s.getY());
    }

    @Test
    void testClickOnShape() {
        list.add(c);
        list.add(s);
        CompoundShape cs = new CompoundShape(list);

        assertEquals(true, cs.clickedOnShape(0,0));
        assertEquals(true, cs.clickedOnShape(25,25));
        assertEquals(false, cs.clickedOnShape(26,26));
        assertEquals(false, cs.clickedOnShape(0,26));
        assertEquals(false, cs.clickedOnShape(26,0));
        assertEquals(false, cs.clickedOnShape(-26,0));
        assertEquals(false, cs.clickedOnShape(0,-26));
    }

    @Test
    void testAddShapeCompound() {
        list.add(c);
        list.add(s);
        CompoundShape cs1 = new CompoundShape(list);
        CompoundShape cs2 = new CompoundShape(new ArrayList<SimpleShape>());

        assertEquals(true, cs2.add(cs1));
        assertNotNull(cs2.getShapes());
    }

    @Test
    void testGetX() {
        CompoundShape cs = new CompoundShape(list);
        assertEquals(0, cs.getX());
    }

    @Test
    void testGetY() {
        CompoundShape cs = new CompoundShape(list);
        assertEquals(0, cs.getY());
    }
}