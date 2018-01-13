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
            case R.id.directlyBt:
                startActivity(new Intent(MainActivity.this, GetFloatWindowDirectlyActivity.class));
                break;
            case R.id.setLocationBt:
                startActivity(new Intent(MainActivity.this, GetFloatWindowSetLocationActivity.class));
                break;
        }
    }

}
