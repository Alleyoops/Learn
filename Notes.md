# viewmodel
```
package com.example.gszs.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    //声明
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> num;
    public NotificationsViewModel() {
        //实例化
        mText = new MutableLiveData<>();
        num = new MutableLiveData<>();
        //更新数据
        mText.setValue("This is notifications fragment1");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
```
平时我们使用LiveData的时候，需要更新数据的时候，LiveData提供了两种更新数据的方式：

setValue(T value)

postValue(T value)
那么这两种方式有什么区别呢？

结论:
setValue()只能在主线程中调用，postValue()可以在任何线程中调用。<br>
————————————————————————————————<br>
原文链接：https://blog.csdn.net/catzifeng/article/details/103931517

    LiveData通常是被放在ViewModel中使用。

进一步区别一下ViewModel和LiveData。ViewModel用于存放页面所需的各种数据，它还包括一些 业务逻辑等，比如我们可以在ViewModel对数据进行加工，获取等操作。而对页面来说，它并不关心这些业务逻辑，它只关心需要展示的数据是什么，并且希望在数据发生变化时，能及时得到通知并做出更新。LiveData的作用就是，在ViewModel中的数据发生变化时通知页面。从LiveData（实时数据）这个名字，我们也能推测出，它的特性与作用。

[onCreate与onCreateView的区别](http://blog.sina.com.cn/s/blog_72a884dd0102w9pz.html)：

    总的来说,onCreate先执行，完成一些与UI无关的Fragment的初始化。然后执行onCreateView()，初始化与界面相关的内容。

[LiveData使用](https://www.jianshu.com/p/c69a7db3299a)<br>


# Java Lambda 表达式
* https://www.runoob.com/java/java8-lambda-expressions.html
* https://blog.csdn.net/weixin_44505177/article/details/108039589?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-8.base&spm=1001.2101.3001.4242
* （应该函数的定义方式也是lambda形式）
## getSupportFragmentManager , getParentFragmentManager和getChildFragmentManager

* getSupportFragmentManager与 activity关联，可以将其视为 activity 的 FragmentManager
* getChildFragmentManager 与 fragment关联，可以将其视为fragment的FragmentManager
* getParentFragmentManager情况稍微复杂，正常情况返回的是该fragment 依附的activity的FragmentManager。如果该fragment是另一个fragment 的子 fragment，则返回的是其父fragment的 getChildFragmentManager
————————————————<br>
版权声明：本文为CSDN博主「Ljl233」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_44631241/article/details/105274260

## [Android 汉字转拼音的多种实现方式](https://blog.csdn.net/zhuwentao2150/article/details/70230341)

## onCreate/onCreateView
* onCreate是指创建该fragment类似于Activity.onCreate，你可以在其中初始化除了view之外的东西， 
* onCreateView是创建该fragment对应的视图，你必须在这里创建自己的视图并返回给调用者，例如
return inflater.inflate(R.layout.fragment_settings, container, false);。
* super.onCreateView有没有调用都无所谓，因为super.onCreateView是直接返回null的。

//    onCreate 先执行，完成与 UI 无关的内容。

//    onCreateView 后执行，完成与 UI 相关的内容

Activity中有7个与生命周期有关的函数。
---
onCreated()是activity第一次被`启动时`执行的，主要是`初始化一些变量`，

onRestart()是当前activity`重新被启动时`调用的；绑定一些`监听器`等；

onStart()是activity界面被`显示`出来的时候执行的；

onResume()是当该activity与用户能进行`交互时`被执行；

onPause()是另一个activity被启动，当前的activity就被`暂停`了，一般在该函数中执行`保存当前的数据`；

onStop()表示另一个activity被启动完成时，当前activity对用户同时又`完全不可见`时才调用的；

onDestroy()是`退出`当前activity时调用的，当然如果程序中调用finish()或者说android系统当前资源不够用时就会被调用。

#### 同理，还要弄清Fragment的生命周期，以及和activity之间的关系[Fragment和Activity生命周期的关系](https://blog.csdn.net/jerechen/article/details/103246201)：

onAttach() 在Fragment 和 Activity 建立关联是调用（Activity 传递到此方法内）
onCreateView() 当Fragment 创建视图时调用
onActivityCreated() 在相关联的 Activity 的 onCreate() 方法已返回时调用。
onDestroyView() 当Fragment中的视图被移除时调用
onDetach() 当Fragment 和 Activity 取消关联时调用。

作者：anloney
链接：https://www.jianshu.com/p/70d7bfae18f3
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

一些参考：https://www.cnblogs.com/wade-pcb/p/4649367.html

## SearchView
关于searchview，是一个安卓原生组件，用来实现搜索的功能，以一个icon的形式显示。当然也可以用EditText来达到searchview的搜索效果，但是需要自己去实现匹配功能。SearchView是Android原生的搜索框控件，它提供了一个用户界面，用于用户搜索查询。
SearchView默认是展示一个search的icon，点击icon展开搜索框，如果你想让搜索框默认就展开，可以通过setIconifiedByDefault(false);实现。

输入类型
android:inputType

最大宽度
android:maxWidth

搜索图标是否显示在搜索框内
mSearchView.setIconifiedByDefault(true);

设置搜索框展开时是否显示提交按钮，可不显示
mSearchView.setSubmitButtonEnabled(true);

让键盘的回车键设置成搜索
mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

搜索框是否展开，false表示展开
mSearchView.setIconified(false);

获取焦点
mSearchView.setFocusable(true);
mSearchView.requestFocusFromTouch();

设置提示词
mSearchView.setQueryHint(“请输入关键字”);

设置输入框文字颜色
EditText editText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
editText.setHintTextColor(ContextCompat.getColor(this, R.color.white));
editText.setTextColor(ContextCompat.getColor(this, R.color.white));
————————————————
版权声明：本文为CSDN博主「yechaoa」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/yechaoa/article/details/80658940

https://blog.csdn.net/weixin_46157140/article/details/108213511?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522163051288816780255263031%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=163051288816780255263031&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~top_click~default-1-108213511.pc_search_ecpm_flag&utm_term=sEARCHVIEW&spm=1018.2226.3001.4187

## AndroidManifests.xml下的meta-data （元数据）
meta-data就像其名一样，主要用来定义一些组件相关的配置值。

按照官方定义，metadata是一组供父组件使用的名值对（name-value pair），因此相应的meta-data元素应该定义在相应的组件中。即如果想在activity中使用metadata，那么meta-data必须定义在AndroidManifest.xml的activity声明中<br>
————————————————<br>
版权声明：本文为CSDN博主「明潮」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u010144805/article/details/79097134


## public static 表示公共的静态方法；public 表示公共的方法。

static：静态。可以设置：静态类、静态变量、静态方法。 没有使用static修饰的成员为实例成员。静态成员的使用：通过类名。

不加static修饰的成员是对象成员，归每个对象所有。

加static修饰的成员是类成员，可以由一个类直接调用，为所有对象共有。

用static关键字标识的程序元素是静态的,不用static标识的程序元素是非静态。
![20211001145751](https://raw.githubusercontent.com/Alleyoops/Image/main/20211001145751.png)


## viewpager显示fragment的时候不会仅仅显示展现的那个fragment，而是将前一个，后一个，和正在显示的fragment的生命周期都跑一遍。


## Fragment之间数据传递的三种方式 https://www.jianshu.com/p/f87baad32662

## ViewPager中如何获取Fragment三种方式 https://www.iteye.com/blog/xhmj12-1990760
ViewPager pager = (ViewPager)findViewById(R.id.viewpager);

FragmentStatePagerAdapter f = pager.getAdapter();

SomeFragment someFragment = (SomeFragment)f.instantiateItem(pager,position);

## 了解fragment的Tag是什么东西？？？
用到的时候再去百度吧（和id有点相似感觉）

# 一些java知识点：
## 1、单例模式
![20210928212749](https://raw.githubusercontent.com/Alleyoops/Image/main/20210928212749.png)
![20210928213040](https://raw.githubusercontent.com/Alleyoops/Image/main/20210928213040.png)
![20210928214018](https://raw.githubusercontent.com/Alleyoops/Image/main/20210928214018.png)

## 2、静态代码块
大概就是用来初始化静态变量(类属性)和方法吧~（在多次new的时候只会被执行一次）
![20210930232724](https://raw.githubusercontent.com/Alleyoops/Image/main/20210930232724.png)
![20210930233559](https://raw.githubusercontent.com/Alleyoops/Image/main/20210930233559.png)

## 3、匿名内部类
![20210930235258](https://raw.githubusercontent.com/Alleyoops/Image/main/20210930235258.png)
![20210930234848](https://raw.githubusercontent.com/Alleyoops/Image/main/20210930234848.png)
<br>（以上属于高级编程范畴了，少于用到。（而内部类的作用一般是为了实现多重继承，即继承或重写多个类的方法、属性））

## 4、接口
![20211001143130](https://raw.githubusercontent.com/Alleyoops/Image/main/20211001143130.png)
![20211001150715](https://raw.githubusercontent.com/Alleyoops/Image/main/20211001150715.png)<br>
关于default关键字：
```
public interface myInterface {

    /*
     * default关键字修饰的方法就是初始化的抽象方法。或者说是一个已经实现了的抽象方法，不需要再在其他implement接口位置进行重写实现。
     *
     */
    //实现dataBinding的onclick方法
    default void test(View view){
        Toast.makeText(view.getContext(), "HI", Toast.LENGTH_SHORT).show();
    }
    void test2();//这种没关键字的是需要继承并实现的
}
```
## 5、集合
set、list、map接口<br>
遍历集合：
![20211003163040](https://raw.githubusercontent.com/Alleyoops/Image/main/20211003163040.png)

## 6、枚举类
enum，类的对象数量有限且固定，每个枚举类都是单例模式

## 7、注解是什么玩意儿
自定义Annotation
![20211003211846](https://raw.githubusercontent.com/Alleyoops/Image/main/20211003211846.png)

## 8、IO流
![20211003212659](https://raw.githubusercontent.com/Alleyoops/Image/main/20211003212659.png)
![20211004210711](https://raw.githubusercontent.com/Alleyoops/Image/main/20211004210711.png)
重点：
![20211004222035](https://raw.githubusercontent.com/Alleyoops/Image/main/20211004222035.png)
<br>普通流是逐个字节进行输入或输出，这样虽然可以完成工作，但是在效率上有很大的问题。当我们将文件读取的时候，会先加载到内存，然而刚刚加载了一个字节到内存，马上又要告诉磁盘，喂~大兄弟，给我把这个字节写到磁盘上，我们知道磁盘的效率比内存要低很多的，在磁盘写入的过程中，内存只能干瞪眼，当磁盘写完一个字节后，内存再把下一个字节交给磁盘，喂~大兄弟，继续写下一个，然后内存又等着磁盘写下一个字节。
<br>
普通流效率低下的最大原因就在于此，频繁的调用磁盘，导致无法发挥内存速度快的优点。于是为了提高效率，缓冲流出现了。看看缓冲流缓冲了什么？缓冲流并不是每一个字节都要调用一次磁盘，而是根据设置的缓冲区大小，每当缓冲区满了以后，再调用一次磁盘，比如上图中，缓冲区设置为3，结果就是每次缓冲区有3个字节的数据以后，再调用一次磁盘，这样一来，调用磁盘的次数就减少了很多，使效率得到了很大的提升。文件越大，缓冲流效率的提升越明显。

## 9、反射Reflection
前提：JVM已经加载这个类，相当于人脑有了这个类的记忆

## 10、动态代理
![20211008162710](https://raw.githubusercontent.com/Alleyoops/Image/main/20211008162710.png)

## 11、多线程
异步是对于主线程来说（时间同步），同步是指按顺序执行（按时间先后）<br>
同步锁`synchronized`(wait()、notify()、notifyAll())

## super.onCreate(savedInstanceState);

## activity启动模式
[我打赌你一定没搞明白的Activity启动模式](https://www.jianshu.com/p/2a9fcf3c11e4)

----
----

## 关于RecyclerView的监听器
众所周知，RecyclerView是继承自ViewGroup的，而不是像ListView一样继承自AbsListview.所以RecyclerView没有OnItemClickListener,没有OnItemLongClickListener,更没有OnItemSelectedListener.所以这都要我们自己实现。What！！！ Are you kidding me？ 严肃脸，没错，就是要我们自己来实现

作者：DorisSunny
链接：https://www.jianshu.com/p/040bfbbca49b
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## tablayout占满屏幕宽度
        app:tabMaxWidth="0dp"
        app:tabGravity="fill"
        app:tabMode="fixed"

## BottomNavigationView的基本使用
https://blog.csdn.net/qq_32534441/article/details/105227916


## Tooltips
Tooltips可以实现类似pc端网页鼠标悬停时出现描述信息的功能，而到安卓中，如果给一个控件使用了Tooltips，那么当用户长按这个控件时，我们预设的描述信息就会悬浮出现在控件附件某个位置

## DataBinding
基础1：https://examplecode.cn/2018/07/20/android-databinding-01-introduction/<br>
基础2：https://blog.csdn.net/lixiong0713/article/details/109051409<br>
点击事件：https://www.jianshu.com/p/212e85f7a435

## OKHttp（retrofit）踩坑：responseBody.string()只能调用一次
https://www.cnblogs.com/suizhikuo/p/12925455.html<br>
说到这，顺便提一嘴，InputStream也是只能读一次，InputStream可以看做是一个数据通道，并不负责数据的存储和处理。类似这样的机制应该还有很多。

## 踩坑：通过 View.isClickable 去控制 View 的重复点击，即使控制了，仍然能够再次触发点击事件->只要点击button都会将setClickable设置为true，所以在setOnClickListener之前setClickable设置为false只不过是多此一举！<br>
https://blog.csdn.net/u011630575/article/details/50113927<br>
https://blog.csdn.net/lv_fq/article/details/82314241<br>
解决办法：https://www.jianshu.com/p/8385d091feca

## SharedPreferences
* SharedPreference 相关修改使用 apply 方法进行提交会先写入内存，然后异步写入磁盘，commit
方法是直接写入磁盘。如果频繁操作的话 apply 的性能会优于 commit，apply会将最后修改内容写入磁盘。
但是如果希望立刻获取存储操作的结果，并据此做相应的其他操作，应当使用 commit。
————————————————
版权声明：本文为CSDN博主「待风」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/fenggering/article/details/78197934
* Jetpack Security提供了文件加密和SharedPreferences加密功能。https://blog.csdn.net/u010982507/article/details/117922210

## PictureSelecter
https://www.jianshu.com/p/3fe1cc959dfa


## 参考 ：navigation每次点击都刷新fragment
![20210904231717](https://raw.githubusercontent.com/Alleyoops/Image/main/20210904231717.png)

## BottomSheet
https://www.jianshu.com/p/c9e24765067e<br>
![20211215175335](https://raw.githubusercontent.com/Alleyoops/Image/main/20211215175335.png)

## [ViewPager + Fragment重复销毁加载视图的解决办法]
* (https://blog.csdn.net/qq994467433/article/details/83448663?utm_medium=distribute.wap_relevant.none-task-blog-2~default~baidujs_title~default-12.wap_blog_relevant_default&spm=1001.2101.3001.4242.7)
* https://www.jianshu.com/p/7a47907f49c2通过ViewPager的setOffscreenPageLimit(int limit)可以设置预加载页面数量，就不会重加载了

## [Android SearchView设置与用法的那点事儿(焦点问题)](https://www.cnblogs.com/molashaonian/p/9097669.html)

## 线程和协程（以及并行和并发）：https://zhuanlan.zhihu.com/p/169426477

## 绘制贝塞尔曲线

## 四大组件之一：广播Broadcast，例如：[安卓任意两个或多个Fragment之间的交互与刷新界面](https://www.cnblogs.com/jiangbeixiaoqiao/p/6214557.html)

## 关于EventBus的使用，还有通知、数据传送的机制、使用方式等

## [Android Jetpack](https://www.zhihu.com/column/Jetpacker)

![1642525737(1)](https://raw.githubusercontent.com/Alleyoops/Image/main/1642525737(1).png)