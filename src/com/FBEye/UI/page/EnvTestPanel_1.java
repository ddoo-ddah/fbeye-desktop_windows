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
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_2 extends Page {
    private boolean isTestStarted;

    private JLabel topQRCode;
    private JLabel bottomQRCode;
    private FlatButton startButton;

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
    protected void initPanel() {
        panel = new JPanel();
        panel.setBackground(new Color(222, 239, 255));
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(null);
        setView();
        panel.setVisible(true);
    }

    @Override
    protected void setView(){
        FlatLabel infoText = new FlatLabel(new FBEyeNotice().envTestInfoText_2);
        infoText.setBackground(new Color(255, 255, 222));
        infoText.setForeground(Color.BLACK);
        Point location = ViewDisposer.getLocation(550, 300);
        Dimension size = ViewDisposer.getSize(400, 180);
        infoText.setLocation(location);
        infoText.setSize(size);
        infoText.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        infoText.setVisible(true);
        panel.add(infoText);

        startButton = new FlatButton("테스트 시작");
        startButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        startButton.setForeground(Color.BLACK);
        location = ViewDisposer.getLocation(800, 630);
        size = ViewDisposer.getSize(150, 70);
        startButton.setLocation(location);
        startButton.setSize(size);
        startButton.setBackground(new Color(255, 109, 112));
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
                if(e.data.equals("ok") && !isTestStarted){
                    startButton.setVisible(false);
                    panel.repaint();
                    isTestStarted = true;
                    list.remove(i);
                }
                else if(e.data.equals("ok")){
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
