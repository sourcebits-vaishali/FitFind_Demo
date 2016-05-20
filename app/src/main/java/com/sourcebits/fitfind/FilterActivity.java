package com.sourcebits.fitfind;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sourcebits.fitfind.fragments.PersonalTrainerFragment;
import com.sourcebits.fitfind.model.DummyData;
import com.sourcebits.fitfind.model.TrainerDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vaishaliarora on 19/05/16.
 */
public class FilterActivity extends Activity implements View.OnClickListener{

    private EditText mDistanceFrom, mDistanceTo;
    private Button mSearch;
    private ToggleButton mToggle;
    private CheckBox mMale,mFemale,mNone;

    private boolean isDistance = false, isMobileTrainer = false, isMalePref = false, isFemalePref= false, noPref = false;
    private int mTo = 0,mFrom = 0;
    private String mSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_menu);

        mDistanceFrom = (EditText)findViewById(R.id.distance_from);
        mDistanceTo = (EditText)findViewById(R.id.distance_to);

        mToggle = (ToggleButton)findViewById(R.id.toggle);
        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMobileTrainer = isChecked ;
            }
        });

        mMale = (CheckBox)findViewById(R.id.male);
        mFemale = (CheckBox)findViewById(R.id.female);
        mNone = (CheckBox)findViewById(R.id.none);

        mMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMalePref = isChecked;
            }
        });

        mFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFemalePref = isChecked;
            }
        });

        mNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                noPref = isChecked;
            }
        });

        mSearch = (Button)findViewById(R.id.filter_search);
        mSearch.setOnClickListener(this);
    }

    class distanceCalculation implements Predicate<TrainerDetails> {

        @Override
        public boolean apply(TrainerDetails products) {
            return (products.getDistance() >= mFrom & products.getDistance() <= mTo) ;
        }
    }

    class sexPreference implements Predicate<TrainerDetails> {

        @Override
        public boolean apply(TrainerDetails products) {
            return (products.getSex().equalsIgnoreCase(mSex)) ;
        }
    }
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){

            case R.id.filter_search:
                applyFilterAction();
                setResult(RESULT_OK);
                this.finish();
                break;
        }
    }

    public void applyFilterAction(){
        List<TrainerDetails> list = DummyData.getData();
        Collection<TrainerDetails> filterRes = null;
        List<TrainerDetails> results = new ArrayList<>();
        String to = mDistanceTo.getText().toString();
        String from = mDistanceFrom.getText().toString();

        if( (to != null & !to.equals("")) &
                (from != null &!from.equals("")) ) {
            mTo = Integer.parseInt(to);
            mFrom = Integer.parseInt(from);
        }

        if(mFrom != 0 && mTo !=0){
            isDistance = true;
            filterRes = Collections2.filter(list, new distanceCalculation());
        } if(isMalePref) {
            mSex = getString(R.string.male);
            filterRes = Collections2.filter((filterRes!=null ? filterRes : list), new sexPreference());
        }if(isFemalePref) {
            mSex = getString(R.string.female);
            filterRes = Collections2.filter((filterRes!=null ? filterRes : list), new sexPreference());
        }if(noPref) {
            mSex = getString(R.string.none);
            filterRes = Collections2.filter((filterRes!=null ? filterRes : list), new sexPreference());
        }


        if(filterRes != null){
            results.addAll(filterRes);
            PersonalTrainerFragment.newInstance().updateList(results);
        } else {
            Toast.makeText(this, "No results found!",Toast.LENGTH_SHORT).show();
        }
    }

}
