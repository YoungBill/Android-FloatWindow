package com.baina.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.baina.floatwindowlib.cutout.CutoutWindow;

public class GetWindowCutoutActivity extends Activity {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x001;

    private CutoutWindow mCutoutWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getfloatwindw_cutout);
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
                            Toast.makeText(GetWindowCutoutActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
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
            case R.id.showCutoutBt:
                if (mCutoutWindow == null) {
                    mCutoutWindow = CutoutWindow.getCutoutViewWindow(GetWindowCutoutActivity.this);
                }
                mCutoutWindow.show();
                break;
            case R.id.dismissCutoutBt:
                if (mCutoutWindow != null) {
                    mCutoutWindow.dismiss();
                }
                break;
            case R.id.scaleUpCutoutBt:
                if (mCutoutWindow != null) {
                    mCutoutWindow.scale(1.5f);
                }
                break;
            case R.id.scaleDownCutoutBt:
                if (mCutoutWindow != null) {
                    mCutoutWindow.scale(0.5f);
                }
                break;
        }
    }

}
