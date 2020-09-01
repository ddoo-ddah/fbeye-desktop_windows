/*
 * QuestionInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package xyz.fbeye.datatype.examdata;

import java.util.List;

public class QuestionInfo {
    public final int questionNumber;
    public final QuestionType type;
    public final String question;
    public final List<String> options;

    public QuestionInfo(int questionNumber, QuestionType type, String question){
        this.questionNumber = questionNumber;
        this.type = type;
        this.question = question;
        options = null;
    }

    public QuestionInfo(int questionNumber, QuestionType type, String question, List<String> options){
        this.questionNumber = questionNumber;
        this.type = type;
        this.question = question;
        this.options = options;
    }
}
