package composeme.song;

import java.util.HashMap;
import java.util.Map;

public class NoteCardDeck {
    private Map<String, NoteCard> noteCardMap = new HashMap<>();

    public NoteCardDeck(){
        addNoteCard("0000", N.Cq(), N.Dq(), N.Eq(), N.Fq());
        addNoteCard("0001", N.Gq(), N.Aq(), N.Bq(), N.Rq());


        addNoteCard("0012", N.Bq(), N.Gh(), N.Bq());

        addNoteCard("0201", N.Bq(), N.Gq(), N.Gh());
        addNoteCard("0212", N.Cq(6), N.Ai(), N.Ai(), N.Ci(6), N.Ci(6), N.Bq());
        addNoteCard("0222", N.Dq(6), N.Dq(6), N.Bi(), N.Ai(), N.Cq(6));
        addNoteCard("0234", N.Ei(6), N.Di(6), N.Ci(6), N.Di(6), N.Eq(6), N.Aq());
        addNoteCard("0242", N.Aq(), N.Fq(), N.Ei(), N.Ai(), N.Ci(6), N.Ei(6));
    }

    public NoteCard getCard(String code){
        if (noteCardMap.containsKey(code))
            return noteCardMap.get(code);
        return null;
    }


    private void addNoteCard(String code, Note ... notes){
        noteCardMap.put(code, new NoteCard(code));
        for (Note note: notes)
            noteCardMap.get(code).getNotes().add(note);
    }
}
