# FloatWindow

## readme-中文
Android自定义悬浮窗
<br/>
原理很简单，就是借用了WindowManager这个管理类来实现的。
<br/>

**1. 首先在AndroidManifest.xml中添加使用权限：**
<br/>
```uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"```
<br/>

**2.二行代码实现自定义弹窗view**
```
View contentView = inflater.inflate(R.layout.layout_pop, null);
NewFloatMainWindow.getFloatMainWindow(MainActivity.this, NewFloatMainWindow.LOCATION_LEFT, contentView);
```
**3.实现悬浮窗如此简单**

## readme-en
A custom floating window for Android.

The theory is quite simple, and the implemention borrows from the WindowManager class.
<br/>

**1. First, allow permission to use AndroidManifest.xml:uses-permission android:**

```name = "android.permission.SYSTEM_ALERT_WINDOW"```
<br/>

**2. The following lines of code will implement your custom pop-up:**

```
viewView contentView = inflater.inflate (R.layout.layout_pop, null);
NewFloatMainWindow.getFloatMainWindow (MainActivity.this, NewFloatMainWindow.LOCATION_LEFT, contentView
```

**3. Pop-up window complete**
## preview
<img src="https://github.com/YoungBill/FloatWindow/blob/master/Screenshots/preview.gif"/><br/>
## 结束 
