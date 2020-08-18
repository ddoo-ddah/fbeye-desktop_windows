/*
 * DateTimeMaker.java
 * Author : susemeeee
 * Created Date : 2020-08-18
 */
package com.FBEye.util;

import java.time.LocalDateTime;

public class DateTimeMaker {
    public static LocalDateTime make(String date){
        String[] splitDate = date.split("-");
        int[] element = new int[6];
        for(int i = 0; i < element.length; i++){
            element[i] = Integer.parseInt(splitDate[i]);
        }
        return LocalDateTime.of(element[0], element[1], element[2], element[3], element[4], element[5]);
    }
}
