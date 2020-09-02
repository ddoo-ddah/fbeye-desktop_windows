/*
 * EnvTestPanel_2.java
 * Author : susemeeee
 * Created Date : 2020-08-10
 */
package xyz.fbeye.UI.page;

import xyz.fbeye.datatype.FBEyeNotice;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.util.QRGenerator;
import xyz.fbeye.util.ViewDisposer;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_1 extends Page {
    private JLabel topQRCode;
    private JLabel bottomQRCode;

    public EnvTestPanel_1(EventList list){
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
        FlatLabel infoText = new FlatLabel(new FBEyeNotice().envTestInfoText_1);
        infoText.setBackground(new Color(255, 255, 222));
        infoText.setForeground(Color.BLACK);
        Point location = ViewDisposer.getLocation(550, 300);
        Dimension size = ViewDisposer.getSize(400, 180);
        infoText.setLocation(location);
        infoText.setSize(size);
        infoText.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        infoText.setVisible(true);
        panel.add(infoText);

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
            if(e.destination == Destination.ENV_TEST_1){
                if(e.eventDataType == EventDataType.SIGNAL && e.data.equals("authOk")){
                    list.remove(i);
                    list.add(new Event(Destination.ENV_TEST_2, EventDataType.NAVIGATE, null));
                }
                else if(e.eventDataType == EventDataType.QR_CODE_DATA){
                    QRDataReceived((String)e.data);
                    list.remove(i);
                }
            }

        }
    }

    private void QRDataReceived(String data){
        Dimension QRSize = ViewDisposer.getSize(70, 70);
        int squaredQRSize = Math.min(QRSize.width, QRSize.height);
        ImageIcon img = QRGenerator.generateQR(data, squaredQRSize, squaredQRSize);
        topQRCode.setIcon(img);
        bottomQRCode.setIcon(img);
        panel.revalidate();
        panel.repaint();
    }
}
