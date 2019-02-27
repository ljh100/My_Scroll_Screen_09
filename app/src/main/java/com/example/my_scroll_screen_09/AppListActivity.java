package com.example.my_scroll_screen_09;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class AppListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        //list 가져옴
        ListView listview = (ListView) findViewById(R.id.list_view);
        PackageManager pm = getPackageManager();

        List<ApplicationInfo> infos = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        //아답터 만듦
        AppInfoAdapter adapter = new AppInfoAdapter(infos);
        listview.setAdapter(adapter);

        //리스트가 표시 되었을때 아이템을 크릭시 동작

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationInfo info= (ApplicationInfo) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                intent.putExtra("info",info);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
