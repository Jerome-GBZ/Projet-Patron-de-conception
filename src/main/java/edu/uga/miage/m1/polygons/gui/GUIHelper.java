package edu.uga.miage.m1.polygons.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *  @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class GUIHelper {

    private GUIHelper(){

    }

    public static void showOnFrame(String frameName) {
        JFrame frame = new JDrawingFrame(frameName);
        frame.setResizable(false);

        WindowAdapter wa = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        frame.addWindowListener(wa);
        frame.pack();
        frame.setVisible(true);
    }
}
