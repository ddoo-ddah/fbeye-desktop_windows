package com.FBEye.util;

import com.FBEye.datatype.EyeData;
import com.FBEye.datatype.Pair;

import java.util.*;
import java.util.List;

public class EyeGazeEstimator {
    private static EyeGazeEstimator instance;

    private final Deque<List<Float>> datasets;
    private final List<EyeData>[][] data;
    private boolean isUsable = false;
    private List<Float> lastData;

    private Runnable gazeDrawer;

    private EyeGazeEstimator(int x, int y){
        data = new ArrayList[x][y];
        datasets = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = new ArrayList<>();
            }
        }
    }

    public static void init(int x, int y){
        instance = new EyeGazeEstimator(x, y);
    }


    public boolean collect(int x, int y){
        if(isUsable){
            data[x][y].add(new EyeData(lastData));
            return true;
        }
        return false;
    }

    public List<Pair<Float,Float>> getEstimatedAreaH() {
        List<Pair<Float,Float>> result = new ArrayList<>();

        for(int i = 0; i < data.length; ++i) {
            for (int j = i; j < data[i].length; ++j) {
                result.addAll(getEstimatedArea(lastData,i,j));
            }
        }
        return result;
    }

    public List<Pair<Float,Float>> getEstimatedArea() {
        return getEstimatedArea(lastData,1,1);
    }

    public List<Pair<Float,Float>> getEstimatedArea(List<Float> dataset, int xSplitSize, int ySplitSize) {

        List<Pair<Float,Float>> result = new ArrayList<>();

        float et = (dataset.get(0) + dataset.get(2))/2;
        float ep = (dataset.get(1) + dataset.get(3))/2;
        Pair<Float,Float> target = new Pair<>(et,ep);

        for(int i = 0; i < data.length - xSplitSize; ++i){
            for(int j = 0; j < data[i].length - ySplitSize; ++j){

                List<Pair<Float,Float>> area = new ArrayList<>();
                area.add(getData(i,j));
                area.add(getData(i,j+ySplitSize));
                area.add(getData(i+xSplitSize,j+ySplitSize));
                area.add(getData(i+xSplitSize,j));

                boolean inArea = ShapeCalculator.isInside(area,target);

                if(inArea){
                    Pair<Float,Float> innerCoord = ShapeCalculator.calcRelateCoord(area,target);
                    System.out.println(innerCoord);
                    result.add(new Pair<>(i + innerCoord.first*xSplitSize, j+ innerCoord.second*ySplitSize));

                }

            }
        }
        return result;
    }

    public Pair<Float,Float> getData(int x, int y){

        Float[] sum = new Float[2];
        Arrays.fill(sum, 0.0f);

        for (EyeData eyeData : data[x][y]) {
            sum[0] += eyeData.getData().get(0);
            sum[0] += eyeData.getData().get(2);
            sum[1] += eyeData.getData().get(1);
            sum[1] += eyeData.getData().get(3);

        }

        for (int i = 0; i < sum.length; i++) {
            sum[i] /= (data[x][y].size()*2);
        }

        return new Pair<>(sum[0], sum[1]);
    }


    public static EyeGazeEstimator getInstance() {
        return instance;
    }


    public void setData(List<Float> data){

        if(data.contains(Float.NaN)){
            isUsable = false;
            return;
        }

        datasets.addFirst(data);
        if(datasets.size()>5){
            datasets.removeLast();
        }
        if(gazeDrawer != null){

            Float[] average = new Float[9];
            Arrays.fill(average,0.0f);

            for (List<Float> dataset : datasets) {
                for (int i = 0; i < dataset.size(); i++) {
                    average[i] += dataset.get(i);
                }
            }
            for (int i = 0; i < average.length; i++) {
                average[i] /= datasets.size();
            }
            lastData = Arrays.asList(average);
            gazeDrawer.run();
        }
        if(datasets.size()>4)
            isUsable = true;

    }
    public void setDrawer(Runnable gazeDrawer){
        this.gazeDrawer = gazeDrawer;
    }

}
