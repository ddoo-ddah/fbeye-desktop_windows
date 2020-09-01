/*
 * BlankFrame.java
 * Author : susemeeee
 * Created Date : 2020-08-20
 */
package xyz.fbeye.UI;

import javax.swing.*;
import java.awt.*;

public class BlankFrame {
    private JFrame frame;

    public BlankFrame(){
        init();
    }

    private void init(){
        frame = new JFrame();
        frame.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point(0,0));
        frame.setSize(screenSize);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public JFrame getFrame(){
        return frame;
    }
}
