package com.baina.floatwindowlib.fixedposition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.baina.floatwindowlib.DeviceUtils;
import com.baina.floatwindowlib.OnFlingListener;
import com.baina.floatwindowlib.R;

/**
 * Created by baina on 17-12-28.
 * 固定几个位置的悬浮窗
 */
public class FloatWindow {

    public static final int LOCATION_LEFT = 0x001;
    public static final int LOCATION_RIGHT = 0x002;
    public static final int LOCATION_BOTTOM = 0x003;
    private static final String TAG = FloatWindow.class.getSimpleName();
    private static FloatWindow mFloatWindow;
    private static WindowManager.LayoutParams mParams = null;
    private static WindowManager mWindowManager = null;
    private static FloatView mFloatView;
    private static int mTouchLocation = LOCATION_LEFT;

    private Context mContext;
    private PopupWindow mPopupWindow;
    private View mPopupView;

    public FloatWindow(Context context, int touchLocation, View popView) {
        mContext = context;
        mTouchLocation = touchLocation;
        mPopupView = popView;
        initFloatView(context);
        initPopupWindow(mPopupView);
    }

    public FloatWindow(Context context, View popView) {
        mContext = context;
        mPopupView = popView;
        initFloatView(context);
        initPopupWindow(mPopupView);
    }

    /**
     * 第一种得到弹窗的方法
     *
     * @param context，上下文对象
     * @param touchLocation，touch按钮所在位置
     * @param popView，弹窗内容
     * @return
     */
    public static FloatWindow getFloatWindow(Context context, int touchLocation, View popView) {
        if (mFloatWindow == null) {
            synchronized (FloatWindow.class) {
                if (mFloatWindow == null) {
                    initFloatViewWindow(context);
                    resolveTouchLocation(touchLocation);
                    mFloatWindow = new FloatWindow(context, touchLocation, popView);
                }
            }
        } else {
            resolveTouchLocation(touchLocation);
        }
        return mFloatWindow;
    }

    /**
     * 第二种得到弹窗的方法，要结合setTouchLocation一起用，这样才会有显示位置
     *
     * @param context，上下文对象
     * @param popView，弹窗内容
     * @return
     */
    public static FloatWindow getFloatWindow(Context context, View popView) {
        if (mFloatWindow == null) {
            synchronized (FloatWindow.class) {
                if (mFloatWindow == null) {
                    initFloatViewWindow(context);
                    mFloatWindow = new FloatWindow(context, popView);
                }
            }
        }
        return mFloatWindow;
    }

    /**
     * 解析touchButton位置
     *
     * @param touchLocation，touch按钮所在位置
     */
    private static void resolveTouchLocation(int touchLocation) {
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
    }

    /**
     * 设置touch按钮位置
     *
     * @param touchLocation，touch按钮位置
     */
    public FloatWindow setTouchLocation(int touchLocation) {
        if (mFloatWindow == null)
            throw new IllegalStateException("FloatWindow can not be null");
        mTouchLocation = touchLocation;
        resolveTouchLocation(touchLocation);
        return mFloatWindow;
    }

    /**
     * 设置弹窗内容
     *
     * @param popupView，弹窗view
     * @return
     */
    public FloatWindow setPopupView(View popupView) {
        if (mFloatWindow == null)
            throw new IllegalStateException("FloatWindow can not be null");
        mPopupView = popupView;
        mPopupWindow.setContentView(popupView);
        return mFloatWindow;
    }

    /**
     * 设置TouchButton图片
     *
     * @param bitmap，图片源
     * @return
     */
    public FloatWindow setTouchImageBitmap(Bitmap bitmap) {
        if (mFloatWindow == null)
            throw new IllegalStateException("FloatWindow can not be null");
        if (bitmap == null)
            throw new NullPointerException("Bitmap can not be null");
        mFloatView.setTouchImageBitmap(bitmap);
        return mFloatWindow;
    }

    public FloatWindow setTouchImageDrawable(Drawable drawable) {
        if (mFloatWindow == null)
            throw new IllegalStateException("FloatWindow can not be null");
        if (drawable == null)
            throw new NullPointerException("Drawable can not be null");
        mFloatView.setTouchImageDrawable(drawable);
        return mFloatWindow;
    }

    public FloatWindow setTouchImageResource(int resId) {
        if (mFloatWindow == null)
            throw new IllegalStateException("FloatWindow can not be null");
        if (resId == 0)
            throw new NullPointerException("ResourceId can not be null");
        mFloatView.setTouchImageResource(resId);
        return mFloatWindow;
    }

    public FloatWindow setTouchImageUri(Uri uri) {
        if (mFloatWindow == null)
            throw new IllegalStateException("FloatWindow can not be null");
        if (uri == null)
            throw new NullPointerException("Uri can not be null");
        mFloatView.setTouchImageURI(uri);
        return mFloatWindow;
    }

    public void show() {
        attachFloatViewToWindow();
    }

    public void dismissPopupWindow() {
        if (mPopupWindow != null)
            mPopupWindow.dismiss();
    }

    /**
     * attach floatView to window
     */
    private static void attachFloatViewToWindow() {
        if (mFloatView == null)
            throw new IllegalStateException("FloatView can not be null");
        if (mParams == null)
            throw new IllegalStateException("WindowManager.LayoutParams can not be null");
        try {
            mWindowManager.updateViewLayout(mFloatView, mParams);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            //if floatView not attached to window,addView
            mWindowManager.addView(mFloatView, mParams);
        }
    }

    /**
     * 初始化initFloatViewWindow参数
     *
     * @param context，上下文对象
     */
    private static void initFloatViewWindow(Context context) {
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
        mParams.format = PixelFormat.RGBA_8888;
    }

    /**
     * 初始化touch按钮所在window
     *
     * @param context，上下文对象
     */
    private void initFloatView(Context context) {
        mFloatView = new FloatView(context, new OnFlingListener() {

            @Override
            public void onSlideUp() {
                if (mTouchLocation != LOCATION_BOTTOM)
                    return;
                Log.e(TAG, "显示向上的popupwindow");
                ImageView touchButton = mFloatView.findViewById(R.id.touchBt);
                touchButton.setVisibility(View.GONE);
                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(mFloatView, mParams);
                mPopupWindow.setAnimationStyle(R.style.AnimationTopFade);
                mPopupWindow.showAtLocation(mFloatView, Gravity.TOP, 0, 0);
            }

            @Override
            public void onSlideLeft() {
                if (mTouchLocation != LOCATION_RIGHT)
                    return;
                Log.e(TAG, "显示向左的popupwindow");
                ImageView touchButton = mFloatView.findViewById(R.id.touchBt);
                touchButton.setVisibility(View.GONE);
                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(mFloatView, mParams);
                mPopupWindow.setAnimationStyle(R.style.AnimationLeftFade);
                mPopupWindow.showAtLocation(mFloatView, Gravity.TOP, 0, 0);
            }

            @Override
            public void onSlideRight() {
                if (mTouchLocation != LOCATION_LEFT)
                    return;
                Log.e(TAG, "显示向右的popupwindow");
                ImageView touchButton = mFloatView.findViewById(R.id.touchBt);
                touchButton.setVisibility(View.GONE);
                mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(mFloatView, mParams);
                mPopupWindow.setAnimationStyle(R.style.AnimationRightFade);
                mPopupWindow.showAtLocation(mFloatView, Gravity.TOP, 0, 0);
            }
        });
    }

    /**
     * 初始化弹窗
     *
     * @param contentView，弹窗内容
     */
    private void initPopupWindow(View contentView) {
        int height = DeviceUtils.getScreenHeight(mContext) + DeviceUtils.getNavigationBarHeight(mContext);
        mPopupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, height);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.colorPopWindow));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setClippingEnabled(false);
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