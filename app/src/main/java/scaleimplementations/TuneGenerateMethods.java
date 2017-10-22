package scaleimplementations;

import android.util.Log;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Allan Sattelberg on 5/10/2017.
 */

public abstract class TuneGenerateMethods implements Tune {
    protected int[] scale;
    protected int minNoteLength;
    protected int beat;
    protected int numBeats;
    protected int numNotes;
    protected int numInScale;
    public static char[] notes = new char[]{'C', '#', 'D', '$', 'E', 'F', '%',
            'G', '&', 'A', 'b', 'B',};
    protected Queue<String> rythm;
    protected Queue<Character> tune;
    //methods to access variable values
    public int beat() {
        return this.beat;
    }

    public void setbeat(int x) {
        this.beat = x;
    }

    public int[] scaleAsInt() {
        return this.scale;
    }

    public int numNotes() {
        return this.numNotes;
    }
    public Queue<String> rythm(){return this.rythm;}
    public Queue<Character> tune(){return this.tune;}


    //secondary methods
    public int indexInScale(char c){
        int i = 0;
        while (this.notes[i] != c){
            i++;
        }
        return i;
    }
    public char scaleAt(int i) {
        return notes[this.scale[i]];
    }
    public static String simplifyFraction(int num, int denom) {
        String fraction = null;
        int gcd = -1;
        if (num == 1) {
            fraction = "[" + 1 + "/" + denom + "]";
        }
        if (denom == 1) {
            fraction = "[" + num + "]";
        }
        if (num == 0) {
            fraction = "0";
        }
        if (fraction == null) {
            gcd = gcd(num, denom);
            fraction = ("[" + num / gcd + "/" + denom / gcd + "]");
        }
        if (fraction.equals("[1/1]")) {
        }
        return fraction;
    }

    public String printTune() {
        String tuneAsString = "";
        for (int i = 0; i < this.tune.size(); i++) {
            char cNote = this.tune.remove();
            switch (cNote) {
                case '#': {
                    tuneAsString+="C# ";
                    break;
                }
                case '$': {
                    tuneAsString+="Eb ";
                    break;
                }
                case '%': {
                    tuneAsString+="F# ";
                    break;
                }
                case '&': {
                    tuneAsString+="G# ";
                    break;
                }
                case 'b': {
                    tuneAsString+="Bb ";
                    break;
                }
                case '*': {
                    tuneAsString+="rest ";
                    break;
                }
                default: {
                    tuneAsString+=cNote + " ";
                    break;
                }
            }
            this.tune.add(cNote);
        }
        return tuneAsString;
    }

    public static int gcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        } else if (n1 > n2) {
            return gcd(n2, n1);
        } else {
            return gcd(n1, (((int) ((long) n2 % n1) + n1) % n1));
        }
    }

    public void addStepPatternToScale(int[] stepPattern) {
        for (int i = 1; i < this.scale.length; i++) {
            //the following computes true mod from the formula
            //a mod b = ((a remainder b) + b) remainder b
            //thus using "clock face logic" to get the correct note in this.scale
            this.scale[i] = ((this.scale[i - 1] + stepPattern[i - 1]) % 11 + 11)
                    % 11;
        }
    }

    public Queue<Character> generateTune() {
        Queue<Character> tune = new LinkedList<Character>();
        int notesGenerated = 0;
        while (tune.size() < this.numNotes()) {
            int nextNote = ThreadLocalRandom.current().nextInt(0, 2 * numInScale + 1);
            /*
             * here we attempt to generate a rest, adding the index in notes for
             * a rest if we do, adding the index in steps if we don't.
             */
            if (nextNote == 2 * numInScale + 1) {
                tune.add('*');
            } else {
                tune.add(scaleAt(nextNote));
            }
            notesGenerated++;

        }
        Log.d("generateTune", notesGenerated + " notes generated" );
        this.tune = tune;
        return tune;
    }

    /**
     * careful: this is independent of which note has the beat.
     *
     * @param minNumNotes the minimum number of notes to be generated
     * @return A queue of note lengths represented as fractions of a whole note.
     */
    public Queue<String> generateRythm(int minNumNotes, boolean snapToBeat) {
        Queue<String> beats = new LinkedList<String>();
        Log.d("mnl/numbeats/beat", this.minNoteLength + "/" + this.numBeats + "/" + this.beat);
        BigInteger beatsAsSmallest = BigInteger
                .valueOf(this.minNoteLength * this.numBeats / this.beat);
        while (beatsAsSmallest.compareTo(BigInteger.valueOf(0)) == 1) {
            /*
             * generates a number, x, that represents the note of length
             * nextNote/minNoteLength
             */
            int nextNote = (int) ThreadLocalRandom.current().nextLong(1,
                    beatsAsSmallest.divide(BigInteger.valueOf(minNumNotes))
                            .longValue() + 1);
            Log.d("NextNoteGen", "BaS is " + beatsAsSmallest.toString());
            beatsAsSmallest = beatsAsSmallest
                    .subtract(BigInteger.valueOf(nextNote));

            Log.d("NextNoteGen","next note is " + nextNote);

            Log.d("NextNoteGen", "BaS after is " + beatsAsSmallest.toString());
            if (minNumNotes > 1) {
                minNumNotes--;
            }
            beats.add(simplifyFraction(nextNote, this.minNoteLength));
            //// FIXME: 8/23/2017 
            //note for fix, fixed singular beat represented as minnotelength
            if (snapToBeat) {
                while (beatsAsSmallest
                        .mod(BigInteger.valueOf(this.minNoteLength/this.beat))
                        .compareTo(BigInteger.valueOf(0)) != 0) {
                    nextNote = (int) ThreadLocalRandom.current().nextLong(1,
                            BigInteger.valueOf(this.beat * this.minNoteLength)
                                    .divide(BigInteger.valueOf(minNumNotes))
                                    .longValue() + 1);
                    beats.add(simplifyFraction(nextNote, this.minNoteLength));
                    beatsAsSmallest = beatsAsSmallest
                            .subtract(BigInteger.valueOf(nextNote));
                    if (minNumNotes > 1) {
                        minNumNotes--;
                    }
                }

            }
        }
        //this.numNotes = beats.size();
        Log.d("tuneMethods", "beats before return is " + beats.toString());
        return beats;
    }

    public Queue<Integer> octaves() {
        Integer octave = 0;
        Queue<Integer> octaves = new LinkedList<Integer>();
        for (int i = 0; i < this.numNotes; i++) {
            octaves.add(octave);
            switch (ThreadLocalRandom.current().nextInt(0, 4)) {
                case 0: {
                    octave++;
                    break;
                }
                case 1: {
                    octave--;
                }
                default: {
                    //50% of retaining octave
                }
            }
        }
        return octaves;
    }
    public void setReferenceScale(int root) {
        this.scale[0] = root;
    }
}

