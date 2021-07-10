# Learn
## 710
#### 安卓
复习了初级控件: 像素 线性布局Linearlayout 滚动视图Scrollview 文本视图textview 按钮button imageview imagebutton 图形Drawable<br>
button是基于textview imagebutton基于imageview<br>
button可显示文本和图形 imagebutton只能显示图形<br>
![image](https://user-images.githubusercontent.com/56183443/125168254-2d59a500-e1d7-11eb-9e43-e20c3dab85d0.png)<br>
```
<Button
        android:id="@+id/btn_icon"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="10dp"
        android:drawableLeft="@mipmap/ic_launcher"
        android:drawablePadding="5dp"
        android:text="热烈欢迎"
        android:textColor="#000000"
        android:textSize="17sp" />
```
水平分割线
```
    <View
        android:id="@+id/v_line"
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="#000000"/>
```
学习指数：⭐
