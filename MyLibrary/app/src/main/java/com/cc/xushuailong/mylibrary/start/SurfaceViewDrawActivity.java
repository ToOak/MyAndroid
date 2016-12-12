package com.cc.xushuailong.mylibrary.start;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.cc.xushuailong.mylibrary.R;
import com.cc.xushuailong.mylibrary.base.RootActivity;

import java.util.Random;
import java.util.Vector;

/**
 * Created by xushuailong on 2016/12/12.
 */
public class SurfaceViewDrawActivity extends RootActivity implements SurfaceHolder.Callback{
    private static int HEIGHT = 0;
    private static int WIDTH = 0;
    private static final int SNOW_NUMBER = 120;
    private SurfaceHolder mHolder = null;
    private Paint mPaint = null;
    private SnowControl mSnowCtrl = null;
    private Bitmap mBitmap = null;
    private Handler mHandler = new Handler();
    private final Runnable drawFramRun = new Runnable() {
        @Override
        public void run() {
            mSnowCtrl.moveSnows();
            drawFrame();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surface);
        final SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        mHolder = surface.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        WIDTH = holder.getSurfaceFrame().width();
        HEIGHT = holder.getSurfaceFrame().height();
        mPaint = new Paint();
        mPaint.setColor(0xffffffff);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2.0f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mSnowCtrl = new SnowControl(WIDTH, HEIGHT);
        mSnowCtrl.initSnows(SNOW_NUMBER);
        mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.bg_snow);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSnowCtrl != null){
            mSnowCtrl.setmHeight(height);
            mSnowCtrl.setmWidth(width);
        }
        HEIGHT = height;
        WIDTH = width;
        drawFramRun.run();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHandler.removeCallbacks(drawFramRun);
    }

    private void drawFrame(){
        Canvas canvas = null;
        try {
            canvas = mHolder.lockCanvas();
            canvas.drawBitmap(mBitmap,null,new Rect(0,0,WIDTH,HEIGHT),null);
            if (canvas != null){
                mSnowCtrl.drawSnow(canvas,mPaint);
            }
        } finally {
            if (canvas != null){
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
        mHandler.removeCallbacks(drawFramRun);
        mHandler.postDelayed(drawFramRun,40);
    }
    /**
     * SnowControl
     */
    class SnowControl{
        private int mWidth = 0;
        private int mHeight = 0;
        private Random mRandom = null;
        private int mSnowNum = 0;
        private Vector<Snow> mSnows = new Vector<>();

        public SnowControl(int width, int height){
            mHeight = height;
            mWidth = width;
            mRandom = new Random();
        }

        public void initSnows(int numOfSnows){
            mSnowNum = numOfSnows;
            for (int i = 0;i < numOfSnows; i++){
                Snow aSnow = new Snow();
                aSnow.setX(mRandom.nextInt(mWidth));
                aSnow.setRadius(mRandom.nextInt(3));
                aSnow.setY(-aSnow.getRadius());
                aSnow.setColor(Color.WHITE);
                aSnow.setXspeed(mRandom.nextInt(4) - 2);
                aSnow.setYspeed(mRandom.nextInt(5) + 1);
                aSnow.setLive(mRandom.nextBoolean());
                mSnows.add(aSnow);
            }
        }

        public void moveSnows(){
            for (int i = 0;i < mSnowNum; i++){
                Snow tempSnow = mSnows.get(i);
                if (tempSnow.isLive()){
                    tempSnow.setX(tempSnow.getX() + tempSnow.getXspeed());
                    tempSnow.setY(tempSnow.getY() + tempSnow.getYspeed());
                    if ((tempSnow.getX() < -tempSnow.getRadius())
                            ||(tempSnow.getX() > mWidth + tempSnow.getRadius())
                            ||(tempSnow.getY() > mHeight + tempSnow.getRadius())){
                        tempSnow.setLive(false);
                    }
                }else {
                    tempSnow.setX(mRandom.nextInt(mWidth));
                    tempSnow.setRadius(mRandom.nextInt(3));
                    tempSnow.setY(-tempSnow.getRadius());
                    tempSnow.setColor(Color.WHITE);
                    tempSnow.setXspeed(mRandom.nextInt(4) - 2);
                    tempSnow.setYspeed(mRandom.nextInt(5) + 1);
                    tempSnow.setLive(true);
                }
            }
        }

        public void drawSnow(Canvas canvas, Paint paint){
            for (int i = 0; i < mSnowNum; i++){
                if (mSnows.get(i).isLive()){
                    Snow ab = mSnows.get(i);
                    paint.setColor(ab.getColor());
                    canvas.drawCircle(ab.getX(),ab.getY(),ab.getRadius(),paint);
                }
            }
        }

        public void setmHeight(int mHeight) {
            this.mHeight = mHeight;
        }

        public void setmWidth(int mWidth) {
            this.mWidth = mWidth;
        }
    }

    /**
     * Snow
     */
    class Snow {
        private int x = -1;
        private int y = -1;
        private int color = -1;
        private int radius = -1;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
            if (this.radius < 1){
                this.radius = 1;
            }
        }

        public int getXspeed() {
            return xspeed;
        }

        public void setXspeed(int xspeed) {
            this.xspeed = xspeed;
        }

        public int getYspeed() {
            return yspeed;
        }

        public void setYspeed(int yspeed) {
            this.yspeed = yspeed;
        }

        public boolean isLive() {
            return isLive;
        }

        public void setLive(boolean live) {
            isLive = live;
        }

        private int xspeed = 0;
        private int yspeed = 0;
        private boolean isLive = false;
    }
}
