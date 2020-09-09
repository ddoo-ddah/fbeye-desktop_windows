package xyz.fbeye.util;

import xyz.fbeye.datatype.EyeData;
import xyz.fbeye.datatype.Pair;

import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;

public class EyeGazeEstimator {
    private static EyeGazeEstimator instance;

    private final Deque<List<Float>> datasets;
    private final List<EyeData>[][] data;

    private final int xSplitLen;
    private final int ySplitLen;
    private boolean isUsable = false;
    private List<Float> lastData;

    private Runnable gazeDrawer;

    private boolean isDetectCheat = false;

    private BiConsumer<Integer,Integer> cheatReporter;

    private EyeGazeEstimator(int xSplitNumber, int ySplitNumber, int xSplitLen, int ySplitLen){
        this.xSplitLen = xSplitLen;
        this.ySplitLen = ySplitLen;
        data = new ArrayList[xSplitNumber][ySplitNumber];
        datasets = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = new ArrayList<>();
            }
        }
    }

    public static void init(int xSplitNumber, int ySplitNumber, int xSplitSize, int ySplitSize) {
        instance = new EyeGazeEstimator(xSplitNumber, ySplitNumber, xSplitSize, ySplitSize);
    }


    public Pair<Float,Float> getPossiblePosition(){
        return getMiddleMean(getAllEstimatedPositions());
    }

    private Pair<Float,Float> getMiddleMean(List<Pair<Float,Float>> data){

        if(data.size() < 1){
            return null;
        }

        List<Pair<Integer,Float>> indexedData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Pair<Float,Float> currentData = data.get(i);
            indexedData.add(new Pair<>(i, currentData.first + currentData.second ));
        }
        indexedData.sort((first, second) -> Float.compare(first.second ,second.second));

        float sumX = 0.0f;
        float sumY = 0.0f;

        int start = (int) (indexedData.size()*0.25);
        int end = (int) (indexedData.size()*0.75);

        for (int i = start; i < end; i++) {
            int currentIdx = indexedData.get(i).first;
            sumX += data.get(currentIdx).first;
            sumY += data.get(currentIdx).second;
        }

        sumX /= (end-start);
        sumY /= (end-start);

        return new Pair<>(sumX,sumY);
    }

    public List<Pair<Float,Float>> getAllEstimatedPositions() {
        List<Pair<Float,Float>> result = new ArrayList<>();

        for(int i = 0; i < data.length; ++i) {
            for (int j = i; j < data[i].length; ++j) {
                result.addAll(getPossiblePositions(lastData,i,j));
            }
        }
        return result;
    }

    private List<Pair<Float,Float>> getPossiblePositions(List<Float> dataset, int xSplitSize, int ySplitSize) {

        List<Pair<Float,Float>> result = new ArrayList<>();
        Pair<Float,Float> target = getCurrentGaze(dataset);

        for(int i = 0; i < data.length - xSplitSize; ++i){
            for(int j = 0; j < data[i].length - ySplitSize; ++j){

                List<Pair<Float,Float>> p0 = getDataList(i, j);
                List<Pair<Float,Float>> p1 = getDataList(i, j + ySplitSize);
                List<Pair<Float,Float>> p2 = getDataList(i + xSplitSize, j + ySplitSize);
                List<Pair<Float,Float>> p3 = getDataList(i + xSplitSize, j);

                for (Pair<Float, Float> p00 : p0) {
                    for (Pair<Float, Float> p01 : p1) {
                        for (Pair<Float, Float> p02 : p2) {
                            for (Pair<Float, Float> p03 : p3) {
                                List<Pair<Float,Float>> area = new ArrayList<>();
                                area.add(p00);
                                area.add(p01);
                                area.add(p02);
                                area.add(p03);
                                Pair<Float,Float> innerCoord = calcInnerCoord(area,target,i,j,xSplitSize,ySplitSize);
                                if(Objects.nonNull(innerCoord)){
                                    result.add(innerCoord);
                                }
                            }
                        }
                    }
                }

            }
        }
        return result;
    }

    public Pair<Float,Float> getCurrentGaze(List<Float> dataset){
        float et = (dataset.get(0) + dataset.get(2))/2;
        float ep = (dataset.get(1) + dataset.get(3))/2;
        return new Pair<>(et,ep);
    }

    public Pair<Float,Float> calcInnerCoord(List<Pair<Float,Float>> area, Pair<Float,Float> target, int xStart, int yStart, int xSplitSize, int ySplitSize){
        if(ShapeCalculator.isInside(area,target)){
            Pair<Float,Float> innerCoord = ShapeCalculator.calcRelateCoord(area,target);
            return new Pair<>(
                    (xStart + innerCoord.first * xSplitSize) * xSplitLen,
                    (yStart + innerCoord.second * ySplitSize) * ySplitLen);
        }
        return null;
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

    public List<Pair<Float,Float>> getDataList(int x, int y){

        List<Pair<Float,Float>> result = new ArrayList<>();

        for (EyeData eyeData : data[x][y]) {

            float p = (eyeData.getData().get(0) + eyeData.getData().get(2)) / 2;
            float t = (eyeData.getData().get(1) + eyeData.getData().get(3)) / 2;
            result.add(new Pair<>(p,t));
        }

        return result;
    }

    public boolean collect(int x, int y){
        if(isUsable){
            data[x][y].add(new EyeData(lastData));
            return true;
        }
        return false;
    }

    private void detectCheat() {

        getAllEstimatedPositions();
        //Not yet Implemented.
//        cheatReporter.accept();

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

            Float[] average = new Float[7];
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

            if(isDetectCheat){
                detectCheat();
            }

        }
        if(datasets.size()>4)
            isUsable = true;

    }

    public void startDetect(){
        isDetectCheat = true;
    }

    public void stopDetect(){
        isDetectCheat = false;
    }

    public static EyeGazeEstimator getInstance() {
        return instance;
    }

    public void setDrawer(Runnable gazeDrawer){
        this.gazeDrawer = gazeDrawer;
    }

    public void setCheatDataSender(BiConsumer<Integer,Integer> sender){
        cheatReporter = sender;
    }

}
