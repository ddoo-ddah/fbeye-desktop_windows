/*
 * ExamMainPanel.java
 * Author : susemeeee
 * Created Date : 2020-08-13
 */
package com.FBEye.UI.page.element;

import com.FBEye.datatype.examdata.*;
import com.FBEye.util.MultipleItemSelectionModel;
import com.FBEye.util.ViewDisposer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExamMainPanel {
    private JPanel panel;
    private Point panelLocation;
    private ExamInfo examInfo;
    private int currentNumber;
    private int prevNumber;
    private boolean isChanged;
    private List<AnswerInfo> answers;

    private JButton delayButton;
    private JTextArea questionText;
    private JTextArea answerArea;
    private JList<String> optionList;
    private JButton prevButton;
    private JButton nextButton;

    public ExamMainPanel(int x, int y, ExamInfo examInfo){
        currentNumber = 0;
        prevNumber = 0;
        isChanged = false;
        this.examInfo = examInfo;
        panelLocation = ViewDisposer.getLocation(x, y);
        initPanel();
        initAnswer();
        controlQuestion();
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setSize(ViewDisposer.getSize(750, 860));
        panel.setLocation(panelLocation);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(3)));
        setView();
        panel.setVisible(true);
    }

    private void setView(){
        delayButton = new JButton("나중에 풀기");
        Point location = ViewDisposer.getLocation(990, 73);
        Dimension size = ViewDisposer.getSize(120, 50);
        delayButton.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        delayButton.setSize(size);
        delayButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        delayButton.setBackground(Color.ORANGE);
        delayButton.addActionListener(e -> {
            onDelayButtonClicked();
        });
        delayButton.setVisible(true);
        panel.add(delayButton);

        questionText = new JTextArea("테스트 문제 뷰");
        location = ViewDisposer.getLocation(390, 130);
        size = ViewDisposer.getSize(720, 350);
        questionText.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        questionText.setSize(size);
        questionText.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        questionText.setEditable(false);
        questionText.setVisible(true);
        panel.add(questionText);

        answerArea = new JTextArea();
        location = ViewDisposer.getLocation(390, 505);
        size = ViewDisposer.getSize(720, 350);
        answerArea.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        answerArea.setSize(size);
        answerArea.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        answerArea.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(2)));
        answerArea.setEditable(true);
        answerArea.setVisible(true);
        panel.add(answerArea);

        optionList = new JList<>();
        optionList.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        optionList.setSize(size);
        optionList.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(30)));
        optionList.setBorder(new LineBorder(Color.BLACK, ViewDisposer.getFontSize(2)));
        optionList.setSelectionModel(new MultipleItemSelectionModel());
        optionList.setVisible(false);
        panel.add(optionList);

        prevButton = new JButton("이전");
        location = ViewDisposer.getLocation(655, 870);
        size = ViewDisposer.getSize(70, 50);
        prevButton.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        prevButton.setSize(size);
        prevButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        prevButton.setBackground(Color.ORANGE);
        prevButton.addActionListener(e -> {
            onPrevButtonClicked();
        });
        prevButton.setVisible(true);
        panel.add(prevButton);

        nextButton = new JButton("다음");
        location = ViewDisposer.getLocation(775, 870);
        nextButton.setLocation(location.x - panelLocation.x, location.y - panelLocation.y);
        nextButton.setSize(size);
        nextButton.setFont(new Font("맑은고딕", Font.PLAIN, ViewDisposer.getFontSize(24)));
        nextButton.setBackground(Color.ORANGE);
        nextButton.addActionListener(e -> {
            onNextButtonClicked();
        });
        nextButton.setVisible(true);
        panel.add(nextButton);
    }

    private void initAnswer(){
        answers = new ArrayList<>();
        for(int i = 0; i < examInfo.count; i++){
            answers.add(new AnswerInfo(i + 1));
        }
    }

    private void controlPrevButton(){
        if(currentNumber == 0){
            prevButton.setEnabled(false);
            return;
        }
        prevButton.setEnabled(true);
    }

    private void controlNextButton(){
        if(currentNumber == examInfo.count - 1){
            nextButton.setEnabled(false);
            return;
        }
        nextButton.setEnabled(true);
    }

    public void controlQuestion(){
        controlPrevButton();
        controlNextButton();

        QuestionInfo currentQuestion = examInfo.questions.get(currentNumber);
        questionText.setText(currentQuestion.question);

        if(currentQuestion.type == QuestionType.DESCRIPTIVE){
            optionList.setVisible(false);
            answerArea.setVisible(true);
            String answer = answers.get(currentNumber).getAnswer();
            if(answer != null && !answer.equals("")){
                answerArea.setText(answer);
            }
            else{
                answerArea.setText("");
            }
            panel.repaint();
        }
        else if(currentQuestion.type == QuestionType.ONE_CHOICE){
            answerArea.setVisible(false);
            optionList.setListData(new Vector<>(currentQuestion.options));
            optionList.setSelectionModel(new DefaultListSelectionModel());
            optionList.setFixedCellHeight(optionList.getHeight() / currentQuestion.options.size());
            optionList.setVisible(true);
            String answer = answers.get(currentNumber).getAnswer();
            if(answer != null && !answer.equals("")){
                optionList.setSelectedIndex(Integer.parseInt(answer));
            }
            else{
                optionList.setSelectedIndex(-1);
            }
            panel.repaint();
        }
        else{
            answerArea.setVisible(false);
            optionList.setListData(new Vector<>(currentQuestion.options));
            optionList.setSelectionModel(new MultipleItemSelectionModel());
            optionList.setFixedCellHeight(optionList.getHeight() / currentQuestion.options.size());
            optionList.setVisible(true);
            String answer = answers.get(currentNumber).getAnswer();
            if(answer != null && !answer.equals("")){
                String[] str = answer.split(",");
                int[] indices = new int[str.length];
                for(int i = 0; i < str.length; i++) {
                    indices[i] = Integer.parseInt(str[i]);
                }
                optionList.setSelectedIndices(indices);
            }
            else{
                optionList.setSelectedIndex(-1);
            }
            panel.repaint();
        }
    }

    public void saveAnswer(AnswerState state){
        QuestionInfo currentQuestion = examInfo.questions.get(currentNumber);
        if(currentQuestion.type == QuestionType.DESCRIPTIVE){
            String answer = answerArea.getText();
            if(!answer.equals("")){
                answers.get(currentNumber).setAnswer(answer);
                answers.get(currentNumber).setState(state);
            }
        }
        else if(currentQuestion.type == QuestionType.ONE_CHOICE){
            int selectedIndex = optionList.getSelectedIndex();
            if(selectedIndex != -1){
                answers.get(currentNumber).setAnswer(Integer.toString(selectedIndex));
                answers.get(currentNumber).setState(state);
            }
        }
        else{
            int[] selectedIndexList = optionList.getSelectedIndices();
            if(selectedIndexList.length != 0){
                String answer = "";
                for(int i = 0; i < selectedIndexList.length; i++){
                    answer += Integer.toString(selectedIndexList[i]);
                    if(i != selectedIndexList.length - 1){
                        answer += ",";
                    }
                }
                answers.get(currentNumber).setAnswer(answer);
                answers.get(currentNumber).setState(state);
            }
        }
    }

    private void onPrevButtonClicked(){
        saveAnswer(AnswerState.SOLVED);
        prevNumber = currentNumber;
        currentNumber--;
        isChanged = true;
        controlQuestion();
    }

    private void onNextButtonClicked(){
        saveAnswer(AnswerState.SOLVED);
        prevNumber = currentNumber;
        currentNumber++;
        isChanged = true;
        controlQuestion();
    }

    private void onDelayButtonClicked(){
        saveAnswer(AnswerState.DELAYED);
        prevNumber = currentNumber;
        if(currentNumber < examInfo.count - 1){
            currentNumber++;
        }
        isChanged = true;
        controlQuestion();
    }

    public JPanel getPanel(){
        return panel;
    }

    public boolean getIsChanged(){
        return isChanged;
    }

    public int getCurrentNumber(){
        return currentNumber;
    }

    public int getPrevNumber(){
        return prevNumber;
    }

    public AnswerState getState(int index){
        return answers.get(index).getState();
    }

    public String getAnswers(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < answers.size(); i++){
            if(answers.get(i).getAnswer() != null && !answers.get(i).getAnswer().equals("")){
                sb.append(i + 1);
                sb.append("#");
                sb.append(answers.get(i).getAnswer());
                sb.append("|");
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public void setCurrentNumber(int currentNumber){
        this.currentNumber = currentNumber;
    }

    public void setPrevNumber(int prevNumber){
        this.prevNumber = prevNumber;
    }

    public void setIsChanged(boolean isChanged){
        this.isChanged = isChanged;
    }
}
