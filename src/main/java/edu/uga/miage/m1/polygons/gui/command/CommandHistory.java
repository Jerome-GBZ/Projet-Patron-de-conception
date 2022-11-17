package edu.uga.miage.m1.polygons.gui.command;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.command.CommandUndo.TypesCommands;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CommandHistory {
    private List<Command> history = new ArrayList<>();

    /*
     * Remove last element of history
     */
    public void pop(Command lastC) {
        history.remove(lastC);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public List<SimpleShape> undo(List<SimpleShape> shapesList) {
        if(!isEmpty()) {
            Command lastCommand = history.get(history.size() - 1);
            shapesList = lastCommand.makeAction(shapesList);
            pop(lastCommand);

            if(!isEmpty()) {
                for (Command cmd : history) {
                    if(lastCommand.getNewShape() == cmd.getNewShape()) {
                        cmd.setNewShape(lastCommand.getOldShape());
                    }
                }
            }
        }

        return shapesList;
    }

    public void add(TypesCommands typeC, SimpleShape oldShape, SimpleShape newShape) {
        history.add(new CommandUndo(typeC, oldShape, newShape));
    }

    public List<Command> getHistory() {
        return history;
    }
}
