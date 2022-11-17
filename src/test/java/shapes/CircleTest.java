package shapes;
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

    @Test
    void testClickOnShape() {
        assertEquals(true, c.clickedOnShape(0,0));
        assertEquals(true, c.clickedOnShape(25,25));
        assertEquals(false, c.clickedOnShape(26,26));
        assertEquals(false, c.clickedOnShape(0,26));
        assertEquals(false, c.clickedOnShape(26,0));
        assertEquals(false, c.clickedOnShape(-26,0));
        assertEquals(false, c.clickedOnShape(0,-26));

    }

    @Test
    void testAddShape() {
        assertEquals(false, c.add(c));
    }
}