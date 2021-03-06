package com.example.loginapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.lang.reflect.Field;

/**
 * Copied by Alleyoop on 716
 */
public class Activity_login extends AppCompatActivity implements OnClickListener {
    private RadioGroup rg_login; // 声明一个单选组对象
    private RadioButton rb_password; // 声明一个单选按钮对象
    private RadioButton rb_verifycode; // 声明一个单选按钮对象
    private EditText et_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText et_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个按钮控件对象
    private CheckBox ck_remember; // 声明一个复选框对象

    private int mRequestCode = 0; // 跳转页面时的请求代码
    private int mType = 0; // 用户类型
    private boolean bRemember = false; // 是否记住密码
    private String mPassword = "111111"; // 默认密码
    private String mVerifyCode; // 验证码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        ck_remember = findViewById(R.id.ck_remember);
        rg_login.setOnCheckedChangeListener(new RadioListener());
        ck_remember.setOnCheckedChangeListener(new CheckListener());
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        et_password.addTextChangedListener(new HideTextWatcher(et_password));
        btn_forget = findViewById(R.id.btn_login);
        btn_forget.setOnClickListener(this);
        findViewById(R.id.btn_forget).setOnClickListener(this);
        initTypeSpinner();
    }
    //初始化下拉框Spinner,先声明下拉字符串数组
    private String[] typeArray = {"个人用户", "公司用户"};

    private void initTypeSpinner() {
        //声明数组适配器:this.选中的文本样式，适配的字符串序列
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, R.layout.item_select, typeArray);
        //下拉后的字符串的文本样式
        typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_type = findViewById(R.id.sp_type);
        sp_type.setPrompt("选择用户类型");
        sp_type.setAdapter(typeAdapter);
        //设置下拉框默认显示第几项
        sp_type.setSelection(mType);
        //设置选择监听器，选中即触发监听器的onItemSelected方法
        sp_type.setOnItemSelectedListener(new TypeSelectedListener());
    }

    // 定义用户类型的选择监听器
    private class TypeSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            mType = arg2;//arg2代表选择项的序号，为int型
            Toast.makeText(Activity_login.this, "您选择的是" + typeArray[arg2], Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }//do nothing
    }

    //定义登录方式的单选监听器
    private class RadioListener implements RadioGroup.OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) { // 选择了密码登录
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
                ck_remember.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_verifycode) { // 选择了验证码登录
                tv_password.setText("　验证码：");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
                ck_remember.setVisibility(View.INVISIBLE);
            }
        }
    }

    //定义是否记住密码的勾选监听器
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.ck_remember) {
                bRemember = isChecked;
            }
        }
    }

    //定义编辑框的文本变化监听器
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        private CharSequence mStr;

        HideTextWatcher(EditText v) {
            super();
            mView = v;
            mMaxLength = getMaxLength(v);
        }

        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }

        // 在编辑框的输入文本变化后触发,注意了解一下Editable
        public void afterTextChanged(Editable s) {
            if (mStr == null || mStr.length() == 0) return;
            // 手机号码输入达到11位，或者密码/验证码输入达到6位，都关闭输入法软键盘
            if ((mStr.length() == 11 && mMaxLength == 11) || (mStr.length() == 6 && mMaxLength == 6)) {
                hideOneInputMethod(Activity_login.this, mView);
            }
        }
    }
    //重写onclick点击方法，对页面的btn进行配置
    public void onClick(View v){
        Editable temp = et_phone.getEditableText();//学习一下Editable
        String phone = temp.toString();
        //// 点击了“忘记密码”按钮(注意”忘记密码“按钮和”获取验证码“是一个按钮，取决于RadioButton的选择)
        if(v.getId() == R.id.btn_forget){
            // 手机号码不足11位
            if(phone.length()<11){
                Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            // 选择了密码方式校验，此时要跳到找回密码页面activity_login_forget
            if(rb_password.isChecked()){
                Intent intent = new Intent(this,Activity_login_forget.class);
                // 携带手机号码跳转到找回密码页面,将phone传递给下个页面
                intent.putExtra("phoneNum",phone);
                //由于要从下个页面收到数据，故：
                startActivityForResult(intent,mRequestCode);
            }
            // 选择了验证码方式校验，此时要生成六位随机数字验证码
            else if (rb_verifycode.isChecked()){
                //随机数生成六位验证码
                mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                //提醒对话框
                new AlertDialog.Builder(this)
                        .setTitle("请记住验证码")
                        .setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码")
                        .setPositiveButton("OK",null)//点击ok没有事件相应
                        .show();
            }
        }
        //点击了"登录"按钮
        else if (v.getId() == R.id.btn_login){
            // 手机号码不足11位
            if(phone.length()<11){
                Toast.makeText(this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            // 选择了密码方式校验，验证密码正确性，先是验证默认密码(预置mPassword为”111111“)
            if(rb_password.isChecked()){
                if(!et_password.getText().toString().equals(mPassword)){
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                } else {
                    //提示登录成功，调用登录成功的方法
                    loginSuccess();
                }
            }
            // 选择了验证码方式校验，此时要验证验证码的正确性
            else if (rb_verifycode.isChecked()) {
                //注意这里的et_password已经不再是指密码了，而是用户输入的验证码
                if(!et_password.getText().toString().equals(mVerifyCode)){
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else {
                    //提示登录成功，调用登录成功的方法
                    loginSuccess();
                }
            }
        }
    }
    //登录成功的方法
    private void loginSuccess(){
        String desc = String.format("您的手机号码是%s，类型是%s。恭喜你通过登录验证，点击“确定”按钮返回上个页面",
                et_phone.getText().toString(), typeArray[mType]);
        //成功的提示框
        new AlertDialog.Builder(this)
                .setTitle("登录成功啦")
                .setMessage(desc)
                .setPositiveButton("好的,退出",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        finish();
                    }
                })
                .setNegativeButton("不退出",null)
                .show();
    }
    //处理从下一个页面传过来的参数，相应startActivityForResult(intent, mRequestCode);
    //注意这个protected,有点学问哦~。还有这个resultCode为结果代码，自行设定，收到返回参数后做一些事件可能会用到，不用就不管

    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == mRequestCode && data != null) {
            // 用户密码已改为新密码，故更新密码变量
            mPassword = data.getStringExtra("new_password");
        }
    }
    // 从修改密码页面返回登录页面，要清空密码的输入框
    // 其实就是每次进入此页面就onRestart一下，达到清空编辑框的目的
    // 这里清空的编辑框为"",而不是null
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }
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
    public static void hideOneInputMethod(Activity act, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}