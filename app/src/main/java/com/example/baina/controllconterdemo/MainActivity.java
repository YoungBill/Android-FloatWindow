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
                            //启动FxService
                        }
                    }
                    break;
            }
        }
    }

    public void OnClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View contentView = inflater.inflate(R.layout.layout_pop, null);
        switch (view.getId()) {
            case R.id.leftBt:
                final FloatMainWindow leftFloatMainWindow = FloatMainWindow.getFloatMainWindow(MainActivity.this, FloatMainWindow.LOCATION_LEFT, contentView);
                contentView.findViewById(R.id.testBt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "你点击了弹出窗的按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leftFloatMainWindow.getPopupWindow().dismiss();
                    }
                });
                break;
            case R.id.rightBt:
                final FloatMainWindow rightFloatMainWindow = FloatMainWindow.getFloatMainWindow(MainActivity.this, FloatMainWindow.LOCATION_RIGHT, contentView);
                contentView.findViewById(R.id.testBt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "你点击了弹出窗的按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rightFloatMainWindow.getPopupWindow().dismiss();
                    }
                });
                break;
            case R.id.bottomBt:
                final FloatMainWindow bottomFloatMainWindow = FloatMainWindow.getFloatMainWindow(MainActivity.this, FloatMainWindow.LOCATION_BOTTOM, contentView);
                contentView.findViewById(R.id.testBt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "你点击了弹出窗的按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomFloatMainWindow.getPopupWindow().dismiss();
                    }
                });
                break;
        }
    }

}
