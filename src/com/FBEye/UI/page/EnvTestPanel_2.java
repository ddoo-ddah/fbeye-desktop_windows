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
import com.FBEye.util.SignalDataMaker;
import com.FBEye.util.ViewDisposer;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_1 extends Page {
    private List<String> infoTexts;
    private int currentStep;

    private FlatLabel infoTextLabel;
    private FlatButton startButton;

    public EnvTestPanel_1(EventList list){
        super(list);
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
    }

    @Override
    protected void initPanel() {
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0, 0, 0};
        layout.columnWeights = new double[]{550, 150, 100, 150, 550, Double.MIN_VALUE};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0};
        layout.rowWeights = new double[]{300, 180, 150, 70, 300, Double.MIN_VALUE};
        constraints = new GridBagConstraints();
        panel = new JPanel();
        panel.setBackground(new Color(222, 239, 255));
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(layout);
        setView();
        panel.setVisible(true);
    }

    @Override
    protected void setView(){
        infoTextLabel = new FlatLabel();
        infoTextLabel.setBackground(new Color(255, 255, 222));
        infoTextLabel.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        infoTextLabel.setText(infoTexts.get(currentStep));
        infoTextLabel.setVisible(true);
        addComponent(infoTextLabel, 1, 1, 3, 1, GridBagConstraints.BOTH);

        startButton = new FlatButton("테스트 시작");
        startButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(new Color(255, 109, 112));
        startButton.addActionListener(e -> {
            onStartButtonClicked();
        });
        startButton.setVisible(true);
        addComponent(startButton, 2, 3, 1, 1, GridBagConstraints.BOTH);
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.ENV_TEST_1 && list.get(i).eventDataType == EventDataType.SIGNAL){
                if(list.get(i).data.equals("ok")){
                    currentStep++;
                }
                list.remove(i);
            }
        }

        if(currentStep == infoTexts.size()){
            list.add(new Event(Destination.ENV_TEST_2, EventDataType.NAVIGATE, null));
            timer.cancel();
        }
        else if(currentStep >= 1){
            startButton.setVisible(false);
            infoTextLabel.setText(infoTexts.get(currentStep));
            panel.revalidate();
        }
    }

    private void onStartButtonClicked(){
        list.add(new Event(Destination.SERVER, EventDataType.SIGNAL, SignalDataMaker.make("startTest")));
    }
}
