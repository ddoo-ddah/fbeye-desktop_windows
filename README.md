# FBEye Desktop Application
## Features
##### QR코드를 통한 실시간 인증
- [처리 서버](https://github.com/ddoo-ddah/fbeye-processing-server "처리 서버")에서 받아온 QR코드 데이터를 [Zxing](https://github.com/zxing/zxing "Zxing")라이브러리를 이용해서 화면으로 출력합니다. 이는 [모바일 앱](https://github.com/ddoo-ddah/fbeye-mobile_android "모바일 앱")을 통해 인증하는 데 사용됩니다.

##### 부정행위 방지
1. QR코드를 통한 실시간 인증에 실패하면 시험 문제가 보이지 않게 됩니다.
2. 창 크기를 조절하거나, 최소화 시킬 수 없게 됩니다.
3. 2개 이상의 모니터 사용 시 다른 모니터의 화면을 가려서 하나의 모니터만 사용할 수 있게 됩니다.
4. 이 프로그램 외에 다른 프로그램을 앞으로 띄워 볼 수 없게 합니다.
5. 시험 응시 중인 화면은 [관리자용 웹페이지](https://github.com/ddoo-ddah/fbeye-web-server "관리자용 웹페이지")에서 관리자가 볼 수 있게 됩니다.

##### 시험 환경 제공
- 화면 밖을 보지 않더라도 시험을 볼 수 있는 환경을 제공합니다.
 1. 메모할 수 있는 공간이 제공됩니다.
 1. 시험 종료까지 남은 시간을 확인할 수 있습니다.
- 처음 사용하는 사람을 위해 시험 전 샘플 환경을 제공합니다.
- 시험 전, 진행 중에 시험 관리자와 채팅을 통해 소통할 수 있습니다.

##### EyeTracking 및 부정행위 감지
* [처리 서버](https://github.com/ddoo-ddah/fbeye-processing-server "처리 서버")에서 전송한 모든 시선 방향 벡터는 최신 5개의 값을 평균내어 사용합니다.
1. Calibration 단계에서 화면상의 특징점을 쳐다보며 키 입력을 하면 해당 좌표에 대한 시선 방향 벡터가 입력됩니다.
2. 서버에서 시선 데이터가 넘어오는 경우, 다음과 같은 순서로 화면상 좌표를 계산합니다.
   1. 점 한개를 고르고 그와 이웃한 3개의 점을 골라 직사각형을 만듭니다. 직사각형이 아닌경우는 계산하지 않습니다.
   2. i에서 만든 직사각형의 각 꼭짓점에 등록되어 있는 시선 방향 벡터들을 교차하여 만들수 있는 모든 사각형에 대하여 다음을 시도합니다.
        1. 검사할 값이 사각형 내부에 있는지 판별합니다.
        2. 직사각형 내부에 있는경우 투영 변환을 통해 해당 사각형 내부의 상대좌표를 구합니다.(x,y 범위 각 0.0~1.0)
        3. i에서 만든 직사각형의 시작점과 길이, b에서 얻은 내부좌표를 사용해 화면상의 실 좌표를 얻어냅니다.
3. 2에서 얻은 모든 값들중 양 끝 25%를 제외한 25%~75%의 값들을 평균을 내어 현재 시선에 대한 화면상 좌표를 얻어냅니다.
4. Calibration 단계에서 Eye-Tracking이 잘 되는지 확인 할 수 있습니다.
5. 모든 특징점에 대해 5회 이상의 값이 입력된경우 Calibration이 종료됩니다.
6. 시험 응시단계에서 2에서 얻은 값이 전혀 없는 경우, 시선이 화면 밖으로 나간것으로 간주, 서버로 부정행위 로그를 전송합니다.

## Library
##### - [Zxing](https://github.com/zxing/zxing "Zxing")
##### - [Socket.io](https://github.com/socketio/socket.io-client-java "Socket.io")
##### - [commons-codec](https://github.com/apache/commons-codec "commons-codec")
##### - [JSON](https://github.com/douglascrockford/JSON-java "JSON")
##### - local-policy
##### - okhttp
##### - okio
##### - US_export_policy
##### - [FlatSwing](https://github.com/Mommoo/FlatSwing "FlatSwing")