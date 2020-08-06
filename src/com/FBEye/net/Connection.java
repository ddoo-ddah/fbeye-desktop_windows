/*
 * Connection.java
 * Author : susemeeee
 * Created Date : 2020-08-05
 */
package com.FBEye.net;

import javax.net.ssl.*;
import java.io.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Connection {
    private SSLSocket sslSocket;
    private SSLContext context;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRead() {
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

                while (sslSocket.isConnected()) {
                    String receiveString = reader.readLine();

                    if (receiveString != null) {
                        System.out.println("server : " + receiveString);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void send(byte[] data) {
        try {
            if (sslSocket.isConnected()) {
                OutputStream outputstream = sslSocket.getOutputStream();
                OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
                BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
                String string = new String(data);
                bufferedwriter.write(string);
                bufferedwriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
