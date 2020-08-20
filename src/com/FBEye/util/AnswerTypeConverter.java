/*
 * AnswerTypeConverter.java
 * Author : susemeeee
 * Created Date : 2020-08-20
 */
package com.FBEye.util;

import com.FBEye.datatype.examdata.AnswerInfo;

import java.util.List;

public class AnswerTypeConverter {
    public static String convert(List<AnswerInfo> answers){
        StringBuilder result = new StringBuilder();
        result.append("\"data\":[\n");
        for(int i = 0; i < answers.size(); i++){
            result.append("\t\t{\n");
            result.append("\t\t\t\"_Id\":\"");
            result.append(answers.get(i).getNumber());
            result.append("\"\n");
            result.append("\t\t\t\"answer\":\"");
            result.append(answers.get(i).getAnswer());
            result.append("\"\n\t\t}\n");
        }
        result.append("\t]");
        return result.toString();
    }
}
