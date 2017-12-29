package com.example.baina.controllconterdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class FloatMainWindow {

    public static final int LOCATION_LEFT = 0x001;
    public static final int LOCATION_RIGHT = 0x002;
    public static final int LOCATION_BOTTOM = 0x003;
    private static final String TAG = FloatMainWindow.class.getSimpleName();
    private static FloatMainWindow floatMainWindow;
    private static WindowManager.LayoutParams mParams = null;
    private static WindowManager mWindowManager = null;
    private static FloatView mFloatView;
    private static View mPopView;
    private static int mTouchLocation = LOCATION_LEFT;

    private Context mContext;
    private PopupWindow mPopupWindow;

    public FloatMainWindow(Context context, int touchLocation, View popView) {
        mContext = context;
        mTouchLocation = touchLocation;
        initFloatView(context);
        showWindow(context);
        initPopupWindow(popView);
    }

    public static FloatMainWindow getFloatMainWindow(Context context, int touchLocation, View popView) {
        if (floatMainWindow == null) {
            synchronized (FloatMainWindow.class) {
                if (floatMainWindow == null) {
                    floatMainWindow = new FloatMainWindow(context, touchLocation, popView);
                }
            }
        } else {
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            switch (touchLocation) {
                case LOCATION_LEFT:
                    mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
                    mTouchLocation = LOCATION_LEFT;
                    break;
                case LOCATION_RIGHT:
                    mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
                    mTouchLocation = LOCATION_RIGHT;
                    break;
                case LOCATION_BOTTOM:
                    mParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                    mTouchLocation = LOCATION_BOTTOM;
                    break;
            }
            mWindowManager.updateViewLayout(mFloatView, mParams);
        }
        return floatMainWindow;
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    private void showWindow(final Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        }
        mParams = new WindowManager.LayoutParams();
        mParams.packageName = context.getPackageName();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mParams.format = PixelFormat.RGBA_8888;
        mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
        switch (mTouchLocation) {
            case LOCATION_LEFT:
                mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
                break;
            case LOCATION_RIGHT:
                mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
                break;
            case LOCATION_BOTTOM:
                mParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                break;
        }
        mWindowManager.addView(mFloatView, mParams);
    }

    private void initFloatView(Context context) {
        mFloatView = new FloatView(context, new OnFlingListener() {

            @Override
            public void onSlideUp() {
                if (mTouchLocation != LOCATION_BOTTOM)
                    return;
                Log.e(TAG, "显示向上的Popuwindow");
                ImageView touchButton = mFloatView.findViewById(R.id.touchBt);
                touchButton.setVisibility(View.GONE);
                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(mFloatView, mParams);
                mPopupWindow.setAnimationStyle(R.style.AnimationTopFade);
                mPopupWindow.setClippingEnabled(false);
                mPopupWindow.showAtLocation(mFloatView, Gravity.TOP, 0, 0);
            }

            @Override
            public void onSlideLeft() {
                if (mTouchLocation != LOCATION_RIGHT)
                    return;
                Log.e(TAG, "显示向左的Popuwindow");
                ImageView touchButton = mFloatView.findViewById(R.id.touchBt);
                touchButton.setVisibility(View.GONE);
                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(mFloatView, mParams);
                mPopupWindow.setAnimationStyle(R.style.AnimationLeftFade);
                mPopupWindow.setClippingEnabled(false);
                mPopupWindow.showAtLocation(mFloatView, Gravity.TOP, 0, 0);
            }

            @Override
            public void onSlideRight() {
                if (mTouchLocation != LOCATION_LEFT)
                    return;
                Log.e(TAG, "显示向右的Popuwindow");
                ImageView touchButton = mFloatView.findViewById(R.id.touchBt);
                touchButton.setVisibility(View.GONE);
                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(mFloatView, mParams);
                mPopupWindow.setAnimationStyle(R.style.AnimationRightFade);
                mPopupWindow.setClippingEnabled(false);
                mPopupWindow.showAtLocation(mFloatView, Gravity.TOP, 0, 0);
            }
        });
    }

    private void initPopupWindow(View contentView) {
        int height = DeviceUtils.getScreenHeight(mContext) + DeviceUtils.getNavigationBarHeight(mContext);
        mPopupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, height);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.colorSelf));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        mWindowManager.updateViewLayout(mFloatView, mParams);
                        mFloatView.findViewById(R.id.touchBt).setVisibility(View.VISIBLE);
                    }
                }, 280);
            }
        });
    }

}