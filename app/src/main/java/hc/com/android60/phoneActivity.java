package hc.com.android60;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class phoneActivity extends AppCompatActivity {
    private EditText et_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        et_num = (EditText) findViewById(R.id.et_num);
    }
    public void callipone(View view) {
        doCallphone();
    }

    private void doCallphone() {
        String number = et_num.getText().toString().trim();
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(intent);
    }
}
