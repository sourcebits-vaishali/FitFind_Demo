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
public class TrainerRegisterFragment extends Fragment implements View.OnClickListener{

    private Spinner mSpeciality;
    private TextView mRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trainer_register , container , false);

        mSpeciality = (Spinner)view.findViewById(R.id.speciality);
        ArrayAdapter<String> trainerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.trainer_speciality));
        trainerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpeciality.setAdapter(trainerAdapter);

        View footer = view.findViewById(R.id.trainer_footer);

        mRegister = (TextView)footer.findViewById(R.id.registration);
        mRegister.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.registration:
                startActivity(new Intent(getActivity() , HomeAcivity.class));
                break;
        }
    }
}
