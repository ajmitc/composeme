package composeme;

import composeme.song.Note;
import composeme.song.NoteCard;
import composeme.song.NoteEnum;
import composeme.song.Song;
import composeme.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;

        view.getSongPanel().getBtnPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /*
                Song song = new Song();
                song.getNotes().add(new Note(NoteEnum.A));
                song.getNotes().add(new Note(NoteEnum.B));
                song.getNotes().add(new Note(NoteEnum.C));
                song.getNotes().add(new Note(NoteEnum.D));
                song.getNotes().add(new Note(NoteEnum.E));
                song.getNotes().add(new Note(NoteEnum.F));
                song.getNotes().add(new Note(NoteEnum.G));
                 */

                String cardCodes = view.getSongPanel().getTaNoteCardCodes().getText();
                Song song = convertNoteCardCodesToSong(cardCodes);
                model.getPlayer().play(song);
            }
        });
    }

    private Song convertNoteCardCodesToSong(String codes){
        Song song = new Song();
        String[] cardCodes = codes.split(" ");
        for (String cardCode: cardCodes){
            NoteCard noteCard = model.getNoteCardDeck().getCard(cardCode);
            if (noteCard != null){
                song.getNotes().addAll(noteCard.getNotes());
            }
        }
        return song;
    }
}
