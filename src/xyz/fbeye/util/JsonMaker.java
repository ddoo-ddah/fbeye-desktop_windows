/*
 * JsonMaker.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package xyz.fbeye.util;

import xyz.fbeye.datatype.event.EventDataType;
import org.json.JSONObject;

import java.util.EnumMap;

public class JsonMaker {
    private EnumMap<EventDataType, String> typeString;

    public JsonMaker(){
        init();
    }

    private void init(){
        typeString = new EnumMap<>(EventDataType.class);
        typeString.put(EventDataType.LOGIN_CODE, "SIN");
        typeString.put(EventDataType.SIGNAL, "REQ");
        typeString.put(EventDataType.COORDINATE, "BTN");
        typeString.put(EventDataType.ANSWER, "ANS");
        typeString.put(EventDataType.SCREEN, "SCR");
    }

    public JSONObject makeJson(EventDataType type, String data){
        String jsonStr = "{\n" + "\t\"type\":\"" + typeString.get(type) + "\",\n\t" + data + "\n}";
        return new JSONObject(jsonStr);
    }
}
