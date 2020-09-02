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
    public final String envTestInfoText_1;
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

        envTestInfoText_1 = "QR코드 인식 테스트\n\n\tQR코드가 나타나면\n\t휴대폰으로 인식하세요.";

        envTestInfoText_2 = "눈 인식 테스트\n\n\t분홍색 원을 바라보면서\n\tspace키를 누르세요.";
    }
}
