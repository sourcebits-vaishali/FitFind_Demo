package com.sourcebits.fitfind.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sourcebits.fitfind.R;
import com.sourcebits.fitfind.custom.CircularImageClass;
import com.sourcebits.fitfind.model.TrainerDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class TrainerAdapter extends RecyclerView.Adapter<TrainerAdapter.DataViewHolder> {

    private List<TrainerDetails> mAllFiles;
    private LayoutInflater mInflater;
    private Context mContext;

    public TrainerAdapter(Context context, List<TrainerDetails> inputElements) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAllFiles = inputElements;
        mContext = context;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trainer_detail, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataViewHolder holder, final int position) {

        final TrainerDetails item = mAllFiles.get(position);
        holder.mTrainerName.setText(item.getName());
        holder.mTrainerLocation.setText(item.getLocation());
        holder.mTrainerDistance.setText(""+item.getDistance() + mContext.getResources().getString(R.string.distance_unit));

        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.download);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
        Bitmap conv_bm = CircularImageClass.getRoundedShape(resized, 80 ,80);
        if(conv_bm != null) {
            holder.mTrainerPicture.setImageBitmap(conv_bm);
        }
    }

    @Override
    public int getItemCount() {
        return mAllFiles.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView mTrainerName , mTrainerLocation, mTrainerDistance;
        ImageView mTrainerPicture;

        public DataViewHolder(View view) {
            super(view);
            mTrainerName = (TextView) view.findViewById(R.id.trainer_name);
            mTrainerLocation = (TextView) view.findViewById(R.id.trainer_loc);
            mTrainerDistance = (TextView) view.findViewById(R.id.trainer_distance);
            mTrainerPicture = (ImageView) view.findViewById(R.id.trainer_img);

        }
    }


    public void update(List<TrainerDetails> aFiles) {
        if (aFiles == null) return;

        mAllFiles = aFiles;
        notifyDataSetChanged();
    }
}