package com.FBEye.util;

import com.FBEye.datatype.Matrix;
import com.FBEye.datatype.Pair;

import java.util.ArrayList;
import java.util.List;

public class ShapeCalculator {

    //0     3
    //*-----*
    //|   *-|->
    //|     |
    //*-----*
    //1     2
    public static boolean isInside(List<Pair<Float, Float>> area, Pair<Float, Float> point){

        int cross = 0;

        for (int i = 0; i < area.size(); i++) {
            int j = (i+1)%area.size();
            //점 2개의 y좌표 사이일때
            if((area.get(i).second > point.second) != (area.get(j).second > point.second)){
                double atX = (area.get(j).first - area.get(i).first) * (point.second - area.get(i).second) / (area.get(j).second - area.get(i).second) + area.get(i).first;
                if(point.first < atX){
                    cross += 1;
                }
            }

        }
        return cross % 2 > 0;
    }

    //0     3
    //*-----*
    //|     |
    //|     |
    //*-----*
    //1     2
    public static Pair<Float,Float> calcRelateCoord(List<Pair<Float, Float>> area, Pair<Float,Float> point){

        List<Pair<Float,Float>> idRect = new ArrayList<>();
        idRect.add(new Pair<>(0f,0f));
        idRect.add(new Pair<>(0f,1f));
        idRect.add(new Pair<>(1f,1f));
        idRect.add(new Pair<>(1f,0f));

        Matrix proj = projectionMatrix(area,idRect);

        double i = point.first;
        double j = point.second;

        double w = proj.getData(2,0) * i + proj.getData(2,1) * j + proj.getData(2, 2);

        double x = (proj.getData(0,0) * i + proj.getData(0,1)*j + proj.getData(0,2))/w;
        double y = (proj.getData(1,0) * i + proj.getData(1,1)*j + proj.getData(1,2))/w;

        return new Pair<>((float)x, (float)y);

    }

    public static Matrix projectionMatrix(List<Pair<Float, Float>> area, List<Pair<Float, Float>> coord){
        Matrix a = new Matrix(8, 8);
        Matrix b = new Matrix(8, 1);

        for(int i = 0; i<4; ++i){
            a.setData(i,0, area.get(i).first);
            a.setData(i,1, area.get(i).second);
            a.setData(i,2, 1.0);
            a.setData(i,6, -1 * coord.get(i).first * area.get(i).first);
            a.setData(i,7, -1 * coord.get(i).first * area.get(i).second);

            b.setData(i,0,coord.get(i).first);
        }

        for(int i = 4; i<8; ++i){
            a.setData(i,3, area.get(i-4).first);
            a.setData(i,4, area.get(i-4).second);
            a.setData(i,5, 1.0);
            a.setData(i,6, -1 * coord.get(i-4).second * area.get(i-4).first);
            a.setData(i,7, -1 * coord.get(i-4).second * area.get(i-4).second);

            b.setData(i,0,coord.get(i-4).second);
        }

        Matrix aInv = Matrix.inverse(a);
        Matrix c = Matrix.Multiply(aInv,b);
        Matrix proj = new Matrix(3,3);

        for(int i = 0; i<8; ++i){
            proj.setData(i/3, i%3, c.getData(i,0));
        }

        proj.setData(2,2,1.0);

        return proj;

    }

}
