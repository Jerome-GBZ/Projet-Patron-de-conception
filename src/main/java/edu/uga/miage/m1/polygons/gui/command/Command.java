package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import ShapePackage.shapes.SimpleShape;

public abstract class Command {
    private SimpleShape oldShape;
    private SimpleShape newShape;

    protected Command(SimpleShape oldS, SimpleShape newS) {
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

    public abstract List<SimpleShape> execute(List<SimpleShape> shapesList);
}
