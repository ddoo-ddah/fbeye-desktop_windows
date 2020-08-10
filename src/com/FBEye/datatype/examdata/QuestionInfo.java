/*
 * QuestionInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package com.FBEye.datatype.examdata;

import java.util.List;

public class QuestionInfo {
    public final int questionNumber;
    public final QuestionType type;
    public final String question;
    public final List<String> options;
    public final int score;

    public QuestionInfo(int questionNumber, QuestionType type, String question, int score){
        this.questionNumber = questionNumber;
        this.type = type;
        this.question = question;
        options = null;
        this.score = score;
    }

    public QuestionInfo(int questionNumber, QuestionType type, String question, List<String> options, int score){
        this.questionNumber = questionNumber;
        this.type = type;
        this.question = question;
        this.options = options;
        this.score = score;
    }
}
