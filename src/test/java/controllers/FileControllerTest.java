package controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.controllers.FileController;

class FileControllerTest {
    FileController fileController = new FileController();

    @Test
    void testConstructorFileController() {
        // Write test
        assertNotNull(fileController);
    }
}
