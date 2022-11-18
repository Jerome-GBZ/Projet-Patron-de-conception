package controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.controllers.JSonController;

class JSonControllerTest {
    JSonController jsonController = new JSonController();

    @Test
    void testConstructorJSonController() {
        // Write test
        assertNotNull(jsonController);
    }
}
