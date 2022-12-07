package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.controllers.JSonController;

class JSonControllerTest {
    JSonController jsonC = new JSonController();

    @Test
    void testImportJSonCircle() throws IOException {
        File f = new File("shapesTest.json");

        f.createNewFile();

        FileWriter writer = new FileWriter(f);
        writer.write("{ \"shapes\":[ { \"type\": \"circle\", \"x\": 50, \"y\": 200 }] }");
        writer.close();

        List<SimpleShape> list = jsonC.getJSonFile("shapesTest.json");

        assertEquals(1, list.size());

        f.delete();
    }

    @Test
    void testImportJSonCompoundShape() throws IOException {
        File f = new File("shapesTest.json");

        f.createNewFile();

        FileWriter writer = new FileWriter(f);
        writer.write("{ \"shapes\":[ { \"type\": \"compoundshape\", \"shapes\": [{ \"type\": \"circle\", \"x\": 50, \"y\": 200 }, { \"type\": \"square\", \"x\": 100, \"y\": 100 }, ]}] }");
        writer.close();

        List<SimpleShape> list = jsonC.getJSonFile("shapesTest.json");

        assertEquals(1, list.size());

        f.delete();
    }
}
