package com.baina.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.baina.floatwindowlib.fixedposition.FloatWindow;

public class GetFloatWindowSetLocationActivity extends Activity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getfloatwindw_fixedposition_setlocation);
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
                            Toast.makeText(GetFloatWindowSetLocationActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO: 18/1/7 已经授权
                        }
                    }
                    break;
            }
        }
    }

    public void OnClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(GetFloatWindowSetLocationActivity.this);
        View contentView = inflater.inflate(R.layout.layout_pop, null);
        //这是第二种方法得到弹窗
        final FloatWindow floatWindow = FloatWindow.getFloatWindow(GetFloatWindowSetLocationActivity.this, contentView);
        contentView.findViewById(R.id.testBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatWindow.dismiss();
                Toast.makeText(GetFloatWindowSetLocationActivity.this, R.string.pressme, Toast.LENGTH_SHORT).show();
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatWindow.dismiss();
            }
        });
        switch (view.getId()) {
            case R.id.leftBt:
                floatWindow.setTouchLocation(FloatWindow.LOCATION_LEFT);
                floatWindow.show();
                break;
            case R.id.rightBt:
                floatWindow.setTouchLocation(FloatWindow.LOCATION_RIGHT);
                floatWindow.show();
                break;
            case R.id.bottomBt:
                floatWindow.setTouchLocation(FloatWindow.LOCATION_BOTTOM);
                floatWindow.show();
                break;
        }
    }

}
