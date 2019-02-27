package com.example.my_scroll_screen_09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1000;
    private ImageView mShortcut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShortcut = (ImageView) findViewById(R.id.shortcut_image);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String packageName = preferences.getString("shortcut",null);
        if(packageName != null){
            try {
                Drawable icon = getPackageManager().getApplicationIcon(packageName);
                mShortcut.setImageDrawable(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void onImageClicked(View view) {
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if(drawable !=  null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String packageName = preferences.getString("shortcut",null);

            if(packageName != null){
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(intent);
            }
        }

    }

    public void onButtonClicked(View view) {
        //button click
        Intent intent =new Intent(this, AppListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // if(resultCode == REQUEST_CODE && resultCode== RESULT_OK && data != null){ //제대로 데이타 가 넘어온다
            ApplicationInfo info = data.getParcelableExtra("info"); //데이타가 Parce 포장 되어 넘어오므로...
            Drawable icon = info.loadIcon(getPackageManager());
            mShortcut.setImageDrawable(icon);
            Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();
            //preference  에도 계속 저장 유지를하려면
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            //아이콘을 preferec 에 유지 저장 하려면 에디터 개채를 얻어야 한다
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("shortcut",info.packageName);
            edit.apply();
            //여기까지가 한세트
       // }
    }
}
