package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import ShapePackage.shapes.Circle;
import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.controllers.FileController;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;

class FileControllerTest {
    FileController fileController = new FileController();

    @Test
    void testConstructorFileController() {
        assertNotNull(fileController);
    }

    @Test
    void testCreationFileAndWriteOnFile() throws FileNotFoundException, IOException {
        List<SimpleShape> list = new ArrayList<>();
        list.add(new Circle(10, 40));

        fileController.writeOnFile(list, new JSonVisitor(), "shapesTest.json");

        try (FileReader reader = new FileReader("shapesTest.json")) {
            assertEquals("{ \"shapes\":[ { \"type\": \"circle\", \"x\": 10, \"y\": 40 }] }", Files.readString(Path.of("shapesTest.json")));
        }

        File f = new File("shapesTest.json");
        f.delete();
    }

    @Test
    void testWriteOnFile() throws FileNotFoundException, IOException {
        List<SimpleShape> list = new ArrayList<>();
        list.add(new Circle(10, 40));

        File f = new File("shapesTest.json");
        f.createNewFile();

        fileController.writeOnFile(list, new JSonVisitor(), "shapesTest.json");

        try (FileReader reader = new FileReader("shapesTest.json")) {
            assertEquals("{ \"shapes\":[ { \"type\": \"circle\", \"x\": 10, \"y\": 40 }] }", Files.readString(Path.of("shapesTest.json")));
        }

        f.delete();
    }
}
