package com.xxx.ipedittext;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xxx.ipedittext.ui.ipedittext.IPView;

public class MainActivity extends AppCompatActivity {

    Button btn;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIpDialog();
            }
        });
    }


    private void showIpDialog() {
        View view = View.inflate(this, R.layout.dialog_ip_edittext, null);
        final IPView ipView = (IPView) view.findViewById(R.id.ipView);
        ipView.setIp(preferences.getString("ip", "192.168.3.47"));
        ipView.setPort(preferences.getString("port", "8080"));
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setContentView(view);
//	        builder.setTitleColor(getResources().getColor(R.color.dialog_title));
        builder.setTitle("修改ip地址和端口");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (ipView.IsValid()) {
                    Log.i(" ipView.getIp()", ipView.getIp());
                    preferences.edit().putString("ip", ipView.getIp()).commit();
                    preferences.edit().putString("port", ipView.getPort()).commit();
                    dialog.dismiss();
                }
                //设置你的操作事项
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }
}
