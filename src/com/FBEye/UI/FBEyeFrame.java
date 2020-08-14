/*
 * FBEyeFrame.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package com.FBEye.UI;

import com.FBEye.UI.page.*;
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

    private LoginPanel loginPanel;
    private ExamInfoPanel examInfoPanel;
    private EnvTestPanel_1 envTestPanel_1;
    private EnvTestPanel_2 envTestPanel_2;
    private EnvTestPanel_3 envTestPanel_3;
    private ExamPanel examPanel;

    public FBEyeFrame(){
        list = new EventList();
        init();
        //connection.Connect();
        timer.schedule(task, 100, 100);
        mainFrame.add(loginPanel.getPanel());
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

        loginPanel = new LoginPanel(list);
        examInfoPanel = new ExamInfoPanel(list);
        envTestPanel_1 = new EnvTestPanel_1(list);
        envTestPanel_2 = new EnvTestPanel_2(list);
        envTestPanel_3 = new EnvTestPanel_3(list);
        examPanel = new ExamPanel(list);
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
        if(targetPage == Destination.LOGIN_PAGE){
            mainFrame.getContentPane().removeAll();
            mainFrame.add(loginPanel.getPanel());
            mainFrame.repaint();
            targetPage = Destination.NONE;
        }
        else if(targetPage == Destination.EXAM_INFO_PAGE){
            mainFrame.getContentPane().removeAll();
            mainFrame.add(examInfoPanel.getPanel());
            mainFrame.repaint();
            targetPage = Destination.NONE;
        }
        else if(targetPage == Destination.ENV_TEST_1){
            mainFrame.getContentPane().removeAll();
            mainFrame.add(envTestPanel_1.getPanel());
            mainFrame.repaint();
            targetPage = Destination.NONE;
        }
        else if(targetPage == Destination.ENV_TEST_2){
            mainFrame.getContentPane().removeAll();
            mainFrame.add(envTestPanel_2.getPanel());
            mainFrame.repaint();
            targetPage = Destination.NONE;
        }
        else if(targetPage == Destination.ENV_TEST_3){
            mainFrame.getContentPane().removeAll();
            mainFrame.add(envTestPanel_3.getPanel());
            mainFrame.repaint();
            targetPage = Destination.NONE;
        }
        else if(targetPage == Destination.EXAM_PAGE){
            mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            mainFrame.repaint();
            mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            mainFrame.getContentPane().removeAll();
            mainFrame.add(examPanel.getPanel());
            mainFrame.repaint();
            targetPage = Destination.NONE;
        }

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
                else if(list.get(i).eventDataType == EventDataType.NAVIGATE){
                    targetPage = list.get(i).destination;
                    list.remove(i);
                }
            }
        }
    }
}
