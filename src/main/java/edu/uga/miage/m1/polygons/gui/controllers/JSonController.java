package edu.uga.miage.m1.polygons.gui.controllers;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.JSONObject;

import ShapePackage.shapes.CompoundShape;
import ShapePackage.shapes.SimpleShape;

import org.json.JSONArray;

public class JSonController {
    private List<SimpleShape> importShape(JSONArray shapesJSON) {
        List<SimpleShape> list = new ArrayList<>();
        ShapeController shapeController = new ShapeController();

        shapesJSON.forEach(shapeObject -> {
            JSONObject shape = (JSONObject) shapeObject;
            String type = shape.getString("type").toUpperCase();

            if(type.equals("COMPOUNDSHAPE")) {
                JSONArray childrenShapesJSON = shape.getJSONArray("shapes");
                list.add(new CompoundShape(importShape(childrenShapesJSON)));
            } else {
                int x = shape.getInt("x");
                int y = shape.getInt("y");
                list.add(shapeController.createSimpleShape(shapeController.getShapes(type), x, y));
            }
        });

        return list;
    }

    public List<SimpleShape> getJSonFile(String nameJsonFile) throws IOException {
        String fileContent = Files.readString(Path.of(nameJsonFile));
        JSONObject jsonObject = new JSONObject(fileContent);
        JSONArray shapesJSON = jsonObject.getJSONArray("shapes");

        return importShape(shapesJSON);
    }
}
