/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle extends Shape implements SimpleShape, Visitable {


    public Triangle(int x, int y) {
        super(x, y);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2, float width) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint((float) getX()-25, (float) getY()-25, Color.GREEN,(getX() + 50), getY(), Color.WHITE);
        g2.setPaint(gradient);

        int[] xcoords = { getX(), getX()-25, getX()+25 };
        int[] ycoords = { getY()-25, getY()+25, getY()+25 };

        GeneralPath polygon = new GeneralPath(Path2D.WIND_EVEN_ODD, xcoords.length);

        polygon.moveTo(getX(), (float) getY()-25);
        for (int i = 0; i < xcoords.length; i++) {
            polygon.lineTo(xcoords[i], ycoords[i]);
        }
        polygon.closePath();
        g2.fill(polygon);

        if(width == 4.0) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(Color.BLACK);
        }

        BasicStroke wideStroke = new BasicStroke(width);
        g2.setStroke(wideStroke);
        g2.draw(polygon);
    }

    public void accept(Visitor visitor) { visitor.visit(this); }

    @Override
    public boolean add(SimpleShape shape) { return false; }
}
