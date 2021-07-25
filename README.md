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
* 可以设置onClick点击事件，不过要点击两次（先获得焦点，再是点击），想实现只点击一次可以设置OnFocusChangeListener监听器，重写onFocusChange方法
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
###### 学习指数：⭐

## 718
#### JAVA
###### [super和this的区别](https://www.jianshu.com/p/b5b74099ea35)
###### 学习指数：⭐

## 719
#### JAVA
###### 包package
![20210719161113](https://raw.githubusercontent.com/Alleyoops/Image/main/20210719161113.png)
###### 封装和隐藏
![20210719161138](https://raw.githubusercontent.com/Alleyoops/Image/main/20210719161138.png)
* 当字段为 public 时，其他对象可以直接访问该字段并对其进行修改，而不会被拥有该字段的对象检测到。通过使用 private 属性封装该字段，可以禁止对字段的直接访问
###### 访问权限修饰符
![20210719174623](https://raw.githubusercontent.com/Alleyoops/Image/main/20210719174623.png)
* 在同一个java文件中可以写多个class，但是只有一个public的，其他的class只能是缺省(default)的
###### 构造方法
* 构造器也叫构造方法
* 默认的构造方法前面有没有访问的修饰符跟定义的类有关，类是public的，默认的构造方法就是public，默认的类是缺省的，默认的构造方法就是缺省的
###### 注解annotation
![20210719230456](https://raw.githubusercontent.com/Alleyoops/Image/main/20210719230456.png)
* 基本注解
* 自定义注解
###### 学习指数：⭐

## 720
#### 安卓
###### 共享参数SharedPreference
###### 数据库SQLite
```
public class UserDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "UserDBHelper";
    private static final String DB_NAME = "user.db"; // 数据库的名称
    private static final int DB_VERSION = 1; // 数据库的版本号
    private static UserDBHelper mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例
    public static final String TABLE_NAME = "user_info"; // 表的名称
    //重写构造方法(private)
    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    //重载构造方法
    private UserDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }
    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new UserDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }
···
}
```

#### JAVA
###### 单例模式
* 单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。<br>
1、单例类只能有一个实例。<br>
2、单例类必须自己创建自己的唯一实例。<br>
3、单例类必须给所有其他对象提供这一实例。<br>

步骤一：创建一个 Singleton 类。
```
//SingleObject.java
public class SingleObject {
   //创建 SingleObject 的一个对象
   private static SingleObject instance = new SingleObject();
   //让构造函数为 private，这样该类就不会被实例化
   private SingleObject(){}
   //获取唯一可用的对象
   public static SingleObject getInstance(){
      return instance;
   }
   public void showMessage(){
      System.out.println("Hello World!");
   }
}
```
步骤二：从 singleton 类获取唯一的对象。
```
//SingletonPatternDemo.java
public class SingletonPatternDemo {
   public static void main(String[] args) {
 
      //不合法的构造函数
      //编译时错误：构造函数 SingleObject() 是不可见的
      //SingleObject object = new SingleObject();
 
      //获取唯一可用的对象
      SingleObject object = SingleObject.getInstance();
 
      //显示消息
      object.showMessage();
   }
}
```
###### 学习指数：⭐⭐

## 722
#### JAVA
###### file类
```
//下面的实例演示了File对象的使用：
import java.io.File;
public class DirList {
    public static void main(String args[]) {
        String dirname = "/java";
        File f1 = new File(dirname);
        if (f1.isDirectory()) {
            System.out.println("Directory of " + dirname);
            String s[] = f1.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(dirname + "/" + s[i]);
                if (f.isDirectory()) {
                    System.out.println(s[i] + " is a directory");
                } else {
                    System.out.println(s[i] + " is a file");
                }
            }
        } else {
            System.out.println(dirname + " is not a directory");
        }
    }
}
//以上实例编译运行结果如下：
Directory of /java
bin is a directory
lib is a directory
demo is a directory
test.txt is a file
README is a file
index.html is a file
include is a directory
```
###### Java.io.FilenameFilter文件名过滤器
[FilenameFilter使用方法介绍](https://blog.csdn.net/u013592116/article/details/73180033)
* (1)String[] fs = f.list();
* (2)File[] fs = f.listFiles();<br>
这两个方法返回f下的所有文件或目录；
FilenameFilter用来把符合要求的文件或目录返回；
因此可以调用：<br>
* (1)String []fs = f.list(FilenameFilter filter);
* (2)File[]fs = f.listFiles(FilenameFilter filter);
```
//FilenameFilter的使用举例
//System.getProperty("catalina.home")获取到Tomcat的根路径，比如：D:\Program Files (x86)\tomcat\apache-tomcat-7.0.70-windows-x86\apache-tomcat-7.0.70

File dataDirectory = new File(System.getProperty("catalina.home") + File.separator + "test" + File.separator + "conf");// 这里获取的是：D:\Program Files (x86)\tomcat\apache-tomcat-7.0.70-windows-x86\apache-tomcat-7.0.70\test\conf
        // 数据源文件必定要是properties类型。 
        File[] dataFiles = dataDirectory.listFiles(new FilenameFilter() {
//以config_db开头，文件后缀为.properties的将被选出来，其余被过滤掉
            @Override
            public boolean accept(File dir, String name) {
                String fileName = name.toLowerCase();

                if (fileName.endsWith(".properties")) {
                    if(fileName.startsWith("config_db")) {
                        return true;
                    }
                }
                return false;
            }
        });
```
###### “==”和“.equals”
* `==`值相等
* `.equals`内容相等
###### Map.entrySet()和Map.Entry()
[遍历map的四种方法及Map.entry详解](https://blog.csdn.net/gm371200587/article/details/82108372)
* Map.entrySet()这个方法返回的是一个`Set<Map.Entry<K,V>>`
* Map.Entry()是Map中的一个接口，它的用途是表示一个`映射项`（里面有Key和Value），而Set<Map.Entry<K,V>>表示一个映射项的Set。Map.Entry里有相应的getKey和getValue方法，即JavaBean，让我们能够从一个项中取出Key和Value。

#### 安卓
###### SD卡文件操作
###### 应用Application基础
* Application是安卓的一大组件，在app运行过程中有且只有一个Application对象贯穿整个生命周期
* 默认的Application节点没有指定name属性
* 利用Application操作全局变量
    * C/C++有全局变量，因为全局变量保存在内存中，所以操作全局变量就是操作内存，内存的读写速度远比读写数据库或读写文件快得多。全局的意思是其他代码都可以引用该变量，因此全局变量是共享数据和消息传递的好帮手。不过，Java 没有全局变量的概念。与之比较接近的是类里面的静态成员变量，该变量可被外部直接引用，并且在不同地方引用的值是一样的(前提是在引用期间不能修改该变量的值),所以可以借助静态成员变量实现类似全局变量的功能。
    * 知道 Application 的生命周期。其生命周期覆盖 App 运行的全过程。不像短暂的 Activity 生命周期，只要进入别的页面，原页面就被停止或销毁。因此,通过利用 Application 的持久存在性可以在 Application 对象中保存全局变量。适合在 Application 中保存的全局变量主要有下面 3 类数据：
        * 会频繁读取的信息，如用户名、手机号等。

        * 从网络上获取的临时数据，为节约流量、减少用户等待时间，想暂时放在内存中供下次使用，如 logo、商品图片等。

        * 容易因频繁分配内存而导致内存泄漏的对象，如 Handler 对象等。
###### xmlns:
* 它是 XML 文档中的一个概念：英文叫做 XML namespace，中文翻译为 XML 命名空间
###### android四大组件
* 页面Activity
* 广播Broadcast
* 服务Service
* 内容提供器ContentProvider
###### 内容提供与处理
* 内容提供器是跟数据存储有关的组件，完整的`内容组件`由以下三部分组成：
    * 内容提供器ContentProvider
    * 内容解析器ContentResolver
    * 内容观察器ContentObserver
###### 学习指数：⭐⭐

## 724
#### JAVA
###### 静态块（static{}）
[Java static关键字与static{}语句块](https://blog.csdn.net/qq_41647999/article/details/87966487)<br>
（1） static关键字还有一个比较关键的作用，用来形成静态代码块（static{}(即static块)）以`优化程序性能`。

（2） static块可以置于类中的任何地方，类中可以有多个static块。

（3） 在类初次被加载的时候执行且`仅会被执行一次`（这是优化性能的原因！！！），会按照static块的顺序来执行每个static块，一般用来初始化静态变量和调用静态方法。
#### 安卓
###### android:exported和android:enabled
[android:exported、enabled属性](https://blog.csdn.net/sandalphon4869/article/details/87703775)
* android:exported 
    * 是Android中的四大组件 Activity，Service，Provider，Receiver 四大组件中都会有的一个属性。
    * 代表是否能被其他应用隐式调用。
* android:enabled 
    * 是否可以被系统实例化，默认为 true因为父标签 也有 enable 属性，所以必须两个都为默认值 true 的情况下服务才会被激活，否则不会激活。  
###### ContentProvider
* [ContentProvider,SQLiteOpenHelper,SQLiteDatabase三者之间的区别和联系](https://blog.csdn.net/androidzhaoxiaogang/article/details/8180628)
* [为什么有contentprovider](https://zhidao.baidu.com/question/1640151995493632780.html)
* ContentProvider应用程序间非常通用的共享数据的一种方式，也是Android官方推荐的方式。Android中许多系统应用都使用该方式实现数据共享，比如通讯录、短信等。但我遇到很多做Android开发的人都不怎么使用它，觉得直接读取数据库会更简单方便，设计用意在于：[参考链接](https://www.jianshu.com/p/5c2f1f9cc419)
    * 1、封装，对数据进行封装，提供统一的接口，`使用者完全不必关心这些数据是在DB，XML、Preferences或者网络请求来的`。当项目需求要改变数据来源时，使用我们的地方完全不需要修改。（当然，要结合其他两个内容组件来实现）
    * 2、提供一种跨进程数据共享的方式
###### 学习指数：⭐

## 725
#### 安卓
###### 菜单Menu