package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class CreateCommand extends Command {
    public CreateCommand(SimpleShape oldS, SimpleShape newS) {
        super(oldS, newS);
    }

    @Override
    public List<SimpleShape> execute(List<SimpleShape> shapesList) {
        shapesList.remove(getNewShape());
        return shapesList;
    }}
