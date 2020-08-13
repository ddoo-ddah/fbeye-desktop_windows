/*
 * QuestionNumberPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-13
 */
package com.FBEye.UI.page.element;

import com.FBEye.datatype.examdata.AnswerState;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionNumberPanel {
    private JPanel panel;
    private Point panelLocation;
    private int questionCount;
    private int currentNumber;
    private int prevNumber;
    private boolean isChanged;

    private JPanel numberPanel;
    private List<JButton> numbers;

    public QuestionNumberPanel(int x, int y, int questionCount){
        currentNumber = 0;
        prevNumber = 0;
        this.questionCount = questionCount;
        isChanged = false;
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 400));
        panel.setLocation(panelLocation);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        JLabel text =  new JLabel("문제 이동");
        Point location = ViewDisposer.getLocation(73, 73);
        Dimension size = ViewDisposer.getSize(294, 22);
        text.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        text.setSize(size);
        text.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        text.setVisible(true);
        panel.add(text);

        numberPanel = new JPanel();
        location = ViewDisposer.getLocation(73, 95);
        size = ViewDisposer.getSize(294, 53 * (questionCount / 5 + 1));
        numberPanel.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        numberPanel.setSize(size);
        numberPanel.setLayout(new GridLayout(questionCount / 5 + 1, 5, 3, 3));
        numberPanel.setBackground(Color.WHITE);
        numberPanel.setVisible(true);

        numbers = new ArrayList<>();
        for(int i = 0; i < questionCount; i++){
            JButton button = new JButton(Integer.toString(i + 1));
            button.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(20)));
            button.setBackground(Color.WHITE);
            button.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(2)));
            button.addActionListener(e -> {
                onButtonClicked(numbers.indexOf(e.getSource()));
            });
            button.setVisible(true);
            numbers.add(button);
            numberPanel.add(button);
        }
        panel.add(numberPanel);
    }

    public void controlNumber(AnswerState state){
        if(state == AnswerState.NOT_SOLVED){
            numbers.get(prevNumber).setBackground(new Color(0xff4500));
        }
        else if(state == AnswerState.DELAYED){
            numbers.get(prevNumber).setBackground(new Color(0xffff00));
        }
        else{
            numbers.get(prevNumber).setBackground(new Color(0xadff2f));
        }
        panel.repaint();
    }

    private void onButtonClicked(int index){
        prevNumber = currentNumber;
        currentNumber = index;
        isChanged = true;
    }

    public JPanel getPanel(){
        return panel;
    }

    public boolean getIsChanged(){
        return isChanged;
    }

    public int getCurrentNumber(){
        return currentNumber;
    }

    public int getPrevNumber(){
        return prevNumber;
    }

    public void setCurrentNumber(int currentNumber){
        this.currentNumber = currentNumber;
    }

    public void setPrevNumber(int prevNumber){
        this.prevNumber = prevNumber;
    }

    public void setIsChanged(boolean isChanged){
        this.isChanged = isChanged;
    }
}