package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.nio.file.Path;
import java.nio.file.Files;
import org.json.JSONObject;
import org.json.JSONArray;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.SimpleFile;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {

    private enum Shapes {
        SQUARE,
        TRIANGLE,
        CIRCLE,
        COMPOUNDSHAPE
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private final JToolBar toolBar;
    private final JPanel panel;
    private final JLabel label;

    private final JButton jsonExportButton;
    private final JButton xmlExportButton;
    private final JButton jsonImportButton;
    private final JButton xmlImportButton;
    private final JCheckBox groupByCheckBox;

    private transient ActionListener mReusableActionListener = new ShapeActionListener();
    private transient ArrayList<SimpleShape> shapesList = new ArrayList<>();
    private transient List<SimpleShape> shapesListGroupBy = new ArrayList<>();
    private Shapes shapeMenuSelected = Shapes.SQUARE;
    private transient SimpleShape shapeSelected = null;
    private boolean groupBySelected = false;

    private transient JSonVisitor jsonVisitor = new JSonVisitor();
    private transient XMLVisitor  xmlVisitor  = new XMLVisitor();

    private transient java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    /**
     * Tracks buttons to manage the background.
     */
    private Map<Shapes, JButton> buttons = new EnumMap<>(Shapes.class);

    private String nameJsonFile = "shapes.json";
    private String nameXMLFile = "shapes.xml";


    /**
     * Default constructor that populates the main window.
     * @param frameName
     */
    public JDrawingFrame(String frameName) {
        super(frameName);

        // Instantiates components
        toolBar = new JToolBar("Toolbar");
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        label = new JLabel(" ", SwingConstants.LEFT);

        // Fills the panel
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);

        // Add shapes in the menu
        addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));

        // Add buttons in the menu
        groupByCheckBox = new JCheckBox("Grouper");
        toolBar.add(groupByCheckBox);

        jsonExportButton = new JButton("Export as Json");
        toolBar.add(jsonExportButton);

        xmlExportButton = new JButton("Export as XML");
        toolBar.add(xmlExportButton);

        jsonImportButton = new JButton("Import as Json");
        toolBar.add(jsonImportButton);

        xmlImportButton = new JButton("Import as XML");
        toolBar.add(xmlImportButton);

        setPreferredSize(new Dimension(650, 400));
        exportButtonAction();
    }

    private void exportButtonAction() {
        jsonExportButton.addActionListener(e -> writeShapeOnFile(this.jsonVisitor, this.nameJsonFile) );

        xmlExportButton.addActionListener(e -> writeShapeOnFile(this.xmlVisitor, this.nameXMLFile) );

        jsonImportButton.addActionListener(e -> {
            try {
                String fileContent = Files.readString(Path.of(this.nameJsonFile));
                JSONObject jsonObject = new JSONObject(fileContent);
                JSONArray shapesJSON = jsonObject.getJSONArray("shapes");

                List<SimpleShape> list = importShape(shapesJSON);
                list.forEach(shape -> {
                    if(shape.add(shape)) {
                        this.shapesList.add(shape);
                    }
                });
            } catch (IOException exp) {
                logger.warning("JSON Import error : "+exp.getMessage());
            }
        });

        xmlImportButton.addActionListener(e -> {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document xmlDocument = db.parse( Path.of(this.nameXMLFile).toFile().getPath() );
                NodeList shapes = xmlDocument.getElementsByTagName("shape");

                int nbShape = shapes.getLength();
                for (int i = 0; i < nbShape; i++) {
                    Element shapeElement = (Element) shapes.item(i);

                    String type = shapeElement.getElementsByTagName("type").item(0).getTextContent();
                    int x = Integer.parseInt(shapeElement.getElementsByTagName("x").item(0).getTextContent());
                    int y = Integer.parseInt(shapeElement.getElementsByTagName("y").item(0).getTextContent());

                    SimpleShape shapeCreated = this.createShape(Shapes.valueOf(type.toUpperCase()), x, y);
                    if(shapeCreated != null) {
                        this.shapesList.add(shapeCreated);
                    }
                }
            } catch (ParserConfigurationException | IOException | SAXException exp) {
                logger.warning("XML Import error: " + exp.getMessage());
            }
        });

        groupByCheckBox.addActionListener(e -> {
            this.groupBySelected = !this.groupBySelected;

            if(!this.groupBySelected && !this.shapesListGroupBy.isEmpty()) {
                this.shapesList.add(new CompoundShape(this.shapesListGroupBy));
                this.shapesListGroupBy.clear();

                reDrawAll();
            }
        });
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        buttons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(mReusableActionListener);

        if (shapeMenuSelected == null) {
            button.doClick();
        }

        toolBar.add(button);
        toolBar.validate();
        repaint();
    }

    /**
     * TO DO Use the factory to abstract shape creation
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if(shapeSelected == null && !groupBySelected) {
            if (panel.contains(evt.getX(), evt.getY())) {
                this.createShape(shapeMenuSelected, evt.getX(), evt.getY());
            }
        } else {
            this.shapeIsSelect(evt.getX(), evt.getY());

            if(this.shapeSelected != null && this.groupBySelected) {
                this.shapesList.remove(this.shapeSelected);
                this.shapesListGroupBy.add(this.shapeSelected);

                SwingUtilities.invokeLater(() -> this.shapesListGroupBy.forEach(shape -> shape.draw((Graphics2D) panel.getGraphics(), (float) 4.0)) );

                this.shapeSelected = null;
            }
        }
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        // EMPTY
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        label.setText(" ");
        label.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mousePressed(MouseEvent evt) {
        this.shapeIsSelect(evt.getX(), evt.getY());

        if(this.shapeSelected != null) {
            this.shapesList.remove(this.shapeSelected);
            this.shapesList.add(this.shapeSelected);
        }
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        this.shapeSelected = null;
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        if(this.shapeSelected != null && !this.groupBySelected) {
            this.shapeSelected.moveTo(evt.getX(), evt.getY());

            reDrawAll();
        }
    }

    private void reDrawAll() {
        panel.repaint();

        SwingUtilities.invokeLater(() -> this.shapesList.forEach(shape -> shape.draw((Graphics2D) panel.getGraphics(), (float) 2.0) ) );
    }

    private void shapeIsSelect(int x, int y) {
        int i = this.shapesList.size() - 1;

        while (i > -1 && !this.shapesList.get(i).clickedOnShape(x, y)) { i--; }

        if(i > -1 && this.shapesList.get(i).clickedOnShape(x, y)) {
            this.shapeSelected = this.shapesList.get(i);
        }
    }

    private List<SimpleShape> importShape(JSONArray shapesJSON) {
        List<SimpleShape> list = new ArrayList<>();

        shapesJSON.forEach(shapeObject -> {
            JSONObject shape = (JSONObject) shapeObject;
            String type = shape.getString("type").toUpperCase();

            if(type.equals("COMPOUNDSHAPE")) {
                JSONArray childrenShapesJSON = shape.getJSONArray("shapes");
                list.add(new CompoundShape(importShape(childrenShapesJSON)));
            } else {
                int x = shape.getInt("x");
                int y = shape.getInt("y");
                list.add(this.createShape(Shapes.valueOf(type), x, y));
            }
        });

        return list;
    }

    private SimpleShape createShape(Shapes type, int x, int y) {
        Graphics2D g2 = (Graphics2D) panel.getGraphics();
        SimpleShape shapeReturn = null;

        switch(type) {
            case CIRCLE:
                Circle circle = new Circle(x, y);
                circle.draw(g2, (float) 2.0);
                this.shapesList.add(circle);
                shapeReturn = circle;
                break;
            case TRIANGLE:
                Triangle triangle =new Triangle(x, y);
                triangle.draw(g2, (float) 2.0);
                this.shapesList.add(triangle);
                shapeReturn = triangle;
                break;
            case SQUARE:
                Square square = new Square(x, y);
                square.draw(g2, (float) 2.0);
                this.shapesList.add(square);
                shapeReturn = square;
                break;
            default:
            logger.log(Level.WARNING, "Error Add shape null (type: {0})", type);
            break;
        }

        return shapeReturn;
    }

    private void writeShapeOnFile(SimpleFile sf, String nameFile) {
        try {
            this.shapesList.forEach(sf::visit);

            File myObj = new File(nameFile);
            if (myObj.createNewFile()) {
                logger.info("File created: " + myObj.getName());
            }

            FileWriter myWriter = new FileWriter(nameFile);
            writeOnFile(sf, myWriter);

        } catch (IOException exc) {
            logger.warning("An error occurred.");
        }
    }

    private void writeOnFile(SimpleFile sf, FileWriter myWriter) throws IOException {
        try {
            myWriter.write(sf.getRepresentation());
        } catch(Exception e){
            logger.warning("Error when writting on file.");
        } finally {
            myWriter.close();
        }
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        label.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            // Itère sur tous les boutons
            Iterator<Shapes> keys = buttons.keySet().iterator();
            while (keys.hasNext()) {
                Shapes shape = keys.next();
                JButton btn = buttons.get(shape);
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    shapeMenuSelected = shape;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }
        }
    }
}
