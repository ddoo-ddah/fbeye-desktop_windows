/*
 * Page.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package xyz.fbeye.UI.page;

import xyz.fbeye.datatype.event.EventList;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Page {
    protected JPanel panel;
    protected EventList list;
    protected Timer timer;
    protected TimerTask task;

    protected GridBagLayout layout;
    protected GridBagConstraints constraints;

    public Page(EventList list){
        this.list = list;
    }

    protected void initPanel(){
        panel = new JPanel();
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(layout);
        panel.setBackground(Color.WHITE);
        setView();
        panel.setVisible(true);
    }

    protected abstract void setView();
    protected abstract void restore();

    protected void addComponent(Component c, int col, int row, int width, int height, int fill){
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = fill;
        layout.setConstraints(c, constraints);
        panel.add(c);
    }

    public void startTimer(){
        timer.schedule(task, 200, 200);
    }

    public void endTimer(){
        timer.cancel();
    }

    public JPanel getPanel(){
        return panel;
    }
}
