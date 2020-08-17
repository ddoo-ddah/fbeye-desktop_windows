/*
 * Page.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package com.FBEye.UI.page;

import com.FBEye.datatype.event.EventList;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Page {
    protected JPanel panel;
    protected EventList list;
    protected Timer timer;
    protected TimerTask task;

    public Page(EventList list){
        this.list = list;
    }

    protected void initPanel(){
        panel = new JPanel();
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        setView();
        panel.setVisible(true);
    }

    protected  abstract void setView();
    protected abstract void restore();

    public JPanel getPanel(){
        return panel;
    }
}
