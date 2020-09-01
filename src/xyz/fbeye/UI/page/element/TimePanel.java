/*
 * TImePanel.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package xyz.fbeye.UI.page.element;

import xyz.fbeye.util.ViewDisposer;
import com.mommoo.util.FontManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TimePanel {
    private JPanel panel;
    private Point panelLocation;

    private JLabel text;
    private JLabel time;

    public TimePanel(int x, int y){
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 150));
        panel.setLocation(panelLocation);
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 222));
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        text = new JLabel("남은 시간");
        Point location = ViewDisposer.getLocation(1150, 105);
        Dimension size = ViewDisposer.getSize(210, 25);
        text.setLocation(new Point(location.x - panelLocation.x, location.y - panelLocation.y));
        text.setSize(size);
        text.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
        text.setVisible(true);
        panel.add(text);

        time = new JLabel("99:59:59");
        location = ViewDisposer.getLocation(1150, 145);
        size = ViewDisposer.getSize(210, 50);
        time.setLocation(new Point(location.x - panelLocation.x, location.y - panelLocation.y));
        time.setSize(size);
        time.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(72)));
        time.setVisible(true);
        panel.add(time);
    }

    public void setTime(String time){
        this.time.setText(time);
        panel.revalidate();
    }

    public JPanel getPanel(){
        return panel;
    }

    public void setText(String text){
        this.text.setText(text);
        panel.revalidate();
    }
}
