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
import com.FBEye.util.DataExchanger;
import com.FBEye.util.QRGenerator;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_2 {
    private JPanel panel;
    private EventList list;
    private Timer timer;
    private TimerTask task;
    private boolean isTestStarted;

    private JLabel topQRCode;
    private JLabel bottomQRCode;
    private JButton startButton;

    public EnvTestPanel_2(EventList list){
        this.list = list;
        isTestStarted = false;
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
    }

    private void setQRCode(){
        //테스트 데이터
        Dimension QRSize = ViewDisposer.getSize(70, 70);
        int squaredQRSize = Math.min(QRSize.width, QRSize.height);
        ImageIcon img = QRGenerator.generateQR("test", squaredQRSize, squaredQRSize);
        //여기까지
        topQRCode = new JLabel(img);
        Point location = ViewDisposer.getLocation(715, 0);
        Dimension size = ViewDisposer.getSize(70, 70);
        topQRCode.setLocation(location);
        topQRCode.setSize(size);
        topQRCode.setVisible(true);
        panel.add(topQRCode);

        bottomQRCode = new JLabel(img);
        location = ViewDisposer.getLocation(715, 920);
        bottomQRCode.setLocation(location);
        bottomQRCode.setSize(size);
        bottomQRCode.setVisible(true);
        panel.add(bottomQRCode);
    }

    private void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.ENV_TEST_2 && list.get(i).eventDataType == EventDataType.SIGNAL){
                if(new DataExchanger<>().fromByteArray(list.get(i).data).equals("OK") && !isTestStarted){
                    //startButton.setVisible(false); 실제 사용 시 주석 지우기
                    setQRCode();
                    panel.repaint();
                    isTestStarted = true;
                    list.remove(i);
                }
                else if(new DataExchanger<>().fromByteArray(list.get(i).data).equals("OK")){
                    list.remove(i);
                    list.add(new Event(Destination.ENV_TEST_3, EventDataType.NAVIGATE, null));
                    timer.cancel();
                    break;
                }
            }
        }
    }

    private void onStartButtonClicked(){
        list.add(new com.FBEye.datatype.event.Event(Destination.SERVER, EventDataType.SIGNAL, new DataExchanger<String>().toByteArray("StartTest")));

        //test
        list.add(new Event(Destination.ENV_TEST_2, EventDataType.SIGNAL, new DataExchanger<String>().toByteArray("OK")));
    }

    public JPanel getPanel(){
        return panel;
    }
}
