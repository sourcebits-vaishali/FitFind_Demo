package com.sourcebits.fitfind.fragments;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourcebits.fitfind.R;
import com.sourcebits.fitfind.adapter.TrainerAdapter;
import com.sourcebits.fitfind.model.DummyData;
import com.sourcebits.fitfind.model.TrainerDetails;

import java.util.List;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class PersonalTrainerFragment extends Fragment {

    private static TrainerAdapter mAdapter;
    private static List<TrainerDetails> mElements;

    public PersonalTrainerFragment() {
    }

    public static PersonalTrainerFragment newInstance() {
        return new PersonalTrainerFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_trainer , container ,false);

        RecyclerView list = (RecyclerView)view.findViewById(R.id.trainer_list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(manager);

        list.addItemDecoration(new VerticalSpaceItemDecoration());

        mElements = DummyData.getData();
        mAdapter = new TrainerAdapter(getActivity() , mElements);
        list.setAdapter(mAdapter);

        return view;
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;

        public VerticalSpaceItemDecoration() {
            mDivider = ResourcesCompat.getDrawable(getResources(), R.drawable.line_divider, null);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
    public void updateList(List<TrainerDetails> newElements){
        mElements.clear();
        mElements = newElements;
        mAdapter.update(newElements);
    }

}
