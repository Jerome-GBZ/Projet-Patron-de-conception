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
import java.awt.geom.Ellipse2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

public class Circle implements SimpleShape, Visitable {

    int mX;

    int mY;

    public Circle(int x, int y) {
        mX = x;
        mY = y;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2, float width) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint((float) mX-25, (float) mY-25, Color.RED, (mX + 50), mY, Color.WHITE);
        g2.setPaint(gradient);

        Ellipse2D ellipse = new Ellipse2D.Double((double) mX-25, (double) mY-25, 50, 50);
        g2.fill(ellipse);

        if(width == 4.0) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(Color.BLACK);
        }

        BasicStroke wideStroke = new BasicStroke(width);
        g2.setStroke(wideStroke);
        g2.draw(ellipse);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public void moveTo(int x, int y) {
        mX = x;
        mY = y;
    }

    public boolean clickedOnShape(int x, int y) {
        return this.getX()-25 <= x && this.getX()+25 >= x && this.getY()-25 <= y && this.getY()+25 >= y;
    }

    @Override
    public boolean add(SimpleShape shape) { return false; }
}
