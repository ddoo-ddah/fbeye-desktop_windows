/*
 * ChatConnection.java
 * Author : susemeeee
 * Created Date : 2020-08-24
 */
package xyz.fbeye.net;

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
                list.add(new Event(Destination.MANAGER, EventDataType.CHAT, "연결되었습니다."));
            }).on("chat", objects -> {
                JSONObject object = (JSONObject)objects[0];
                list.add(new Event(Destination.MANAGER, EventDataType.CHAT, object.getString("message")));
                //채팅받음 누가 보내는지 받을 수 있음 key=name
            }).on("disconnect", objects -> {
                //연결 해제(본인)
            }).on("force-disconnect", objects -> {
                //연결 해제(물리)
            });
            socket.connect();
            socket.emit("welcome", "김기현");
            socket.emit("chat", new JSONObject("{\"message\":\"asdf\"}"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(String msg){
        socket.emit("chat", new JSONObject("{\"message\":\"" + msg + "\"}"));
    }

    public void disconnect(){
        socket.disconnect();
    }
}
