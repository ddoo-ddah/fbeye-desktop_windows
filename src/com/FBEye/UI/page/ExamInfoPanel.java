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
import java.util.List;

public class ExamInfoPanel {
    private JPanel panel;
    private EventList list;
    private ExamInfo examInfo;
    private UserInfo userInfo;

    private JCheckBox readNoticeCheck;
    private JButton takeExamButton;

    public ExamInfoPanel(EventList list){
        this.list = list;
        initPanel();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        setView();
        panel.setVisible(true);

    }

    private void setView(){
        //테스트 데이터
        examInfo = new ExamInfo("test", 3, new ArrayList<>());
        userInfo = new UserInfo(123456, "이름", "test@test.com",  "소속이름");
        List<String> examInfos = new ArrayList<>();
        examInfos.add("시험 정보");
        examInfos.add("  과목명: " + examInfo.name);
        examInfos.add("  시작 시간: " + examInfo.startTime.toString());
        examInfos.add("  종료 시간: " + examInfo.endTime.toString());
        examInfos.add("  문항 수: " + examInfo.count);
        examInfos.add("  총점: " + examInfo.getTotalScore() + " 점");
        List<String> userInfos = new ArrayList<>();
        userInfos.add("응시자 정보");
        userInfos.add("  id: " + userInfo.id);
        userInfos.add("  이름: " + userInfo.name);
        userInfos.add("  이메일: " + userInfo.email);
        userInfos.add("  소속: " + userInfo.department);
        List<String> notices = new FBEyeNotice().notices;
        //여기까지

        JList<String> userInfoListView = new JList<>(new Vector<>(userInfos));
        Point location = ViewDisposer.getLocation(180, 130);
        Dimension size = ViewDisposer.getSize(490, 350);
        userInfoListView.setLocation(location);
        userInfoListView.setSize(size);
        userInfoListView.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        userInfoListView.setSelectionModel(new DisabledItemSelectionModel());
        userInfoListView.setVisible(true);
        panel.add(userInfoListView);

        JList<String> examInfoListView = new JList<>(new Vector<>(examInfos));
        location = ViewDisposer.getLocation(180, 520);
        examInfoListView.setLocation(location);
        examInfoListView.setSize(size);
        examInfoListView.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        examInfoListView.setSelectionModel(new DisabledItemSelectionModel());
        examInfoListView.setVisible(true);
        panel.add(examInfoListView);

        JList<String> noticeListView = new JList<>(new Vector<>(notices));
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

    private void onChecked(){
        readNoticeCheck.setEnabled(false);
        takeExamButton.setEnabled(true);
        panel.repaint();
    }

    private void onButtonPressed(){
        list.add(new Event(Destination.ENV_TEST_1, EventDataType.NAVIGATE, null));
    }

    public JPanel getPanel(){
        return panel;
    }
}
