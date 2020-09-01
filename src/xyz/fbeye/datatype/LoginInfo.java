/*
 * LoginInfo.java
 * Author : susemeeee
 * Created Date : 2020-08-17
 */
package xyz.fbeye.datatype;

import java.io.Serializable;

public class LoginInfo implements Serializable {
    public final String examCode;
    public final String userCode;

    public LoginInfo(String examCode, String userCode){
        this.examCode = examCode;
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "\"data\":{\n" + "\t\t\"examCode\":\"" + examCode + "\",\n" + "\t\t\"userCode\":\"" + userCode + "\"\n" + "\t}";
    }
}
