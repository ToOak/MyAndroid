package com.cc.xushuailong.mylibrary.start;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.mylibrary.PhoneInfo;
import com.cc.xushuailong.mylibrary.MyApplication;
import com.cc.xushuailong.mylibrary.R;
import com.cc.xushuailong.mylibrary.base.RootActivity;
import com.cc.xushuailong.mylibrary.utils.CommonUtils;
import com.cc.xushuailong.mylibrary.utils.Log4jUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends RootActivity implements View.OnClickListener {

    private Button btn_selector;
    private TextView textView;
    private Button btn_bitmap;

    private int xsrc, ysrc;
    private Configuration cfg;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews();
        viewEvents();
    }

    public void initViews() {
        textView = (TextView) findViewById(R.id.text);
        btn_selector = (Button) findViewById(R.id.btn_selector);
        btn_bitmap = (Button) findViewById(R.id.btn_gotobitmap);
        cfg = getResources().getConfiguration();
    }

    public void viewEvents() {
        btn_selector.setOnClickListener(this);
        btn_bitmap.setOnClickListener(this);
        MainActivity.this.registerForContextMenu(btn_selector);

        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final int screenHeight = getResources().getDisplayMetrics().heightPixels - 50;

        findViewById(R.id.btn_orientation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    //                若为横屏，改为竖屏
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else if (cfg.orientation == Configuration.ORIENTATION_PORTRAIT){
                    //                若为竖屏，改为横屏
                    MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

        findViewById(R.id.btn_move).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:{
                        xsrc = (int) event.getRawX();
                        ysrc = (int) event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:{
                        int x = (int) (event.getRawX() - xsrc);
                        int y = (int) (event.getRawY() - ysrc);

                        int top = v.getTop() + y;
                        int right = v.getRight() + x;
                        int bottom = v.getBottom() + y;
                        int left = v.getLeft() + x;

                        if (top < 0 ){
                            top = 0;
                            bottom = v.getHeight() + top;
                        }

                        if (right > screenWidth){
                            right = screenWidth;
                            left = right - v.getWidth();
                        }

                        if (bottom > screenHeight){
                            bottom = screenHeight;
                            top = bottom - v.getHeight();
                        }

                        if (left < 0){
                            left = 0;
                            right = v.getWidth() + left;
                        }

                        v.layout(left,top,right,bottom);

                        xsrc = (int) event.getRawX();
                        ysrc = (int) event.getRawY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_main_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_show_info:{
                Log4jUtil.i("Get Phone Info !");
                getPhoneInfo();
                break;
            }
            case R.id.item_hide_info:{
                Log4jUtil.i("Hide Phone Info !");
                textView.setText("");
                break;
            }
            case R.id.item_goto_second:{
                Intent intent = new Intent();
                intent.setClass(MyApplication.getApp().getApplicationContext(),Main2Activity.class);
                startActivity(intent);
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_clear_log:{
                Log4jUtil.d("Clear Log !");
                MyApplication.getApp().reSetLogFile();
                break;
            }
            case R.id.item_close_app:{
                Log4jUtil.d("Close the Project !");
                MyApplication.getApp().removeAllActivity();
                break;
            }
            case R.id.item_error_close:{
                Log4jUtil.e("Error Close !");
                System.exit(0);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPhoneInfo(){
        PhoneInfo mPhoneInfo = new PhoneInfo(MainActivity.this);
        if (!TextUtils.isEmpty(textView.getText().toString())){
            textView.setText("");
        }
        textView.setText(textView.getText() + "devices id: " + mPhoneInfo.getDeviceId() + "\n");
        textView.setText(textView.getText() + "PhoneModule: " + mPhoneInfo.getPhoneModule() + "\n");
        textView.setText(textView.getText() + "SerialNumber: " + mPhoneInfo.getSerialNumber() + "\n");
        textView.setText(textView.getText() + "PhoneNumber: " + mPhoneInfo.getPhoneNumber() + "\n");
        textView.setText(textView.getText() + "MacAddress: " + mPhoneInfo.getMacAddress() + "\n");
        textView.setText(textView.getText() + "CpuInfo: " + mPhoneInfo.getCpuInfo()[0] + "\t" + mPhoneInfo.getCpuInfo()[1] + "\n");
        textView.setText(textView.getText() + "TotalMemory: " + mPhoneInfo.getTotalMemory() + "\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.this.unregisterForContextMenu(btn_selector);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_selector:{
                if (CommonUtils.isFastDoubleClick(MyApplication.getApp().getApplicationContext(),800)){
                    return;
                }else {
                    Log4jUtil.d("click btn_selecter: " + new Date(System.currentTimeMillis()));
                }
                break;
            }
            case R.id.btn_gotobitmap:{
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.bitmap_view,null);
                view.setDrawingCacheEnabled(true);
                view.measure(View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY));
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();
                ((ImageView)findViewById(R.id.iamge)).setImageBitmap(bitmap);
                saveCroppedImage(bitmap);
                break;
            }
        }
    }
    /** 保存方法 */
    private void saveCroppedImage(Bitmap bmp) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/yjk" );
        if (!file.exists()) {
            file.mkdir();
        }

        String newFilePath = Environment.getExternalStorageDirectory().getPath() + "/yjk"
                + File.separator + "保存图片" + System.currentTimeMillis() + ".jpg";
        Log.d("xsl","newFilePath"+newFilePath);
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.d("xsl","error");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4jUtil.d("onConfigurationChanged");
    }
}
