# Learn

## 710
#### 安卓
* 复习了初级控件: 像素 线性布局Linearlayout 滚动视图Scrollview 文本视图textview 按钮button imageview imagebutton 图形Drawable<br>
* button是基于textview imagebutton基于imageview<br>
* button可显示文本和图形 imagebutton只能显示图形<br>
* 九宫格点九图片，扩展名为.png,文件名后常带有“.9”字样<br>
* ![image](https://user-images.githubusercontent.com/56183443/125168254-2d59a500-e1d7-11eb-9e43-e20c3dab85d0.png)
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
* 水平分割线
```
<View
        android:id="@+id/v_line"
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="#000000"/>
```
###### 学习指数：⭐

## 711
#### 安卓
###### 相对布局relativelayout
* 相对指定视图或相对上级视图
* 添加新视图的函数例子
```
    // 通过代码在相对布局下面添加新视图，referId代表参考对象的编号
    private void addNewView(int firstAlign, int secondAlign, int referId) {
        // 创建一个新的视图对象
        View v = new View(this);
        // 把该视图的背景设置为半透明的绿色
        v.setBackgroundColor(0xaa66ff66);
        // 声明一个布局参数，其中宽度为100p，高度也为100dp
        RelativeLayout.LayoutParams rl_params = new RelativeLayout.LayoutParams(
                Utils.dip2px(this, 100), Utils.dip2px(this, 100));
        // 给布局参数添加第一个相对位置的规则，firstAlign代表位置类型，referId代表参考对象
        rl_params.addRule(firstAlign, referId);
        if (secondAlign >= 0) {
            // 如果存在第二个相对位置，则同时给布局参数添加第二个相对位置的规则
            rl_params.addRule(secondAlign, referId);
        }
        // 给该视图设置布局参数
        v.setLayoutParams(rl_params);
        // 往相对布局中添加该视图
        rl_content.addView(v);
        // 设置该视图的长按监听器
        v.setOnLongClickListener(new OnLongClickListener() {
            // 在用户长按该视图时触发
            public boolean onLongClick(View vv) {
                // 一旦监听到长按事件，就从相对布局中删除该视图
                rl_content.removeView(vv);
                return true;
            }
        });
    }
```
###### 框架布局framelayout<br>
* 其下级视图无法指定所处的位置，只能统统从上级framelayout的左上角开始添加，并且后面的子视图会把之前的子视图覆盖掉。一般用于需要重叠显示的场合
* foreground：指定布局的前景图像，最顶层，不会被覆盖
* foregroundGravity：前景图像的对齐方式，同gravity
###### 特殊按钮
* 复选框Checkbox
* 开关按钮switch
* 单选按钮Radiobutton<br>
 	* 基于RadioGroup容器
 	* radiogroup实质上是个布局，内可含textview，imageview等
	* 只能单选，再次点击同一按钮不会取消选中	
###### 学习指数：⭐

## 712
#### 安卓
###### 下拉框spinner
* 配合适配器使用：ArrayAdapter SimpleAdapter
* ArrayAdapter只能显示文本列表，SimpleAdapter更高级，所以并不simple，下面是Arrayadpter的例子
```
    // 初始化下拉框
    private void initSpinner() {
        // 声明一个下拉列表的数组适配器
        /*R.layout.item_select指定下拉框当前文本的样式，
        这个布局文件内只有一个Textview，
        定义了当前选中文本(注意是“选中”)的大小颜色对齐方式等属性*/
```
![image](https://user-images.githubusercontent.com/56183443/125267994-dcae8d00-e339-11eb-8652-fabe2a39c9e8.png)
```
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, starArray);
        // 设置数组适配器的布局样式
        /*定义下拉列表的文本样式，里面的Textview定义了列表中(注意是“列表中”)的文本属性*/
```
![image](https://user-images.githubusercontent.com/56183443/125268057-ecc66c80-e339-11eb-9a64-13a8377b85ed.png)
```
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        // 从布局文件中获取名叫sp_dialog的下拉框
        Spinner sp = findViewById(R.id.sp_dialog);
        // 设置下拉框的标题
        sp.setPrompt("请选择行星");
        // 设置下拉框的数组适配器
        sp.setAdapter(starAdapter);
        // 设置下拉框默认显示第一项
        sp.setSelection(0);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.setOnItemSelectedListener(new MySelectedListener());
    }
    // 定义下拉列表需要显示的文本数组
    private String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};
    // 定义一个选择监听器，它实现了接口OnItemSelectedListener
    class MySelectedListener implements OnItemSelectedListener {
        // 选择事件的处理方法，其中arg2代表选择项的序号
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Toast.makeText(SpinnerDialogActivity.this, "您选择的是" + starArray[arg2], Toast.LENGTH_LONG).show();
        }
        // 未选择时的处理方法，通常无需关注
        public void onNothingSelected(AdapterView<?> arg0) {}
    }
```
###### 文本编辑框EditText
* 获取编辑框最大长度
```
	// 获取编辑框的最大长度，通过反射机制调用隐藏方法
    public static int getMaxLength(EditText et) {
        int length = 0;
        try {
            InputFilter[] inputFilters = et.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
```
* 监控当前已输入文本长度：文本监听器接口TextWatcher，提供三个接口方法（不触发则可不写具体触发内容）
	* beforeTextChanged 在文本改变之前触发
	* onTextChanged 在文本改变时触发
	* afterTextChanged 在文本改变之后触发
* 两种关闭软键盘的方式
```
	public static void hideAllInputMethod(Activity act) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) { // 软键盘如果已经打开则关闭之
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    	public static void hideOneInputMethod(Activity act, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
```
* 输入回车自动跳转下一编辑框
```
	 // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            // 发现输入回车符或换行符
            if (str.contains("\r") || str.contains("\n")) {
                // 去掉回车符和换行符
                mThisView.setText(str.replace("\r", "").replace("\n", ""));
                if (mNextView != null) {
                    // 让下一个视图获得焦点，即将光标移到下个视图
                    mNextView.requestFocus();
                    // 如果下一个视图是编辑框，则将光标自动移到编辑框的文本末尾
                    if (mNextView instanceof EditText) {
                        EditText et = (EditText) mNextView;
                        // 让光标自动移到编辑框内部的文本末尾
                        // 方式一：直接调用EditText的setSelection方法
                        et.setSelection(et.getText().length());
                        // 方式二：调用Selection类的setSelection方法
                        //Editable edit = et.getText();
                        //Selection.setSelection(edit, edit.length());
                    }
                }
            }
        }
    }
```
###### 自动完成编辑框AutoCompleteTextview
* 实现原理：EditText结合监听器Textwatcher与下拉列表Spinner
###### 活动Activity
* 生命周期：跳转前的页面先调用onPause方法，然后跳转后的页面依次调用onCreate/omRestart->onStart->onResume，最后`跳转前的页面调用onStop方法`（若返回上级页面，则下级页面还需调用onDestroy方法） 
* 横竖屏切换：无论横屏切换竖屏还是竖屏切换到横屏，都是原屏幕的页面从onPause到onStop再到onDestroy一路销毁，然后新屏幕的页面从onCreate到onStart再到onResume一路创建而来 
* 隐式intent：如拨号路径
```
if (v.getId() == R.id.btn_call) { // 点击了直接拨号按钮
            // 拨号功能还需在AndroidManifest.xml中添加拨号权限配置
            Intent intent = new Intent(); // 创建一个新意图
            intent.setAction(Intent.ACTION_CALL); // 设置意图动作为直接拨号
            // 声明一个拨号的Uri
            /*"tel:"是规定好的，例如：
            Uri myBlogUri = Uri.parse("http://xxxxx.com");
            Uri mapUri = Uri.parse("geo:38.899533,-77.036476");
            Uri playUri = Uri.parse("file:///sdcard/download/everything.mp3");
            ····
             */
            Uri uri = Uri.parse("tel:" + phone);
            intent.setData(uri); // 设置意图前往的路径
            startActivity(intent); // 启动意图通往的活动页面
        } else if ···
```
* Activity间传递参数：putExtras Bundle setResult onActivityResult onActivityForResult ···
###### 文本工具TextUtils
###### 提醒对话框AlertDialog
* create()方法
* show()方法
###### 学习指数：⭐⭐

## 713
#### JAVA
###### ![image](https://user-images.githubusercontent.com/56183443/125480917-c87119e8-c00c-47ed-a37c-dba1d7e51c12.png)
###### ![image](https://user-images.githubusercontent.com/56183443/125481275-edc20141-c883-422a-b2ca-a3985fc40977.png)
###### ![image](https://user-images.githubusercontent.com/56183443/125481451-74bd48ac-e7ac-4014-bd29-88553b6bcdfc.png)
* 短路与`&&`，短路或`||`
* 与`&`，或`|`根据两边的类型是数还是布尔来决定是`与`和`或` 还是`按位与`和`按位或`
###### ![image](https://user-images.githubusercontent.com/56183443/125483048-0cde1bb0-97b3-454e-91a9-21d3c07c4970.png)
###### ![image](https://user-images.githubusercontent.com/56183443/125483210-3e2bf6ce-35bf-4ed5-b115-29ee1568a1d7.png)
#### 科目一
###### 学习指数：⭐

## 714
#### 科目一
###### 学习指数：⭐

## 715
#### 科目一考试
###### 学习指数：⭐

## 716
#### 安卓
###### 实战登录app
![20210716221929](https://raw.githubusercontent.com/Alleyoops/Image/main/20210716221929.png)
###### AlertDialog()的使用方法
* 先创建一个建造器builder，然后对建造器设置，要用到create()方法，然后show()
```
AlertDialog.Builder builder = new AlertDialog.Builder(this);
builder.setTitle("请记住验证码");
builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
builder.setPositiveButton("好的", null);
AlertDialog alert = builder.create();
alert.show();
```     
* 直接new一个建造器，不用create(),直接show()
```
new AlertDialog.Builder(this)
        .setTitle("请记住验证码")
        .setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码")
        .setPositiveButton("OK",null)//点击ok没有事件相应
        .show();
```
###### 学习指数：⭐⭐

## 717
#### 安卓
###### finish()方法
![20210717005533](https://raw.githubusercontent.com/Alleyoops/Image/main/20210717005533.png)
#### JAVA
![20210717231127](https://raw.githubusercontent.com/Alleyoops/Image/main/20210717231127.png)
![20210717231240](https://raw.githubusercontent.com/Alleyoops/Image/main/20210717231240.png)
![20210717231443](https://raw.githubusercontent.com/Alleyoops/Image/main/20210717231443.png)