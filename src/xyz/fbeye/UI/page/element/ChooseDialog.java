/*
 * ChooseDialog.java
 * Author : susemeeee
 * Created Date : 2020-09-04
 */
package xyz.fbeye.UI.page.element;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.util.FontManager;
import xyz.fbeye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;

public class ChooseDialog {
    private boolean buttonValue;

    private JDialog dialog;
    private JPanel panel;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public ChooseDialog(JFrame parent, String title, String content){
        buttonValue = false;
        dialog = new JDialog(parent, title, true);

        panel = new JPanel();
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
        layout.columnWeights = new double[]{50, 60, 60, 60, 60, 60, 50};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0};
        layout.rowWeights = new double[]{20, 110, 40, 30, 40};
        constraints = new GridBagConstraints();
        panel.setBackground(new Color(255, 255, 222));
        panel.setLayout(layout);

        dialog.setSize(ViewDisposer.getSize(300, 200));
        dialog.setResizable(false);
        dialog.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (dialog.getWidth() / 2),
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (dialog.getHeight() / 2));
        dialog.setLayout(new GridLayout());

        JLabel contentText = new JLabel(content);
        contentText.setBackground(new Color(255, 255, 222));
        contentText.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        contentText.setVisible(true);
        addComponent(contentText, 1, 1, 5, 1, GridBagConstraints.NONE);

        FlatButton OKButton = new FlatButton("예");
        OKButton.setBackground(new Color(255, 109, 112));
        OKButton.setForeground(Color.BLACK);
        OKButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(20)));
        OKButton.addActionListener(e -> {
            buttonValue = true;
            dialog.dispose();
        });
        addComponent(OKButton, 2, 3, 1, 1, GridBagConstraints.BOTH);

        FlatButton cancelButton = new FlatButton("아니오");
        cancelButton.setBackground(new Color(255, 109, 112));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(20)));
        cancelButton.addActionListener(e -> {
            buttonValue = false;
            dialog.dispose();
        });
        addComponent(cancelButton, 4, 3, 1, 1, GridBagConstraints.BOTH);

        dialog.add(panel);
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

    public boolean show(){
        dialog.setVisible(true);
        return buttonValue;
    }
}
