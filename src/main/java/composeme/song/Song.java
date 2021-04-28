package composeme.song;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private String name = "Default";
    private List<Note> notes = new ArrayList<>();

    public Song(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
