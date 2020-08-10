/*
 * UserInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package com.FBEye.datatype;

public class UserInfo {
    public final int id;
    public final String name;
    public final String email;
    public final String department;
    
    public UserInfo(int id, String name, String email, String department){
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
    }
}
