package hc.com.andpermission60;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 第三方的Android6.0权限库：permissionGen
 * 优点：使用简单，代码量少，易于阅读
 * 缺点：通过观看源码PermissionGen类，发现在找到注解的方法后，使用反射执行注解方法，一致于程序的性能会受到影响
 * <p>
 * 使用流程:
 * 1.添加依赖
 * 2.添加权限
 * 3.判断涉及到用户隐私的功能是否授权了对应的权限，如果没有自动申请
 * 4.申请权限成功，自动执行的逻辑
 * 5.用户不同意权限，自动回调的逻辑
 * 6.覆写处理回调的接口方法
 */
public class MainActivity extends BaseActivity {
    private EditText et_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_num = (EditText) findViewById(R.id.et_num);
    }

    public void callipone(View view) {
        //进行动态权限的适配，程序正常运行，判断涉及到用户隐私的功能是否授权对应的权限，如果没有就自动申请
        //参数：1上下文   2int权限区分码   3String数组，要申请的权限
        PermissionGen.needPermission(this,100,new String[]{Manifest.permission.CALL_PHONE});
    }
    @PermissionSuccess(requestCode = 100)
    private void doCallphone() {
        String number = et_num.getText().toString().trim();
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    //异步回掉接口，判断是否通过了权限，做对应的逻辑处理
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //因为这是处理所有权限申请的回调，为方便做对应的权限申请的逻辑操适应switch判断之前的请求码的设置，区分权限
//        switch ((requestCode)) {
//            case 0:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    doCallphone();//打电话的逻辑
//                } else {//用户拒绝权限的请求，给用户提示权限功能
//                    Toast.makeText(MainActivity.this, "权限没有授予", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }


//    }
    @PermissionFail(requestCode = 100)
    private void daCallPhoneFail(){
        Toast.makeText(this,"不好意思！权限没有通过",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }
    public void jum(View view) {
        startActivity(new Intent(MainActivity.this,phoneActivity.class));
    }
    public void Cache(View view){
        //判断APP是否有读写的权限
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            //申请权限
            requestPermission(Constans.WTITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }else {
            //检查有权限，做打电话
            doCache();

        }
    }

    @Override
    public void doCache() {
        super.doCache();
        Object obj=null;
       int string= Log.e("object","="+obj);

        File sdCar=Environment.getExternalStorageDirectory();//获取SDcard
        File sdFile=new File(sdCar,"log.txt");
        try {
            FileOutputStream fos = new FileOutputStream(sdFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(string);// 写入
            fos.close(); // 关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            File file = new File(Environment.getExternalStorageDirectory(), "cache.txt");
//            FileOutputStream fos = new FileOutputStream(file);
//            Object obj=null;
//            int info =  Log.e("object","="+obj);
//            fos.write(info);
//            fos.close();
//            System.out.println("写入成功：");
//
//            Object o=null;
//            o.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
