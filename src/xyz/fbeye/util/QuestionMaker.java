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
    public static List<QuestionInfo> makeQuestion(JSONArray jsonObject){
        JSONArray jsonArray = jsonObject;
        List<QuestionInfo> result = new ArrayList<>();
        JSONObject questionObject;
        JSONArray optionArray;
        for(int i = 0; i < jsonArray.length(); i++){
            questionObject = jsonArray.getJSONObject(i);
            int number = i+1;
            QuestionType questionType;
            String question = questionObject.getString("question");
            List<String> options = new ArrayList<>();

            String type = questionObject.getString("type");
            if(type.equals("주관식")){
                questionType = QuestionType.DESCRIPTIVE;
                result.add(new QuestionInfo(number, questionType, question));
            }
            else{
                if(type.equals("선다형")){
                    questionType = QuestionType.ONE_CHOICE;
                }
                else{
                    questionType = QuestionType.MULTIPLE_CHOICE;
                }

                optionArray = questionObject.getJSONArray("multipleChoices");
                for(int j = 0; j < optionArray.length(); j++){
                    String optionObject = optionArray.getString(j);
                    options.add(optionObject);
                }
                result.add(new QuestionInfo(number, questionType, question, options));
            }
        }

        return result;
    }
}
