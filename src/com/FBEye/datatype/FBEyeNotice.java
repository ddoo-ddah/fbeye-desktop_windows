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
    public final List<String> envTestInfoTexts_1;
    public final String envTestInfoText_2;

    public FBEyeNotice(){
        List<String> notices = new ArrayList<>();
        notices.add("이용 시 주의사항");
        notices.add("1.주의사항"); //테스트 데이터
        notices.add("2.주의사항");
        notices.add("3.주의사항");
        notices.add("4.주의사항");
        notices.add("5.주의사항");

        this.notices = notices;

        envTestInfoTexts_1 = new ArrayList<>();
        envTestInfoTexts_1.add("얼굴인식 테스트");
        for(int i = 0; i < 3; i++){ //테스트 데이터
            envTestInfoTexts_1.add("테스트 " + (i + 1) + "단계");
        }

        envTestInfoText_2 = "QR코드 인식 테스트";
    }
}
