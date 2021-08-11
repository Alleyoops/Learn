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
* 选项菜单OptionMenu（手动打开选项菜单可以调用openOptionsMenu()方法）
* 上下文菜单ContextMenu（openContextMenu(v); // 显式打开上下文菜单）
###### 5种存储方式
* 共享参数SharedPreferences
* 数据库SQLite
* SD卡文件
* 全局内存(利用Application)
* 内容提供器ContentProvider
###### 日期时间控件
* 日期选择器DatePicker(Dialog)
* 时间选择器TimePicker(Dialog)
###### ViewHolder,ConvertView
* 要想使用 ListView 就需要编写一个 Adapter 将数据适配到 ListView上，而为了节省资源提高运行效率，一般自定义类 ViewHolder 来减少 findViewById() 的使用以及避免过多地 inflate view，从而实现目标，如：
```
    // 定义一个视图持有者，以便重用列表项的视图资源
    public final class ViewHolder {
        public ImageView iv_icon; // 声明行星图片的图像视图对象
        public TextView tv_name; // 声明行星名称的文本视图对象
        public TextView tv_desc; // 声明行星描述的文本视图对象
    }
```
* [android开发 BaseAdapter的convertView参数是什么意思](https://zhidao.baidu.com/question/423895201122905772.html)<br>
在listview中，每一条item都是一个新的对象，如果的数据源里面有一万条数据的话就要生成一万个item，手机内存直接就溢出了，所以android中出现了回收机制，在运行getView时，会检测是不是有item从屏幕上面滚动出去，如果有一个item从屏幕上方出去了的话，这个item就会被回收，被回收的这个item就会传入convertView，所以convertView中回收的视图何以重复利用<br>
参考：[Android性能：经典ListView适配器convertView缓存及复用机制](https://blog.csdn.net/zhangphil/article/details/78435502)<br>
PS：有点难理解ಥ_ಥ555~
```
    // 获取指定位置的列表项视图
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) { // 转换视图为空
            holder = new ViewHolder(); // 创建一个新的视图持有者
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else { // 转换视图非空
            // 从转换视图中获取之前保存的视图持有者
            holder = (ViewHolder) convertView.getTag();
        }
        Planet planet = mPlanetList.get(position);
        holder.iv_icon.setImageResource(planet.image); // 显示行星的图片
        holder.tv_name.setText(planet.name); // 显示行星的名称
        holder.tv_desc.setText(planet.desc); // 显示行星的描述
        return convertView;
    }
```
###### View和ViewGroup
在Android APP中，所有的用户界面元素都是由View和ViewGroup的对象构成的。View是绘制在屏幕上的用户能与之交互的一个对象。而ViewGroup则是一个用于存放其他View（和ViewGroup）对象的布局容器！ Android为我们提供了一个View和ViewGroup子类的集合，集合中提供了一些常用的输入控件(比如按钮和文本域)和各种各样的布局模式（比如线性或相对布局）<br>
![20210725182848](https://raw.githubusercontent.com/Alleyoops/Image/main/20210725182848.png)
###### 学习指数：⭐

## 726
#### 安卓
###### 列表视图ListView
* 分行展示
* ListView与Spinner类似。<br>
    * 同：将列出的选项先创建成字符串数组资源，再赋值给entries，执行时自动列出数组内容。
    * 异：
        * 1）Spinner等用户按下，才会展开选项内容，ListView在画面上直接列出。
        * 2）选取事件监听器所使用的接口不同。
###### 网格试图GridView
* 分行又分列展示
###### 翻页视图ViewPager(允许页面在水平方向左右滑动)
* 对于 ViewPager 来说，一个页面就是一个项（相当于 ListView 的一个列表项），许多   面组成 ViewPager 的页面项。明确了 ViewPager 的原理类似 ListView 和 GridView，翻页视图的用法也与它俩类似。ListView 和 GridView的适配器使用 BaseAdapter, ViewPager 的适配器使用 PagerAdapter;ListView 和 GridView 的监听器使用 OnltemClickListener，ViewPager 的监听器使用OnPageChangeListener,表示监听页面切换事件。
* 设置页面切换监听器addOnPageChangeListener，实现接口OnPageListener三个方法：
    * onPageScrollStateChanged：页面滑动状态变化时触发
    * onPageScrolled：页面滑动过程中触发
    * onPageSelected：选中页面时，即滑动结束后触发
* 可应用于简单的欢迎页
```
    <!-- 注意翻页视图ViewPager的节点名称要填全路径 -->
    <android.support.v4.view.ViewPager
        android:id="@+id/vp_content"
        android:layout_width="match_parent"
        android:layout_height="400dp" />
```
###### 学习指数：⭐

## 806
#### 安卓
###### 翻页标题栏PagerTitleStrip/PagerTabStrip
* 后者可以点击进行页面切换而已
```
    <!-- 注意翻页视图ViewPager的节点名称要填全路径 -->
    <android.support.v4.view.ViewPager
        android:id="@+id/vp_content"
        android:layout_width="match_parent"
        android:layout_height="400dp">

        <!-- 注意翻页标题栏PagerTitleStrip的节点名称要填全路径 -->
        <android.support.v4.view.PagerTitleStrip
            android:id="@+id/pts_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />
    </android.support.v4.view.ViewPager>
```
###### 碎片Fragment
* Fragment 的出现一方面是为了缓解 Activity 任务过重的问题，另一方面是为了处理在不同屏幕上 UI 组件的布局问题，而且它还提供了一些新的特性（例如 Retainable）来处理一些在 Activity 中比较棘手的问题。
* Fragment 拥有和 Activity 一致的生命周期，它和 Activity 一样被定义为 Controller 层的类。有过中大型项目开发经验的开发者，应该都会遇到过 Activity 过于臃肿的情况，而 Fragment 的出现就是为了缓解这一状况，可以说 它将屏幕分解为多个「Fragment（碎片）」（这句话很重要），但它又不同于 View，它干的实质上就是 Activity 的事情，负责控制 View 以及它们之间的逻辑。
* 将屏幕碎片化为多个 Fragment 后，其实 Activity 只需要花精力去管理当前屏幕内应该显示哪些 Fragments，以及应该对它们进行如何布局就行了。这是一种组件化的思维，用 Fragment 去组合了一系列有关联的 UI 组件，并管理它们之间的逻辑，而 Activity 负责在不同屏幕下（例如横竖屏）布局不同的 Fragments 组合。
* 使用`静态注册`需要注意以下两点:
    * (1)fragment节点必须指定id属性，否则App运行时会报错Must specify unique android:id,android:tag, or have a parent with an id for ***.
    * (2)如果页面代码继承自Activity, Fragment类就必须继承自android.app.Fragment, 不能使用android.support.v4.app.Fragment,否则App运行会报错Trying to instantiate a class \*** that is not a Fragment 或报错java.lang.ClassCastException : \*** cannot be cast toandroid. app.Fragment;如果页面代码继承自AppCompatActivity或FragmentActivity,那么无论是android app.Fragment还是android.support.v4.app.Fragment都可以使用。
* Fragment拥有两种使用方式，即静态注册和`动态注册`。相比静态注册，实际开发中动态注册用得更多。`静态注册在布局文件中直接指定Fagment而动态注册直到在代码中才动态添加Frngmet`。`动态碎片`就是给翻页视图用的，ViewPager 和Fragment是一对好搭档。用到FragmentStatePagerAdapter适配器。
* 很重要的一点：进入第一个Fragment, 实际只加载了第一 页和第二页，并没有加载全部Fragment。这正是Fragment的优越之处，无论当前位于哪一页， 系统都只会加载当前页及相邻的前后两页，总共加载不超过三页。一旦发生页面切换，相邻页面就被加载，非相邻页面就被回收。这么做的好处是节省了宝贵的系统资源，只有用户正在浏览与将要浏览的Fragment才会加载，避免所有Fragment一起加载造成资源浪费，这正是普通ViewPager的缺点。
###### 广播Broadcast
* 用于Android组件之间的灵活通信
* 发送广播
```
    // 创建一个广播事件的意图
    Intent intent = new Intent(BroadcastFragment.EVENT);
    intent.putExtra("seq", arg2);
    intent.putExtra("color", mColorIdArray[arg2]);
    // 通过本地的广播管理器来发送广播
    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
```
* 接受广播
```
    // 定义一个广播接收器，用于处理背景色变更事件
    private class BgChangeReceiver extends BroadcastReceiver {

        // 一旦接收到背景色变更的广播，马上触发接收器的onReceive方法
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                // 从广播消息中取出最新的颜色
                int color = intent.getIntExtra("color", Color.WHITE);
                // 把页面背景设置为广播发来的颜色
                ll_brd_temp.setBackgroundColor(color);
            }
        }
    }
```
```
    // 声明一个背景色变更的广播接收器
    private BgChangeReceiver bgChangeReceiver;
```
```
    @Override
    public void onStart() {
        super.onStart();
        // 创建一个背景色变更的广播接收器
        bgChangeReceiver = new BgChangeReceiver();
        // 创建一个意图过滤器，只处理指定事件来源的广播
        IntentFilter filter = new IntentFilter(BroadcastFragment.EVENT);
        // 注册广播接收器，注册之后才能正常接收广播
        LocalBroadcastManager.getInstance(this).registerReceiver(bgChangeReceiver, filter);
    }
```
```
    @Override
    public void onStop() {
        super.onStop();
        // 注销广播接收器，注销之后就不再接收广播
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bgChangeReceiver);
    }
```
* 定时器AlarmManager
    * 全局定时器，利用系统闹钟定时发送广播
    * 即使app退出，也能自动响应
    * 在AndroidManifest.xml的application节点下增加广播接收器的声明（凡是在AndroidManifest.xml中声明的就叫静态注册，在代码中声明叫动态注册，android9.0后只能动态注册）
```
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_alarm) {
            // 创建一个广播事件的意图
            Intent intent = new Intent(ALARM_EVENT);
            // 创建一个用于广播的延迟意图
            PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            // 从系统服务中获取闹钟管理器
            AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            // 给当前时间加上若干秒
            calendar.add(Calendar.SECOND, mDelay);
            // 开始设定闹钟，延迟若干秒后，携带延迟意图发送闹钟广播
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
            mDesc = DateUtil.getNowTime() + " 设置闹钟";
            tv_alarm.setText(mDesc);
        }
    }
```
`PendingIntent是延迟的意图，只要不是立即传递的消息，都要用PendingIntent，PendingIntent调用了getBroadcast方法，表示这次携带的消息用于发送广播，getBroadcast返回的PendingIntent对象`
```
    // 适配Android9.0开始
    @Override
    public void onStart() {
        super.onStart();
        // 从Android9.0开始，系统不再支持静态广播，应用广播只能通过动态注册
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 创建一个闹钟的广播接收器
            alarmReceiver = new AlarmReceiver();
            // 创建一个意图过滤器，只处理指定事件来源的广播
            IntentFilter filter = new IntentFilter(ALARM_EVENT);
            // 注册广播接收器，注册之后才能正常接收广播
            registerReceiver(alarmReceiver, filter);
        }
    }
```
###### 月份选择器
* android提供了日期选择器DatePicker和时间选择器TimePicker，却没有月份选择器MonthPicker，其实把日期选择器后面的日子隐藏就行了
```
//自定义打包一个widget组件：

package com.example.senior.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
// 由日期选择器派生出月份选择器
public class MonthPicker extends DatePicker {
    public MonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取年月日的下拉列表项
        ViewGroup vg = ((ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(0));
        if (vg.getChildCount() == 3) {
            // 有的机型显示格式为“年月日”，此时隐藏第三个控件
            vg.getChildAt(2).setVisibility(View.GONE);
        } else if (vg.getChildCount() == 5) {
            // 有的机型显示格式为“年|月|日”，此时隐藏第四个和第五个控件（即“|日”）
            vg.getChildAt(3).setVisibility(View.GONE);
            vg.getChildAt(4).setVisibility(View.GONE);
        }
    }
}
```
```
//在布局文件中直接引用：

            <com.example.senior.widget.MonthPicker
                android:id="@+id/mp_month"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:calendarViewShown="false"
                android:datePickerMode="spinner"
                android:gravity="center"
                android:spinnersShown="true" />
```
###### 震动器Vibrator
* 要在AndroiManifest.xml中加上权限
```
    <!-- 震动 -->
    <uses-permission android:name="android.permission.VIBRATE" />
```
```
    // 从系统服务中获取震动管理器
    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    // 命令震动器吱吱个3秒
    vibrator.vibrate(3000);
```
###### 自定义视图
* 声明属性
* 构造对象
    * (1)重写构造函数，初始化该视图的自有属性。
    * (2)重写测量函数onMeasure,计算该视图的宽与高(一般只有复杂视图才重写该函数)。
    * (3)重写绘图函数onL ayout、onDraw、 dispatchDraw, 视情况重写3个中的一个或多个。
* 测量尺寸
    * 文本尺寸测量
    * 图形尺寸测量
    * 布局尺寸测量
* 宽高尺寸的动态调整（重写onMeasure方法）
    * ScrollView本身叫做滚动视图，而列表视图ListView也是可以滚动的，一个滚动视图嵌套另一个也能滚动的视图，那么在双方的重叠区域，上下滑动的手势究竟表示要滚动哪个视图?这就是`滚动冲突`的问题，所以Android 目前的处理对策是:如果ListView的高度被设置为wrap content, 则此时列表视图只显示一行的高度，然后布局内部只支持滚动ScrollView,但是又带来一个新问题，列表视图仅仅显示一行内容
    * 改造列表视图的一个可行方案，便是重写它的测量函数onMeasure,不管布局文件中设定的视图高度为何，都把`列表视图ListView的高度改为最大高度`，即所有列表项高度加起来的总高度。<br>根据以上思路自定义一个扩展自ListView的不滚动列表视图`NoSCrollListView`,它的实现代码如下所示:
    <br>（重写onMeasure函数还可用来让视图适应横竖屏）
```
//自定义的`NoSCrollListView`小组件
package com.example.custom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context) {
        super(context);
    }
    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    // 重写onMeasure方法，以便自行设定视图的高度
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 将高度设为最大值，即所有项加起来的总高度
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
```
```
            <com.example.custom.widget.NoScrollListView
                android:id="@+id/nslv_planet"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="50dp"
                android:dividerHeight="1dp" />
```
* 绘制视图
    * onLayout用于定位子视图在本布局视图中的位置
    * onDraw是最常用的绘图方法，该方法的入参为Canvas画布对象
    * dispatchDraw也是绘图方法，调用在绘制子视图之后，onDraw调用在绘制视图之前
###### 自定义动画
* 任务Runable
    * 实现Runable接口需要重写run函数，在启动Runable实例时就会调用对象的run方法，实际开发中经常利用handler启动Runable任务
```
    private boolean isStarted = false; // 是否开始计数
    private Handler mHandler = new Handler(); // 声明一个处理器对象
    private int mCount = 0; // 计数值
    // 定义一个计数任务
    private Runnable mCounter = new Runnable() {
        @Override
        public void run() {
            mCount++;
            tv_result.setText("当前计数值为：" + mCount);
            // 延迟一秒后重复计数任务
            mHandler.postDelayed(this, 1000);
        }
    };

        @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_runnable) {
            if (!isStarted) { // 不在计数，则开始计数
                btn_runnable.setText("停止计数");
                // 立即启动计数任务
                mHandler.post(mCounter);
            } else { // 已在计数，则停止计数
                btn_runnable.setText("开始计数");
                // 立即取消计数任务
                mHandler.removeCallbacks(mCounter);
            }
            isStarted = !isStarted;
        }
    }
```
###### 自定义对话框
* 窗口Windows
    * App界面附着在窗口Windows上。大至整个活动页面，小至Toast的提示窗口，以及对话框Dialog都建立在窗口上。
    * Windows的5个常用方法：
        * setContentView：设置内容视图
        * setLayout：设置内容视图的宽、高尺寸
        * setGravity：设置内容视图的对齐方式
        * setBackgroundDrawble：设置内容视图的背景
        * findViewById：根据资源ID获取该视图的对象
###### 自定义通知栏
* 通知推送Notification
    * 简单消息
    * 计时消息
    * Android8.0后要求每条通知都区分它的重要性程度
* 进度条ProgressBar
    * 消息通知Notification的setProgress方法是对内置进度条进行操作，不过很多时候进度条会单独使用。
    * 进度条的常用属性
    * 进度条的常用方法
    * progressDrawable进度图形不能用普通图形，只能用层次图形LayerDrawable。层次图形可在XML文件中定义。
* 远程视图RemoteViews
    * 前面介绍Notification 的常用方法时提到setContent 方法可以在设置定制的通知栏视图RemoteViews时取代Builder的默认视图模板。这表示通知栏允许自定义，并且自定义通知栏需要采用远程视图RemoteViews。
    * 与活动页面相比，如果说对话框是一个小型页面，远程视图就是一个小型且简化的页面。简化的意思是功能减少了，限制变多了。虽然RemoteViews与Activity 一样有自己的布局文件，但是RemoteViews的使用权限小了很多。两者的区别主要有:
        * (1) RemoteViews主要用于`通知栏部件`和`桌面部件`，而Activity用于页面。
        * (2) RemoteViews只支持少数几种控件，如TextView、ImageView、Button、ImageButton、ProgressBar、Chronometer(计时器)和AnalogClock(模拟时钟)。
        * (3) RemoteViews不可直接获取和设置控件信息，`只能通过该对象的set方法修改控件信息`。
###### 学习指数：⭐⭐

## 809
#### 安卓
###### 服务Service
* Service是Android的四大组件之一，常用在看不见页面的高级场合，如系统的闹钟服务、通知服务
* Service与Activity相比，不同之处在于没有对应的页面，相同之处在于都有`生命周期`
* 服务Service存在多种启停方式
    * 普通启停
    ```
        @Override
    public int onStartCommand(Intent intent, int flags, int startid) { // 启动服务，Android2.0以上使用
        Log.d(TAG, "测试服务到此一游！");
        refresh("onStartCommand. flags=" + flags);
        return START_STICKY;
    }
    ```
    ```
            // 创建一个通往普通服务的意图
        mIntent = new Intent(this, NormalService.class);
    ```
    ```
        @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) { // 点击了启动服务按钮
            startService(mIntent); // 启动指定意图的服务
        } else if (v.getId() == R.id.btn_stop) { // 点击了停止服务按钮
            stopService(mIntent); // 停止指定意图的服务
        }
    }
    ```
    * 立即绑定
        * 因为绑定的服务可能运行于另一个进程，所以必须定义一个Binder对象用来进行进程间的通信
        ```
        // 创建一个粘合剂对象
        private final IBinder mBinder = new LocalBinder();
        // 定义一个当前服务的粘合剂，用于将该服务黏到活动页面的进程中
        public class LocalBinder extends Binder {
            public BindImmediateService getService() {
                return BindImmediateService.this;
            }
        }
        ```
        * 在Activity 中，绑定/解绑服务的做法与普通方式不同，首先要定义一个ServiceConnection的服务连接对象，然后调用bindService 方法或unbindService方法进行绑定或解绑操作，具体的示例代码如下:
        ```
                private ServiceConnection mFirstConn = new ServiceConnection() {

            // 获取服务对象时的操作
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 如果服务运行于另外一个进程，则不能直接强制转换类型，
                // 否则会报错“java.lang.ClassCastException: android.os.BinderProxy cannot be cast to...”
                mBindService = ((BindImmediateService.LocalBinder) service).getService();
                Log.d(TAG, "onServiceConnected");
            }

            // 无法获取到服务对象时的操作
            public void onServiceDisconnected(ComponentName name) {
                mBindService = null;
                Log.d(TAG, "onServiceDisconnected");
            }
        };
        ```
    * 延迟绑定
        * 延迟绑定与立即绑定的区别在于：延迟绑定是在页面上先通过startService方法启动服务，然后通过bindSerice方法绑定已存在的服务。这样一来，因为启动操作在先，所以解绑操作只能撒销绑定操作，而不能撤销启动操作。由于解绑服务不能停止服务，因此存在再次绑定服务的可能。
        * 延迟绑定与立即绑定两种方式的生命周期区别在于:
            * (1)延迟绑定的首次绑定操作只调用onBind方法，再次绑定只调用onRebind方法(是否允许再次绑定要看上次onUnbind方法的返回值)
            * (2)延迟绑定的解绑操作只调用onUnbind方法。
* 推送服务到前台
    * startForeground：切换到前台，即展示到通知栏
    * stopForeground：停止前台运行
    * 从android9.0开始，要想在服务中调用startForeground方法，需要修改AndroidManifest.xml，添加前台服务权限配置：
    ```
        <!-- 允许前台服务 -->
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    ```
    * 要想让触发的事件作用域服务内部，只能通过广播的方式（getBroadcast方法 instead of getActivity方法）
###### 应用包管理器PackageManager
* 获取已安装应用的应用包信息，包括应用的进程编号、名称、图标以及流量信息。其中，应用包的基本信息可通过PackageManager 与ApplicationInfo 联合获得
* 应用产生的流量数据可通过工具类TrafficStats读取
###### 标签按钮
* 要想高亮显示背景，可通过给background属性设置状态图形;要想高亮显示图标，可通过给drawableTop属性设置状态图形;高亮显示文本也能通过给)textColor属性设置状态图形StateDrawable实现<br>
![20210809212927](https://i.loli.net/2021/08/09/Q9bPAzMKnODsX4y.png)
```
    <!-- 注意这个文本视图的背景、文字颜色和顶部图标都采用了状态图形，使其看起来像个崭新的标签控件 -->
    <TextView
        android:id="@+id/tv_tab_button"
        android:layout_width="100dp"
        android:layout_height="60dp"
        android:padding="5dp"
        android:layout_gravity="center"
        android:gravity="center"
        android:background="@drawable/tab_bg_selector"
        android:text="点我"
        android:textSize="12sp"
        android:textColor="@drawable/tab_text_selector"
        android:drawableTop="@drawable/tab_first_selector" />
```
```
<!-- 在style.xml中定义统一的新风格，只需在节点添加一行style=...即可 -->
    <style name="TabButton">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_height">match_parent</item>
        <item name="android:padding">5dp</item>
        <item name="android:layout_gravity">center</item>
        <item name="android:gravity">center</item>
        <item name="android:background">@drawable/tab_bg_selector</item>
        <item name="android:textSize">12sp</item>
        <item name="android:textStyle">normal</item>
        <item name="android:textColor">@drawable/tab_text_selector</item>
    </style>
```
###### 底部标签栏
* 基于TabActivity的标签栏
    * 提供了TabHost和TabWidget两个控件
    * 由于它们仅用于标签栏，学会套用固定框架就行
    ```
    <!-- 该方式的底部标签栏，根布局必须是TabHost，且id必须为@android:id/tabhost -->
    <TabHost xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@android:id/tabhost"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <!-- 内容页面都挂在这个框架布局下面,FrameLayout必须命名为tabcontent -->
            <FrameLayout
                android:id="@android:id/tabcontent"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginBottom="@dimen/tabbar_height" />

            <!-- 这是例行公事的选项部件，实际隐藏掉了,TabWidget必须命名为tabs-->
            <TabWidget
                android:id="@android:id/tabs"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:visibility="gone" />

            <!-- 下面是事实上的底部标签栏，采取水平线性布局展示 -->
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="@dimen/tabbar_height"
                android:layout_alignParentBottom="true"
                android:gravity="bottom"
                android:orientation="horizontal">

                <!-- 第一个标签控件 -->
                <LinearLayout
                    android:id="@+id/ll_first"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        style="@style/TabButton"
                        android:drawableTop="@drawable/tab_first_selector"
                        android:text="@string/menu_first" />
                </LinearLayout>

                <!-- 第二个标签控件 -->
                <LinearLayout
                    android:id="@+id/ll_second"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        style="@style/TabButton"
                        android:drawableTop="@drawable/tab_second_selector"
                        android:text="@string/menu_second" />
                </LinearLayout>

                <!-- 第三个标签控件 -->
                <LinearLayout
                    android:id="@+id/ll_third"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:orientation="vertical">

                    <TextView
                        style="@style/TabButton"
                        android:drawableTop="@drawable/tab_third_selector"
                        android:text="@string/menu_third" />
                </LinearLayout>
            </LinearLayout>
        </RelativeLayout>
    </TabHost>
    ```
    ```
    //页面代码关键语句：

    private String FIRST_TAG = "first"; // 第一个标签的标识串

    ll_first = findViewById(R.id.ll_first); // 获取第一个标签的线性布局

    ll_first.setOnClickListener(this); // 给第一个标签注册点击监听器
    
    // 往标签栏添加第一个标签，其中内容视图展示TabFirstActivity
    tab_host.addTab(getNewTab(FIRST_TAG, R.string.menu_first,
    R.drawable.tab_first_selector, TabFirstActivity.class));

    changeContainerView(ll_first); // 默认显示第一个标签的内容视图
    ```
    * 该方式核心是`getNewTab`函数
* 基于ActivityGroup的标签栏
    * ActivityGroup就是Activity的组合，允许在内部开启活动页面，即`ActivityGroup与Activity`的关系相当于`Activity与Fragment`的关系
    * 该方式的布局文件与TabActivity方式相比，主要有三处改动：
        * 根布局节点不在采用TabHost，改为LinearLayout
        * 删除了`例行公事`的选型部件`TabWidget`
        * 内容页面由`固定编号的框架布局`改成`自定义编号的线性布局`，且内容页面都挂在这个线性布局下面：
        ```
        <!-- 内容页面都挂在这个线性布局下面 -->
        <LinearLayout
        android:id="@+id/ll_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:gravity="bottom|center"
        android:orientation="horizontal" />
        ```
    * 页面代码的变化集中在如何切换标签页，该方式的核心是`toActivity`函数
* 基于FragmeActivity的标签栏
    * 与ActivityGroup采用一对多个Activity相比，此方式采用的是一对多个Fragment
    * 代码相比前两种比较简洁明了
    ```
        <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <!-- 这是实际的内容框架，内容页面都挂在这个框架布局下面。
            把FragmentLayout放在FragmentTabHost上面，标签栏就在页面底部；
            反之FragmentLayout在FragmentTabHost下面，标签栏就在页面顶部。 -->
        <FrameLayout
            android:id="@+id/realtabcontent"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1" />

        <!-- 碎片标签栏的id必须是@android:id/tabhost -->
        <android.support.v4.app.FragmentTabHost
            android:id="@android:id/tabhost"
            android:layout_width="match_parent"
            android:layout_height="@dimen/tabbar_height">

            <!-- 这是例行公事的选项内容，实际看不到 -->
            <FrameLayout
                android:id="@android:id/tabcontent"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:layout_weight="0" />
        </android.support.v4.app.FragmentTabHost>
    </LinearLayout>
    ```
    ```
    //直接在页面代码中指定每个标签按钮的视图和对应的Fragment页面

    package com.example.group;

    import com.example.group.fragment.TabFirstFragment;
    import com.example.group.fragment.TabSecondFragment;
    import com.example.group.fragment.TabThirdFragment;

    import android.graphics.drawable.Drawable;
    import android.os.Bundle;
    import android.support.v4.app.FragmentTabHost;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.LinearLayout;
    import android.widget.TabHost.TabSpec;
    import android.widget.TextView;

    public class TabFragmentActivity extends AppCompatActivity {
        private static final String TAG = "TabFragmentActivity";
        private FragmentTabHost tabHost; // 声明一个碎片标签栏对象

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tab_fragment);
            Bundle bundle = new Bundle(); // 创建一个包裹对象
            bundle.putString("tag", TAG); // 往包裹中存入名叫tag的标记
            // 从布局文件中获取名叫tabhost的碎片标签栏
            tabHost = findViewById(android.R.id.tabhost);
            // 把实际的内容框架安装到碎片标签栏
            tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
            // 往标签栏添加第一个标签，其中内容视图展示TabFirstFragment
            tabHost.addTab(getTabView(R.string.menu_first, R.drawable.tab_first_selector),
                    TabFirstFragment.class, bundle);
            // 往标签栏添加第二个标签，其中内容视图展示TabSecondFragment
            tabHost.addTab(getTabView(R.string.menu_second, R.drawable.tab_second_selector),
                    TabSecondFragment.class, bundle);
            // 往标签栏添加第三个标签，其中内容视图展示TabThirdFragment
            tabHost.addTab(getTabView(R.string.menu_third, R.drawable.tab_third_selector),
                    TabThirdFragment.class, bundle);
            // 不显示各标签之间的分隔线
            tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }

        // 根据字符串和图标的资源编号，获得对应的标签规格
        private TabSpec getTabView(int textId, int imgId) {
            // 根据资源编号获得字符串对象
            String text = getResources().getString(textId);
            // 根据资源编号获得图形对象
            Drawable drawable = getResources().getDrawable(imgId);
            // 设置图形的四周边界。这里必须设置图片大小，否则无法显示图标
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            // 根据布局文件item_tabbar.xml生成标签按钮对象
            View item_tabbar = getLayoutInflater().inflate(R.layout.item_tabbar, null);
            TextView tv_item = item_tabbar.findViewById(R.id.tv_item_tabbar);
            tv_item.setText(text);
            // 在文字上方显示标签的图标
            tv_item.setCompoundDrawables(null, drawable, null, null);
            // 生成并返回该标签按钮对应的标签规格
            return tabHost.newTabSpec(text).setIndicator(item_tabbar);
        }
    }
    ```
    * 该方式的核心是addTab函数
    * 因为FragmentTabHost已经自动处理了点击事件，所以无须另外调用sSelectede方法。该方式与前两种方式的不同之处在于标签页是Fragment而不是Activity，因此标签页内部无法直接操作选项菜单。
###### 学习指数：⭐

## 811
#### 安卓
###### 工具栏Toolbar
* Android5.0之后推出了Toolbar工具栏控件，意在取代ActionBar，但ActionBar仍然保留，所以`引入Toolbar前要先关闭ActionBar`:
    * 在style.xml中定义一个不包含ActionBar的风格样式：
    ```
        < name="AppCompatTheme" parent="Theme.AppCompat.Light.NoActionBar"/>
    ```
    * 修改AndroidManifest.xml，把activity节点下的android:theme属性值修改为上一步定义的风格：
    ```
    android:theme="@style/AppCompatTheme"
    ```
    * 将页面布局文件的根节点改为LinearLayout，且为vertical方向；然后增加Toolbar元素（Toolbar本质是一个`ViewGroup`，所以里面可以添加别的控件）：
    ```
        <android.support.v7.widget.Toolbar
        android:id="@+id/tl_head"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
    ```
    * Activity代码改为继承自`AppCompatActivity`（一般新建文件时就是默认继承AppCompatActivity）
###### 溢出菜单OverflowMenu
* 导航栏右边往往有个三点图标，点击后会弹出菜单。这个右上角的弹出菜单名叫溢出菜单，意指导航栏不够放了、溢出来了
* 溢出菜单其实就是把`选项菜单OptionsMenu`撤到了页面右上方，具体的菜单布局与代码用法基本同选项菜单，不同之处在于溢出菜单多了个`showAsAction`属性，该属性用来控制菜单项在导航栏上的展示位置
* 默认情况下，菜单列表的菜单项不会在文字左边显示图标，即使在菜单布局中设置了属性也没有作用所以想让菜单项显示左侧图标就得调用`MenuBuilder`的`setOptionalIconsVisible`方法。`该方法是一个隐藏方法，只能通过反射机制调用`：
```
//自定义的一个工具类

package com.example.group.util;

import android.content.Context;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MenuUtil {

    // 如果设备有物理菜单按键，需要将其屏蔽才能显示OverflowMenu
    // API18以下需要该函数在右上角强制显示选项菜单
    // 现在的开发过程中显然不必要求以下代码
    public static void forceShowOverflowMenu(Context context) {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.
                    getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 显示OverflowMenu的Icon
    public static void setOverflowIconVisible(int featureId, Menu menu) {
        // ActionBar的featureId是8，Toolbar的featureId是108
        if (featureId % 100 == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    // setOptionalIconsVisible是个隐藏方法，需要通过反射机制调用
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
```
* 菜单布局中将`showAsAction`属性设置为`ifRoom`或`always`，不过即使工具栏上还有空间，该菜单项也不会显示在工具栏上，解决办法挺简单，首先在菜单局的menu根节点增加命名空间声明`xmlns:app="http://schemas.android.com/apk/res-auto"`，然后把`android:showAsAction="ifRoom"`改为`app:showAsAction="ifRoom"`。（这分明就是`自定义属性的做法`）。<br>下面来看用于溢出菜单的布局文件代码：(可见第一个item直接显示在了有剩余空间的导航栏上)<br>
![20210811153444](https://i.loli.net/2021/08/11/hRTf3vFCx6awkou.png)
![20210811153539](https://i.loli.net/2021/08/11/U2cN31aPVFiOzL4.png)
```
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto" >

    <item
        android:id="@+id/menu_refresh"
        android:orderInCategory="1"
        android:icon="@drawable/ic_refresh"
        app:showAsAction="ifRoom"
        android:title="刷新"/>
    <item
        android:id="@+id/menu_about"
        android:orderInCategory="8"
        android:icon="@drawable/ic_about"
        app:showAsAction="never"
        android:title="关于"/>
    <item
        android:id="@+id/menu_quit"
        android:orderInCategory="9"
        android:icon="@drawable/ic_quit"
        app:showAsAction="never"
        android:title="退出"/>
</menu>
```
###### 搜索框SearchView
* 在菜单布局文件中定义搜索项：
```
    <item
        android:id="@+id/menu_search"
        android:orderInCategory="1"
        android:icon="@drawable/ic_search"
        app:showAsAction="ifRoom"
        android:title="搜索"
        app:actionViewClass="android.support.v7.widget.SearchView" />
```
* 在res/xml目录下新建searchable.xml，设置搜索框的样式代码：
```
<searchable xmlns:android="http://schemas.android.com/apk/res/android"
    android:label="@string/app_name"
    android:hint="@string/please_input"
    android:inputType="text"
    android:searchButtonText="@string/search" />
```
* 在AndroidManifest.xml中加入一个搜索结果页面的activity节点定义，需要指定action和meta-data：
```
<span style="font-size:18px;"><intent-filter>
        <action android:name="android.intent.action.SEARCH" />
   </intent-filter>
 
   <meta-data
        android:name="android.app.searchable"
        android:resource="@xml/searchable" /></span>
```
* 在页面代码中初始化搜索框：
```
    // 根据菜单项初始化搜索框
    private void initSearchView(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        // 从菜单项中获取搜索框对象
        SearchView searchView = (SearchView) menuItem.getActionView();
        // 设置搜索框默认自动缩小为图标
        searchView.setIconifiedByDefault(getIntent().getBooleanExtra("collapse", true));
        // 设置是否显示搜索按钮。搜索按钮只显示一个箭头图标，Android暂不支持显示文本。
        // 查看Android源码，搜索按钮用的控件是ImageView，所以只能显示图标不能显示文字。
        searchView.setSubmitButtonEnabled(true);
        // 从系统服务中获取搜索管理器
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // 创建搜索结果页面的组件名称对象
        ComponentName cn = new ComponentName(this, SearchResultActvity.class);
        // 从结果页面注册的activity节点获取相关搜索信息，即searchable.xml定义的搜索控件
        SearchableInfo info = sm.getSearchableInfo(cn);
        if (info == null) {
            Log.d(TAG, "Fail to get SearchResultActvity.");
            return;
        }
```
* 搜索框比较复杂，详情参考[Android中的Searchable使用，及删除记录](https://blog.csdn.net/antimage08/article/details/50783131)
###### 标签布局TabLayout
* `design库`中的标签布局`TabLayout`,使用该控件前要先修改build.gradle,在dependencies节点中加入一行代码表示导入design库，例如：
```
implementation 'com android.supprt:design:28.0.0
```
* TabLayout的展现形式类似于PagerTabStrip, 同样是文字标签带下划线，不同的是TabLayout允许定制更丰富的样式
```
        <!-- 注意TabLayout节点需要使用完整路径 -->
        <android.support.design.widget.TabLayout
            android:id="@+id/tab_title"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_centerInParent="true"
            app:tabIndicatorColor="@color/red"
            app:tabIndicatorHeight="2dp"
            app:tabSelectedTextColor="@color/red"
            app:tabTextColor="@color/grey"
            app:tabTextAppearance="@style/TabText" />
```
* 同样，把TabLayout与`ViewPager`结合起来就是一个固定的套路，使用时直接套框架就行
```
    // 初始化标签布局
    private void initTabLayout() {
        // 从布局文件中获取名叫tab_title的标签布局
        tab_title = findViewById(R.id.tab_title);
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        // 给tab_title添加一个指定文字的标签
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        // 给tab_title添加标签选中监听器
        tab_title.addOnTabSelectedListener(this);
    }

    // 初始化标签翻页
    private void initTabViewPager() {
        // 从布局文件中获取名叫vp_content的翻页视图
        vp_content = findViewById(R.id.vp_content);
        // 构建一个商品信息的翻页适配器
        GoodsPagerAdapter adapter = new GoodsPagerAdapter(
                getSupportFragmentManager(), mTitleArray);
        // 给vp_content设置商品翻页适配器
        vp_content.setAdapter(adapter);
        // 给vp_content添加页面变更监听器
        vp_content.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 选中tab_title指定位置的标签
                tab_title.getTabAt(position).select();
            }
        });
    }

    // 在标签被重复选中时触发
    public void onTabReselected(Tab tab) {}

    // 在标签选中时触发
    public void onTabSelected(Tab tab) {
        // 让vp_content显示指定位置的页面
        vp_content.setCurrentItem(tab.getPosition());
    }

    // 在标签取消选中时触发
    public void onTabUnselected(Tab tab) {}
```
* TabLayout默认采用文本标签，也支持自定义标签，除了放文本还可以放图像，比如加一个角标。<br>
![20210811172036](https://i.loli.net/2021/08/11/NifBk16yeZWzghQ.png)
###### 自定义指示器
* 指示器即banner或者欢迎页下面的一排圆点<br>
![20210811173926](https://i.loli.net/2021/08/11/C4qeamWT7r9sR2n.png)
* 要`实现指示器的左右平滑滚动效果`，得用到`ViewPager`的页面变化监听器`OnPageChangeListener`，关于前面学ViewPager的监听器时提到的三种方法<br>
onPageScrollStateChanged：页面滑动状态变化时触发<br>
onPageScrolled：页面滑动过程中触发<br>
onPageSelected：选中页面时，即滑动结束后触发<br>
在具体场合有下面两种用法：
    * 只实现onPageSelected方法，在页面滚动结束时触发(该用法最常见)。<br>
    只需创建一个SimpleOnPageChangeListener实例即可，该内部类在ViewPager源码中已经封装好了，开发者只需实现onPageSelected方法即可：
    ```
    //给翻页视图添加简 单的页面变更监听器，此时只需重写onPageSelected 方法

    Vp_ banner.addOnPageChangeListener(new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            //高亮显示该位置的指示按钮
            setButton(position);
        }
        });
    ```
    * 三种方法都实现。适用于指示器，特别是onPageScrolled方法的参数已经明确指出当前的滚动进度，正好给指示器的滚动位置提供参考
* 自定义指示器控件
###### 学习指数：⭐