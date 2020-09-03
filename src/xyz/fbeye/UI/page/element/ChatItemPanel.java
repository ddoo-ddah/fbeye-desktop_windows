/*
 * ChatItemPanel.java
 * Author : susemeeee
 * Created Date : 2020-09-03
 */
package xyz.fbeye.UI.page.element;

import com.mommoo.util.FontManager;
import xyz.fbeye.datatype.ChatInfo;
import xyz.fbeye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;

public class ChatItemPanel {
    private JPanel panel;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public ChatItemPanel(ChatInfo chatInfo){
        panel = new JPanel();
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0};
        layout.columnWeights = new double[]{1, Double.MIN_VALUE};
        layout.rowHeights = new int[]{0, 0, 0};
        layout.rowWeights = new double[]{18, 24, 18};
        constraints = new GridBagConstraints();
        panel.setBackground(new Color(255, 255, 222));
        panel.setLayout(layout);
        panel.setVisible(true);

        JLabel sender = new JLabel(chatInfo.name + " :");
        sender.setBackground(new Color(255, 255, 222));
        sender.setFont(FontManager.getNanumGothicFont(Font.BOLD, ViewDisposer.getFontSize(18)));
        addComponent(sender, 0, 0, 1, 1, GridBagConstraints.BOTH);

        JTextArea message = new JTextArea(chatInfo.message);
        message.setBackground(new Color(255, 255, 222));
        message.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(26)));
        message.setEditable(false);
        addComponent(message, 0, 1, 0, 0, GridBagConstraints.BOTH);

        JLabel time = new JLabel(chatInfo.timestamp, SwingConstants.RIGHT);
        time.setBackground(new Color(255, 255, 222));
        time.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(18)));
        addComponent(time, 0, 2, 1, 1, GridBagConstraints.BOTH);
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
}
