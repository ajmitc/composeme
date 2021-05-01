package composeme.song;

import java.util.ArrayList;
import java.util.List;

public class NoteCard {
    private String code;
    private List<Note> notes = new ArrayList<>();

    public NoteCard(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void addNotes(Note ... notes){
        for (Note note: notes)
            this.notes.add(note);
    }

    public List<Note> flipNotes(){
        List<Note> flipped = new ArrayList<>();
        this.notes.stream().forEach(note -> {
            flipped.add(flipNote(note));
        });
        return flipped;
    }

    private Note flipNote(Note note){
        // Flip note on B5
        int middleB = N.Bq().getNoteValue();
        int noteValue = note.getNoteValue();
        int diff = (middleB - noteValue);
        int newValue = middleB + diff;
        int newOctave = newValue / 7;
        NoteEnum newNote = NoteEnum.values()[newValue % 7];
        Note flippedNote = new Note(newNote, newOctave, note.getAccidental(), note.getDuration(), note.isDottedDuration());
        return flippedNote;
    }
}
