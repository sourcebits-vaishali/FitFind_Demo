package com.sourcebits.fitfind.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sourcebits.fitfind.R;

import java.util.List;

/**
 * Created by vaishaliarora on 24/05/16.
 */
public class ImageAdapter extends BaseAdapter {

    private List<Bitmap> mImages;
    private Context mContext;

    public ImageAdapter(Context ctx ,List<Bitmap> images){
        mContext = ctx;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null == convertView){
            convertView = convertView.inflate(mContext, R.layout.two_way , null);
            holder = new ViewHolder();

            holder.img = (ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.img.setImageBitmap(mImages.get(position));

        return convertView;
    }

    class ViewHolder{
        ImageView img;
    }
}
