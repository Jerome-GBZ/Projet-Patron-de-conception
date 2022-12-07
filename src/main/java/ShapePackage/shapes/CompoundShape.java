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
package ShapePackage.shapes;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

public class CompoundShape implements SimpleShape, Visitable {

    private List<SimpleShape> shapesList;

    public CompoundShape(List<SimpleShape> s) {
        this.shapesList = new ArrayList<>(s);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2, float width) {
        this.shapesList.forEach(shape -> shape.draw(g2, width));
    }

    @Override
    public void moveTo(int x, int y) {
        if(!shapesList.isEmpty()) {
            int translationX = x - shapesList.get(0).getX();
            int translationY = y - shapesList.get(0).getY();

            this.getShapes().forEach(shape -> shape.moveTo(shape.getX()+translationX, shape.getY()+translationY) );
        }
    }

    public boolean clickedOnShape(int x, int y) {
        int i = shapesList.size() - 1;

        while(i > -1 && !shapesList.get(i).clickedOnShape(x, y)) {
            i--;
        }

        return i > -1;
    }

    @Override
    public void accept(Visitor visitor) { visitor.visit(this); }

    @Override
    public int getX() { return -1; }

    @Override
    public int getY() { return -1; }

    public List<SimpleShape> getShapes() { return shapesList; }

    @Override
    public boolean add(SimpleShape shape) {
        this.shapesList = ((CompoundShape) shape).getShapes();

        return true;
    }

    public void add(Graphics2D g2, SimpleShape s) {
        this.shapesList.add(s);
        s.draw(g2, getX());
    }

    public void addShape(SimpleShape s) {
        this.shapesList.add(s);
    }
}
