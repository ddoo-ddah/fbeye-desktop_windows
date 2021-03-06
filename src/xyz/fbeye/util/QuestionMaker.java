/*
 * QuestionMaker.java
 * Author : susemeeee
 * Created Date : 2020-08-20
 */
package xyz.fbeye.util;

import xyz.fbeye.datatype.examdata.QuestionInfo;
import xyz.fbeye.datatype.examdata.QuestionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionMaker {
    public static List<QuestionInfo> makeQuestion(JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<QuestionInfo> result = new ArrayList<>();
        JSONObject questionObject;
        JSONArray optionArray;

        for(int i = 0; i < jsonArray.length(); i++){
            questionObject = jsonArray.getJSONObject(i);
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

                optionArray = questionObject.getJSONArray("multiplechoices");
                for(int j = 0; j < optionArray.length(); j++){
                    JSONObject optionObject = optionArray.getJSONObject(j);
                    options.add(optionObject.getString("content"));
                }
                result.add(new QuestionInfo(number, questionType, question, options));
            }
        }

        return result;
    }
}
