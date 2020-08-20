/*
 * JsonMaker.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package com.FBEye.util;

import com.FBEye.datatype.event.EventDataType;
import org.json.JSONObject;

import java.util.EnumMap;

public class JsonMaker {
    private EnumMap<EventDataType, String> typeString;

    public JsonMaker(){
        init();
    }

    private void init(){
        typeString = new EnumMap<EventDataType, String>(EventDataType.class);
        typeString.put(EventDataType.LOGIN_CODE, "SIN");
        typeString.put(EventDataType.SIGNAL, "SIG");
        typeString.put(EventDataType.COORDINATE, "BTN");
        typeString.put(EventDataType.ANSWER, "ANS");
    }

    public JSONObject makeJson(EventDataType type, String data){
        String jsonStr = "{\n" + "\t\"type\":\"" + typeString.get(type) + "\",\n\t" + data + "\n}";
        return new JSONObject(jsonStr);
    }
}
