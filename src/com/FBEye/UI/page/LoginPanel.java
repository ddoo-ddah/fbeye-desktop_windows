/*
 * LoginPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package com.FBEye.UI.page;

import com.FBEye.datatype.LoginInfo;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.util.DataExchanger;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPanel extends Page{

    private JButton loginButton;
    private JTextField inputExamId;
    private JTextField inputUserId;
    private JLabel examIdLabel;
    private JLabel userIdLabel;
    private JLabel logoImageLabel;

    public LoginPanel(EventList list){
        super(list);
        initPanel();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                restore();
            }
        };
        timer.schedule(task, 100, 100);
    }

    @Override
    protected void setView(){
        loginButton = new JButton("인증");
        Point location = ViewDisposer.getLocation(625, 750);
        Dimension size = ViewDisposer.getSize(200, 80);
        loginButton.setBounds(location.x, location.y, size.width, size.height);
        loginButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(50)));
        loginButton.addActionListener(e -> {
            onLoginButtonClicked();
        });
        loginButton.setVisible(true);
        panel.add(loginButton);

        inputExamId = new JTextField();
        location = ViewDisposer.getLocation(438, 500);
        size = ViewDisposer.getSize(625, 85);
        inputExamId.setBounds(location.x, location.y, size.width, size.height);
        inputExamId.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(50)));
        inputExamId.setVisible(true);
        panel.add(inputExamId);

        inputUserId = new JTextField();
        location = ViewDisposer.getLocation(438, 615);
        inputUserId.setBounds(location.x, location.y, size.width, size.height);
        inputUserId.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(50)));
        inputUserId.setVisible(true);
        panel.add(inputUserId);

        examIdLabel = new JLabel("시험 코드");
        location = ViewDisposer.getLocation(112, 500);
        size = ViewDisposer.getSize(214, 67);
        examIdLabel.setBounds(location.x, location.y, size.width, size.height);
        examIdLabel.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(50)));
        examIdLabel.setVisible(true);
        panel.add(examIdLabel);

        userIdLabel = new JLabel("응시자 코드");
        location = ViewDisposer.getLocation(112, 615);
        userIdLabel.setBounds(location.x, location.y, size.width, size.height);
        userIdLabel.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(50)));
        userIdLabel.setVisible(true);
        panel.add(userIdLabel);

        ImageIcon img = new ImageIcon("files/FBI.png");
        logoImageLabel = new JLabel(img);
        System.out.println();
        location = ViewDisposer.getLocation(626, 150);
        size = ViewDisposer.getSize(250, 250);
        logoImageLabel.setBounds(location.x, location.y, size.width, size.height);
        logoImageLabel.setVisible(true);
        panel.add(logoImageLabel);
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.LOGIN_PAGE && list.get(i).eventDataType == EventDataType.SIGNAL){
                if(new DataExchanger<>().fromByteArray(list.get(i).data).equals("OK")){
                    login();
                }
                else{
                    JOptionPane.showMessageDialog(panel, "입력한 정보에 맞는 시험이 없습니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
                }
                list.remove(i);
            }
        }
    }

    private void login(){
        list.add(new Event(Destination.EXAM_INFO_PAGE, EventDataType.NAVIGATE, null));
    }

    private void onLoginButtonClicked(){
        String loginData = new LoginInfo(inputExamId.getText(), inputUserId.getText()).toString();
        list.add(new Event(Destination.SERVER, EventDataType.LOGINCODE, new DataExchanger<String>().toByteArray(loginData)));
    }
}
