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
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private JLabel text;
    private JLabel time;

    public TimePanel(int x, int y){
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0};
        layout.columnWeights = new double[]{70, 160, 70, Double.MIN_VALUE};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0};
        layout.rowWeights = new double[]{35, 30, 5, 50, 30, Double.MIN_VALUE};
        constraints = new GridBagConstraints();
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 150));
        panel.setLocation(panelLocation);
        panel.setLayout(layout);
        panel.setBackground(new Color(255, 255, 222));
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        text = new JLabel("남은 시간");
        text.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
        text.setVisible(true);
        addComponent(text, 1, 1, 1, 1, GridBagConstraints.NONE);

        time = new JLabel("99:59:59");
        time.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(72)));
        time.setVisible(true);
        addComponent(time, 1, 3, 1, 1, GridBagConstraints.NONE);
    }

    private void addComponent(Component c, int col, int row, int width, int height, int fill){
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = fill;
        layout.setConstraints(c, constraints);
        panel.add(c);
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
