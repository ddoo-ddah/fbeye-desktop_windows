/*
 * FBEyeFrame.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package xyz.fbeye.UI;

import org.json.JSONException;
import xyz.fbeye.UI.page.*;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.net.ChatConnection;
import xyz.fbeye.net.Connection;
import xyz.fbeye.util.JsonMaker;
import xyz.fbeye.util.JsonParser;
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
    private ChatConnection chatConnection;
    private JFrame mainFrame;
    private EventList list;
    private EventList chatEventList;
    private Timer timer;
    private TimerTask task;
    private Destination targetPage;
    private Destination currentPage;
    private JsonMaker jsonMaker;
    private JsonParser jsonParser;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private EnumMap<Destination, Page> pageMap;

    public FBEyeFrame(){
        list = new EventList();
        chatEventList = new EventList();
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        layout.columnWidths = new int[]{0};
        layout.columnWeights = new double[]{1};
        layout.rowHeights = new int[]{0};
        layout.rowWeights = new double[]{1};
        init();
        //connection.connect("localhost", 9000);//test
        connection.connect("fbeye.xyz", 10100);
        timer.schedule(task, 100, 100);
        addComponent(pageMap.get(currentPage).getPanel());
        pageMap.get(currentPage).startTimer();
        pageMap.get(currentPage).getPanel().revalidate();
        mainFrame.repaint();
    }

    private void init(){
        jsonMaker = new JsonMaker();
        jsonParser = new JsonParser();
        connection = new Connection(list);
        chatConnection = new ChatConnection("http://15.165.77.217:3000/", chatEventList);
        mainFrame = new JFrame("FBEye");
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
        pageMap.put(Destination.EXAM_PAGE, new ExamPanel(list));
    }

    private void initMainFrame(){
        mainFrame.setLayout(layout);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        //mainFrame.setAlwaysOnTop(true);
        //mainFrame.setUndecorated(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
               mainFrame.setState(JFrame.NORMAL);
            }
        });
        mainFrame.setVisible(true);
    }

    private void addComponent(Component c){
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        layout.setConstraints(c, constraints);
        mainFrame.add(c);
    }

    private void restore(){
        if(targetPage != Destination.NONE){
            if(targetPage == Destination.EXAM_PAGE){
                mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                mainFrame.repaint();
                mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            }
            pageMap.get(currentPage).endTimer();
            pageMap.get(targetPage).startTimer();
            mainFrame.getContentPane().removeAll();
            addComponent(pageMap.get(targetPage).getPanel());
            pageMap.get(targetPage).getPanel().revalidate();
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
                    if(e.eventDataType == EventDataType.DISCONNECT){
                        connection.disconnect();
                        chatConnection.disconnect();
                    }
                    else if(e.eventDataType == EventDataType.CHAT){
                        chatEventList.add(new Event(Destination.SERVER, EventDataType.CHAT, e.data));
                    }
                    else if(e.eventDataType == EventDataType.LOGIN_CODE){
                        connection.send(jsonMaker.makeJson(e.eventDataType, (String)e.data));
                        chatEventList.add(new Event(Destination.SERVER, EventDataType.LOGIN_CODE, e.data));
                    }
                    else{
                        connection.send(jsonMaker.makeJson(e.eventDataType, (String)e.data));
                    }
                }
                list.remove(i);
            }
            else if(list.get(i).destination == Destination.MANAGER){
                Event e = list.get(i);
                if(e.data != null){
                    try {
                        List<Object> receivedData = jsonParser.parse(new JSONObject((String)e.data));
                        if(receivedData.get(0) == EventDataType.SIGNAL){
                            list.add(new Event(currentPage, EventDataType.SIGNAL, receivedData.get(1)));
                        }
                        else if(receivedData.get(0) == EventDataType.EXAM_INFO){
                            list.add(new Event(Destination.EXAM_INFO_PAGE, EventDataType.EXAM_INFO, receivedData.get(1)));
                        }
                        else if(receivedData.get(0) == EventDataType.USER_INFO){
                            list.add(new Event(Destination.EXAM_INFO_PAGE, EventDataType.USER_INFO, receivedData.get(1)));
                        }
                        else if(receivedData.get(0) == EventDataType.QR_CODE_DATA){
                            list.add(new Event(currentPage, EventDataType.QR_CODE_DATA, receivedData.get(1)));
                        }
                        else if(receivedData.get(0) == EventDataType.ENCRYPTED_QUESTION){
                            list.add(new Event(currentPage, EventDataType.ENCRYPTED_QUESTION, receivedData.get(1)));
                        }
                        else if(receivedData.get(0) == EventDataType.QUESTION_KEY){
                            list.add(new Event(currentPage, EventDataType.QUESTION_KEY, receivedData.get(1)));
                        }
                    }catch (JSONException exception){
                        exception.printStackTrace();
                    }
                }
                list.remove(i);
            }
            else if(list.get(i).eventDataType == EventDataType.NAVIGATE){
                targetPage = list.get(i).destination;
                list.remove(i);
            }
        }

        for(int i = 0; i < chatEventList.size(); i++){
            if(chatEventList.get(i) == null){
                break;
            }
            else if(chatEventList.get(i).destination == Destination.SERVER){
                Event e = chatEventList.get(i);
                if(e.data != null){
                    if(e.eventDataType == EventDataType.LOGIN_CODE){
                        chatConnection.connect((String)e.data);
                    }
                    else{
                        chatConnection.send((String)e.data);
                    }
                }
                chatEventList.remove(i);
            }
            else if(chatEventList.get(i).destination == Destination.MANAGER){
                Event e = chatEventList.get(i);
                if(e.data != null &&
                        (currentPage == Destination.ENV_TEST_3 || currentPage == Destination.EXAM_PAGE)){
                    list.add(new Event(currentPage, EventDataType.CHAT, e.data));
                }
                chatEventList.remove(i);
            }
        }
    }
}
