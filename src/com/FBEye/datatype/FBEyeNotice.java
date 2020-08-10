/*
 * FBEyeNotice.java
 * Author : susemeeee
 * Created Date : 2020-08-10
 */
package com.FBEye.datatype;

import java.util.ArrayList;
import java.util.List;

public class FBEyeNotice {
    public final List<String> notices;

    public FBEyeNotice(){
        List<String> notices = new ArrayList<>();
        notices.add("이용 시 주의사항");
        notices.add("1.주의사항"); //테스트 데이터
        notices.add("2.주의사항");
        notices.add("3.주의사항");
        notices.add("4.주의사항");
        notices.add("5.주의사항");

        this.notices = notices;
    }
}
