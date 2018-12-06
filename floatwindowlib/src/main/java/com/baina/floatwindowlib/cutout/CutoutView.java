package com.baina.floatwindowlib.cutout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.widget.LinearLayout;

import com.baina.floatwindowlib.R;

/**
 * Created by chentao on 2018/1/14.
 * 浮动按钮视图
 */

@SuppressLint("ViewConstructor")
public class CutoutView extends LinearLayout {

    private static final String TAG = CutoutView.class.getSimpleName();

    private Context mContext;

    public CutoutView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_cutout, this);
        setGravity(Gravity.CENTER);
    }
}
