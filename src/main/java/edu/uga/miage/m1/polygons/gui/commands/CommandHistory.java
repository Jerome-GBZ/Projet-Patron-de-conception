package edu.uga.miage.m1.polygons.gui.commands;

import java.util.ArrayList;
import java.util.List;
import edu.uga.miage.m1.polygons.gui.commands.Command;
import edu.uga.miage.m1.polygons.gui.commands.CommandUndo.TypesCommands;
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
                int i = history.size() - 1;
                while(i > -1 && lastCommand.getNewShape() != history.get(i).getNewShape()) {
                    i--;
                }

                if(i > -1 ) {
                    history.get(i).setNewShape(lastCommand.getOldShape());
                }
            }
        }

        return shapesList;
    }

    public void add(TypesCommands typeC, SimpleShape oldShape, SimpleShape newShape) {
        history.add(new CommandUndo(typeC, oldShape, newShape));
    }
}
