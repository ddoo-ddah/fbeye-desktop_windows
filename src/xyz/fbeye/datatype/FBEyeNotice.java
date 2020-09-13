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
        notices.add("  시험 응시 시 주의사항");
        notices.add("\t1. 제출하지 않은 답변은 서버에 저장되지 않습니다.");
        notices.add("\t2. 휴대폰의 배터리를 충분히 확보하고, 와이파이에 연결되어 있는지 주세요.");
        notices.add("\t3. 응시 중에는 감독관이 응시자의 얼굴을 언제든지 확인 할 수 있습니다.");
        notices.add("\t4. 응시 중 자리를 비우거나 휴대폰 카메라를 가리는 경우 부정행위로 간주될 수 있습니다.");
        notices.add("\t5. 응시 중 시선을 모니터 밖으로 돌리는 경우 부정행위로 간주될 수 있습니다.");
        notices.add("\t6. 응시 중 휴대폰의 위치를 변경하는 경우 부정행위로 간주 될 수 있습니다.");
        notices.add("\t7. 응시 중 QR코드가 인식되지 않으면 부정행위로 간주될 수 있습니다.");
        notices.add("\t8. 응시 중 QR코드가 인식되지 않으면 시험문제를 볼 수 없습니다.");
        notices.add("\t9. 응시 중 시험시간이 종료되는 경우 즉시 답안이 제출됩니다.");
        notices.add("\t10. 응시 중 감독관과 의사소통이 필요한 경우 채팅 기능을 사용해주세요.");


        this.notices = notices;

        envTestInfoText_1 = "\n    휴대폰 및 서버 동기화" +
                "\n\n\t휴대폰과 시험프로그램, 시험서버를 동기화 하는 작업입니다." +
                "\n\tQR코드 표시까지 30초이하의 시간이 소요될 수 있습니다." +
                "\n\t이 작업은 QR코드 인식 이후 5 ~ 20초정도 소요 될 수 있습니다." +
                "\n\n\t휴대폰을 흔들리지 않는 위치에 배치해 주세요." +
                "\n\tQR코드가 나타나면 FBEye 어플리케이션으로 인식하세요." +
                "\n\t동기화 이후 휴대폰의 위치가 변경될 경우 부정행위로 간주 될 수 있습니다.";

        envTestInfoText_2 = "\n    시선 좌표 보정" +
                "\n\n\t응시자가 보는 방향과 화면상의 좌표를 매칭하는 작업입니다." +
                "\n\t이 작업은 2 ~ 3분정도 소요 될 수 있습니다." +
                "\n\n\t카메라 범위안에 다른 사람이 없는지 확인해 주세요." +
                "\n\t분홍색 원을 정확히 쳐다보면서 space키를 누르세요." +
                "\n\t시선처리가 불분명한경우 불이익이 발생 할 수 있습니다.";
    }
}
