/*
 * QuestionMaker.java
 * Author : susemeeee
 * Created Date : 2020-08-20
 */
package com.FBEye.util;

import com.FBEye.datatype.examdata.QuestionInfo;
import com.FBEye.datatype.examdata.QuestionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionMaker {
    public static List<QuestionInfo> makeQuestion(JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<QuestionInfo> result = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject questionObject = jsonArray.getJSONObject(i);
            int number = questionObject.getInt("_Id");
            QuestionType questionType;
            String question = questionObject.getString("question");
            List<String> options = new ArrayList<>();

            String type = questionObject.getString("type");
            if(type.equals("descriptive")){
                questionType = QuestionType.DESCRIPTIVE;
                result.add(new QuestionInfo(number, questionType, question));
            }
            else{
                if(type.equals("one_choice")){
                    questionType = QuestionType.ONE_CHOICE;
                }
                else{
                    questionType = QuestionType.MULTIPLE_CHOICE;
                }

                JSONArray optionArray = questionObject.getJSONArray("multiplechoices");
                for(int j = 0; j < optionArray.length(); i++){
                    JSONObject optionObject = optionArray.getJSONObject(i);
                    options.add(optionObject.getString("content"));
                }
                result.add(new QuestionInfo(number, questionType, question, options));
            }
        }

        return result;
    }
}
