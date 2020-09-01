package com.FBEye.datatype;

import java.util.*;

public class EyeData {

    private final List<Float> dataset;

    public EyeData(List<Float> data){
        dataset = new ArrayList<>(9);
        dataset.addAll(data);
    }

    public List<Float> getData(){
        return dataset;
    }

    @Override
    public String toString() {
        return "EyeData{" +
                "dataset=" + dataset +
                '}';
    }

}
