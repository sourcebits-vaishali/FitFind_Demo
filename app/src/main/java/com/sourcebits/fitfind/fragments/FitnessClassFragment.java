package com.sourcebits.fitfind.fragments;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.sourcebits.fitfind.R;
import com.sourcebits.fitfind.custom.ExpandableTextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class FitnessClassFragment extends Fragment {

    Button mVideo;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness_class, container , false);
        ExpandableTextView description = (ExpandableTextView)view.findViewById(R.id.description);
        description.setText(getActivity().getString(R.string.random_text));

        ArrayList<Bitmap> items = new ArrayList<>();
        for (int i=0;i<10;i++) {
            items.add(BitmapFactory.decodeResource(getActivity().getResources(),
                    R.mipmap.ic_launcher));
        }

        MyAdapter adapter = new MyAdapter(items);
        TwoWayView lvTest = (TwoWayView) view.findViewById(R.id.lvItems);
        lvTest.setItemMargin(10);
        lvTest.setAdapter(adapter);

        mVideo = (Button)view.findViewById(R.id.video);
        Drawable icon = ContextCompat.getDrawable(getContext(), R.drawable.rectangle).mutate();
        icon.setColorFilter(new
                PorterDuffColorFilter(ContextCompat.getColor(getActivity(), R.color.violet),PorterDuff.Mode.MULTIPLY));
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mVideo.setBackgroundDrawable(icon);
        } else {
            mVideo.setBackground(icon);
        }

        return view; //TODO - Vertical view is not visible. If we give hard coded value to its height is becomes visible
    }
    class MyAdapter extends BaseAdapter{

        private List<Bitmap> mImages;
        public MyAdapter(List<Bitmap> images){
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
                convertView = convertView.inflate(getActivity() , R.layout.two_way , null);
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
}
