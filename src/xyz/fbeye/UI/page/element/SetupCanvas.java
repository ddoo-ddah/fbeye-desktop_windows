package xyz.fbeye.UI.page.element;

import xyz.fbeye.datatype.Pair;
import xyz.fbeye.util.EyeGazeEstimator;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.List;

public class SetupCanvas {

    private Canvas canvas;

    BufferStrategy bufferStrategy;
    private Toolkit toolkit;

    private final int XSplitFactor = 7;
    private final int YSplitFactor = 5;
    //private final int XSplitFactor = 2;//test
    //private final int YSplitFactor = 2;//test
    private final int VALIDATION_SIZE = 5;

    private int next = 0;
    private int currentCount = 0;

    private int XSplit;
    private int YSplit;

    private boolean gazeDrawEnable = false;

    private boolean isEnd;

    {
        canvas = new Canvas();
        toolkit = Toolkit.getDefaultToolkit();
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

        //EyeGazeEstimator.init(XSplitFactor, YSplitFactor);
        //EyeGazeEstimator.getInstance().setDrawer(this::drawEyeGaze);
        clearScreen();
        drawFeaturePointer();
        drawTargetPoint(next%XSplitFactor, next/XSplitFactor);
        finishDraw();

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyCode() == KeyEvent.VK_E){
                    gazeDrawEnable = !gazeDrawEnable;
                }

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    boolean success = true;//test
                    //boolean success = EyeGazeEstimator.getInstance().collect(next%XSplitFactor, next/XSplitFactor);

                    if(success){
                        currentCount++;
                    }
                    if(currentCount>VALIDATION_SIZE){
                        currentCount = 0;
                        next++;
                    }
                    if(next>=XSplitFactor*YSplitFactor){
                        //next=0;  //여기서 캔슬
                        isEnd = true;
                    }
                    clearScreen();
                    drawFeaturePointer();
                    drawTargetPoint(next%XSplitFactor, next/XSplitFactor);
                    finishDraw();
                }

            }
        });
        canvas.requestFocus();
    }

    public void drawTargetPoint(int x, int y){
        canvas.setForeground(Color.MAGENTA);
        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        g2d.fillOval(x*XSplit, y*YSplit, 30,30);
    }

    /*public void drawEyeGaze(){
        if(!gazeDrawEnable){
            return;
        }
        clearScreen();
        drawFeaturePointer();
        canvas.setForeground(Color.BLUE);

        List<Pair<Float,Float>> pos = EyeGazeEstimator.getInstance().getEstimatedAreaH();


        Graphics2D g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
        for (Pair<Float, Float> p : pos) {
            g2d.fillOval(Math.round(p.first * XSplit) - 25, Math.round(p.second*YSplit) - 25, 50,50);
        }
        finishDraw();
    }*/

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
