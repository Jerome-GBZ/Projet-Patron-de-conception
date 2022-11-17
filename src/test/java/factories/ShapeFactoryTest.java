package factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.factory.ShapeFactory;

class ShapeFactoryTest {

    @Test
    void testConstructorShapeFactory() {
        // Write test
        ShapeFactory shapeFac = new ShapeFactory();
        assertNotNull(shapeFac);
    }
}
