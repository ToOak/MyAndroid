package com.cc.xushuailong.mylibrary.start;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;

import com.cc.xushuailong.mylibrary.R;
import com.cc.xushuailong.mylibrary.base.RootActivity;

/**
 * Created by xushuailong on 2016/12/12.
 */
public class MatriaxActivity extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MatriaView(this));
    }

    private class MatriaView extends View{
        private Bitmap bitmap = null;
        private Matrix mat = null;
        private float degree = 30.0f;
        private float scale = 1.5f;
        private float transX = 30.0f;
        private float transY = 30.0f;
        private float skewX = 0.5f;
        private float skewY = 0.2f;

        public MatriaView(Context context) {
            super(context);
            mat = new Matrix();
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.instance);
            setFocusable(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mat.reset();
            mat.postRotate(degree);
            mat.postTranslate(scale,scale);
            mat.postSkew(skewX,skewY,transX,transY);
            canvas.drawBitmap(bitmap,mat,null);
        }
    }
}
