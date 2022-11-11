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
}
