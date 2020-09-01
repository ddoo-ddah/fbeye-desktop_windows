/*
 * ExamInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package xyz.fbeye.datatype.examdata;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

public class ExamInfo {
    public final String name;
    public final int count;
    public final LocalDateTime startTime;
    public final LocalDateTime endTime;
    public final List<QuestionInfo> questions;

    public ExamInfo(String name, int count, LocalDateTime startTime,
                    LocalDateTime endTime, List<QuestionInfo> questions){
        this.name = name;
        this.count = count;
        this.startTime = startTime;
        this.endTime = endTime;
        this.questions = questions;
    }

    public ExamInfo(String name, int count, LocalDateTime startTime,
                    LocalDateTime endTime){
        this.name = name;
        this.count = count;
        this.startTime = startTime;
        this.endTime = endTime;
        questions = null;
    }

    public ExamInfo(String name, int count, List<QuestionInfo> questions){
        this.name = name;
        this.count = count;
        startTime = LocalDateTime.of(2020, 8, 16, 9, 0, 0);
        endTime = LocalDateTime.of(2020, 8, 19, 1, 18, 0);
        this.questions = questions;
    }

    public Vector<String> getInfoList(){
        Vector<String> result = new Vector<>();
        result.add("시험 정보");
        result.add("\t시험 이름: " + name);
        result.add("\t문항 수: " + count);
        result.add("\t시작 시간: " + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        result.add("\t종료 시간: " + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return result;
    }

    @Override
    public String toString() {
        return "ExamInfo{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", questions=" + questions +
                '}';
    }
}
