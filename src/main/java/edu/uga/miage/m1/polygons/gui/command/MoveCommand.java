package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import ShapePackage.shapes.SimpleShape;

public class MoveCommand extends Command {
    public MoveCommand(SimpleShape oldS, SimpleShape newS) {
        super(oldS, newS);
    }

    @Override
    public List<SimpleShape> execute(List<SimpleShape> shapesList) {
        shapesList.remove(getNewShape());
        shapesList.add(getOldShape());
        return shapesList;
    }}
