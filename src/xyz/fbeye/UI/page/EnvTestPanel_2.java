/*
 * EnvTestPanel_3.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package xyz.fbeye.UI.page;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.util.FontManager;
import xyz.fbeye.UI.page.element.SetupCanvas;
import xyz.fbeye.datatype.FBEyeNotice;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.util.ViewDisposer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_2 extends Page{
    private SetupCanvas canvas;
    private FlatButton startButton;

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
        FlatLabel infoTextLabel = new FlatLabel(new FBEyeNotice().envTestInfoText_2);
        infoTextLabel.setBackground(new Color(255, 255, 222));
        infoTextLabel.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
        infoTextLabel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        infoTextLabel.setVisible(true);
        addComponent(infoTextLabel, 1, 1, 3, 1, GridBagConstraints.BOTH);

        JPanel startPanel = new JPanel();
        startPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        startPanel.setLayout(new GridLayout());
        startPanel.setVisible(true);
        startButton = new FlatButton("테스트 시작");
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(new Color(255, 109, 112));
        startButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        startButton.addActionListener(e -> {
            startTest();
        });
        startButton.setVisible(true);
        startPanel.add(startButton);
        addComponent(startPanel, 2, 3, 1, 1, GridBagConstraints.BOTH);
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            Event e = list.get(i);
            if(e.destination == Destination.ENV_TEST_2 && e.eventDataType == EventDataType.SIGNAL){
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
        canvas.postVisible();
        panel.revalidate();
    }
}
