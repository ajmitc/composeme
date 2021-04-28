package composeme;

import composeme.song.Note;
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
                Song song = new Song();
                song.getNotes().add(new Note(NoteEnum.A));
                song.getNotes().add(new Note(NoteEnum.B));
                song.getNotes().add(new Note(NoteEnum.C));
                song.getNotes().add(new Note(NoteEnum.D));
                song.getNotes().add(new Note(NoteEnum.E));
                song.getNotes().add(new Note(NoteEnum.F));
                song.getNotes().add(new Note(NoteEnum.G));
                model.getPlayer().play(song);
            }
        });
    }
}
