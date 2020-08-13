/*
 * MemoPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package com.FBEye.UI.page.element;

import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MemoPanel {
    private JPanel panel;
    private Point panelLocation;

    private JPanel memoArea;
    private JTextArea memoText;

    public MemoPanel(int x, int y){
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 620));
        panel.setLocation(panelLocation);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        JLabel text = new JLabel("메모지");
        Dimension size = ViewDisposer.getSize(100, 30);
        text.setLocation(ViewDisposer.getFontSize(3), 0);
        text.setSize(size);
        text.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        text.setVisible(true);
        panel.add(text);

        memoArea = new JPanel();
        Point location = ViewDisposer.getLocation(1133, 255);
        size = ViewDisposer.getSize(292, 582);
        memoArea.setLayout(new GridLayout());
        memoArea.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        memoArea.setSize(size.width, size.height);
        memoArea.setBackground(Color.WHITE);

        memoText = new JTextArea();
        memoText.setLocation(0, 0);
        memoText.setSize(size);
        memoText.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(20)));
        memoText.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(memoText);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        memoArea.add(scrollPane);
        panel.add(memoArea);
        memoText.setVisible(true);
        scrollPane.setVisible(true);
        memoArea.setVisible(true);
    }

    public JPanel getPanel(){
        return panel;
    }

    public String getText(){
        return memoText.getText();
    }
}
