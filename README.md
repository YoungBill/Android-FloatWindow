# FloatWindow
Android自定义悬浮窗
<br/>
原理很简单，就是借用了WindowManager这个管理类来实现的。
<br/>
1.首先在AndroidManifest.xml中添加使用权限：
uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"
<br/>
2.二行代码实现自定义弹窗view
<br/>
View contentView = inflater.inflate(R.layout.layout_pop, null);
<br/>
NewFloatMainWindow.getFloatMainWindow(MainActivity.this, NewFloatMainWindow.LOCATION_LEFT, contentView);
<br/>
3.轻松实现弹窗
