package com.sourcebits.fitfind;

/**
 * Created by vaishaliarora on 24/05/16.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sourcebits.fitfind.fragments.PersonalTrainerFragment;
import com.sourcebits.fitfind.model.DummyData;
import com.sourcebits.fitfind.model.TrainerDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
    String query;
    class searchResult implements Predicate<TrainerDetails> {

        @Override
        public boolean apply(TrainerDetails products) {
            return (products.getName().contains(query)) ;
        }
    }
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            List<TrainerDetails> list = DummyData.getData();
            Collection<TrainerDetails> searchRes = null;
            List<TrainerDetails> results = new ArrayList<>();

            searchRes = Collections2.filter(list, new searchResult());


            if(searchRes != null){
                results.addAll(searchRes);
                PersonalTrainerFragment.newInstance().updateList(results);
            } else {
                Toast.makeText(this, "No results found!", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
