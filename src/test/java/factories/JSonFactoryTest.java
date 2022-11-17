package factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.factory.JSonFactory;

class JSonFactoryTest {

    @Test
    void testConstructorJSonFactory() {
        // Write test
        JSonFactory jsonFac = new JSonFactory();
        assertNotNull(jsonFac);
    }
}
