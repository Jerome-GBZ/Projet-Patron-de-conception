package edu.uga.miage.m1.polygons.gui.Factory;

import edu.uga.miage.m1.polygons.gui.Shapes.CompoundShape;
import edu.uga.miage.m1.polygons.gui.Shapes.SimpleShape;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;


public class JSonFactory {
    public List<SimpleShape> importShape(JSONArray shapesJSON) {
        List<SimpleShape> list = new ArrayList<>();
        ShapeFactory shapeFac = new ShapeFactory();

        shapesJSON.forEach(shapeObject -> {
            JSONObject shape = (JSONObject) shapeObject;
            String type = shape.getString("type").toUpperCase();

            if(type.equals("COMPOUNDSHAPE")) {
                JSONArray childrenShapesJSON = shape.getJSONArray("shapes");
                list.add(new CompoundShape(importShape(childrenShapesJSON)));
            } else {
                int x = shape.getInt("x");
                int y = shape.getInt("y");
                list.add(shapeFac.createSimpleShape(shapeFac.getShapes(type), x, y));
            }
        });

        return list;
    }
}
