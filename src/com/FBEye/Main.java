/*
 * Main.java
 * Author : susemeeee
 * Created Date : 2020-08-05
 */
package com.FBEye;

import com.FBEye.UI.BlankFrame;
import com.FBEye.UI.FBEyeFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        for(int i = 0; i < devices.length; i++){
            devices[i].setFullScreenWindow(new BlankFrame().getFrame());
            ((JFrame)devices[i].getFullScreenWindow()).getContentPane().setBackground(Color.BLACK);
            devices[i].getFullScreenWindow().repaint();
        }
        new FBEyeFrame();
    }
}
