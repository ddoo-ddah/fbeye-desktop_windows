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
import com.FBEye.util.AnswerTypeConverter;
import com.FBEye.util.QRGenerator;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class ExamPanel extends Page{
    private ExamInfo examInfo;
    private int squaredQRSize;

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
        Dimension QRSize = ViewDisposer.getSize(70, 70);
        squaredQRSize = Math.min(QRSize.width, QRSize.height);
        topQRCode = new JLabel();
        topQRCode.setLocation(ViewDisposer.getLocation(715, 0));
        topQRCode.setSize(QRSize);
        topQRCode.setVisible(true);
        panel.add(topQRCode);

        bottomQRCode = new JLabel();
        bottomQRCode.setLocation(ViewDisposer.getLocation(715, 928));
        bottomQRCode.setSize(QRSize);
        bottomQRCode.setVisible(true);
        panel.add(bottomQRCode);

        timePanel = new TimePanel(1130, 70);
        panel.add(timePanel.getPanel());
        memoPanel = new MemoPanel(1130, 225);
        panel.add(memoPanel.getPanel());
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

    private void setQuestionNumberBackground(){
        questionNumberPanel.setPrevNumber(examMainPanel.getPrevNumber());
        questionNumberPanel.setCurrentNumber(examMainPanel.getCurrentNumber());
        questionNumberPanel.controlNumber(examMainPanel.getState(examMainPanel.getPrevNumber()));
        examMainPanel.setIsChanged(false);
    }

    private void moveQuestion(){
        examMainPanel.saveAnswer(AnswerState.SOLVED);
        examMainPanel.setPrevNumber(questionNumberPanel.getPrevNumber());
        examMainPanel.setCurrentNumber(questionNumberPanel.getCurrentNumber());
        examMainPanel.controlQuestion();
        questionNumberPanel.controlNumber(examMainPanel.getState(examMainPanel.getPrevNumber()));
        questionNumberPanel.setIsChanged(false);
    }

    private void setQRCode(String data){
        ImageIcon img = QRGenerator.generateQR(data, squaredQRSize, squaredQRSize);
        topQRCode.setIcon(img);
        bottomQRCode.setIcon(img);
        panel.repaint();
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                list.remove(i);
                break;
            }
            Event e = list.get(i);
            if(e.destination == Destination.EXAM_PAGE){
                if(e.eventDataType == EventDataType.EXAM_INFO){
                    examInfoReceived((ExamInfo)e.data);
                }
                else if(e.eventDataType == EventDataType.QR_CODE_DATA){
                    setQRCode((String)e.data);
                }
                else if(e.eventDataType == EventDataType.CHAT){
                    chatReceived((String)e.data);
                }
                list.remove(i);
            }
        }

        if(examMainPanel.getIsChanged()) {
            setQuestionNumberBackground();
        }
        if(questionNumberPanel.getIsChanged()){
            moveQuestion();
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
    }

    private void chatReceived(String chat){
        chatPanel.addChatLog(chat);
        chatPanel.resetChatContent();
        chatPanel.getPanel().repaint();
    }

    private void examInfoReceived(ExamInfo examInfo){
        this.examInfo = examInfo;
        examMainPanel = new ExamMainPanel(375, 70, examInfo);
        panel.add(examMainPanel.getPanel());
        questionNumberPanel = new QuestionNumberPanel(70, 70, examInfo.count);
        panel.add(questionNumberPanel.getPanel());
        panel.repaint();
    }

    private void endTest(){
        examMainPanel.saveAnswer(AnswerState.SOLVED);
        list.add(new Event(Destination.SERVER, EventDataType.ANSWER, AnswerTypeConverter.convert(examMainPanel.getAnswer())));
        JOptionPane.showMessageDialog(panel, "수고하셨습니다.", "제출", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void onSubmissionButtonClicked(){
        int result = JOptionPane.showConfirmDialog(panel, "제출하시겠습니까?", "제출", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            endTest();
        }
    }
}
