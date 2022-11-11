import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

class SquareTest {
    Square s = new Square(0, 0);

    @Test
    void test_getX (){
        assertEquals(0, s.getX());
    }

    @Test
    void test_getY (){
        assertEquals(0, s.getY());
    }

    @Test
    void test_moveTo() {
        s.moveTo(10, 5);
        assertEquals(10, s.getX());
        assertEquals(5, s.getY());
    }

    @Test
    void testClickOnShape() {
        assertEquals(true, s.clickedOnShape(0,0));
        assertEquals(true, s.clickedOnShape(25,25));
        assertEquals(false, s.clickedOnShape(26,26));
        assertEquals(false, s.clickedOnShape(0,26));
        assertEquals(false, s.clickedOnShape(26,0));
        assertEquals(false, s.clickedOnShape(-26,0));
        assertEquals(false, s.clickedOnShape(0,-26));
    }

    @Test
    void testAddShape() {
        assertEquals(false, s.add(s));
    }
}
