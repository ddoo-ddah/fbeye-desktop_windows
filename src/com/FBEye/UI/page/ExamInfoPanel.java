/*
 * ExamInfoPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package com.FBEye.UI.page;

import com.FBEye.datatype.FBEyeNotice;
import com.FBEye.datatype.UserInfo;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.datatype.examdata.ExamInfo;
import com.FBEye.util.DisabledItemSelectionModel;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class ExamInfoPanel extends Page{
    private ExamInfo examInfo;
    private UserInfo userInfo;

    private JList<String> userInfoListView;
    private JList<String> examInfoListView;
    private JCheckBox readNoticeCheck;
    private JButton takeExamButton;

    public ExamInfoPanel(EventList list){
        super(list);
        examInfo = null;
        userInfo = null;
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
    protected void setView(){
        userInfoListView = new JList<>();
        Point location = ViewDisposer.getLocation(180, 130);
        Dimension size = ViewDisposer.getSize(490, 350);
        userInfoListView.setLocation(location);
        userInfoListView.setSize(size);
        userInfoListView.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        userInfoListView.setSelectionModel(new DisabledItemSelectionModel());
        userInfoListView.setVisible(true);
        panel.add(userInfoListView);

        examInfoListView = new JList<>();
        location = ViewDisposer.getLocation(180, 520);
        examInfoListView.setLocation(location);
        examInfoListView.setSize(size);
        examInfoListView.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        examInfoListView.setSelectionModel(new DisabledItemSelectionModel());
        examInfoListView.setVisible(true);
        panel.add(examInfoListView);

        JList<String> noticeListView = new JList<>(new Vector<>(new FBEyeNotice().notices));
        location = ViewDisposer.getLocation(840, 130);
        size = ViewDisposer.getSize(510, 650);
        noticeListView.setLocation(location);
        noticeListView.setSize(size);
        noticeListView.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        noticeListView.setSelectionModel(new DisabledItemSelectionModel());
        noticeListView.setVisible(true);
        panel.add(noticeListView);

        readNoticeCheck = new JCheckBox("나는 이걸 읽었다");
        location = ViewDisposer.getLocation(850, 830);
        size = ViewDisposer.getSize(210, 40);
        readNoticeCheck.setLocation(location);
        readNoticeCheck.setSize(size);
        readNoticeCheck.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        readNoticeCheck.setBackground(Color.WHITE);
        readNoticeCheck.addItemListener(e -> {
            onChecked();
        });
        readNoticeCheck.setVisible(true);
        panel.add(readNoticeCheck);

        takeExamButton = new JButton("응시");
        location = ViewDisposer.getLocation(1200, 800);
        size = ViewDisposer.getSize(150, 100);
        takeExamButton.setLocation(location);
        takeExamButton.setSize(size);
        takeExamButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        takeExamButton.setEnabled(false);
        takeExamButton.setVisible(true);
        takeExamButton.addActionListener(e -> {
            onButtonPressed();
        });
        panel.add(takeExamButton);
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.EXAM_INFO_PAGE && list.get(i).eventDataType != EventDataType.NAVIGATE){
                Event e = list.get(i);
                if(e.eventDataType == EventDataType.EXAM_INFO){
                    examInfoReceived((ExamInfo) e.data);
                }
                else if(e.eventDataType == EventDataType.USER_INFO){
                    userInfoReceived((UserInfo) e.data);
                }
                list.remove(i);
            }
        }
    }

    private void examInfoReceived(ExamInfo examInfo){
        this.examInfo = examInfo;
        examInfoListView.setListData(this.examInfo.getInfoList());
        panel.repaint();
    }

    private void userInfoReceived(UserInfo userInfo){
        this.userInfo = userInfo;
        userInfoListView.setListData(this.userInfo.getInfoList());
        panel.repaint();
    }

    private void onChecked(){
        readNoticeCheck.setEnabled(false);
        takeExamButton.setEnabled(true);
        panel.repaint();
    }

    private void onButtonPressed() {
        list.add(new Event(Destination.ENV_TEST_1, EventDataType.NAVIGATE, null));
    }
}
