/*
 * SizeConverter.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package com.FBEye.util;

import java.awt.*;

public class ViewDisposer {
    public static Point getLocation(int x, int y){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point(x / 1500 * screenSize.width, y / 1000 * screenSize.width);
    }

    public static Dimension getSize(int width, int height){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension(width / 1500 * screenSize.width, height / 1000 * screenSize.height);
    }
}
