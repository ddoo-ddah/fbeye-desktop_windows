/*
 * UserInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package xyz.fbeye.datatype;

import java.util.Vector;

public class UserInfo {
    public final String id;
    public final String name;
    public final String email;
    
    public UserInfo(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Vector<String> getInfoList(){
        Vector<String> result = new Vector<>();
        result.add("  응시자 정보");
        result.add("\tID: " + id);
        result.add("\t이름: " + name);
        result.add("\t이메일: " + email);
        return result;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
