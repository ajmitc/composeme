package composeme.song;

public class Note {
    private NoteEnum note;
    private int octave = 5;
    private Accidental accidental;
    private Duration duration = Duration.QUARTER;
    private boolean dottedDuration = false; // Add 50% of duration

    public Note(){

    }

    public Note(NoteEnum note){
        this.note = note;
    }

    public Note(NoteEnum note, int octave, Accidental accidental, Duration duration, boolean dottedDuration){
        this.note = note;
        this.octave = octave;
        this.accidental = accidental;
        this.duration = duration;
        this.dottedDuration = dottedDuration;
    }

    public NoteEnum getNote() {
        return note;
    }

    public void setNote(NoteEnum note) {
        this.note = note;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public Accidental getAccidental() {
        return accidental;
    }

    public void setAccidental(Accidental accidental) {
        this.accidental = accidental;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean isDottedDuration() {
        return dottedDuration;
    }

    public void setDottedDuration(boolean dottedDuration) {
        this.dottedDuration = dottedDuration;
    }
}
