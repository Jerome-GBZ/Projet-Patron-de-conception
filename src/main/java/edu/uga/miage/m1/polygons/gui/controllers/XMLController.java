package edu.uga.miage.m1.polygons.gui.controllers;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.text.html.HTMLDocument.RunElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.factories.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class XMLController {

    public List<SimpleShape> importShape(Document xmlDocument) throws XPathExpressionException {
        List<SimpleShape> list = new ArrayList<>();
        ShapeFactory shapeFac = new ShapeFactory();

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList compoundshapeList = (NodeList) xPath.compile("//shape[@type='compoundshape']/shapes").evaluate(xmlDocument, XPathConstants.NODESET);

        if(compoundshapeList.getLength() > 0) {
            System.out.println(compoundshapeList.getLength());
            for (int i = 0; i < compoundshapeList.getLength(); i++) {
                CompoundShape cShape = new CompoundShape(new ArrayList<SimpleShape>());

                Element shapeElement = (Element) compoundshapeList.item(i);
                NodeList shapes = shapeElement.getChildNodes();

                for (int j = 1; j < shapes.getLength(); j += 2) {
                    Element shape = (Element) shapes.item(j);

                    String type = shape.getAttribute("type");
                    int x = Integer.parseInt(shape.getElementsByTagName("x").item(0).getTextContent());
                    int y = Integer.parseInt(shape.getElementsByTagName("y").item(0).getTextContent());

                    cShape.addShape(shapeFac.createSimpleShape(shapeFac.getShapes(type), x, y));
                }

                if (cShape.getShapes().size() > 1) {
                    list.add(cShape);
                } else if(cShape.getShapes().size() == 1) {
                    list.add(cShape.getShapes().get(0));
                }
            }
        }

        NodeList shapeList = (NodeList) xPath.compile("/root/shapes/shape[not(@type='compoundshape')]").evaluate(xmlDocument, XPathConstants.NODESET);

        for (int i = 0; i < shapeList.getLength(); i++) {
            Element shapeElement = (Element) shapeList.item(i);

            String type = shapeElement.getAttribute("type");
            int x = Integer.parseInt(shapeElement.getElementsByTagName("x").item(0).getTextContent());
            int y = Integer.parseInt(shapeElement.getElementsByTagName("y").item(0).getTextContent());

            SimpleShape shapeCreated = shapeFac.createSimpleShape(shapeFac.getShapes(type), x, y);

            if(shapeCreated != null) {
                list.add(shapeCreated);
            }
        }

        return list;
    }

    public List<SimpleShape> getXMLFile(String nameXMLFile) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmlDocument = db.parse( Path.of(nameXMLFile).toFile().getPath() );

        return importShape(xmlDocument);
    }
}
