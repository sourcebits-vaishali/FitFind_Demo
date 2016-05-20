package com.sourcebits.fitfind.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourcebits.fitfind.R;
import com.sourcebits.fitfind.custom.ExpandableTextView;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class FitnessClassFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness_class, container , false);
        ExpandableTextView description = (ExpandableTextView)view.findViewById(R.id.description);
        description.setText(getActivity().getString(R.string.random_text));
        return view;
    }
}
