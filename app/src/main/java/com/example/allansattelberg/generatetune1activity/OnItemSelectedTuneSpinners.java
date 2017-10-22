package com.example.allansattelberg.generatetune1activity;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Allan Sattelberg on 8/17/2017.
 */

public class OnItemSelectedTuneSpinners implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        parent.getItemAtPosition(pos);
    }
    @Override
    public void onNothingSelected(AdapterView<?> foo){

    }
}
