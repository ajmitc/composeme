package composeme.song;

import java.util.HashMap;
import java.util.Map;

public class NoteCardDeck {
    private Map<String, NoteCard> noteCardMap = new HashMap<>();

    public NoteCardDeck(){
        noteCardMap.put("0000", new NoteCard("0000"));
        noteCardMap.put("0001", new NoteCard("0001"));

        noteCardMap.get("0000").getNotes().add(new Note(NoteEnum.C));
        noteCardMap.get("0000").getNotes().add(new Note(NoteEnum.D));
        noteCardMap.get("0000").getNotes().add(new Note(NoteEnum.E));
        noteCardMap.get("0000").getNotes().add(new Note(NoteEnum.F));

        noteCardMap.get("0001").getNotes().add(new Note(NoteEnum.G));
        noteCardMap.get("0001").getNotes().add(new Note(NoteEnum.A));
        noteCardMap.get("0001").getNotes().add(new Note(NoteEnum.B));
        noteCardMap.get("0001").getNotes().add(new Note(NoteEnum.R));
    }

    public NoteCard getCard(String code){
        if (noteCardMap.containsKey(code))
            return noteCardMap.get(code);
        return null;
    }
}
