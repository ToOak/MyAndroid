package com.cc.xushuailong.mylibrary.start;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.cc.xushuailong.mylibrary.R;
import com.cc.xushuailong.mylibrary.base.RootActivity;
import com.cc.xushuailong.mylibrary.utils.RenderScriptGaussianBlur;

public class Main2Activity extends RootActivity {

    private ImageView img;
    private SeekBar seekBar;
    private RenderScriptGaussianBlur mRenderScriptGaussianBlur;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img = (ImageView) findViewById(R.id.image);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        mRenderScriptGaussianBlur = new RenderScriptGaussianBlur(this);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.img);
//        img.setImageBitmap(bitmap);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radius = seekBar.getProgress();
                Log.d("oak","radius: " + radius);
                if (radius <= 0) {
                    radius = 1;
                } // 0 < radius <= 25
                img.setImageBitmap(mRenderScriptGaussianBlur.blur(radius,bitmap));
            }
        });
    }
}
