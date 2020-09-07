/*
 * ChatConnection.java
 * Author : susemeeee
 * Created Date : 2020-08-24
 */
package xyz.fbeye.net;

import xyz.fbeye.datatype.ChatInfo;
import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

public class ChatConnection {
    private Socket socket;
    private EventList list;

    public ChatConnection(String host, EventList list){
        this.list = list;
        try{
            socket = IO.socket(host);
            socket.on("welcome", objects -> {

            }).on("chat", objects -> {
                JSONObject object = (JSONObject)objects[0];
                ChatInfo chat = new ChatInfo(object.getString("sender"), object.getString("message")
                        , object.getString("timestamp"));
                list.add(new Event(Destination.MANAGER, EventDataType.CHAT, chat));
            }).on("disconnect", objects -> {
                //연결 해제(본인)
            }).on("force-disconnect", objects -> {
                //연결 해제(물리)
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void connect(String loginData){
        socket.connect();
        socket.emit("desktop-welcome", new JSONObject("{" + loginData + "}"));
    }

    public void send(EventDataType type, String data){
        if(type == EventDataType.SCREEN){
            socket.emit("screen", new JSONObject(data));
        }
        else if(type == EventDataType.CHAT){
            socket.emit("desktop-chat", new JSONObject("{\"message\":\"" + data + "\"}"));
        }
        else if(type == EventDataType.CHEAT){
            socket.emit("cheat", new JSONObject(data));
        }
    }

    public void disconnect(){
        socket.disconnect();
    }
}
