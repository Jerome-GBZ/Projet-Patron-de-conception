package factories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.factory.FileFactory;

class FileFactoryTest {

    @Test
    void testConstructorFileFactory() {
        // Write test
        FileFactory fileFac = new FileFactory();
        assertNotNull(fileFac);
    }
}
