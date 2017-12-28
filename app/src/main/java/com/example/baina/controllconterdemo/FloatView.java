package com.example.baina.controllconterdemo;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by baina on 17-12-28.
 * 浮动按钮视图
 */

public class FloatView extends LinearLayout {

    private static final String TAG = FloatView.class.getSimpleName();
    private static final int LIMIT_X = 50;
    private static final int LIMIT_Y = 50;

    private Context mContext;
    private ImageView mTouchBt;
    private GestureDetector mGestureDetector;
    private OnFlingListener mOnFlingListener;

    public FloatView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_add_left, this);
        mTouchBt = findViewById(R.id.touchBt);
        mGestureDetector = new GestureDetector(mContext, new FloatViewOnGestureListener());
        mTouchBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    public FloatView(Context context, OnFlingListener flingListener) {
        super(context);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_add_left, this);
        mTouchBt = findViewById(R.id.touchBt);
        mOnFlingListener = flingListener;
        mGestureDetector = new GestureDetector(mContext, new FloatViewOnGestureListener(mOnFlingListener));
        mTouchBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return false;

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    class FloatViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final String TAG = "MyOnGestureListener";

        private OnFlingListener mFlingListener;

        public FloatViewOnGestureListener() {
        }

        public FloatViewOnGestureListener(OnFlingListener flingListener) {
            mFlingListener = flingListener;
        }

        public void setOnFlingListener(OnFlingListener flingListener) {
            mFlingListener = flingListener;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling");
            //手指划过屏幕的横向距离
            float x = e2.getX() - e1.getX();
            //手指划过屏幕的纵向距离
            float y = e2.getY() - e1.getY();
            //取得横向距离的绝对值
            float x_abs = Math.abs(x);
            //取得纵向距离的绝对值
            float y_abs = Math.abs(y);
            //手指在屏幕上横向滑动
            if (x_abs >= y_abs) {
                if (x > LIMIT_X || x < -LIMIT_X) {
                    if (x > 0) {
                        //向右移动
                        Log.e(TAG, "Direction Right");
                        if (mFlingListener != null)
                            mFlingListener.onSlideRight();
                    } else if (x < 0) {
                        //向左移动
                        Log.e(TAG, "Direction Left");
                        if (mFlingListener != null)
                            mFlingListener.onSlideLeft();
                    }
                }
            }
            //竖向滑动
            else {
                if (y > LIMIT_Y || y < -LIMIT_Y) {
                    if (y > 0) {
                        //向下
                        Log.e(TAG, "Direction Down");
                        if (mFlingListener != null)
                            mFlingListener.onSlideDown();
                    } else if (y < 0) {
                        //向上
                        Log.e(TAG, "Direction Up");
                        if (mFlingListener != null)
                            mFlingListener.onSlideUp();
                    }
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
