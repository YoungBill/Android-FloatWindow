package com.baina.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baina.floatwindowlib.freeposition.DraggableFloatView;
import com.baina.floatwindowlib.freeposition.DraggableFloatWindow;

/**
 * Created by chentao on 2018/1/14.
 */

public class GetFloatWindowFreePositionActivity extends Activity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getfloatwindw_freeposition);
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
                            Toast.makeText(GetFloatWindowFreePositionActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                        } else {
                            // TODO: 18/1/7 已经授权
                        }
                    }
                    break;
            }
        }
    }

    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.showBt:
                DraggableFloatWindow floatWindow = DraggableFloatWindow.getDraggableFloatWindow(GetFloatWindowFreePositionActivity.this, null);
                floatWindow.show();
                floatWindow.setOnTouchButtonListener(new DraggableFloatView.OnTouchButtonClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(GetFloatWindowFreePositionActivity.this, R.string.tip_click, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
