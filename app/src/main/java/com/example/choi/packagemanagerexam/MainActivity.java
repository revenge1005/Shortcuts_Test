package com.example.choi.packagemanagerexam;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1000;
    private ImageView mShortcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShortcut = (ImageView)findViewById(R.id.shortcut_image);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // 저장된 stortcut 값을 얻음. 만약 저장된 값이 없을 경우 기본박으로 null로 반환
        String packageName = preferences.getString("shortcut", null);

        if(packageName != null){
            try {
                Drawable icon = getPackageManager().getApplicationIcon(packageName);


                // 아이콘을 이미지뷰에 표시
                mShortcut.setImageDrawable(icon);
            }catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                // Applist.java로 부터 념겨 받은 ApplicationInfo 객체
                // Parcelable 객체를 받기 위해 getParcelableExtra()로 얻음
                ApplicationInfo info = data.getParcelableExtra("info");

                // loadIcon()에 PackageManager를 넘겨주면 아이콘을 Drawable로 얻을 수 있음
                Drawable icon = info.loadIcon(getPackageManager());

                // 기본 SharedPreferences 환경을 얻음
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

                // SharedPreferences를 수정하기 위한 객체를 얻음
                SharedPreferences.Editor editor = preferences.edit();

                // info 객체로 packageName을 얻고, shortcut 키와 함께 프리퍼언스에 저장
                editor.putString("shortcut", info.packageName);

                // 변경사항 적용
                editor.apply();
                mShortcut.setImageDrawable(icon);
            }
        }

    }

    public void onButtonClicked(View view){
        Intent intent = new Intent(this, Applist.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void onImageClicked(View view){

        // 클릭된 이미지뷰에서 Drawble 객체를 얻음
        ImageView imageView = (ImageView) view;

        Drawable drawable = imageView.getDrawable();
        if(drawable != null){

            // 프리퍼런스에 shortcut 키로 지정된 패키지명을 가져옴
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String packageName = preferences.getString("shortcut", null);

            if (packageName != null){

                // 이 패키지를 실행할 수 있는 인텐트를 얻어서 액티비티 시작
                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                startActivity(intent);
            }
        }
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("종료 확인");
        builder.setMessage("정멀로 종료하시겠습니까?");

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });


        builder.setNegativeButton("취소", null);
        builder.show();

    }
}
