/*
 * Connection.java
 * Author : susemeeee
 * Created Date : 2020-08-05
 */
package xyz.fbeye.net;

import xyz.fbeye.datatype.event.Destination;
import xyz.fbeye.datatype.event.Event;
import xyz.fbeye.datatype.event.EventDataType;
import xyz.fbeye.datatype.event.EventList;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Connection {
    private SSLSocket sslSocket;
    private EventList list;

    public Connection(EventList list){
        this.list = list;
    }

    public void connect(String host, int port) {
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
            sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
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
                InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);

                while (sslSocket.isConnected()) {
                    StringBuilder receiveString = new StringBuilder();

                    do{
                        int c = reader.read();
                        receiveString.append((char)c);
                    }while(reader.ready());
                    list.add(new Event(Destination.MANAGER, EventDataType.HEADER, receiveString.toString()));
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

    public void disconnect(){
        try{
            sslSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
