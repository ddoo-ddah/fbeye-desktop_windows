/*
 * QRGenerator.java
 * Author : susemeeee
 * Created Date : 2020-08-11
 */
package com.FBEye.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class QRGenerator {
    public static ImageIcon generateQR(String data, int width, int height){
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter writer = new QRCodeWriter();
        try{
            BitMatrix byteMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);
            int matrixWidth = byteMatrix.getWidth();
            int matrixHeight = byteMatrix.getHeight();
            BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D)image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, matrixWidth, matrixHeight);
            graphics.setColor(Color.BLACK);
            for(int i = 0; i < matrixHeight; i++){
                for(int j = 0; j < matrixWidth; j++){
                    if(byteMatrix.get(i, j)){
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            return new ImageIcon(image);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
