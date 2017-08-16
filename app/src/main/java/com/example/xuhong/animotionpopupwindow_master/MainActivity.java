package com.example.xuhong.animotionpopupwindow_master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button(View view) {

        final List<String> list = new ArrayList<>();
        list.add("修改资料");
        list.add("切换账号");
        list.add("登录账号");
        list.add("退出账号");

        AnimotionPopupWindow popupWindow = new AnimotionPopupWindow(this, list);
        popupWindow.show();
        popupWindow.setAnimotionPopupWindowOnClickListener(new AnimotionPopupWindow.AnimotionPopupWindowOnClickListener() {
            @Override
            public void onPopWindowClickListener(int position) {
                Toast.makeText(MainActivity.this, "点击了:" + list.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
