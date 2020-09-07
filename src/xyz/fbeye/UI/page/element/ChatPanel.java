/*
 * ChatPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-14
 */
package xyz.fbeye.UI.page.element;

import xyz.fbeye.datatype.ChatInfo;
import xyz.fbeye.util.ViewDisposer;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.component.FlatScrollPane;
import com.mommoo.flat.list.FlatListView;
import com.mommoo.flat.text.textfield.FlatTextField;
import com.mommoo.flat.text.textfield.format.FlatTextFormat;
import com.mommoo.util.FontManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatPanel {
    private JPanel panel;
    private Point panelLocation;
    private boolean sendChat;

    private FlatListView<JPanel> chatList;
    private FlatScrollPane scrollPane;
    private FlatTextField chatText;
    private FlatButton sendButton;

    public ChatPanel(int x, int y){
        sendChat = false;
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 445));
        panel.setLocation(panelLocation);
        panel.setBackground(new Color(255, 255, 222));
        panel.setLayout(null);
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        JLabel text = new JLabel("채팅");
        Point location = ViewDisposer.getLocation(73, 478);
        Dimension size = ViewDisposer.getSize(294, 24);
        text.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        text.setSize(size);
        text.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(24)));
        text.setVisible(true);
        panel.add(text);

        JPanel chatListPanel = new JPanel();
        location = ViewDisposer.getLocation(73, 505);
        size = ViewDisposer.getSize(294, 370);
        chatListPanel.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        chatListPanel.setSize(size);
        chatListPanel.setBackground(new Color(255, 255, 222));
        chatListPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(2)));
        chatListPanel.setLayout(new GridLayout());

        chatList = new FlatListView<>();
        chatList.setBackground(new Color(255, 255, 222));
        chatList.setMultiSelectionMode(false);
        chatList.setSingleSelectionMode(false);
        chatList.setSelectionColor(new Color(255, 255, 222));
        scrollPane = new FlatScrollPane(chatList.getComponent());
        scrollPane.setBackground(new Color(255, 255, 222));
        scrollPane.setVerticalScrollTrackColor(new Color(255, 222, 222));
        scrollPane.setAutoscrolls(true);
        scrollPane.setVisible(true);
        chatListPanel.add(scrollPane);
        panel.add(chatListPanel);

        chatText = new FlatTextField(false);
        location = ViewDisposer.getLocation(73, 880);
        size = ViewDisposer.getSize(240, 32);
        chatText.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        chatText.setSize(size);
        chatText.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(1)));
        chatText.setOpaque(false);
        chatText.setHint("채팅 입력");
        chatText.setFormat(FlatTextFormat.TEXT, FlatTextFormat.NUMBER_DECIMAL, FlatTextFormat.SPECIAL_CHARACTER);
        chatText.setHintForeground(new Color(255, 109, 112));
        chatText.setFocusGainedColor(new Color(255, 109, 112));
        chatText.setFocusLostColor(Color.BLACK);
        chatText.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(24)));
        chatText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    onSendButtonClicked();
                }
            }
        });
        chatText.setVisible(true);
        panel.add(chatText);

        sendButton = new FlatButton("전송");
        location = ViewDisposer.getLocation(317, 880);
        size = ViewDisposer.getSize(47, 32);
        sendButton.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        sendButton.setSize(size);
        sendButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(22)));
        sendButton.setBackground(new Color(255, 109, 112));
        sendButton.setForeground(Color.BLACK);
        sendButton.addActionListener(e -> {
            onSendButtonClicked();
        });
        sendButton.setVisible(true);
        panel.add(sendButton);
    }

    private void onSendButtonClicked(){
        if(!chatText.getText().equals("")){
            sendChat = true;
        }
        else{
            sendChat = false;
        }
    }

    public void addChat(ChatInfo chat){
        JPanel item2 = new ChatItemPanel(chat).getPanel();
        chatList.addItem(item2, chatList.getItemSize());
    }

    public void resetChatContent(){
        chatText.setText(null);
        chatText.setHint("채팅 입력");
    }

    public JPanel getPanel(){
        return panel;
    }

    public String getChatContent(){
        return chatText.getText();
    }

    public boolean getSendChat(){
        return sendChat;
    }

    public void setSendChat(boolean sendChat){
        this.sendChat = sendChat;
    }
}
