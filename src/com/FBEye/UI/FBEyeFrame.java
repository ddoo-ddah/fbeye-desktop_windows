/*
 * FBEyeFrame.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package com.FBEye.UI;

import com.FBEye.UI.page.LoginPanel;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.net.Connection;
import com.FBEye.util.DataExchanger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FBEyeFrame {
    private Connection connection;
    private JFrame mainFrame;
    private EventList list;
    private Timer timer;
    private TimerTask task;
    private List<Object> parameters;
    private Destination targetPage;

    //private LoginPanel loginPanel;

    public FBEyeFrame(){
        list = new EventList();
        init();
        //connection.Connect();
        timer.schedule(task, 100, 100);
        //mainFrame.add(loginPanel.getPanel());
        mainFrame.repaint();
    }

    private void init(){
        //connection = new Connection();
        mainFrame = new JFrame("FBEye");
        parameters = new ArrayList<>();
        targetPage = Destination.NONE;
        initMainFrame();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                restore();
            }
        };
        //loginPanel = new LoginPanel(list);
    }

    private void initMainFrame(){
        mainFrame.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(new Point(0,0));
        mainFrame.setSize(screenSize);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                mainFrame.setState(JFrame.NORMAL);
            }
        });
        mainFrame.setVisible(true);
    }

    private void restore(){
        //페이지 이동 코드

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            else if(list.get(i).destination == Destination.SERVER){
                //서버로 보내기
            }
            else if(list.get(i).destination == Destination.MANAGER){
                //서버에서 온 것
            }
            else{
                if(list.get(i).eventDataType == EventDataType.PARAMETER && list.get(i).data != null){
                    parameters.add(new DataExchanger<>().fromByteArray(list.get(i).data));
                    list.remove(i);
                }
                if(list.get(i).eventDataType == EventDataType.NAVIGATE){
                    targetPage = list.get(i).destination;
                    list.remove(i);
                }
            }
        }
    }
}
