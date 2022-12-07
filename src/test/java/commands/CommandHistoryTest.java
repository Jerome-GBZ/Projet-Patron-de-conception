package commands;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ShapePackage.shapes.Circle;
import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.command.CreateCommand;
import edu.uga.miage.m1.polygons.gui.command.MoveCommand;
import edu.uga.miage.m1.polygons.gui.controllers.HistoryController;

class CommandHistoryTest {
    HistoryController cmdHist = new HistoryController();

    @Test
    void test_histCommandsEmpty() {
        assertEquals(true, cmdHist.isEmpty());
    }

    @Test
    void test_addCommand() {
        Circle cBefore = new Circle(0, 0);
        cmdHist.add(new CreateCommand(null, cBefore));

        Circle cAfter = new Circle(10, 100);
        cmdHist.add(new MoveCommand(cBefore, cAfter));

        assertEquals(false, cmdHist.isEmpty());
    }

    @Test
    void testAddCommand() {
        Circle cBefore = new Circle(0, 0);
        cmdHist.add(new CreateCommand(null, cBefore));

        assertEquals(1, cmdHist.getHistory().size());
    }

    @Test
    void testUndoHistoryEmpty() {
        List<SimpleShape> shapesList = new ArrayList<>();
        shapesList = cmdHist.undo(shapesList);

        assertEquals(true, cmdHist.isEmpty());
    }

    @Test
    void testUndo() {
        List<SimpleShape> shapesList = new ArrayList<>();

        Circle cBefore = new Circle(0, 0);
        cmdHist.add(new CreateCommand(null, cBefore));

        Circle cAfter = new Circle(10, 100);
        cmdHist.add(new MoveCommand(cBefore, cAfter));

        shapesList.add(cAfter);
        shapesList = cmdHist.undo(shapesList);

        assertEquals(1, cmdHist.getHistory().size());
        // assertEquals(1, shapesList.size());
    }

    @Test
    void testUndoUndo() {
        List<SimpleShape> shapesList = new ArrayList<>();

        Circle cBefore = new Circle(0, 0);
        cmdHist.add(new CreateCommand(null, cBefore));

        cBefore.moveTo(100, 200);
        cmdHist.add(new MoveCommand(cBefore, cBefore));

        shapesList.add(cBefore);
        shapesList = cmdHist.undo(shapesList);
        shapesList = cmdHist.undo(shapesList);

        assertEquals(0, cmdHist.getHistory().size());
    }
}