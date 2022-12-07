package edu.uga.miage.m1.polygons.gui.controllers;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.SimpleFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.Logger;

import ShapePackage.shapes.SimpleShape;

public class FileController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void writeOnFile(List<SimpleShape> shapesList, SimpleFile sf, String nameFile) {
        try {
            shapesList.forEach(sf::visit);

            File myObj = new File(nameFile);
            if(myObj.createNewFile()) { logger.info("File created."); }

            FileWriter writer = new FileWriter(nameFile);
            writeOnFile(sf, writer);
        } catch (IOException exc) {
            logger.warning("An error occurred.");
        }
    }

    private void writeOnFile(SimpleFile sf, FileWriter writer) throws IOException {
        try {
            writer.write(sf.getRepresentation());
        } catch(Exception e){
            logger.warning("Error when writting on file.");
        } finally {
            writer.close();
        }
    }
}
