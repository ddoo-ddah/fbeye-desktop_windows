/*
 * ChatPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-14
 */
package com.FBEye.UI.page.element;

import com.FBEye.util.DisabledItemSelectionModel;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class ChatPanel {
    private JPanel panel;
    private Point panelLocation;
    private Vector<String> chatLog;
    private boolean sendChat;

    private JList<String> chatList;
    private JTextField chatText;
    private JButton sendButton;

    public ChatPanel(int x, int y){
        chatLog = new Vector<>();
        sendChat = false;
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(300, 455));
        panel.setLocation(panelLocation);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
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
        text.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        text.setVisible(true);
        panel.add(text);

        JPanel chatListPanel = new JPanel();
        location = ViewDisposer.getLocation(73, 505); //틈새 3로 줌
        size = ViewDisposer.getSize(294, 390);
        chatListPanel.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        chatListPanel.setSize(size);
        chatListPanel.setLayout(new GridLayout());

        chatList = new JList<>(chatLog);
        chatList.setBackground(Color.ORANGE);
        chatList.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(20)));
        chatList.setSelectionModel(new DisabledItemSelectionModel());
        chatList.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(chatList);
        scrollPane.setVisible(true);
        chatListPanel.add(scrollPane);
        chatListPanel.setVisible(true);
        panel.add(chatListPanel);

        chatText = new JTextField();
        location = ViewDisposer.getLocation(73, 895);
        size = ViewDisposer.getSize(240, 32);
        chatText.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        chatText.setSize(size);
        chatText.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        chatText.setEditable(true);
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

        sendButton = new JButton("전송");
        location = ViewDisposer.getLocation(313, 895);
        size = ViewDisposer.getSize(50, 32);
        sendButton.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        sendButton.setSize(size);
        sendButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(22)));
        sendButton.setBackground(Color.ORANGE);
        sendButton.addActionListener(e -> {
            onSendButtonClicked();
        });
        sendButton.setVisible(true);
        panel.add(sendButton);
    }

    private void onSendButtonClicked(){
        if(!chatText.getText().equals("")){
            chatLog.add(chatText.getText());
            sendChat = true;
        }
        else{
            sendChat = false;
        }
    }

    public void resetChatContent(){
        chatText.setText("");
        chatList.setListData(chatLog);
    }

    public void addChatLog(String chat){
        chatLog.add(chat);
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
