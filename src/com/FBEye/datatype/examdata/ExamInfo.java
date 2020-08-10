/*
 * ExamInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package com.FBEye.datatype.examdata;

import java.time.LocalDateTime;
import java.util.List;

public class ExamInfo {
    public final String name;
    public final int count;
    public final LocalDateTime startTime;
    public final LocalDateTime endTime;
    public final List<QuestionInfo> questions;

    public ExamInfo(String name, int count, LocalDateTime startTime, LocalDateTime endTime, List<QuestionInfo> questions){
        this.name = name;
        this.count = count;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
    }

    public ExamInfo(String name, int count, List<QuestionInfo> questions){//test
        this.name = name;
        this.count = count;
        startTime = LocalDateTime.of(2020, 8, 10, 14, 0, 0);
        endTime = LocalDateTime.of(2020, 8, 10, 16, 0, 0);
        this.questions = questions;
    }

    public int getTotalScore(){
        return 100; //test
    }
}
