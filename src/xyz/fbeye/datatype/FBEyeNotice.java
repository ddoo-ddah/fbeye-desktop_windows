/*
 * FBEyeNotice.java
 * Author : susemeeee
 * Created Date : 2020-08-10
 */
package xyz.fbeye.datatype;

import java.util.ArrayList;
import java.util.List;

public class FBEyeNotice {
    public final List<String> notices;
    public final List<String> envTestInfoTexts_1;
    public final String envTestInfoText_2;

    public FBEyeNotice(){
        List<String> notices = new ArrayList<>();
        notices.add("이용 시 주의사항");
        notices.add("\t1.주의사항"); //테스트 데이터
        notices.add("\t2.주의사항");
        notices.add("\t3.주의사항");
        notices.add("\t4.주의사항");
        notices.add("\t5.주의사항");

        this.notices = notices;

        envTestInfoTexts_1 = new ArrayList<>();
        envTestInfoTexts_1.add("얼굴인식 테스트\n\n\t시작 버튼을 누르세요");
        for(int i = 0; i < 3; i++){ //테스트 데이터
            envTestInfoTexts_1.add("얼굴인식 테스트\n\n\t테스트 " + (i + 1) + "단계");
        }

        envTestInfoText_2 = "QR코드 인식 테스트\n\n\t시작 버튼을 누르고 QR코드가 나타나면\n\t휴대폰으로 인식하세요.";
    }
}
