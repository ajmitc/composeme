package composeme.song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteCardDeck {
    private Map<String, NoteCard> noteCardMap = new HashMap<>();

    public NoteCardDeck(){
        addNoteCard("0000", N.Cq(), N.Dq(), N.Eq(), N.Fq());
        addNoteCard("0001", N.Gq(), N.Aq(), N.Bq(), N.Rq());


        addNoteCard("0012", N.Bq(), N.Gh(), N.Bq());
        addNoteCard("0011", getCard("0012").flipNotes());

        addNoteCard("0143", N.Bq(), N.Cq(6), N.Dq(6), N.Bq());

        addNoteCard("0163", N.Gq(), N.Aq(6), N.Gq(), N.Ai(6), N.Bi(6));

        addNoteCard("0201", N.Bq(), N.Gq(), N.Gh());
        addNoteCard("0202", getCard("0201").flipNotes());

        addNoteCard("0212", N.Cq(6), N.Ai(), N.Ai(), N.Ci(6), N.Ci(6), N.Bq());
        addNoteCard("0211", getCard("0212").flipNotes());

        addNoteCard("0214", N.Bq(), N.Ci(6), N.Ci(6), N.Ai(), N.Ai(), N.Cq(6));
        //addNoteCard("0211", getCard("0212").flipNotes());

        addNoteCard("0222", N.Dq(6), N.Dq(6), N.Bi(), N.Ai(), N.Cq(6));
        addNoteCard("0221", getCard("0222").flipNotes());

        addNoteCard("0234", N.Ei(6), N.Di(6), N.Ci(6), N.Di(6), N.Eq(6), N.Aq());
        addNoteCard("0233", getCard("0234").flipNotes());

        addNoteCard("0242", N.Aq(), N.Fq(), N.Ei(), N.Ai(), N.Ci(6), N.Ei(6));
        addNoteCard("0241", getCard("0242").flipNotes());

        addNoteCard("0244", N.Ei(6), N.Ci(6), N.Ai(), N.Ei(), N.Fq(), N.Aq());
        addNoteCard("0263", N.Fq(), N.Fi(6), N.Ei(6), N.Ch(6));
        addNoteCard("0273", N.Fq(6), N.Di(6), N.Ci(6), N.Ai(), N.Gi(), N.Fq());
        addNoteCard("0283", N.Fh(), N.Cq(6), N.Fq());
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

    private void addNoteCard(String code, List<Note> notes){
        noteCardMap.put(code, new NoteCard(code));
        for (Note note: notes)
            noteCardMap.get(code).getNotes().add(note);
    }
}
