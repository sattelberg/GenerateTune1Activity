package com.example.allansattelberg.generatetune1activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import scaleimplementations.HeptatonicMajor;
import scaleimplementations.Tune;

public class GetInformationActivity extends AppCompatActivity {
    Spinner rootSpinner;
    Spinner scaleSpinner;
    TextView minNoteLengthView;
    TextView numBeatsView;
    TextView numNotesView;
    TextView rhythmView;
    TextView beatView;
    TextView notesView;
    Tune tune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getinformation);
        setUpSpinners();
        setUpTextViews();
    }

    private boolean checkAllFields() {
        Log.d("Check input","check integers");
        if(isInteger(minNoteLengthView.getText()) && isInteger(numNotesView.getText()) && isInteger(numBeatsView.getText()) && isInteger(beatView.getText().toString()))
        {
            Log.d("Check input","check valid integers");
            if (Integer.parseInt(minNoteLengthView.getText().toString()) < Integer.MAX_VALUE && Integer.parseInt(numNotesView.getText().toString()) < Integer.MAX_VALUE && Integer.parseInt(numBeatsView.getText().toString()) < Integer.MAX_VALUE && Integer.parseInt(beatView.getText().toString()) < Integer.MAX_VALUE && Integer.parseInt(beatView.getText().toString()) <= Integer.parseInt(minNoteLengthView.getText().toString())) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
    private Character getRootFromSpinner(){
        String selection = rootSpinner.getSelectedItem().toString();
        if (selection.length() > 1){
            if (selection.equals("C#/Db")) {
                return '#';
            }else if (selection.equals("D#/Eb")){
                return '$';
            }else if (selection.equals("F#")){
                return '%';
            }else if (selection.equals("G#/Ab")){
                return '&';
            }else if (selection.equals("A#/Bb")){
                return 'b';
            }
        }else{
            return selection.charAt(0);
        }
        return'*';
    }
    public static boolean isInteger(CharSequence s) {
        if(s.length() == 0) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),10) < 0) return false;
        }
        return true;
    }

    public void setUpSpinners() {
        rootSpinner = (Spinner) findViewById(R.id.rootSpinner);
        scaleSpinner = (Spinner) findViewById(R.id.scaleSpinner);
        ArrayAdapter<CharSequence> rootAdapter = ArrayAdapter.createFromResource(this, R.array.RootNoteSpinner, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> scaleAdapter = ArrayAdapter.createFromResource(this, R.array.ScaleTypeSpinner, android.R.layout.simple_spinner_item);
        rootAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rootSpinner.setAdapter(rootAdapter);
        scaleSpinner.setAdapter(scaleAdapter);
        //rootSpinner.setOnItemSelectedListener(new OnItemSelectedTuneSpinners());
        //scaleSpinner.setOnItemSelectedListener(new OnItemSelectedTuneSpinners());
    }

    public void setUpTextViews() {
        minNoteLengthView = (TextView) findViewById(R.id.minNote);
        numBeatsView = (TextView) findViewById(R.id.numBeats);
        numNotesView = (TextView) findViewById(R.id.numNotes);
        notesView = (TextView) findViewById(R.id.notesview);
        rhythmView = (TextView) findViewById(R.id.rythmView);
        beatView = (TextView) findViewById(R.id.beatNote);
    }

    public void onGenerate(View view) {
        if (checkAllFields()) {
            Log.d("Check input","all checks passed");
            String spinval = scaleSpinner.getSelectedItem().toString();
            Log.d("Check spinner", spinval);
            if (scaleSpinner.getSelectedItem().toString().equals("Heptatonic-Major")) {

                    Log.d("tuneobject","making tune object");
                    tune = new HeptatonicMajor(getRootFromSpinner(), Integer.valueOf(numBeatsView.getText().toString()), Integer.valueOf(numNotesView.getText().toString()), Integer.valueOf(minNoteLengthView.getText().toString()),Integer.valueOf(beatView.getText().toString()));
                    Log.d("tuneobject","tune object made");

            }
            notesView.setText(tune.printTune());
            Log.d("displayresults","tune printed");
            Log.d("displayresults","tune is " + tune.printTune());
            rhythmView.setText(tune.rythm().toString());
            Log.d("displayresults","rhythym printed");
            Log.d("displayresults","rhythm is " + tune.rythm().toString());
        } else {
            notesView.setText("You made a mistake");
            rhythmView.setText("Or I did.");
        }
    }
}
