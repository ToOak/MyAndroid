package com.cc.xushuailong.mylibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cc.xushuailong.mylibrary.R;
import com.cc.xushuailong.mylibrary.utils.Log4jUtil;

/**
 * Created by xushuailong on 2016/12/9.
 */
public class TouchView extends View {
    private float left = 20;
    private float top = 20;
    private float right = 60;
    private float bottom = 60;
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log4jUtil.d("TouchView onDraw");
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.drawRect(left,top,right,bottom,p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        left = event.getX()- 20;
        right = event.getX() + 20;
        top = event.getY() - 20;
        bottom = event.getY() + 20;
        Log4jUtil.d("TouchView onTouchEvent");
        invalidate();
        return true;
//        return super.onTouchEvent(event);
    }
}
