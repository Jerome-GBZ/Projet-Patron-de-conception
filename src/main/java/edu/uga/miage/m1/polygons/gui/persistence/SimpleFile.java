package edu.uga.miage.m1.polygons.gui.persistence;
import ShapePackage.persistence.Visitor;

public interface SimpleFile extends Visitor{
    public String getRepresentation();

    public void clear();
}
