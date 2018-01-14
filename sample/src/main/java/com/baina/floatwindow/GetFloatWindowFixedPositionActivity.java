package com.baina.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chentao on 2018/1/14.
 */

public class GetFloatWindowFixedPositionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getfloatwindow_fixedposition);
    }


    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.directlyBt:
                startActivity(new Intent(GetFloatWindowFixedPositionActivity.this, GetFloatWindowDirectlyActivity.class));
                break;
            case R.id.setLocationBt:
                startActivity(new Intent(GetFloatWindowFixedPositionActivity.this, GetFloatWindowSetLocationActivity.class));
                break;
        }
    }
}
