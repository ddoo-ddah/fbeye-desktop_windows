/*
 * AnswerInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-13
 */
package com.FBEye.datatype.examdata;

public class AnswerInfo {
    private int number;
    private String answer;
    private AnswerState state;

    public AnswerInfo(int number){
        this.number = number;
        answer = "";
        state = AnswerState.NOT_SOLVED;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AnswerState getState() {
        return state;
    }

    public void setState(AnswerState state) {
        this.state = state;
    }
}
