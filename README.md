#FBEye Desktop Application
##Features
#####QR코드를 통한 실시간 인증
- [처리 서버](https://github.com/ddoo-ddah/fbeye-processing-server "처리 서버")에서 받아온 QR코드 데이터를 [Zxing](https://github.com/zxing/zxing "Zxing")라이브러리를 이용해서 화면으로 출력합니다. 이는 [모바일 앱](https://github.com/ddoo-ddah/fbeye-mobile_android "모바일 앱")을 통해 인증하는 데 사용됩니다.

#####부정행위 방지
1. QR코드를 통한 실시간 인증에 실패하면 시험 문제가 보이지 않게 됩니다.
2. 창 크기를 조절하거나, 최소화 시킬 수 없게 됩니다.
3. 2개 이상의 모니터 사용 시 다른 모니터의 화면을 가려서 하나의 모니터만 사용할 수 있게 됩니다.
4. 이 프로그램 외에 다른 프로그램을 앞으로 띄워 볼 수 없게 합니다.
5. 시험 응시 중인 화면은 [관리자용 웹페이지](https://github.com/ddoo-ddah/fbeye-web-server "관리자용 웹페이지")에서 관리자가 볼 수 있게 됩니다.

#####시험 환경 제공
- 화면 밖을 보지 않더라도 시험을 볼 수 있는 환경을 제공합니다.
 1. 메모할 수 있는 공간이 제공됩니다.
 1. 시험 종료까지 남은 시간을 확인할 수 있습니다.
- 처음 사용하는 사람을 위해 시험 전 샘플 환경을 제공합니다.
- 시험 전, 진행 중에 시험 관리자와 채팅을 통해 소통할 수 있습니다.

##Library
##### - [Zxing](https://github.com/zxing/zxing "Zxing")
##### - [Socket.io](https://github.com/socketio/socket.io-client-java "Socket.io")
##### - [commons-codec](https://github.com/apache/commons-codec "commons-codec")
##### - [JSON](https://github.com/douglascrockford/JSON-java "JSON")
##### - local-policy
##### - okhttp
##### - okio
##### - US_export_policy
##### - [FlatSwing](https://github.com/Mommoo/FlatSwing "FlatSwing")