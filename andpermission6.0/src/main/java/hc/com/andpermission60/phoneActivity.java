package hc.com.andpermission60;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Android6.0-封装的整体思路：减少冗余代码的编写，提高代码的阅读性和开发效率
 在基类BaseAdapter中为子类提高权限的检查，和权限申请的方法，再在请求权限的回调处理接口中，
 暴露出子类要复写并实现其业务逻辑的方法，如果子类继承基类就可以大大简化6.0权限申请的步骤，
 根据基类暴露出需要覆写的方法，实现其对应的业务逻辑
0.常量类
 1.在清单文件里配置权限
 2.创建一个基类，并在基类中为子类提高一个权限检查的方法
 3.在基类中为子类提供一个权限的申请方法
 4.在基类中集中处理请求权限回调的业务逻辑
 5.暴露给子类实现具体业务逻辑的方法，子类如果有此功能，复写方法即可，没有就不用管此方法

 */
public class phoneActivity extends BaseActivity {
    private EditText et_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        et_num = (EditText) findViewById(R.id.et_num);
        phoneActivity.this.requestPermission(Constans.CALL_PHONE, Manifest.permission.CALL_PHONE);
        boolean permission = phoneActivity.this.hasPermission(Manifest.permission.CALL_PHONE);
    }
    public void callipone(View view) {
        doChallPhone();
    }

    @Override
    public void doChallPhone() {
        super.doChallPhone();
        String number = et_num.getText().toString().trim();
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }



}
