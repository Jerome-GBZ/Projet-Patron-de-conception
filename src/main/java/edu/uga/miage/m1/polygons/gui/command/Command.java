package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public abstract class Command {
    private SimpleShape oldShape;
    private SimpleShape newShape;

    public Command(SimpleShape oldS, SimpleShape newS) {
        oldShape = oldS;
        newShape = newS;
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

    public abstract List<SimpleShape> execute(List<SimpleShape> shapesList);
}
