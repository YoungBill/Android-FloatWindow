package com.example.baina.controllconterdemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case OVERLAY_PERMISSION_REQ_CODE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.canDrawOverlays(this)) {
                            Toast.makeText(MainActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO: 18/1/7 已经授权
                        }
                    }
                    break;
            }
        }
    }

    public void OnClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View contentView = inflater.inflate(R.layout.layout_pop, null);
        //这是第二种方法得到弹窗，如下右边，底部位置的弹窗就是这种办法得到的
        final FloatWindow floatWindow = FloatWindow.getFloatWindow(MainActivity.this, contentView);
        contentView.findViewById(R.id.testBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatWindow.getPopupWindow().dismiss();
                Toast.makeText(MainActivity.this, R.string.pressme, Toast.LENGTH_SHORT).show();
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatWindow.getPopupWindow().dismiss();
            }
        });
        switch (view.getId()) {
            case R.id.leftBt:
                //这是第一种方法得到弹窗
                final FloatWindow leftFloatWindow = FloatWindow.getFloatWindow(MainActivity.this, FloatWindow.LOCATION_LEFT, contentView);
                contentView.findViewById(R.id.testBt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leftFloatWindow.getPopupWindow().dismiss();
                        Toast.makeText(MainActivity.this, R.string.pressme, Toast.LENGTH_SHORT).show();
                    }
                });
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leftFloatWindow.getPopupWindow().dismiss();
                    }
                });
                break;
            case R.id.rightBt:
                floatWindow.setTouchLocation(FloatWindow.LOCATION_RIGHT);
                break;
            case R.id.bottomBt:
                floatWindow.setTouchLocation(FloatWindow.LOCATION_BOTTOM);
                break;
        }
    }

}
