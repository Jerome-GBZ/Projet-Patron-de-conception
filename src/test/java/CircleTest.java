import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;

class CircleTest{
    Circle c = new Circle(0, 0);

    @Test
    void test_getX (){
        assertEquals(0, c.getX());
    }

    @Test
    void test_getY (){
        assertEquals(0, c.getY());
    }

    @Test
    void test_moveTo() {
        c.moveTo(10, 5);
        assertEquals(10, c.getX());
        assertEquals(5, c.getY());
    }
}