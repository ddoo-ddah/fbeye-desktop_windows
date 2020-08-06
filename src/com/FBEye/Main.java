/*
 * Main.java
 * Author : susemeeee
 * Created Date : 2020-08-05
 */
package com.FBEye;

import com.FBEye.net.Connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        Connection connection = new Connection();
        connection.Connect();

        InputStream inputstream = System.in;
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        try{
            connection.startRead();
            String str;
            while((str = bufferedreader.readLine()) != null){
                connection.send(str.getBytes());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
