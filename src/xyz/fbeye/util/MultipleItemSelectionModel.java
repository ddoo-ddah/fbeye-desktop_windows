/*
 * MultipleItemSelectionModel.java
 * Author : susemeeee
 * Created Date : 2020-08-13
 */
package xyz.fbeye.util;

import javax.swing.*;

public class MultipleItemSelectionModel extends DefaultListSelectionModel {
    @Override
    public void setSelectionInterval(int index0, int index1) {
        if(super.isSelectedIndex(index0)) {
            super.removeSelectionInterval(index0, index1);
        }
        else {
            super.addSelectionInterval(index0, index1);
        }
    }
}
