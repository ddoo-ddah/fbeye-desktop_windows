/*
 * EnvTestPanel_4.java
 * Author : susemeeee
 * Created Date : 2020-08-14
 */
package xyz.fbeye.UI.page;

import com.mommoo.flat.button.FlatButton;
import com.mommoo.util.FontManager;
import xyz.fbeye.UI.page.element.*;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import org.json.JSONObject;
import xyz.fbeye.datatype.examdata.AnswerState;
import xyz.fbeye.datatype.examdata.ExamInfo;
import xyz.fbeye.datatype.examdata.QuestionInfo;
import xyz.fbeye.datatype.examdata.QuestionType;
import xyz.fbeye.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EnvTestPanel_3 extends Page{
    private ExamInfo sampleExamInfo;
    private ExamInfo examInfo;
    private int squaredQRSize;
    private String encryptedQuestion;
    private boolean isDecrypted;

    private JLabel topQRCode;
    private JLabel bottomQRCode;
    private TimePanel timePanel;
    private MemoPanel memoPanel;
    private ExamMainPanel examMainPanel;
    private QuestionNumberPanel questionNumberPanel;
    private ChatPanel chatPanel;
    private FlatButton startTestButton;

    public EnvTestPanel_3(EventList list){
        super(list);
        isDecrypted = false;
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
        timePanel.setText("시작까지 남은 시간");
        panel.add(timePanel.getPanel());
        memoPanel = new MemoPanel(1130, 225);
        panel.add(memoPanel.getPanel());
        generateSampleData();
        examMainPanel = new ExamMainPanel(375, 70, sampleExamInfo);
        panel.add(examMainPanel.getPanel());
        questionNumberPanel = new QuestionNumberPanel(70, 70, sampleExamInfo.count);
        panel.add(questionNumberPanel.getPanel());
        chatPanel = new ChatPanel(70, 475);
        panel.add(chatPanel.getPanel());

        JPanel startPanel = new JPanel();
        startPanel.setLocation(ViewDisposer.getLocation(1130, 840));
        startPanel.setSize(ViewDisposer.getSize(300, 80));
        startPanel.setLayout(new GridLayout());
        startPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        startPanel.setVisible(true);
        startTestButton = new FlatButton("시험 시작");
        startTestButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
        startTestButton.setBackground(new Color(255, 109, 112));
        startTestButton.setForeground(Color.BLACK);
        startTestButton.addActionListener(e -> {
            onStartTestButtonClicked();
        });
        startTestButton.setEnabled(false);
        startTestButton.setVisible(true);
        startPanel.add(startTestButton);
        panel.add(startPanel);
    }

    private void setQRCode(String data){
        ImageIcon img = QRGenerator.generateQR(data, squaredQRSize, squaredQRSize);
        topQRCode.setIcon(img);
        bottomQRCode.setIcon(img);
        panel.repaint();
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

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                list.remove(i);
                break;
            }
            Event e = list.get(i);
            if(e.destination == Destination.ENV_TEST_3){
                if(e.eventDataType == EventDataType.CHAT){
                    chatReceived((String)e.data);
                }
                else if(e.eventDataType == EventDataType.EXAM_INFO){
                    examInfoReceived((ExamInfo) e.data);
                }
                else if(e.eventDataType == EventDataType.QR_CODE_DATA){
                    setQRCode((String)e.data);
                }
                else if(e.eventDataType == EventDataType.ENCRYPTED_QUESTION){
                    encryptedQuestion = (String)e.data;
                }
                else if(e.eventDataType == EventDataType.QUESTION_KEY){
                    try{
                        encryptedQuestion = Decryptor.decrypt(encryptedQuestion, (String)e.data);
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                    List<QuestionInfo> questions = QuestionMaker.makeQuestion(new JSONObject(encryptedQuestion));
                    if(questions.size() != 0){
                        ExamInfo newExamInfo = new ExamInfo(examInfo.name, examInfo.count,
                                examInfo.startTime, examInfo.endTime, questions);
                        list.add(new Event(Destination.EXAM_PAGE, EventDataType.EXAM_INFO, newExamInfo));
                        isDecrypted = true;
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
            chatPanel.getPanel().repaint();
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        Duration startDuration = Duration.between(now.toLocalDateTime(), examInfo.startTime);
        Duration endDuration = Duration.between(now.toLocalDateTime(), examInfo.endTime);
        if(endDuration.toSeconds() <= 0){
            JOptionPane.showMessageDialog(panel, "시험이 종료 되었습니다.", "시험 종료", JOptionPane.INFORMATION_MESSAGE);
            timer.cancel();
            endTest();
        }
        if(startDuration.toSeconds() <= 0){
            timePanel.setTime("00:00:00");
            startTestButton.setEnabled(true);
            panel.repaint();
        }
        else{
            timePanel.setTime(String.format("%02d:%02d:%02d", startDuration.toHours(), startDuration.toMinutesPart(),
                    startDuration.toSecondsPart()));
        }
    }

    private void chatReceived(String chat){
        chatPanel.addChatLog(chat);
        chatPanel.resetChatContent();
        chatPanel.getPanel().repaint();
    }

    private void examInfoReceived(ExamInfo examInfo){
        this.examInfo = examInfo;
    }

    private void generateSampleData(){
        List<QuestionInfo> questions = new ArrayList<>();
        List<String> options = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            options.add((i + 1) + "번 보기");
        }
        for(int i = 0; i < 3; i++){
            if(i == 0){
                questions.add(new QuestionInfo(i + 1, QuestionType.DESCRIPTIVE, (i + 1) + ".서술형 문제"));
            }
            else if(i == 1){
                questions.add(new QuestionInfo(i + 1, QuestionType.ONE_CHOICE, (i + 1) + ".객관식 문제", options));
            }
            else{
                questions.add(new QuestionInfo(i + 1, QuestionType.MULTIPLE_CHOICE, (i + 1) + ".객관식 다수 선택 문제", options));
            }
        }
        sampleExamInfo = new ExamInfo("test exam", 3, questions);
    }

    private void onStartTestButtonClicked(){
        startTest();
    }

    private void startTest(){
        if(isDecrypted){
            timer.cancel();
            list.add(new Event(Destination.SERVER, EventDataType.SIGNAL, SignalDataMaker.make("startExam")));
            list.add(new Event(Destination.EXAM_PAGE, EventDataType.NAVIGATE, null));
        }
    }

    private void endTest(){
        list.add(new Event(Destination.SERVER, EventDataType.DISCONNECT, null));
    }
}
