/*
 * JsonParser.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package com.FBEye.util;

import com.FBEye.datatype.UserInfo;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.examdata.ExamInfo;
import org.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public JsonParser(){
        init();
    }

    private void init(){
    }

    public List<Object> parse(JSONObject jsonObject){
        String type = jsonObject.getString("type");
        List<Object> result = new ArrayList<>();
        if(type.equals("RES")){
            result.add(EventDataType.SIGNAL);
            result.add(jsonObject.get("data"));
        }
        else if(type.equals("INF")){
            result.add(EventDataType.EXAM_INFO);
            JSONObject object = jsonObject.getJSONObject("data");
            result.add(new ExamInfo(object.getString("title"), object.getString("admin"),
                    object.getInt("count"), DateTimeMaker.make(object.getString("startTime")),
                    DateTimeMaker.make(object.getString("endTime"))));
        }
        else if(type.equals("USRINF")){
            result.add(EventDataType.USER_INFO);
            JSONObject object = jsonObject.getJSONObject("data");
            result.add(new UserInfo(object.getString("id"), object.getString("name"),
                    object.getString("email")));
        }
        else if(type.equals("AUT")){
            result.add(EventDataType.QR_CODE_DATA);
            result.add(jsonObject.get("data"));
        }
        else if(type.equals("QUE")){
            result.add(EventDataType.ENCRYPTED_QUESTION);
            result.add(jsonObject.getString("data"));
        }
        else if(type.equals("KEY")){
            result.add(EventDataType.QUESTION_KEY);
            result.add(jsonObject.getString("data"));
        }
        else{
            return null;
        }
        return result;
    }
}
