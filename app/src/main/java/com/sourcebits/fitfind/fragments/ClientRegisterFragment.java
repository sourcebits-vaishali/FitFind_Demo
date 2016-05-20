package com.sourcebits.fitfind.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sourcebits.fitfind.HomeAcivity;
import com.sourcebits.fitfind.R;

/**
 * Created by vaishaliarora on 18/05/16.
 */
public class ClientRegisterFragment extends Fragment implements View.OnClickListener{

    private Spinner mGoals;
    private Spinner mTrainer;
    private TextView mRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_register , container , false);

        mGoals = (Spinner)view.findViewById(R.id.goal);
        ArrayAdapter<String> goalsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.client_goals));
        goalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGoals.setAdapter(goalsAdapter);

        //TODO - Trainer preference text is missing

        mTrainer = (Spinner)view.findViewById(R.id.trainer);
        ArrayAdapter<String> trainerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.trainers));
        trainerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTrainer.setAdapter(trainerAdapter);

        View footer = view.findViewById(R.id.client_footer);

        mRegister = (TextView)footer.findViewById(R.id.registration);
        mRegister.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.registration:
                startActivity(new Intent(getActivity(), HomeAcivity.class));
                break;
        }
    }
}
