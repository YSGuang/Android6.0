package hc.com.android60;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Android6.0权限实现思路：
 * 1.所有权限都必须先在清单文件里配置，否则程序会报错，且在6.0系统上报错还不容易查找，直接退出程序
 * 2.你要判断，用户的手机系统，是否是Android6.0以上版本，如果不是，那就不做任何操作，如果是，那就调用对应的代码（这一步你不做，功能不受影响，如果做的话，性能会得到些许提升）
 * 3.判断涉及到用户隐私的功能是否授权了对应的权限，没有申请权限，做权限的申请即可，申请了权限，做逻辑操作即可
 * 4.异步回调接口，判断是否通过授权，做对应的逻辑操作
 */

public class MainActivity extends AppCompatActivity {

    private EditText et_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_num = (EditText) findViewById(R.id.et_num);
    }

    public void callipone(View view) {
        //.判断涉及到用户隐私的功能是否授权了对应的权限，没有申请权限，做权限的申请即可，申请了权限，做逻辑操作即可
        //参数：上下文    某权限    3PackageManager.PERMISSION_DENIED授权权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_DENIED) {
            //没有申请权限,做权限的申请  参数：1.上下文  2.字符串数组，可以一次申请多个权限  3.int型请求码方便我们以后权限区分
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
        } else {
            doCallphone();
        }
    }

    private void doCallphone() {
        String number = et_num.getText().toString().trim();
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }
    private void jum(View view) {
        startActivity(new Intent(MainActivity.this,phoneActivity.class));
    }

    //异步回掉接口，判断是否通过了权限，做对应的逻辑处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //因为这是处理所有权限申请的回调，为方便做对应的权限申请的逻辑操适应switch判断之前的请求码的设置，区分权限
        switch ((requestCode)){
            case 0:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    doCallphone();//打电话的逻辑
                }else{//用户拒绝权限的请求，给用户提示权限功能
                    Toast.makeText(MainActivity.this,"权限没有授予",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
