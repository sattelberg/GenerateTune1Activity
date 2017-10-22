package scaleimplementations;

import android.util.Log;

/**
 * Created by Allan Sattelberg on 8/17/2017.
 */

public class HeptatonicMajor extends TuneGenerateMethods {
    public HeptatonicMajor() {
        this.scale = new int[]{0, 2, 4, 5, 7, 9, 11, 0, 11, 9, 7, 5, 4, 2,
                0};
        this.numBeats = 4;
        this.beat = 1;
        this.numNotes = 12;
        this.minNoteLength = 64;
        this.numInScale = 7;
    }

    public HeptatonicMajor(char root, int numBeats, int numNotes, int minNoteLength, int beatNote) {
        this.scale = new int[]{0, 2, 4, 5, 7, 9, 11, 0, 11, 9, 7, 5, 4, 2,
                0};
        Log.d("tuneobject", "this.scale established");
        int[] stepPattern = {2,2,1,2,2,2,1,-1,-2,-2,-2,-1,-2,-2};
        this.numInScale = 7;
        this.scale[0] = indexInScale(root);
        this.numBeats = numBeats;
        this.numNotes = numNotes;
        this.minNoteLength = minNoteLength;
        this.beat = beatNote;
        this.addStepPatternToScale(stepPattern);
        Log.d("tuneobject", "this.scale added step pattern");
        this.rythm = this.generateRythm(this.numNotes, true);
        Log.d("tuneobject", "rhythm generated");
        Log.d("tuneobject","rhythm after generation is " + this.rythm.toString());
        this.generateTune();
        Log.d("tuneobject", "this.tune generated");
        Log.d("tuneobject","tune after generation is " + this.tune.toString());
    }
}
