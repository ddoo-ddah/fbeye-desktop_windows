/*
 * DataExchanger.java
 * Author : susemeeee
 * Created Date : 2020-08-06
 */
package com.FBEye.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataExchanger<T>{
    public byte[] toByteArray(T item){
        if(item == null){
            return null;
        }

        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(item);
            oos.flush();

            return bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public T fromByteArray(byte[] item){
        if(item == null){
            return null;
        }

        try{
            ByteArrayInputStream bis = new ByteArrayInputStream(item);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
