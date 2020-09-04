/*
 * ExamPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package xyz.fbeye.UI.page;

import xyz.fbeye.datatype.ChatInfo;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.datatype.examdata.ExamInfo;
import xyz.fbeye.util.AnswerTypeConverter;
import xyz.fbeye.util.QRGenerator;
import xyz.fbeye.util.SignalDataMaker;
import xyz.fbeye.util.ViewDisposer;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.util.FontManager;
import xyz.fbeye.UI.page.element.*;
import xyz.fbeye.datatype.examdata.AnswerState;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private FlatButton submissionButton;

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
    protected void initPanel() {
        panel = new JPanel();
        panel.setBackground(new Color(222, 239, 255));
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(null);
        setView();
        panel.setVisible(true);
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
        bottomQRCode.setLocation(ViewDisposer.getLocation(715, 920));
        bottomQRCode.setSize(QRSize);
        bottomQRCode.setVisible(true);
        panel.add(bottomQRCode);

        timePanel = new TimePanel(1130, 70);
        panel.add(timePanel.getPanel());
        memoPanel = new MemoPanel(1130, 225);
        panel.add(memoPanel.getPanel());
        chatPanel = new ChatPanel(70, 475);
        panel.add(chatPanel.getPanel());

        JPanel submissionPanel = new JPanel();
        submissionPanel.setLocation(ViewDisposer.getLocation(1130, 840));
        submissionPanel.setSize(ViewDisposer.getSize(300, 80));
        submissionPanel.setLayout(new GridLayout());
        submissionPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));

        submissionButton = new FlatButton("제출");
        submissionButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
        submissionButton.setBackground(new Color(255, 109, 112));
        submissionButton.setForeground(Color.BLACK);
        submissionButton.addActionListener(e -> {
            onSubmissionButtonClicked();
        });
        submissionButton.setVisible(true);
        submissionPanel.add(submissionButton);
        submissionPanel.setVisible(true);
        panel.add(submissionPanel);
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
        panel.revalidate();
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
                    chatReceived((ChatInfo)e.data);
                }
                else if(e.eventDataType == EventDataType.DIALOG_RESULT){
                    if((int)e.data == 0){
                        endTest();
                    }
                    else if((int)e.data == 2){
                        System.exit(0);
                    }
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
            chatPanel.getPanel().revalidate();
            chatPanel.getPanel().repaint();
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Duration duration = Duration.between(now.toLocalDateTime(), examInfo.endTime);
        if(duration.toSeconds() <= 0){
            list.add(new Event(Destination.MANAGER, EventDataType.DIALOG_REQUEST, "examEnd"));
            timer.cancel();
            endTest();
        }
        timePanel.setTime(String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(),
                duration.toSecondsPart()));
    }

    private void chatReceived(ChatInfo chat){
        chatPanel.addChat(chat);
        chatPanel.getPanel().revalidate();
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
        list.add(new Event(Destination.SERVER, EventDataType.SIGNAL, SignalDataMaker.make("endExam")));
        list.add(new Event(Destination.SERVER, EventDataType.ANSWER, AnswerTypeConverter.convert(examMainPanel.getAnswer())));
        list.add(new Event(Destination.SERVER, EventDataType.DISCONNECT, null));
        list.add(new Event(Destination.MANAGER, EventDataType.DIALOG_REQUEST, "submissionEnd"));
    }

    private void onSubmissionButtonClicked(){
        list.add(new Event(Destination.MANAGER, EventDataType.DIALOG_REQUEST, "submission"));
    }
}
