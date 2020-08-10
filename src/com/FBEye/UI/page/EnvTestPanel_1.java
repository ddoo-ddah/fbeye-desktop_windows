/*
 * EnvTestPageStep1.java
 * Author : susemeeee
 * Created Date : 2020-08-10
 */
package com.FBEye.UI.page;

import com.FBEye.datatype.FBEyeNotice;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.util.DataExchanger;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_1 {
    private JPanel panel;
    private EventList list;
    private Timer timer;
    private TimerTask task;
    private List<String> infoTexts;
    private int currentStep;

    private JLabel infoTextLabel;
    private JButton startButton;

    public EnvTestPanel_1(EventList list){
        this.list = list;
        infoTexts = new FBEyeNotice().envTestInfoTexts_1;
        currentStep = 0;
        initPanel();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                restore();
            }
        };
        timer.schedule(task, 100, 100);
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        infoTextLabel = new JLabel();
        Point location = ViewDisposer.getLocation(384, 440);
        Dimension size = ViewDisposer.getSize(760, 200);
        infoTextLabel.setLocation(location);
        infoTextLabel.setSize(size);
        infoTextLabel.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(40)));
        infoTextLabel.setText(infoTexts.get(currentStep));
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
    }

    private void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.ENV_TEST_1 && list.get(i).eventDataType == EventDataType.SIGNAL){
                if(new DataExchanger<>().fromByteArray(list.get(i).data).equals("OK")){
                    currentStep++;
                }
                list.remove(i);
            }
        }

        if(currentStep == infoTexts.size()){
            System.out.println("테스트 1단계 완료"); //test
            startButton.setVisible(false); //test
            panel.repaint(); //test
        }
        else if(currentStep >= 1){
            //panel.remove(startButton); //실제 사용 시 주석 해제
            infoTextLabel.setText(infoTexts.get(currentStep));
            panel.repaint();
        }
    }

    private void onStartButtonClicked(){
        list.add(new Event(Destination.SERVER, EventDataType.SIGNAL, new DataExchanger<String>().toByteArray("StartTest")));

        //test
        list.add(new Event(Destination.ENV_TEST_1, EventDataType.SIGNAL, new DataExchanger<String>().toByteArray("OK")));
    }

    public JPanel getPanel(){
        return panel;
    }
}
