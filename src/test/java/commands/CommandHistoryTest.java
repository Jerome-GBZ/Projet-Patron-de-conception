package commands;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.command.CommandHistory;
import edu.uga.miage.m1.polygons.gui.command.CommandUndo.TypesCommands;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class CommandHistoryTest {
    CommandHistory cmdHist = new CommandHistory();

    @Test
    void test_histCommandsEmpty() {
        assertEquals(true, cmdHist.isEmpty());
    }

    @Test
    void test_addCommand() {
        Circle cBefore = new Circle(0, 0);
        cmdHist.add(TypesCommands.CREATE, null, cBefore);
        Circle cAfter = new Circle(10, 100);

        cmdHist.add(TypesCommands.MOVE, cBefore, cAfter);

        assertEquals(false, cmdHist.isEmpty());
    }

    @Test
    void testUndo() {
        List<SimpleShape> shapesList = new ArrayList<>();
        Circle cBefore = new Circle(0, 0);
        cmdHist.add(TypesCommands.CREATE, null, cBefore);
        cBefore.moveTo(100, 200);
        cmdHist.add(TypesCommands.MOVE, cBefore, cBefore);

        shapesList.add(cBefore);
        shapesList = cmdHist.undo(shapesList);

        assertEquals(1, cmdHist.getHistory().size());
        assertEquals(1, shapesList.size());
    }

    @Test
    void testUndoUndo() {
        List<SimpleShape> shapesList = new ArrayList<>();
        CommandHistory cmdHist = new CommandHistory();

        Circle cBefore = new Circle(0, 0);
        cmdHist.add(TypesCommands.CREATE, null, cBefore);
        cBefore.moveTo(100, 200);
        cmdHist.add(TypesCommands.MOVE, cBefore, cBefore);

        shapesList.add(cBefore);
        shapesList = cmdHist.undo(shapesList);
        shapesList = cmdHist.undo(shapesList);

        assertEquals(true, cmdHist.isEmpty());
        assertEquals(true, shapesList.isEmpty());
    }
}