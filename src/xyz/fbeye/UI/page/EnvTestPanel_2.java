/*
 * EnvTestPanel_3.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package xyz.fbeye.UI.page;

import xyz.fbeye.UI.page.element.SetupCanvas;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.util.SignalDataMaker;
import xyz.fbeye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_2 extends Page{
    private SetupCanvas canvas;
    private JLabel infoTextLabel;
    private JButton startButton;

    public EnvTestPanel_2(EventList list){
        super(list);
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
        infoTextLabel = new JLabel("asfd");
        Point location = ViewDisposer.getLocation(365, 440);
        Dimension size = ViewDisposer.getSize(760, 200);
        infoTextLabel.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(36)));
        infoTextLabel.setVisible(true);
        addComponent(infoTextLabel, 1, 1, 3, 1, GridBagConstraints.BOTH);

        startButton = new JButton("테스트 시작");
        location = ViewDisposer.getLocation(1200, 500);
        size = ViewDisposer.getSize(150, 100);
        startButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
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
            Event e = list.get(i);
            if(e.destination == Destination.ENV_TEST_2 && e.eventDataType == EventDataType.SIGNAL){
                if(e.data.equals("ok")){
                    startTest();
                }
                list.remove(i);
            }
        }

        if(canvas != null && canvas.getIsEnd()){
            list.add(new Event(Destination.ENV_TEST_3, EventDataType.NAVIGATE, null));
        }
    }

    private void startTest(){
        panel.removeAll();
        canvas = new SetupCanvas();
        canvas.getCanvas().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        canvas.getCanvas().setVisible(true);
        addComponent(canvas.getCanvas(), 0, 0, 5, 5, GridBagConstraints.BOTH);
        panel.revalidate();
        canvas.postVisible();
    }

    private void onStartButtonClicked(){
        list.add(new Event(Destination.SERVER, EventDataType.SIGNAL, SignalDataMaker.make("startTest")));
    }
}
