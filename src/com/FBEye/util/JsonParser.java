/*
 * JsonParser.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package com.FBEye.util;

import com.FBEye.datatype.event.EventDataType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private Map<String, EventDataType> typeData;

    public JsonParser(){
        init();
    }

    private void init(){
        typeData = new HashMap<>();
        typeData.put("RES", EventDataType.SIGNAL);
    }

    public List<Object> parse(JSONObject jsonObject){
        String jsonStr = jsonObject.toString();
        String[] data = jsonStr.split("\\{|:|,|}");
        if(data[data.length - 1].equals("\"RES\"")){
            System.out.println(1);
            List<Object> result = new ArrayList<>();
            result.add(EventDataType.SIGNAL);
            result.add(data[2]);
            return result;
        }
        return null;
    }
}
