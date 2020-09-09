/*
 * ExamInfoPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package xyz.fbeye.UI.page;

import xyz.fbeye.datatype.FBEyeNotice;
import xyz.fbeye.datatype.UserInfo;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.datatype.examdata.ExamInfo;
import xyz.fbeye.util.ViewDisposer;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.list.FlatListView;
import com.mommoo.flat.select.FlatCheckBox;
import com.mommoo.flat.text.label.FlatLabel;
import com.mommoo.util.FontManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class ExamInfoPanel extends Page{
    private ExamInfo examInfo;
    private UserInfo userInfo;

    private FlatListView<FlatLabel> userInfoListView;
    private FlatListView<FlatLabel> examInfoListView;
    //private FlatCheckBox readNoticeCheck;
    private JCheckBox readNoticeCheck;
    private FlatButton takeExamButton;

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
    protected void initPanel() {
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
        layout.columnWeights = new double[]{150, 550, 100, 300, 100, 150, 150, Double.MIN_VALUE};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        layout.rowWeights = new double[]{130, 350, 40, 210, 80, 60, 130, Double.MIN_VALUE};
        constraints = new GridBagConstraints();
        panel = new JPanel();
        panel.setBackground(new Color(222, 239, 255));
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLocation(new Point(0,0));
        panel.setLayout(layout);
        setView();
        panel.setVisible(true);
    }

    @Override
    protected void setView(){
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setBackground(new Color(255, 255, 222));
        userInfoPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        userInfoPanel.setLayout(new GridLayout());
        userInfoPanel.setVisible(true);
        userInfoListView = new FlatListView<>();
        userInfoListView.setMultiSelectionMode(false);
        userInfoListView.setSingleSelectionMode(false);
        userInfoListView.setBackground(new Color(0, 0, 0, 0));
        userInfoListView.setSelectionColor(new Color(255, 255, 222));
        userInfoPanel.add(userInfoListView.getComponent());
        addComponent(userInfoPanel, 1, 1, 1, 1, GridBagConstraints.BOTH);

        JPanel examInfoPanel = new JPanel();
        examInfoPanel.setBackground(new Color(255, 255, 222));
        examInfoPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        examInfoPanel.setLayout(new GridLayout());
        examInfoPanel.setVisible(true);
        examInfoListView = new FlatListView<>();
        examInfoListView.setMultiSelectionMode(false);
        examInfoListView.setSingleSelectionMode(false);
        examInfoListView.setSelectionColor(new Color(255, 255, 222));
        examInfoListView.setBackground(new Color(255, 255, 222));
        examInfoPanel.add(examInfoListView.getComponent());
        addComponent(examInfoPanel, 1, 3, 1, 3, GridBagConstraints.BOTH);

        JPanel noticePanel = new JPanel();
        noticePanel.setBackground(new Color(255, 255, 222));
        noticePanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        noticePanel.setLayout(new GridLayout());
        noticePanel.setVisible(true);
        FlatListView<FlatLabel> noticeListView = new FlatListView<>();
        noticeListView.setBackground(new Color(255, 255, 222));
        noticeListView.setMultiSelectionMode(false);
        noticeListView.setSingleSelectionMode(false);
        noticeListView.setSelectionColor(new Color(255, 255, 222));
        Vector<String> notice = new Vector<>(new FBEyeNotice().notices);
        for(int i = 0; i < notice.size(); i++){
            FlatLabel item = new FlatLabel(notice.get(i));
            if(i == 0){
                item.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
            }
            else{
                item.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
            }
            item.setBackground(new Color(255, 255, 222));
            item.setVisible(true);
            noticeListView.addItem(item);
        }
        noticePanel.add(noticeListView.getComponent());
        addComponent(noticePanel, 3, 1, 3, 3, GridBagConstraints.BOTH);

        JPanel checkPanel = new JPanel();
        checkPanel.setBackground(new Color(255, 255, 222));
        checkPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        checkPanel.setLayout(new GridLayout());
        checkPanel.setVisible(true);
        readNoticeCheck = new JCheckBox("위 사항들을 읽었으며 동의합니다.");
        readNoticeCheck.setBackground(new Color(255, 255, 222));
        readNoticeCheck.setIcon(new ImageIcon("files/icons8-unchecked-checkbox-96.png"));
        readNoticeCheck.setSelectedIcon(new ImageIcon("files/icons8-checked-checkbox-96.png"));
        readNoticeCheck.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(24)));
        readNoticeCheck.setOpaque(true);
        readNoticeCheck.addActionListener(e -> {
            onChecked();
        });
        readNoticeCheck.setVisible(true);
        checkPanel.add(readNoticeCheck);
        addComponent(checkPanel, 3, 5, 1, 1, GridBagConstraints.BOTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setVisible(true);
        takeExamButton = new FlatButton("응시");
        takeExamButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        takeExamButton.setEnabled(false);
        takeExamButton.setVisible(true);
        takeExamButton.setBackground(new Color(255, 109, 112));
        takeExamButton.setForeground(Color.BLACK);
        takeExamButton.addActionListener(e -> {
            onButtonPressed();
        });
        buttonPanel.add(takeExamButton);
        addComponent(buttonPanel, 5, 5, 1, 1, GridBagConstraints.BOTH);
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
                else if(e.eventDataType == EventDataType.QR_CODE_DATA){
                    list.add(new Event(Destination.ENV_TEST_1, EventDataType.QR_CODE_DATA, e.data));
                }
                list.remove(i);
            }
        }
    }

    private void examInfoReceived(ExamInfo examInfo){
        this.examInfo = examInfo;
        Vector<String> result = this.examInfo.getInfoList();
        for(int i = 0; i < result.size(); i++){
            FlatLabel item = new FlatLabel(result.get(i));
            if(i == 0){
                item.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
            }
            else{
                item.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
            }
            item.setBackground(new Color(255, 255, 222));
            item.setVisible(true);
            examInfoListView.addItem(item);
        }
        list.add(new Event(Destination.ENV_TEST_3, EventDataType.EXAM_INFO, examInfo));
        panel.revalidate();
    }

    private void userInfoReceived(UserInfo userInfo){
        this.userInfo = userInfo;
        list.add(new Event(Destination.EXAM_PAGE, EventDataType.USER_INFO, userInfo));
        Vector<String> result = this.userInfo.getInfoList();
        for(int i = 0; i < result.size(); i++){
            FlatLabel item = new FlatLabel(result.get(i));
            if(i == 0){
                item.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(36)));
            }
            else{
                item.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
            }
            item.setBackground(new Color(255, 255, 222));
            item.setVisible(true);
            userInfoListView.addItems(item);
        }
        panel.revalidate();
    }

    private void onChecked(){
        takeExamButton.setEnabled(readNoticeCheck.isSelected());
        panel.revalidate();
    }

    private void onButtonPressed() {
        list.add(new Event(Destination.ENV_TEST_1, EventDataType.NAVIGATE, null));
    }
}
