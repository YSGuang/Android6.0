package hc.com.andpermission60;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * 为子类提高一个权限检查方法
//     *@param peremissions:表示不定参数，也就是调用这个方法的时候，可以传入多个String对象（Jdk5新特性）
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
    /**
     *在基类中为子类提供一个权限申请方法
     * 参数1.int，区分码    参数2.权限
     */
    public void requestPermission(int code,String... permissions){
        ActivityCompat.requestPermissions(this,permissions,code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       //处理打电话的权限
        switch (requestCode){
            case Constans.CALL_PHONE:
                //判断打电话权限申请是否成功，成功就执行打电话的逻辑
                //注意：因为集合里只有一个权限申请，所以参数为0代表打电话权限
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(BaseActivity.this,"用户没有授予权限！！！",Toast.LENGTH_SHORT).show();
                }
                break;
            case Constans.WTITE_EXTERNAL_STORAGE:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(BaseActivity.this,"用户没有授予权限！！！",Toast.LENGTH_SHORT).show();
                }
        }

    }
    /**打电话业务逻辑
     * 写一个空实现,而不是抽象方法,因为有些页面没有打电话功能,如果是抽象方法,
     * 还要覆写此方法,不是很好;子类如果有此功能,覆写此方法即可,不用管权限配置
     */
    public void doChallPhone(){

    }
    public void doCache(){

    }
}
