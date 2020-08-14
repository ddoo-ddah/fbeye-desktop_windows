/*
 * TImePanel.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package com.FBEye.UI.page.element;

import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;

public class TimePanel {
    private JPanel panel;
    private Point panelLocation;

    private JLabel text;
    private JLabel time;

    public TimePanel(int x, int y){
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 150));
        panel.setLocation(panelLocation);
        panel.setLayout(null);
        panel.setBackground(Color.ORANGE);
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        text = new JLabel("남은 시간");
        Point location = ViewDisposer.getLocation(1210, 105);
        Dimension size = ViewDisposer.getSize(70, 20);
        text.setLocation(new Point(location.x - panelLocation.x, location.y - panelLocation.y));
        text.setSize(size);
        text.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        text.setVisible(true);
        panel.add(text);

        time = new JLabel("99:59:59");
        location = ViewDisposer.getLocation(1210, 145);
        size = ViewDisposer.getSize(130, 45);
        time.setLocation(new Point(location.x - panelLocation.x, location.y - panelLocation.y));
        time.setSize(size);
        time.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(50)));
        time.setVisible(true);
        panel.add(time);
    }

    public void setTime(String time){
        this.time.setText(time);
        panel.repaint();
    }

    public JPanel getPanel(){
        return panel;
    }

    public void setText(String text){
        this.text.setText(text);
        panel.repaint();
    }
}
