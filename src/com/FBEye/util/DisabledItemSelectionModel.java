/*
 * DisabledSelectionModel.java
 * Author : susemeeee
 * Created Date : 2020-08-07
 */
package com.FBEye.util;

import javax.swing.*;

public class DisabledItemSelectionModel extends DefaultListSelectionModel {
    @Override
    public void setSelectionInterval(int index0, int index1) {
        super.setSelectionInterval(-1, -1);
    }
}
