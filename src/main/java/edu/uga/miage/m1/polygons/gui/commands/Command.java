package edu.uga.miage.m1.polygons.gui.commands;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class Command {
    public enum TypesCommands {
        CREATE,
        MOVE,
    }

    private TypesCommands typeC;
    private SimpleShape oldShape;
    private SimpleShape newShape;

    public Command(TypesCommands tc, SimpleShape oldS, SimpleShape newS) {
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
                // System.out.println(shapesList.get(0));
                // System.out.println(getNewShape());
                // System.out.println("");

                shapesList.remove(getNewShape());
                break;

            case MOVE:
                // System.out.println("shapesList");
                // shapesList.forEach(s -> {
                //     System.out.println(s);
                // });
                // System.out.println("");
                // System.out.println("command");
                // System.out.println(shapesList.get(0));
                // System.out.println(getNewShape());
                // System.out.println(getOldShape());
                // System.out.println("");

                shapesList.remove(getNewShape());
                shapesList.add(getOldShape());
                break;

            default:
                //
        }

        return shapesList;
    }
}
