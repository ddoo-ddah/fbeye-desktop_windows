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
        notices.add("");
        notices.add("");
        notices.add("\t1.제출 버튼 이외에는 저장되지 않습니다.");
        notices.add("");
        notices.add("\t2.응시 버튼을 누른 후 지시에 따라주세요.");
        notices.add("");
        notices.add("\t3.휴대폰 배터리를 충분히 확보해 주세요.");
        notices.add("");
        notices.add("\t4.시험 중 시선을 모니터 밖으로 돌릴 경우 부정행위로 간주될 수 있습니다.");
        notices.add("");
        notices.add("\t5.QR코드가 계속 인식되지 않으면 시험 문제가 보이지 않습니다.");

        this.notices = notices;

        envTestInfoText_1 = "QR코드 인식 테스트\n\n\tQR코드가 나타나면\n\t휴대폰으로 인식하세요.";

        envTestInfoText_2 = "눈 인식 테스트\n\n\t분홍색 원을 바라보면서\n\tspace키를 누르세요.";
    }
}
