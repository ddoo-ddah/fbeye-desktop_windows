/*
 * FBEyeFrame.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package com.FBEye.UI;

import com.FBEye.UI.page.*;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.net.Connection;
import com.FBEye.util.JsonMaker;
import com.FBEye.util.JsonParser;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class FBEyeFrame {
    private Connection connection;
    private JFrame mainFrame;
    private EventList list;
    private Timer timer;
    private TimerTask task;
    private List<Object> parameters;
    private Destination targetPage;
    private Destination currentPage;
    private JsonMaker jsonMaker;
    private JsonParser jsonParser;

    private EnumMap<Destination, Page> pageMap;

    public FBEyeFrame(){
        list = new EventList();
        init();
        connection.Connect();
        timer.schedule(task, 100, 100);
        mainFrame.add(pageMap.get(currentPage).getPanel());
        pageMap.get(currentPage).startTimer();
        mainFrame.repaint();
    }

    private void init(){
        jsonMaker = new JsonMaker();
        jsonParser = new JsonParser();
        connection = new Connection(list);
        mainFrame = new JFrame("FBEye");
        parameters = new ArrayList<>();
        currentPage = Destination.LOGIN_PAGE;
        targetPage = Destination.NONE;
        initMainFrame();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                restore();
            }
        };

        pageMap = new EnumMap<>(Destination.class);
        pageMap.put(Destination.LOGIN_PAGE, new LoginPanel(list));
        pageMap.put(Destination.EXAM_INFO_PAGE, new ExamInfoPanel(list));
        pageMap.put(Destination.ENV_TEST_1, new EnvTestPanel_1(list));
        pageMap.put(Destination.ENV_TEST_2, new EnvTestPanel_2(list));
        pageMap.put(Destination.ENV_TEST_3, new EnvTestPanel_3(list));
        pageMap.put(Destination.ENV_TEST_4, new EnvTestPanel_4(list));
        pageMap.put(Destination.EXAM_PAGE, new ExamPanel(list));
    }

    private void initMainFrame(){
        mainFrame.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(new Point(0,0));
        mainFrame.setSize(screenSize);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                mainFrame.setState(JFrame.NORMAL);
            }
        });
        mainFrame.setVisible(true);
    }

    private void restore(){
        if(targetPage != Destination.NONE){
            if(targetPage == Destination.EXAM_PAGE){
                mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                mainFrame.repaint();
                mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            }
            pageMap.get(targetPage).startTimer();
            pageMap.get(currentPage).endTimer();
            mainFrame.getContentPane().removeAll();
            mainFrame.add(pageMap.get(targetPage).getPanel());
            mainFrame.repaint();
            currentPage = targetPage;
            targetPage = Destination.NONE;
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            else if(list.get(i).destination == Destination.SERVER){
                Event e = list.get(i);
                if(e.data != null){
                    connection.send(jsonMaker.makeJson(e.eventDataType, (String)e.data));
                }
                list.remove(i);
            }
            else if(list.get(i).destination == Destination.MANAGER){
                Event e = list.get(i);
                if(e.data != null){
                    List<Object> receivedData = jsonParser.parse(new JSONObject((String)e.data));
                    if(receivedData.get(0) == EventDataType.SIGNAL){
                        list.add(new Event(currentPage, EventDataType.SIGNAL, receivedData.get(1)));
                    }
                    else if(receivedData.get(0) == EventDataType.EXAM_INFO){
                        list.add(new Event(currentPage, EventDataType.EXAM_INFO, receivedData.get(1)));
                    }
                    else if(receivedData.get(0) == EventDataType.USER_INFO){
                        list.add(new Event(currentPage, EventDataType.USER_INFO, receivedData.get(1)));
                    }
                    else if(receivedData.get(0) == EventDataType.QR_CODE_DATA){
                        list.add(new Event(currentPage, EventDataType.QR_CODE_DATA, receivedData.get(1)));
                    }
                }
                list.remove(i);
            }
            else if(list.get(i).eventDataType == EventDataType.NAVIGATE){
                targetPage = list.get(i).destination;
                list.remove(i);
            }
        }
    }
}
