/*
 * EnvTestPanel_2.java
 * Author : susemeeee
 * Created Date : 2020-08-10
 */
package com.FBEye.UI.page;

import com.FBEye.datatype.FBEyeNotice;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.util.QRGenerator;
import com.FBEye.util.SignalDataMaker;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_2 extends Page {
    private boolean isTestStarted;

    private JLabel topQRCode;
    private JLabel bottomQRCode;
    private JButton startButton;

    public EnvTestPanel_2(EventList list){
        super(list);
        isTestStarted = false;
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
        JLabel infoText = new JLabel(new FBEyeNotice().envTestInfoText_2);
        Point location = ViewDisposer.getLocation(384, 440);
        Dimension size = ViewDisposer.getSize(760, 200);
        infoText.setLocation(location);
        infoText.setSize(size);
        infoText.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(40)));
        infoText.setVisible(true);
        panel.add(infoText);

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

        topQRCode = new JLabel();
        location = ViewDisposer.getLocation(715, 0);
        size = ViewDisposer.getSize(70, 70);
        topQRCode.setLocation(location);
        topQRCode.setSize(size);
        topQRCode.setVisible(true);
        panel.add(topQRCode);

        bottomQRCode = new JLabel();
        location = ViewDisposer.getLocation(715, 920);
        bottomQRCode.setLocation(location);
        bottomQRCode.setSize(size);
        bottomQRCode.setVisible(true);
        panel.add(bottomQRCode);
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            Event e = list.get(i);
            if(e.destination == Destination.ENV_TEST_2 && e.eventDataType == EventDataType.SIGNAL){
                if(e.data.equals("OK") && !isTestStarted){
                    startButton.setVisible(false);
                    panel.repaint();
                    isTestStarted = true;
                    list.remove(i);
                }
                else if(e.data.equals("OK")){
                    list.remove(i);
                    list.add(new Event(Destination.ENV_TEST_3, EventDataType.NAVIGATE, null));
                }
            }
            else if(e.destination == Destination.ENV_TEST_2 && e.eventDataType == EventDataType.QR_CODE_DATA){
                QRDataReceived((String)e.data);
                list.remove(i);
            }
        }
    }

    private void QRDataReceived(String data){
        Dimension QRSize = ViewDisposer.getSize(70, 70);
        int squaredQRSize = Math.min(QRSize.width, QRSize.height);
        ImageIcon img = QRGenerator.generateQR(data, squaredQRSize, squaredQRSize);
        topQRCode.setIcon(img);
        bottomQRCode.setIcon(img);
    }

    private void onStartButtonClicked(){
        list.add(new com.FBEye.datatype.event.Event(Destination.SERVER, EventDataType.SIGNAL, SignalDataMaker.make("startTest")));
    }
}
