/*
 * Connection.java
 * Author : susemeeee
 * Created Date : 2020-08-05
 */
package com.FBEye.net;

import com.FBEye.datatype.event.Destination;
import com.FBEye.datatype.event.Event;
import com.FBEye.datatype.event.EventDataType;
import com.FBEye.datatype.event.EventList;
import com.FBEye.util.DataExchanger;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Connection {
    private SSLSocket sslSocket;
    private EventList list;

    public Connection(EventList list){
        this.list = list;
    }

    public void Connect() {
        try {
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            TrustManager[] trustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };
            context.init(null, trustManagers, new SecureRandom());
            SSLSocketFactory sslSocketFactory = context.getSocketFactory();
            String[] suites = sslSocketFactory.getSupportedCipherSuites();
            sslSocket = (SSLSocket) sslSocketFactory.createSocket("localhost", 9000); //실제는 10100
            sslSocket.setEnabledCipherSuites(suites);
            sslSocket.startHandshake();
            startRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startRead() {
        new Thread(() -> {
            try {
                BufferedInputStream in = new BufferedInputStream(sslSocket.getInputStream());

                while (sslSocket.isConnected()) {
                    String receiveString = "";

                    do{
                        int c = in.read();
                        receiveString += (char)c;
                    }while(in.available() > 0);
                    list.add(new Event(Destination.MANAGER, EventDataType.HEADER, new DataExchanger<String>().toByteArray(receiveString)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void send(JSONObject data) {
        try {
            if (sslSocket.isConnected()) {
                OutputStream outputstream = sslSocket.getOutputStream();
                OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
                BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
                String string = data.toString();
                bufferedwriter.write(string);
                bufferedwriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
