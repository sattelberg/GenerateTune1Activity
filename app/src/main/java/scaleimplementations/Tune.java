package scaleimplementations;

import java.util.Queue;

/**
 * Created by Allan Sattelberg on 8/17/2017.
 */

 public interface Tune {
    int beat();

    void setbeat(int x);
    int[] scaleAsInt();

    int numNotes();
    Queue<String> rythm();
    Queue<Character> tune();


    //secondary method
    char scaleAt(int i);

     void addStepPatternToScale(int[] stepPattern);

     Queue<Character> generateTune();
    /**
     * careful: this is independent of which note has the beat.
     *
     * @param minNumNotes the minimum number of notes to be generated
     * @return A queue of note lengths represented as fractions of a whole note.
     */
    public Queue<String> generateRythm(int minNumNotes, boolean snapToBeat);
    public Queue<Integer> octaves();
    public void setReferenceScale(int root);
    public String printTune();
}
