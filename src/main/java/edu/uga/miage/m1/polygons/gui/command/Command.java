package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.command.CommandUndo.TypesCommands;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public interface Command {
    TypesCommands getTypeCommand();

    SimpleShape getOldShape();

    SimpleShape getNewShape();

    void setNewShape(SimpleShape newShape);

    void setOldShape(SimpleShape oldShape);

    List<SimpleShape> makeAction(List<SimpleShape> shapesList);
}
