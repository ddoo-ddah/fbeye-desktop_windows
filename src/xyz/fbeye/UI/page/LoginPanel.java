/*
 * LoginPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package xyz.fbeye.UI.page;

import xyz.fbeye.datatype.LoginInfo;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import xyz.fbeye.util.ViewDisposer;
import com.mommoo.flat.button.FlatButton;
import com.mommoo.flat.frame.FlatDialog;
import com.mommoo.flat.frame.dialog.DialogButtonInfo;
import com.mommoo.flat.text.textfield.FlatTextField;
import com.mommoo.flat.text.textfield.format.FlatTextFormat;
import com.mommoo.util.FontManager;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class LoginPanel extends Page{
    private FlatTextField inputExamId;
    private FlatTextField inputUserId;
    private FlatButton exitButton;

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
    }

    @Override
    protected void initPanel() {
        layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 0, 0, 0};
        layout.columnWeights = new double[]{0.8, 1.0, 0.5, 0.1, Double.MIN_VALUE};
        layout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        layout.rowWeights = new double[]{50, 350, 100, 85, 30, 85, 70, 80, 150, Double.MIN_VALUE};
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
        FlatButton loginButton = new FlatButton("     인 증     ");
        loginButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(50)));
        loginButton.addActionListener(e -> {
            onLoginButtonClicked();
        });
        loginButton.setForeground(Color.BLACK);
        loginButton.setBackground(new Color(255, 109, 112));
        loginButton.setVisible(true);
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        addComponent(loginButton, 1, 7, 1, 1, GridBagConstraints.VERTICAL);
        constraints.anchor = GridBagConstraints.CENTER;

        inputExamId = new FlatTextField(false);
        inputExamId.setOpaque(false);
        inputExamId.setHint("시험 코드");
        inputExamId.setFormat(FlatTextFormat.TEXT, FlatTextFormat.NUMBER_DECIMAL);
        inputExamId.setHintForeground(new Color(255, 109, 112));
        inputExamId.setFocusGainedColor(new Color(255, 109, 112));
        inputExamId.setFocusLostColor(Color.BLACK);
        inputExamId.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(50)));
        inputExamId.setIconImage(Toolkit.getDefaultToolkit().createImage("files/loginIcon.png"));
        inputExamId.setVisible(true);
        addComponent(inputExamId, 1, 3, 1, 1, GridBagConstraints.BOTH);

        inputUserId = new FlatTextField(false);
        inputUserId.setOpaque(false);
        inputUserId.setHint("응시자 코드");
        inputUserId.setFormat(FlatTextFormat.TEXT, FlatTextFormat.NUMBER_DECIMAL);
        inputUserId.setHintForeground(new Color(255, 109, 112));
        inputUserId.setFocusGainedColor(new Color(255, 109, 112));
        inputUserId.setFocusLostColor(Color.BLACK);
        inputUserId.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(50)));
        inputUserId.setIconImage(Toolkit.getDefaultToolkit().createImage("files/loginIcon.png"));
        inputUserId.setVisible(true);
        addComponent(inputUserId, 1, 5, 1, 1, GridBagConstraints.BOTH);

        ImageIcon img = new ImageIcon("files/FBI.png");
        JLabel logoImageLabel = new JLabel(img);
        logoImageLabel.setVisible(true);
        addComponent(logoImageLabel, 1, 1, 1, 1, GridBagConstraints.BOTH);

        exitButton = new FlatButton("   X   ");
        exitButton.setFont(FontManager.getNanumGothicFont(Font.PLAIN, ViewDisposer.getFontSize(30)));
        exitButton.setBackground(Color.WHITE);
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(e -> {
            exitButton.setBackground(Color.RED);
            onExitButtonClicked();
        });
        exitButton.setVisible(true);
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        addComponent(exitButton, 3, 0, 1 , 1, GridBagConstraints.NONE);
        constraints.anchor = GridBagConstraints.CENTER;
    }

    @Override
    protected void restore(){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == null){
                break;
            }
            if(list.get(i).destination == Destination.LOGIN_PAGE && list.get(i).eventDataType == EventDataType.SIGNAL){
                if(list.get(i).data.equals("ok")){
                    login();
                }
                else{
                    new FlatDialog.Builder().
                            setTitle("로그인 오류").
                            setContent("입력한 정보에 맞는 시험이 없습니다.").
                            setWindowTranslucent(0.2f).
                            setLocationScreenCenter().
                            setButtonText("확인").
                            setButtonTextColor(new Color(255, 109, 112)).
                            setButtonTextFont(FontManager.getNanumGothicFont(Font.BOLD, ViewDisposer.getFontSize(30))).
                            build().
                            show();
                }
                list.remove(i);
            }
            else if(list.get(i).destination == Destination.LOGIN_PAGE && list.get(i).eventDataType == EventDataType.QR_CODE_DATA){
                list.add(new Event(Destination.ENV_TEST_1, EventDataType.QR_CODE_DATA, list.get(i).data));
                list.remove(i);
            }
        }
    }

    private void login(){
        list.add(new Event(Destination.EXAM_INFO_PAGE, EventDataType.NAVIGATE, null));
    }

    private void onLoginButtonClicked(){
        String loginData = new LoginInfo(inputExamId.getText(), inputUserId.getText()).toString();
        list.add(new Event(Destination.SERVER, EventDataType.LOGIN_CODE, loginData));
    }

    private void onExitButtonClicked(){
        DialogButtonInfo OKButton = new DialogButtonInfo();
        OKButton.setTextColor(new Color(255, 109, 112));
        OKButton.setTextFont(FontManager.getNanumGothicFont(Font.BOLD, ViewDisposer.getFontSize(30)));
        OKButton.setText("종료").setOnClickListener(component -> {
            System.exit(0);
        });

        new FlatDialog.Builder().
                setTitle("종료").
                setContent("종료하시겠습니까?").
                setWindowTranslucent(0.2f).
                setLocationScreenCenter().
                setButtonText("취소").
                setButtonTextColor(new Color(255, 109, 112)).
                setButtonTextFont(FontManager.getNanumGothicFont(Font.BOLD, ViewDisposer.getFontSize(30))).
                setOnClickListener(component -> exitButton.setBackground(Color.WHITE)).
                appendButton(OKButton).
                build().
                show();
    }
}
