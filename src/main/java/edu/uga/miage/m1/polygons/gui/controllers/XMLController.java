package edu.uga.miage.m1.polygons.gui.controllers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class XMLController {

    private ShapeController shapeController = new ShapeController();

    private List<SimpleShape> importShape(Document xmlDocument) throws XPathExpressionException {
        List<SimpleShape> list = new ArrayList<>();

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList compoundshapeList = (NodeList) xPath.compile("//shape[@type='compoundshape']/shapes").evaluate(xmlDocument, XPathConstants.NODESET);

        if(compoundshapeList.getLength() > 0) {
            System.out.println(compoundshapeList.getLength());
            for (int i = 0; i < compoundshapeList.getLength(); i++) {
                CompoundShape cShape = new CompoundShape(new ArrayList<SimpleShape>());
                NodeList shapes = ((Element) compoundshapeList.item(i)).getChildNodes();

                for (int j = 1; j < shapes.getLength(); j += 2) {
                    cShape.addShape(getSimpleShape( (Element) shapes.item(i) ));
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
            list.add(getSimpleShape( (Element) shapeList.item(i) ));
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

    private SimpleShape getSimpleShape(Element shapeElement) {
        String type = shapeElement.getAttribute("type");
        int x = Integer.parseInt(shapeElement.getElementsByTagName("x").item(0).getTextContent());
        int y = Integer.parseInt(shapeElement.getElementsByTagName("y").item(0).getTextContent());

        SimpleShape shape = shapeController.createSimpleShape(shapeController.getShapes(type), x, y);

        return shape;
    }
}
