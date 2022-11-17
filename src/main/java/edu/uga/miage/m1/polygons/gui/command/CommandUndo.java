package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CommandUndo implements Command {
    public enum TypesCommands {
        CREATE,
        MOVE,
    }

    private TypesCommands typeC;
    private SimpleShape oldShape;
    private SimpleShape newShape;

    public CommandUndo(TypesCommands tc, SimpleShape oldS, SimpleShape newS) {
        oldShape = oldS;
        newShape = newS;
        typeC = tc;
    }

    public TypesCommands getTypeCommand() {
        return typeC;
    }

    public SimpleShape getOldShape() {
        return oldShape;
    }

    public SimpleShape getNewShape() {
        return newShape;
    }

    public void setNewShape(SimpleShape newShape) {
        this.newShape = newShape;
    }

    public void setOldShape(SimpleShape oldShape) {
        this.oldShape = oldShape;
    }

    public List<SimpleShape> makeAction(List<SimpleShape> shapesList) {
        switch(getTypeCommand()) {
            case CREATE:
                shapesList.remove(getNewShape());
                break;

            case MOVE:
                shapesList.remove(getNewShape());
                shapesList.add(getOldShape());
                break;

            default:
                //
        }

        return shapesList;
    }
}
