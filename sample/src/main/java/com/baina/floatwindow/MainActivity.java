package com.baina.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;


public class MainActivity extends Activity {


    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        Log.d(TAG, "onCreate,window == null?" + window == null ? "yes" : "false");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Window window = getWindow();
        Log.d(TAG, "onPause,window == null?" + window == null ? "yes" : "false");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Window window = getWindow();
        Log.d(TAG, "onStop,window == null?" + window == null ? "yes" : "false");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Window window = getWindow();
        Log.d(TAG, "onDestroy,window == null?" + window == null ? "yes" : "false");
    }

    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.getFixedPositionWindowBt:
                startActivity(new Intent(MainActivity.this, GetFloatWindowFixedPositionActivity.class));
                break;
            case R.id.getFreePositionWindowBt:
                startActivity(new Intent(MainActivity.this, GetFloatWindowFreePositionActivity.class));
                break;
            case R.id.getCutoutWindowBt:
                startActivity(new Intent(MainActivity.this, GetWindowCutoutActivity.class));
                break;
        }
    }

}
