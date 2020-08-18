/*
 * UserInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package com.FBEye.datatype;

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
        result.add("id: " + id);
        result.add("이름: " + name);
        result.add("이메일: " + email);
        return result;
    }
}
