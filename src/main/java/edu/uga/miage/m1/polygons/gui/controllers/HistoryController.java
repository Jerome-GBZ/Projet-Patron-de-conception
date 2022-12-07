package edu.uga.miage.m1.polygons.gui.controllers;

import java.util.ArrayList;
import java.util.List;

import ShapePackage.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.command.Command;

public class HistoryController {
    private List<Command> history = new ArrayList<>();

    public void pop(Command lastC) {
        history.remove(lastC);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public List<SimpleShape> undo(List<SimpleShape> shapesList) {
        if(!isEmpty()) {
            Command lastCommand = history.get(history.size() - 1);
            shapesList = lastCommand.execute(shapesList);
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

    public void add(Command c) {
        history.add(c);
    }

    public List<Command> getHistory() {
        return history;
    }
}
