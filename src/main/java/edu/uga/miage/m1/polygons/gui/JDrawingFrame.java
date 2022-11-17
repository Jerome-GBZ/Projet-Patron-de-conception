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
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.nio.file.Path;
import java.nio.file.Files;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.json.JSONObject;
import org.json.JSONArray;

import edu.uga.miage.m1.polygons.gui.factory.JSonFactory;
import edu.uga.miage.m1.polygons.gui.factory.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.factory.ShapeFactory.Shapes;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.SimpleFile;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.commands.CommandHistory;
import edu.uga.miage.m1.polygons.gui.commands.Command.TypesCommands;
import edu.uga.miage.m1.polygons.gui.factory.FileFactory;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    private final JToolBar toolBar;
    private final JPanel panel;
    private final JLabel label;

    private final JButton undoButton;
    private final JButton jsonExportButton;
    private final JButton xmlExportButton;
    private final JButton jsonImportButton;
    private final JButton xmlImportButton;
    private final JCheckBox groupByCheckBox;

    private transient ShapeFactory shapeFac = new ShapeFactory();
    private transient JSonFactory jsonFac = new JSonFactory();
    private transient FileFactory fileFac = new FileFactory();
    private transient CommandHistory cmdHist = new CommandHistory();

    private transient ActionListener reusableActionListener = new ShapeActionListener();
    private transient List<SimpleShape> shapesList = new ArrayList<>();
    private transient List<SimpleShape> shapesListGroupBy = new ArrayList<>();
    private Shapes shapeMenuSelected = shapeFac.getShapes("SQUARE");
    private transient SimpleShape shapeSelected = null;
    private transient SimpleShape oldShapeSelected = null;
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

        toolBar.setFloatable(false);

        // Fills the panel
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);

        // Add shapes in the menu
        addShapeMenu(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShapeMenu(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShapeMenu(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));

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

        Icon icon = new ImageIcon(getClass().getResource("icons/undo.png"));
        undoButton = new JButton(icon);
        toolBar.add(undoButton);

        setPreferredSize(new Dimension(700, 600));
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

                List<SimpleShape> list = jsonFac.importShape(shapesJSON);
                list.forEach(shape -> {
                    if(shape.add(shape)) {
                        this.shapesList.add(shape);
                    }
                });

                reDrawAll();
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
                NodeList shapes = xmlDocument.getElementsByTagName("shapes");
                System.out.println(shapes);

                int nbShape = shapes.getLength();

                for (int i = 0; i < nbShape; i++) {
                    Element shapeElement = (Element) shapes.item(i);

                    String type = shapeElement.getElementsByTagName("type").item(0).getTextContent();
                    int x = Integer.parseInt(shapeElement.getElementsByTagName("x").item(0).getTextContent());
                    int y = Integer.parseInt(shapeElement.getElementsByTagName("y").item(0).getTextContent());

                    System.out.println(type);

                    SimpleShape shapeCreated = this.createShape(shapeFac.getShapes(type), x, y);
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

        undoButton.addActionListener(e -> {
            shapesList = cmdHist.undo(shapesList);
            reDrawAll();
        });
    }

    private void addShapeMenu(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);

        button.setBorderPainted(false);
        buttons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(reusableActionListener);

        if (shapeMenuSelected == null) {
            button.doClick();
        }

        toolBar.add(button);
        toolBar.validate();
        repaint();
    }

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

    public void mouseEntered(MouseEvent evt) {
        // EMPTY
    }

    public void mouseExited(MouseEvent evt) {
        label.setText(" ");
        label.repaint();
    }

    public void mousePressed(MouseEvent evt) {
        this.shapeIsSelect(evt.getX(), evt.getY());

        if(this.shapeSelected != null) {
            this.shapesList.remove(this.shapeSelected);
            this.shapesList.add(this.shapeSelected);
        }
    }

    public void mouseReleased(MouseEvent evt) {
        if(shapeSelected != null && oldShapeSelected != null ) {
            cmdHist.add(TypesCommands.MOVE, oldShapeSelected, shapeSelected);

            System.out.println("shapesList");
            shapesList.forEach(s -> {
                System.out.println(s);
            });
            System.out.println("");

            reDrawAll();
        }

        this.shapeSelected = null;
    }

    public void mouseDragged(MouseEvent evt) {
        if(shapeSelected != null && !this.groupBySelected) {
            shapeSelected.moveTo(evt.getX(), evt.getY());

            reDrawAll();
        }
    }

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

    private void shapeIsSelect(int x, int y) {
        shapeSelected = shapeFac.shapeIsSelect(shapesList, x, y);
        if(shapeSelected != null) {
            oldShapeSelected = shapeFac.createSimpleShape(shapeFac.getShapes(shapeSelected.getClass().getSimpleName()), shapeSelected.getX(), shapeSelected.getY());
            // System.out.println("shapeIsSelect");
            // System.out.println(shapesList.get(0));
            // System.out.println(oldShapeSelected);
            // System.out.println(shapeSelected);
            // System.out.println("");
        }
    }

    public SimpleShape createShape(Shapes type, int x, int y) {
        Graphics2D g2 = (Graphics2D) this.getPanel().getGraphics();
        SimpleShape shapeReturn = shapeFac.createSimpleShape(type, x, y);

        if(shapeReturn != null) {
            shapeReturn.draw(g2, (float) 2.0);
            shapesList.add(shapeReturn);

            // System.out.println("createShape");
            // System.out.println(shapesList.get(0));
            // System.out.println(shapeReturn);
            // System.out.println("");

            cmdHist.add(TypesCommands.CREATE, null, shapeReturn);

            System.out.println("shapesList");
            shapesList.forEach(s -> {
                System.out.println(s);
            });
            System.out.println("");
        }

        return shapeReturn;
    }

    private void writeShapeOnFile(SimpleFile sf, String nameFile) {
        fileFac.writeOnFile(shapesList, sf, nameFile);
    }

    private void reDrawAll() {
        panel.repaint();

        SwingUtilities.invokeLater(() -> this.shapesList.forEach(shape -> shape.draw((Graphics2D) panel.getGraphics(), (float) 2.0) ) );
    }

    public JPanel getPanel() {
        return panel;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
