package com.example.choi.packagemanagerexam;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppInfoAdapter extends BaseAdapter {

    private List<ApplicationInfo> mInfos;

    public AppInfoAdapter(List<ApplicationInfo> data){

        mInfos = data;

    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);

            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.icon);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.app_name_text);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 앱 정보
        ApplicationInfo info = mInfos.get(position);

        // 앱 아이콘
        Drawable icon = info.loadIcon(parent.getContext().getPackageManager());
        viewHolder.imageView.setImageDrawable(icon);

        // 앱 이름
        String name = String.valueOf(info.loadLabel(parent.getContext().getPackageManager()));
        viewHolder.textView.setText(name);

        return convertView;






    }


    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
