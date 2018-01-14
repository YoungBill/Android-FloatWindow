package com.baina.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.getFixedPositionWindowBt:
                startActivity(new Intent(MainActivity.this, GetFloatWindowFixedPositionActivity.class));
                break;
            case R.id.getFreePositionWindowBt:
                startActivity(new Intent(MainActivity.this, GetFloatWindowFreePositionActivity.class));
                break;
        }
    }

}
