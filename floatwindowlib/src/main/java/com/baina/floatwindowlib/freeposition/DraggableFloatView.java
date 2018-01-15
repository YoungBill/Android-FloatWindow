package com.baina.floatwindowlib.freeposition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baina.floatwindowlib.OnFlingListener;
import com.baina.floatwindowlib.OnTouchButtonListener;
import com.baina.floatwindowlib.R;

/**
 * Created by chentao on 2018/1/14.
 * 浮动按钮视图
 */

@SuppressLint("ViewConstructor")
public class DraggableFloatView extends LinearLayout {

    private static final String TAG = DraggableFloatView.class.getSimpleName();

    private Context mContext;
    private ImageView mTouchBt;
    private OnFlingListener mOnFlingListener;
    private OnTouchButtonListener mTouchButtonListener;

    public DraggableFloatView(Context context, OnFlingListener flingListener) {
        super(context);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_floatview_freeposition, this);
        mTouchBt = findViewById(R.id.touchBt);
        mOnFlingListener = flingListener;
        mTouchBt.setOnTouchListener(new OnTouchListener() {

            float downX, downY;
            float moveX, moveY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getRawX();
                    downY = motionEvent.getRawY();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    moveX = motionEvent.getRawX();
                    moveY = motionEvent.getRawY();
                    if (mOnFlingListener != null)
                        mOnFlingListener.onMove(moveX - downX, moveY - downY);
                    downX = moveX;
                    downY = moveY;
                }
                if (mTouchButtonListener != null) {
                    mTouchButtonListener.OnTouch(view, motionEvent);
                }
                return true;
            }
        });

        mTouchBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTouchButtonListener != null) {
                    mTouchButtonListener.onClick(v);
                }
            }
        });
    }

    public void setOnTouchButtonListener(OnTouchButtonListener touchButtonListener) {
        mTouchButtonListener = touchButtonListener;
    }
}
