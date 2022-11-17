package edu.uga.miage.m1.polygons.gui.commands;

import java.util.ArrayList;
import java.util.List;
import edu.uga.miage.m1.polygons.gui.commands.Command.TypesCommands;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CommandHistory {
    private List<Command> history = new ArrayList<>();

    public void push(Command c) {
        history.add(c);
    }

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
            System.out.println(lastCommand.getNewShape());

            shapesList = lastCommand.makeAction(shapesList);

            System.out.println("lastCommand ");
            System.out.println(lastCommand.getTypeCommand()+" new: "+lastCommand.getNewShape()+" old: "+lastCommand.getOldShape());
            System.out.println("shapesList");
            shapesList.forEach(s -> {
                    System.out.println(s);
            });
            System.out.println("");

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
        history.add(new Command(typeC, oldShape, newShape));

        System.out.println("history");
        history.forEach(h -> {
            System.out.println(h.getTypeCommand()+" "+h.getNewShape()+" "+h.getOldShape());
        });
        System.out.println("");
    }
}
