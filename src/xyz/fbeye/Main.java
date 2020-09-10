/*
 * Main.java
 * Author : susemeeee
 * Created Date : 2020-08-05
 */
package xyz.fbeye;

import xyz.fbeye.UI.BlankFrame;
import xyz.fbeye.UI.FBEyeFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for(int i = 1; i < devices.length; i++){
            devices[i].setFullScreenWindow(new BlankFrame().getFrame());
            ((JFrame)devices[i].getFullScreenWindow()).getContentPane().setBackground(Color.BLACK);
            devices[i].getFullScreenWindow().repaint();
        }
        new FBEyeFrame();
    }
}
