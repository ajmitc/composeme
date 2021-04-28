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
}
