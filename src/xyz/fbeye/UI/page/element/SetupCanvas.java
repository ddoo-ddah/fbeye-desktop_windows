package xyz.fbeye.UI.page.element;

import xyz.fbeye.datatype.Pair;
import xyz.fbeye.util.EyeGazeEstimator;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.List;

public class SetupCanvas {

    private final Canvas canvas;

    BufferStrategy bufferStrategy;

    private final int XSplitFactor = 6;
    private final int YSplitFactor = 5;
    private final int VALIDATION_SIZE = 5;

    private int next = 0;
    private int currentCount = 0;

    private int XSplit;
    private int YSplit;

    private boolean isEnd;

    {
        canvas = new Canvas();
        isEnd = false;
    }


    /**
     * Configure Environment of canvas after frame has visibility.
     */
    public void postVisible(){
        canvas.createBufferStrategy(2);
        canvas.setIgnoreRepaint(true);
        bufferStrategy = canvas.getBufferStrategy();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        XSplit = (screenSize.width-30) / (XSplitFactor-1);
        YSplit = (screenSize.height-30) / (YSplitFactor-1);

        EyeGazeEstimator.init(XSplitFactor, YSplitFactor, XSplit, YSplit);
        EyeGazeEstimator.getInstance().setDrawer(this::drawEyeGaze);


        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    isEnd = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    boolean success = EyeGazeEstimator.getInstance().collect(next%XSplitFactor, next/XSplitFactor);

                    if(success){
                        currentCount++;
                    }
                    if(currentCount>=VALIDATION_SIZE){
                        currentCount = 0;
                        next++;
                    }
                    if(next>=XSplitFactor*YSplitFactor){
                        next = 0;
                    }
                    drawOnce();
                }

            }
        });
        canvas.requestFocus();
    }

    public void drawOnce(){
        clearScreen();
        drawFeaturePointer();
        drawTargetPoint(next%XSplitFactor, next/XSplitFactor);
        drawDegree();
        finishDraw();
    }

    public void drawTargetPoint(int x, int y){
        canvas.setForeground(Color.MAGENTA);
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.fillOval(x*XSplit, y*YSplit, 30,30);
    }

    public void drawEyeGaze(){

        clearScreen();
        drawFeaturePointer();
        drawTargetPoint(next%XSplitFactor, next/XSplitFactor);
//        drawDegree();
        canvas.setForeground(Color.BLUE);

        List<Pair<Float,Float>> pos = EyeGazeEstimator.getInstance().getAllEstimatedPositions();

        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        for (Pair<Float, Float> p : pos) {
            g2d.fillOval(Math.round(p.first) - 10, Math.round(p.second) - 10, 20,20);
        }
        finishDraw();
    }

    public void clearScreen(){
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        g2d.clearRect(0,0, screenSize.width, screenSize.height);
    }

    public void drawFeaturePointer(){
        canvas.setForeground(Color.GREEN);
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        for(int i = 0; i<XSplitFactor; ++i){
            for(int j = 0; j<YSplitFactor; ++j){
                g2d.fillOval(i*XSplit, j*YSplit, 30,30);
            }
        }
    }

    public void drawDegree(){

        canvas.setForeground(Color.BLACK);
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        for(int i = 0; i<XSplitFactor; ++i){

            for (int j = 0; j < YSplitFactor; j++) {
                Pair<Float,Float> data = EyeGazeEstimator.getInstance().getData(i,j);
//                List<Float> data = new ArrayList<>(List.of(EyeGazeEstimator.getInstance().getData(i, j)));
//                data.addAll();

                int xoffset = 30;
                int yoffset = 40;
                if(i == XSplitFactor-1){
                    xoffset = -50;
                }

                if(j == YSplitFactor-1){
                    yoffset = -30;
                }

                g2d.drawString(String.valueOf(data.first),i*XSplit+xoffset, j*YSplit+yoffset+10*0);
                g2d.drawString(String.valueOf(data.second),i*XSplit+xoffset, j*YSplit+yoffset+10*1);
//                for (int k = 0; k < data.size(); k++) {
//                    g2d.drawString(String.valueOf(data.get(k)),i*XSplit+xoffset, j*YSplit+yoffset+10*k);
//                }
            }

        }
        g2d.dispose();


    }

    public void finishDraw(){
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.dispose();
        bufferStrategy.show();

    }
    public Canvas getCanvas(){
        return this.canvas;
    }

    public boolean getIsEnd(){
        return isEnd;
    }
}
