package com.baina.floatwindowlib.freeposition;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.baina.floatwindowlib.OnFlingListener;


/**
 * Created by chentao on 2018/1/14.
 * 自由拖动的悬浮窗
 */

public class DraggableFloatWindow {

    private static final String TAG = DraggableFloatWindow.class.getSimpleName();
    private static DraggableFloatWindow mDraggableFloatWindow;
    private static WindowManager.LayoutParams mParams = null;
    private static WindowManager mWindowManager = null;
    private static DraggableFloatView mDraggableFloatView;

    private Context mContext;

    public DraggableFloatWindow(Context context, View popView) {
        mContext = context;
        initDraggableFloatView(context);
    }

    /**
     * 第一种得到弹窗的方法
     *
     * @param context，上下文对象
     * @param popView，弹窗内容
     * @return
     */
    public static DraggableFloatWindow getDraggableFloatWindow(Context context, View popView) {
        if (mDraggableFloatWindow == null) {
            synchronized (DraggableFloatWindow.class) {
                if (mDraggableFloatWindow == null) {
                    initDraggableFloatViewWindow(context);
                    mDraggableFloatWindow = new DraggableFloatWindow(context, popView);
                }
            }
        }
        return mDraggableFloatWindow;
    }

    public void setOnTouchButtonListener(DraggableFloatView.OnTouchButtonClickListener touchButtonListener) {
        mDraggableFloatView.setTouchButtonClickListener(touchButtonListener);
    }


    public void show() {
        attachFloatViewToWindow();
    }

    public void dismiss() {

    }

    /**
     * attach floatView to window
     */
    private static void attachFloatViewToWindow() {
        if (mDraggableFloatView == null)
            throw new IllegalStateException("DraggableFloatView can not be null");
        if (mParams == null)
            throw new IllegalStateException("WindowManager.LayoutParams can not be null");
        try {
            mWindowManager.updateViewLayout(mDraggableFloatView, mParams);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            //if floatView not attached to window,addView
            mWindowManager.addView(mDraggableFloatView, mParams);
        }
    }

    /**
     * 初始化initFloatViewWindow参数
     *
     * @param context，上下文对象
     */
    private static void initDraggableFloatViewWindow(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.packageName = context.getPackageName();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //The default position is vertically to the right
//        mParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        mParams.format = PixelFormat.RGBA_8888;
    }

    /**
     * 初始化touch按钮所在window
     *
     * @param context，上下文对象
     */
    private void initDraggableFloatView(Context context) {
        mDraggableFloatView = new DraggableFloatView(context, new OnFlingListener() {
            @Override
            public void onMove(float moveX, float moveY) {
                mParams.x = (int) (mParams.x + moveX);
                mParams.y = (int) (mParams.y + moveY);
                mWindowManager.updateViewLayout(mDraggableFloatView, mParams);
            }
        });
    }
}
