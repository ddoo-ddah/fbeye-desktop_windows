/*
 * EnvTestPanel_3.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package com.FBEye.UI.page;

import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class EnvTestPanel_3 extends Page{
    private final int RANDOM_BUTTON_COUNT = 5;
    private final int BUTTON_COUNT = 14;
    private final int CYCLE = 4;

    private List<Point> testButtonLocations;
    private int currentStep;
    private int testCycle;

    private JLabel infoTextLabel;
    private JButton startButton;
    private JButton testButton;

    public EnvTestPanel_3(EventList list){
        super(list);
        currentStep = -1;
        testCycle = 0;
        initPanel();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                restore();
            }
        };

    }

    @Override
    protected void setView(){
        infoTextLabel = new JLabel("화면에 나타나는 버튼을 누르세요");
        Point location = ViewDisposer.getLocation(365, 440);
        Dimension size = ViewDisposer.getSize(760, 200);
        infoTextLabel.setLocation(location);
        infoTextLabel.setSize(size);
        infoTextLabel.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(36)));
        infoTextLabel.setVisible(true);
        panel.add(infoTextLabel);

        startButton = new JButton("테스트 시작");
        location = ViewDisposer.getLocation(1200, 500);
        size = ViewDisposer.getSize(150, 100);
        startButton.setLocation(location);
        startButton.setSize(size);
        startButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        startButton.addActionListener(e -> {
            onStartButtonClicked();
        });
        startButton.setVisible(true);
        panel.add(startButton);

        setButtonLocations();
        testButton = new JButton("Click!");
        size = ViewDisposer.getSize(100, 80);
        testButton.setSize(size);
        testButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(20)));
        testButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                onTestButtonClicked(e);
            }
        });
        testButton.setVisible(false);
        panel.add(testButton);
    }

    private void setButtonLocations(){
        testButtonLocations = new ArrayList<>();
        testButtonLocations.add(ViewDisposer.getLocation(700, 460));
        testButtonLocations.add(ViewDisposer.getLocation(0, 0));
        testButtonLocations.add(ViewDisposer.getLocation(1400, 0));
        testButtonLocations.add(ViewDisposer.getLocation(0, 920));
        testButtonLocations.add(ViewDisposer.getLocation(1400, 920));
        testButtonLocations.add(ViewDisposer.getLocation(700, 0));
        testButtonLocations.add(ViewDisposer.getLocation(0, 460));
        testButtonLocations.add(ViewDisposer.getLocation(1400, 460));
        testButtonLocations.add(ViewDisposer.getLocation(700, 920));
        for(int i = 0; i < RANDOM_BUTTON_COUNT; i++){
            int x = ThreadLocalRandom.current().nextInt(0, 1400);
            int y = ThreadLocalRandom.current().nextInt(0, 920);
            testButtonLocations.add(ViewDisposer.getLocation(x, y));
        }
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.ENV_TEST_3 && list.get(i).eventDataType == EventDataType.SIGNAL){
                if(list.get(i).data.equals("OK")){
                    currentStep++;
                    if(currentStep == 0){
                        infoTextLabel.setVisible(false);
                        startButton.setVisible(false);
                        testButton.setVisible(true);
                    }
                    moveButton();
                }
                list.remove(i);
            }
        }
    }

    private void moveButton(){
        if(testCycle < CYCLE && currentStep == 9){
            currentStep = 0;
            testCycle++;
        }
        else if(currentStep >= BUTTON_COUNT){
            timer.cancel();
            list.add(new Event(Destination.ENV_TEST_4, EventDataType.NAVIGATE, null));
            return;
        }
        testButton.setLocation(testButtonLocations.get(currentStep));
    }

    private void onTestButtonClicked(MouseEvent e){
        Point p = new Point(testButton.getX() + e.getX(), testButton.getY() + e.getY());
        list.add(new Event(Destination.SERVER, EventDataType.COORDINATE, p));

        //test
        list.add(new Event(Destination.ENV_TEST_3, EventDataType.SIGNAL, "OK"));
    }

    private void onStartButtonClicked(){
        list.add(new com.FBEye.datatype.event.Event(Destination.SERVER, EventDataType.SIGNAL, "StartTest"));

        //test
        list.add(new Event(Destination.ENV_TEST_3, EventDataType.SIGNAL, "OK"));
    }
}
