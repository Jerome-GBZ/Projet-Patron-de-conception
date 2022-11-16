package edu.uga.miage.m1.polygons.gui.factory;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.SimpleFile;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.Logger;

public class FileFactory {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public void writeOnFile(List<SimpleShape> shapesList, SimpleFile sf, String nameFile) {
        try {
            shapesList.forEach(sf::visit);

            File myObj = new File(nameFile);
            if(myObj.createNewFile()) { logger.info("File created."); }

            FileWriter myWriter = new FileWriter(nameFile);
            writeOnFile(sf, myWriter);
        } catch (IOException exc) {
            logger.warning("An error occurred.");
        }
    }

    private void writeOnFile(SimpleFile sf, FileWriter myWriter) throws IOException {
        try {
            myWriter.write(sf.getRepresentation());
        } catch(Exception e){
            logger.warning("Error when writting on file.");
        } finally {
            myWriter.close();
        }
    }
}
