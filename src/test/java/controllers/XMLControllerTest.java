package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.controllers.XMLController;

class XMLControllerTest {
    XMLController xmlC = new XMLController();

    @Test
    void testImportXmlCircle() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        File f = new File("shapesTest.xml");
        f.createNewFile();

        FileWriter writer = new FileWriter(f);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root> <shapes> <shape type='circle'> <x>50</x> <y>200</y> </shape> </shapes> </root>");
        writer.close();

        List<SimpleShape> list = xmlC.getXMLFile("shapesTest.xml");

        assertEquals(1, list.size());

        f.delete();
    }

    @Test
    void testImportXmlCompoundShape() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        File f = new File("shapesTest.xml");
        f.createNewFile();

        FileWriter writer = new FileWriter(f);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <root> <shapes> <shape type='compoundshape'> <shapes> <shape type='square'> <x>386</x> <y>121</y> </shape> <shape type='circle'> <x>420</x> <y>148</y> </shape> </shapes> </shape> </shapes> </root>");
        writer.close();

        List<SimpleShape> list = xmlC.getXMLFile("shapesTest.xml");

        assertEquals(1, list.size());

        f.delete();
    }
}
