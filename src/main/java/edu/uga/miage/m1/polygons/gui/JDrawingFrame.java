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
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import ShapePackage.shapes.CompoundShape;
import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.SimpleFile;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.command.CreateCommand;
import edu.uga.miage.m1.polygons.gui.command.MoveCommand;
import edu.uga.miage.m1.polygons.gui.controllers.FileController;
import edu.uga.miage.m1.polygons.gui.controllers.HistoryController;
import edu.uga.miage.m1.polygons.gui.controllers.JSonController;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController;
import edu.uga.miage.m1.polygons.gui.controllers.XMLController;
import edu.uga.miage.m1.polygons.gui.controllers.ShapeController.Shapes;

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

    private transient ShapeController shapeController = new ShapeController();
    private transient HistoryController histController = new HistoryController();
    private transient JSonController jsonController = new JSonController();
    private transient XMLController xmlController = new XMLController();
    private transient FileController fileController = new FileController();

    private transient ActionListener reusableActionListener = new ShapeActionListener();
    private Shapes shapeMenuSelected = shapeController.getShapes("SQUARE");

    private transient List<SimpleShape> shapesList = new ArrayList<>();
    private transient List<SimpleShape> shapesListGroupBy = new ArrayList<>();
    private transient SimpleShape shapeSelected = null;
    private transient SimpleShape oldShapeSelected = null;
    private boolean groupBySelected = false;

    private transient JSonVisitor jsonVisitor = new JSonVisitor();
    private transient XMLVisitor  xmlVisitor  = new XMLVisitor();

    private String nameJsonFile = "shapes.json";
    private String nameXMLFile = "shapes.xml";

    private transient Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Tracks buttons to manage the background.
     */
    private Map<Shapes, JButton> buttons = new EnumMap<>(Shapes.class);


    /**
     * Default constructor that populates the main window.
     * @param frameName
     * @throws SAXException
     * @throws ParserConfigurationException
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
        addShapeMenu(Shapes.FIGMA, new ImageIcon(getClass().getResource("images/figma.png")));

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

        setPreferredSize(new Dimension(800, 600));
        exportButtonAction();
    }

    private void exportButtonAction() {
        jsonExportButton.addActionListener(e -> writeShapeOnFile(this.jsonVisitor, this.nameJsonFile) );

        xmlExportButton.addActionListener(e -> writeShapeOnFile(this.xmlVisitor, this.nameXMLFile) );

        jsonImportButton.addActionListener(e -> {
            try {
                List<SimpleShape> list = jsonController.getJSonFile(nameJsonFile);

                list.forEach(shape -> {
                    if(shape.add(shape)) {
                        this.shapesList.add(shape);
                    }
                });

                reDrawAll();
            } catch (IOException exp) {
                logger.warning("JSon Import error: " + exp.getMessage());
            }
        });

        xmlImportButton.addActionListener(e -> {
            try {
                List<SimpleShape> list = xmlController.getXMLFile(nameXMLFile);

                list.forEach(shape -> {
                    shapesList.add(shape);
                });

                reDrawAll();
            } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException exp) {
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
            shapesList = histController.undo(shapesList);
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
            histController.add(new MoveCommand(oldShapeSelected, shapeSelected));

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
        shapeSelected = shapeController.shapeIsSelect(shapesList, x, y);
        if(shapeSelected != null) {
            oldShapeSelected = shapeController.createSimpleShape(shapeController.getShapes(shapeSelected.getClass().getSimpleName()), shapeSelected.getX(), shapeSelected.getY());
        }
    }

    public SimpleShape createShape(Shapes type, int x, int y) {
        Graphics2D g2 = (Graphics2D) this.getPanel().getGraphics();
        SimpleShape shapeReturn = shapeController.createSimpleShape(type, x, y);

        if(shapeReturn != null) {
            shapeReturn.draw(g2, (float) 2.0);
            shapesList.add(shapeReturn);

            histController.add(new CreateCommand(null, shapeReturn));
        }

        return shapeReturn;
    }

    private void writeShapeOnFile(SimpleFile sf, String nameFile) {
        sf.clear();
        fileController.writeOnFile(shapesList, sf, nameFile);
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
