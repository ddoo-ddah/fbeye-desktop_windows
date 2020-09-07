/*
 * JsonParser.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package xyz.fbeye.util;

import org.json.JSONException;
import xyz.fbeye.datatype.UserInfo;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.examdata.ExamInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public JsonParser(){
    }

    public List<Object> parse(JSONObject jsonObject){
        List<Object> result = null;
        try {
            String type = jsonObject.getString("type");
            result = new ArrayList<>();
            if(type.equals("EYE")){
                result.add(EventDataType.EYE);
                List<Float> data = new ArrayList<>();
                JSONArray array = jsonObject.getJSONArray("data");
                for(int i = 0; i < array.length(); i++){
                    data.add(((Number)array.get(i)).floatValue());
                }
                result.add(data);
                if(EyeGazeEstimator.getInstance() != null){
                    EyeGazeEstimator.getInstance().setData(data);
                }
            }
            else if(type.equals("RES")){
                result.add(EventDataType.SIGNAL);
                result.add(jsonObject.get("data"));
            }
            else if(type.equals("INF")){
                result.add(EventDataType.EXAM_INFO);
                JSONObject object = jsonObject.getJSONObject("data");
                result.add(new ExamInfo(object.getString("title"),
                        object.getInt("count"), LocalDateTime.parse(object.getString("startTime")),
                        LocalDateTime.parse(object.getString("endTime"))));
            }
            else if(type.equals("USRINF")){
                result.add(EventDataType.USER_INFO);
                JSONObject object = jsonObject.getJSONObject("data");
                result.add(new UserInfo(object.getString("accessCode"), object.getString("name"),
                        object.getString("email")));
            }
            else if(type.equals("AUT")){
                result.add(EventDataType.QR_CODE_DATA);
                JSONObject object = jsonObject.getJSONObject("data");
                result.add(object.toString());
            }
            else if(type.equals("QUE")){
                result.add(EventDataType.ENCRYPTED_QUESTION);
                result.add(jsonObject.getString("data"));
            }
            else if(type.equals("KEY")){
                result.add(EventDataType.QUESTION_KEY);
                result.add(jsonObject.getString("data"));
            }
        }catch (JSONException e){
            System.exit(-1);
        }
        return result;
    }
}
