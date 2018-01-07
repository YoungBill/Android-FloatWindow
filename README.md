# FloatWindow

## readme-中文
Android自定义悬浮窗
<br/>
原理很简单，就是借用了WindowManager这个管理类来实现的。
<br/>
1.首先在AndroidManifest.xml中添加使用权限：
<br/>
uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"
<br/>
2.二行代码实现自定义弹窗view
<br/>
View contentView = inflater.inflate(R.layout.layout_pop, null);
<br/>
NewFloatMainWindow.getFloatMainWindow(MainActivity.this, NewFloatMainWindow.LOCATION_LEFT, contentView);
<br/>
## readme-en
Android custom floating window
The principle is very simple, is to borrow WindowManager this management class to achieve.
1.First, add permission to use AndroidManifest.xml:uses-permission android: name = "android.permission.SYSTEM_ALERT_WINDOW"
2.Two lines of code to achieve custom pop-up 
<br/>viewView contentView = inflater.inflate (R.layout.layout_pop, null);
<br/>NewFloatMainWindow.getFloatMainWindow (MainActivity.this, NewFloatMainWindow.LOCATION_LEFT, contentView);
3.Pop-up window easily
## preview
<img src="https://github.com/YoungBill/FloatWindow/blob/master/Screenshots/preview.gif"/><br/>
## 结束 
