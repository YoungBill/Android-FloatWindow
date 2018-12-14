package com.baina.floatwindowlib.cutout;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

public class CutoutWindow {

    private static final String TAG = CutoutWindow.class.getSimpleName();
    private static CutoutWindow sCutoutWindow;
    private static WindowManager.LayoutParams sParams = null;
    private static WindowManager sWindowManager = null;
    private static CutoutView sCutoutView;

    private Context mContext;

    public CutoutWindow(Context context) {
        mContext = context;
        sCutoutView = new CutoutView(context);
    }

    /**
     * 第一种得到弹窗的方法
     *
     * @param context，上下文对象
     * @return
     */
    public static CutoutWindow getCutoutViewWindow(Context context) {
        if (sCutoutWindow == null) {
            synchronized (CutoutWindow.class) {
                if (sCutoutWindow == null) {
                    initCutoutViewWindow(context);
                    sCutoutWindow = new CutoutWindow(context);
                }
            }
        }
        return sCutoutWindow;
    }

    public void show() {
        attachCutoutViewToWindow();
    }

    public void dismiss() {
        detachCutoutViewFromWindow();
    }

    public void scale(float scale) {
        if (sCutoutView == null)
            throw new IllegalStateException("CutoutView can not be null");
        sCutoutView.setScaleX(scale);
    }

    /**
     * attach floatView to window
     */
    private static void attachCutoutViewToWindow() {
        if (sCutoutView == null)
            throw new IllegalStateException("CutoutView can not be null");
        if (sParams == null)
            throw new IllegalStateException("WindowManager.LayoutParams can not be null");
        try {
            sWindowManager.updateViewLayout(sCutoutView, sParams);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            //if floatView not attached to window,addView
            sWindowManager.addView(sCutoutView, sParams);
        }
    }

    /**
     * detach floatView from window
     */
    private static void detachCutoutViewFromWindow() {
        // TODO: 18-7-30 @lhr2528 you can fix issue 2 here
        if (sCutoutView == null)
            throw new IllegalStateException("CutoutView can not be null");
        try {
            sWindowManager.removeViewImmediate(sCutoutView);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 初始化initFloatViewWindow参数
     *
     * @param context，上下文对象
     */
    private static void initCutoutViewWindow(Context context) {
        sWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        sParams = new WindowManager.LayoutParams();
        sParams.gravity = Gravity.TOP;
        sParams.packageName = context.getPackageName();
        sParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        sParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        sParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            sParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }
        //The default position is vertically to the right
//        sParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
        sParams.format = PixelFormat.RGBA_8888;
    }
}
