/*
 * ExamPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package com.FBEye.UI.page;

import com.FBEye.UI.page.element.*;
import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.datatype.examdata.*;
import com.FBEye.util.QRGenerator;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExamPanel extends Page{
    private final double QR_CHANGE_CYCLE = 3;

    private ExamInfo examInfo;
    private List<AnswerInfo> answers;
    private double QRChangeTime;
    private int squaredQRSize;
    private int data = 0; //test

    private JLabel topQRCode;
    private JLabel bottomQRCode;
    private TimePanel timePanel;
    private MemoPanel memoPanel;
    private ExamMainPanel examMainPanel;
    private QuestionNumberPanel questionNumberPanel;
    private ChatPanel chatPanel;
    private JButton submissionButton;

    public ExamPanel(EventList list){
        super(list);
        QRChangeTime = 0;
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
        Dimension QRSize = ViewDisposer.getSize(70, 70);
        squaredQRSize = Math.min(QRSize.width, QRSize.height);
        ImageIcon img = QRGenerator.generateQR(Double.toString(QRChangeTime), squaredQRSize, squaredQRSize);
        topQRCode = new JLabel(img);
        topQRCode.setLocation(ViewDisposer.getLocation(715, 0));
        topQRCode.setSize(QRSize);
        topQRCode.setVisible(true);
        panel.add(topQRCode);

        bottomQRCode = new JLabel(img);
        bottomQRCode.setLocation(ViewDisposer.getLocation(715, 928));
        bottomQRCode.setSize(QRSize);
        bottomQRCode.setVisible(true);
        panel.add(bottomQRCode);

        timePanel = new TimePanel(1130, 70);
        panel.add(timePanel.getPanel());
        memoPanel = new MemoPanel(1130, 225);
        panel.add(memoPanel.getPanel());
        generateTestData(); //test
        examMainPanel = new ExamMainPanel(375, 70, examInfo);
        panel.add(examMainPanel.getPanel());
        questionNumberPanel = new QuestionNumberPanel(70, 70, examInfo.count);
        panel.add(questionNumberPanel.getPanel());
        chatPanel = new ChatPanel(70, 475);
        panel.add(chatPanel.getPanel());

        submissionButton = new JButton("제출");
        submissionButton.setLocation(ViewDisposer.getLocation(1130, 850));
        submissionButton.setSize(ViewDisposer.getSize(300, 80));
        submissionButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(36)));
        submissionButton.setBackground(Color.ORANGE);
        submissionButton.addActionListener(e -> {
            onSubmissionButtonClicked();
        });
        submissionButton.setVisible(true);
        panel.add(submissionButton);
    }

    @Override
    protected void restore(){
        if(examMainPanel.getIsChanged()) {
            questionNumberPanel.setPrevNumber(examMainPanel.getPrevNumber());
            questionNumberPanel.setCurrentNumber(examMainPanel.getCurrentNumber());
            questionNumberPanel.controlNumber(examMainPanel.getState(examMainPanel.getPrevNumber()));
            examMainPanel.setIsChanged(false);
        }
        if(questionNumberPanel.getIsChanged()){
            examMainPanel.setPrevNumber(questionNumberPanel.getPrevNumber());
            examMainPanel.setCurrentNumber(questionNumberPanel.getCurrentNumber());
            examMainPanel.controlQuestion();
            questionNumberPanel.controlNumber(examMainPanel.getState(examMainPanel.getPrevNumber()));
            questionNumberPanel.setIsChanged(false);
        }
        if(chatPanel.getSendChat()){
            String chat = chatPanel.getChatContent();
            list.add(new Event(Destination.SERVER, EventDataType.CHAT, chat));
            chatPanel.setSendChat(false);
            chatPanel.resetChatContent();
            chatPanel.getPanel().repaint();
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Duration duration = Duration.between(now.toLocalDateTime(), examInfo.endTime);
        if(duration.toSeconds() <= 0){
            JOptionPane.showMessageDialog(panel, "시험이 종료 되었습니다.", "시험 종료", JOptionPane.INFORMATION_MESSAGE);
            timer.cancel();
            endTest();
        }
        timePanel.setTime(duration.toHours() + ":" + duration.toMinutesPart() + ":" + duration.toSecondsPart());

        if(QRChangeTime >= QR_CHANGE_CYCLE){
            ImageIcon img = QRGenerator.generateQR(Integer.toString(++data), squaredQRSize, squaredQRSize);
            topQRCode.setIcon(img);
            bottomQRCode.setIcon(img);
            panel.repaint();
            QRChangeTime = 0;
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                list.remove(i);
                break;
            }
            Event e = list.get(i);
            if(e.destination == Destination.EXAM_PAGE && e.eventDataType == EventDataType.PARAMETER){

            }
            else if(e.destination == Destination.EXAM_PAGE && e.eventDataType == EventDataType.CHAT){
                   chatReceived((String)e.data);
            }
        }

        QRChangeTime += 0.1;
    }

    private void chatReceived(String chat){
        chatPanel.addChatLog(chat);
        chatPanel.resetChatContent();
        chatPanel.getPanel().repaint();
    }

    private void generateTestData(){  //이 함수는 테스트 데이터 생성용
        List<QuestionInfo> questions = new ArrayList<>();
        List<String> options = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            options.add((i + 1) + "번 보기");
        }
        for(int i = 0; i < 9; i++){
            if(i < 3){
                questions.add(new QuestionInfo(i + 1, QuestionType.DESCRIPTIVE, (i + 1) + "번 문제", 1));
            }
            else if(i < 6){
                questions.add(new QuestionInfo(i + 1, QuestionType.ONE_CHOICE, (i + 1) + "번 문제", options, 1));
            }
            else{
                questions.add(new QuestionInfo(i + 1, QuestionType.MULTIPLE_CHOICE, (i + 1) + "번 문제", options, 1));
            }
        }
        examInfo = new ExamInfo("test exam", 9, questions);
    }

    private void endTest(){
        examMainPanel.saveAnswer(AnswerState.SOLVED);
        list.add(new Event(Destination.SERVER, EventDataType.ANSWER, examMainPanel.getAnswers()));
        list.add(new Event(Destination.LOGIN_PAGE, EventDataType.NAVIGATE, null));
    }

    private void onSubmissionButtonClicked(){
        int result = JOptionPane.showConfirmDialog(panel, "제출하시겠습니까?", "제출", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            endTest();
        }
    }
}
