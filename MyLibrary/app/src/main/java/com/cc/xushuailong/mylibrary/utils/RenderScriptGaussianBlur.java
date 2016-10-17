package com.cc.xushuailong.mylibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsic;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by xushuailong on 2016/10/17.
 */
public class RenderScriptGaussianBlur {
    private RenderScript rs;
    public RenderScriptGaussianBlur(Context context){
        this.rs = RenderScript.create(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blur(int radius, Bitmap bitmapOriginal){
        final Allocation input = Allocation.createFromBitmap(rs,bitmapOriginal);
        final Allocation output = Allocation.createTyped(rs,input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmapOriginal);
        return bitmapOriginal;
    }
}
