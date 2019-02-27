package com.example.my_scroll_screen_09;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class AppInfoAdapter  extends BaseAdapter {
    private List<ApplicationInfo> mInfos;
    public AppInfoAdapter(List<ApplicationInfo> data) {
        this.mInfos = data;
    }
    @Override
    public int getCount() {
        return mInfos.size();
    }



    @Override
    public Object getItem(int position) {
        return mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_app,parent,false);
            holder.imageView = convertView.findViewById(R.id.icon_image);
            holder.textView = convertView.findViewById(R.id.app_name_text);
            convertView.setTag(holder);

        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ApplicationInfo info = mInfos.get(position);
        //아이콘 가져오기 drawabel 로
        Drawable icon = info.loadIcon(parent.getContext().getPackageManager());
        holder.imageView.setImageDrawable(icon);
        String name = String.valueOf(info.loadLabel(parent.getContext().getPackageManager()));
        holder.textView.setText(name);

        return convertView;
    }
    //view holder static call 로 만들어야 메모리 leak protect
    private static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
