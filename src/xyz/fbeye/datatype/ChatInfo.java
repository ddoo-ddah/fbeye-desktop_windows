/*
 * ChatInfo.java
 * Author : susemeeee
 * Created Date : 2020-09-03
 */
package xyz.fbeye.datatype;

public class ChatInfo {
    public final String name;
    public final String message;
    public final String timestamp;

    public ChatInfo(String name, String message, String timestamp){
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{\"sender\":\"" + name +
                "\",\"message\":\"" + message +
                "\"}";
    }
}
