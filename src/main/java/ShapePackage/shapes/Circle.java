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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

public class Circle extends Shape implements Visitable {

    public Circle(int x, int y) {
        super(x, y);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    @Override
    public void draw(Graphics2D g2, float width) {
        try {
            File fileImage = new File("src/main/resources/images/circle.png");
            BufferedImage image = ImageIO.read(fileImage);
            g2.drawImage(image, getX()-25, getY()-25, null);

            if(width > 0) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(width));
                g2.drawRect(getX()-25, getY()-25, 53, 53);
                g2.setStroke(g2.getStroke());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(Visitor visitor) { visitor.visit(this); }
}
