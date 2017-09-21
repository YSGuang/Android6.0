package hc.com.andpermission60;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import kr.co.namee.permissiongen.PermissionGen;

public abstract class quanxian implements View.OnClickListener {
    public Context context;

    public quanxian(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        PermissionGen.needPermission((Activity) context,100,new String[]{Manifest.permission.CALL_PHONE});
    }



}