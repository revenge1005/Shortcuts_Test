package com.example.choi.packagemanagerexam;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class Applist extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        listView = (ListView)findViewById(R.id.list_view);

        // 기기에 설치된 모든 앱 목록
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> infos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        AppInfoAdapter adapter = new AppInfoAdapter(infos);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long id) {

                ApplicationInfo info = (ApplicationInfo) (adapterView.getAdapter()).getItem(positon);

                Intent intent = new Intent();
                intent.putExtra("info", info);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }



}
