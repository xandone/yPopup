package com.ypopue.xandone.ypopup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                new BasePopup(this) {
                    @Override
                    public View setLayout() {
                        return LayoutInflater.from(MainActivity.this).inflate(R.layout.right_slide_layout, null);
                    }
                }.show();
                break;
            case R.id.btn_2:
                new BasePopup(this) {
                    @Override
                    public View setLayout() {
                        return LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_slide_layout, null);
                    }
                }.setSlideDirect(BasePopup.DIRECT_BOTTOM).show();
                break;
        }
    }
}
