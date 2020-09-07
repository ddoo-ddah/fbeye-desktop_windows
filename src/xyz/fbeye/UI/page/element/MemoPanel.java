/*
 * MemoPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package xyz.fbeye.UI.page.element;

import xyz.fbeye.util.ViewDisposer;
import com.mommoo.flat.component.FlatScrollPane;
import com.mommoo.flat.text.textarea.FlatTextArea;
import com.mommoo.util.FontManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MemoPanel {
    private JPanel panel;
    private Point panelLocation;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private JPanel memoArea;
    private JTextArea memoText;

    public MemoPanel(int x, int y){
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0};
        layout.columnWeights = new double[]{5, 290, 5, Double.MIN_VALUE};
        layout.rowHeights = new int[]{0, 0, 0, 0};
        layout.rowWeights = new double[]{30, 5, 560, 5, Double.MIN_VALUE};
        constraints = new GridBagConstraints();
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 610));
        panel.setLocation(panelLocation);
        panel.setLayout(layout);
        panel.setBackground(new Color(255, 255, 222));
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        JLabel text = new JLabel("메모지");
        text.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(24)));
        text.setVisible(true);
        addComponent(text, 1, 0, 2, 1, GridBagConstraints.BOTH);
        memoArea = new JPanel();
        memoArea.setBackground(new Color(255, 255, 222));
        memoArea.setLayout(new GridLayout());

        memoText = new JTextArea();
        memoText.setBackground(new Color(255, 255, 222));
        memoText.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(20)));
        memoText.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(2)));
        FlatScrollPane scrollPane = new FlatScrollPane(memoText);
        scrollPane.setAutoscrolls(true);
        scrollPane.setVerticalScrollTrackColor(new Color(255, 222, 222));
        scrollPane.setVisible(true);
        memoArea.add(scrollPane);
        addComponent(memoArea, 1, 2, 1, 1, GridBagConstraints.BOTH);
    }

    protected void addComponent(Component c, int col, int row, int width, int height, int fill){
        constraints.gridx = col;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = fill;
        layout.setConstraints(c, constraints);
        panel.add(c);
    }

    public JPanel getPanel(){
        return panel;
    }

    public String getText(){
        return memoText.getText();
    }
}
