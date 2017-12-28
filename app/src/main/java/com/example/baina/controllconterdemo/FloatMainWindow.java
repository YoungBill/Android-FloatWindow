package com.example.baina.controllconterdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class FloatMainWindow implements GestureDetector.OnGestureListener {

    private static final String TAG = FloatMainWindow.class.getSimpleName();
    private static final int LIMIT_X = 50;
    private static final int LIMIT_Y = 50;
    public static final int LOCATION_LEFT = 0x001;
    public static final int LOCATION_RIGHT = 0x002;
    public static final int LOCATION_BOTTOM = 0x003;
    private static FloatMainWindow floatMainWindow;
    private static int mImageLocation = LOCATION_LEFT;

    private MainActivity mContext;
    private static View mView;
    private ImageView mImageView;
    private static WindowManager.LayoutParams mParams = null;
    private static WindowManager mWindowManager = null;
    private GestureDetector mGestureDetector;
    private int mDirection;
    private PopupWindow mPopupWindow;

    public FloatMainWindow(MainActivity context, View view) {
        mContext = context;
        mView = view;
        showWindow(context);
        mGestureDetector = new GestureDetector(context, this);
        popupWindowInit();
    }

    public FloatMainWindow(MainActivity context, View view, int imageLocation) {
        mContext = context;
        mView = view;
        mImageLocation = imageLocation;
        showWindow(context);
        mGestureDetector = new GestureDetector(context, this);
        popupWindowInit();
    }

    public static FloatMainWindow getFloatMessagerMainWindow(MainActivity context, View view, int imageLocation) {
//        floatMainWindow = new FloatMainWindow(context, view, imageLocation);
        mImageLocation = imageLocation;
        if (floatMainWindow == null) {
            synchronized (FloatMainWindow.class) {
                if (floatMainWindow == null) {
                    floatMainWindow = new FloatMainWindow(context, view, imageLocation);
                }
            }
        } else {
            switch (mImageLocation) {
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
            mWindowManager.updateViewLayout(mView, mParams);
        }
        return floatMainWindow;
    }

    private void popupWindowInit() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_pop, null);
        mPopupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.colorSelf));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowManager.removeViewImmediate(mView);
                mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                mWindowManager.addView(mView, mParams);
            }
        });
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
        mImageView = mView.findViewById(R.id.touchBt);
        switch (mImageLocation) {
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
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "image=========", Toast.LENGTH_SHORT).show();
//                View view = LayoutInflater.from(context).inflate(R.layout.float_pople_room_layout, null);
//                FloatMessagePopleDialog.getInstance(context, R.style.webviewTheme).setContextView(view);
            }
        });
//        floatView = new AVCallFloatView(context);
//        floatView.setParams(mParams);
//        floatView.setIsShowing(true);
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        mWindowManager.addView(mView, mParams);
    }

    private int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.1f);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG, "onDown");
        Toast.makeText(mContext, "image=========", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onFling");
        //手指划过屏幕的横向距离

        float x = motionEvent1.getX() - motionEvent.getX();
        //手指划过屏幕的纵向距离
        float y = motionEvent1.getY() - motionEvent.getY();

        //取得横向距离的绝对值
        float x_abs = Math.abs(x);
        //取得纵向距离的绝对值
        float y_abs = Math.abs(y);

        //屏幕1/5的距离
//        float x_limit = screen.widthPixels / 5;
//        float y_limit = screen.heightPixels / 5;

        //手指在屏幕上横向滑动
        if (x_abs >= y_abs) {
            if (x > LIMIT_X || x < -LIMIT_X) {
                if (x > 0) {
                    //向右移动
                    mDirection = Direction.RIGHT.ordinal();
                    Log.e(TAG, "Direction Right");
                } else if (x < 0) {
                    //向左移动
                    mDirection = Direction.LEFT.ordinal();
                    Log.e(TAG, "Direction Left");
                }
            }
            initPopWindow();
        }
        //竖向滑动
        else {
            if (y > LIMIT_Y || y < -LIMIT_Y) {
                if (y > 0) {
                    //向下
                    mDirection = Direction.TOP.ordinal();
                    Log.e(TAG, "Direction Down");
                } else if (y < 0) {
                    //向上
                    mDirection = Direction.BOTTOM.ordinal();
                    Log.e(TAG, "Direction Up");
                }
                initPopWindow();
            }
        }
        return false;
    }

    private void initPopWindow() {
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowManager.updateViewLayout(mView, mParams);
        if (Direction.LEFT.ordinal() == mDirection) {
            mPopupWindow.setAnimationStyle(R.style.AnimationLeftFade);
        }
        if (Direction.RIGHT.ordinal() == mDirection) {
            mPopupWindow.setAnimationStyle(R.style.AnimationRightFade);
        }
        if (Direction.BOTTOM.ordinal() == mDirection) {
            mPopupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        }
        if (Direction.TOP.ordinal() == mDirection) {
            mPopupWindow.setAnimationStyle(R.style.AnimationTopFade);
        }

        if (Direction.LEFT.ordinal() == mDirection) {
            mPopupWindow.showAtLocation(mView,
                    Gravity.END, 0, 0);

            Log.e(TAG, "显示向左的Popuwindow");
        } else if (Direction.RIGHT.ordinal() == mDirection) {
            mPopupWindow.showAtLocation(mView,
                    Gravity.START, 0, 0);

            Log.e(TAG, "显示向右的Popuwindow");

        } else if (Direction.BOTTOM.ordinal() == mDirection) {
            mPopupWindow.showAtLocation(mView,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            Log.e(TAG, "显示向下的Popuwindow");
        } else if (Direction.TOP.ordinal() == mDirection) {
            mPopupWindow.showAtLocation(mView,
                    Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }

    private enum Direction {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}