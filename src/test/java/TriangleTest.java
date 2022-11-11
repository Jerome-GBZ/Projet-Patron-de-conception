import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

class TriangleTest {
    Triangle t = new Triangle(0, 0);

    @Test
    void test_getX (){
        assertEquals(0, t.getX());
    }

    @Test
    void test_getY (){
        assertEquals(0, t.getY());
    }

    @Test
    void test_moveTo() {
        t.moveTo(10, 5);
        assertEquals(10, t.getX());
        assertEquals(5, t.getY());
    }

    @Test
    void testClickOnShape() {
        assertEquals(true, t.clickedOnShape(0,0));
        assertEquals(true, t.clickedOnShape(25,25));
        assertEquals(false, t.clickedOnShape(26,26));
        assertEquals(false, t.clickedOnShape(0,26));
        assertEquals(false, t.clickedOnShape(26,0));
        assertEquals(false, t.clickedOnShape(-26,0));
        assertEquals(false, t.clickedOnShape(0,-26));
    }

    @Test
    void testAddShape() {
        assertEquals(false, t.add(t));
    }
}
