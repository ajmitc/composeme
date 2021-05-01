package composeme;

import composeme.song.Instrument;
import composeme.song.NoteCard;
import composeme.song.Song;
import composeme.view.View;
import org.jfugue.pattern.Pattern;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.concurrent.CompletableFuture;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;

        view.getSongPanel().getBtnPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateFromCodes();
                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        model.getPlayer().play(model.getSong());
                    }
                });

                //model.getPlayer().play(getItsyBitsySpider());
            }
        });

        view.getSongPanel().getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getSongPanel().getTaNoteCardCodes().setText("");
            }
        });

        view.getSongPanel().getCbInstrument().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (model.getSong() != null){
                    model.getSong().setInstrument((Instrument) view.getSongPanel().getCbInstrument().getSelectedItem());
                    //System.out.println("Set Instrument to " + model.getSong().getInstrument());
                }
            }
        });

        view.getNoteCardPanelJList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            }
        });

        view.getNoteCardPanelJList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() >= 2){
                    NoteCard noteCard = view.getNoteCardPanelJList().getSelectedValue();
                    view.getSongPanel().getTaNoteCardCodes().setText(view.getSongPanel().getTaNoteCardCodes().getText() + " " + noteCard.getCode());
                    updateFromCodes();
                }
            }
        });

        updateFromCodes();
    }

    private void updateFromCodes(){
        String cardCodes = view.getSongPanel().getTaNoteCardCodes().getText();
        Song song = convertNoteCardCodesToSong(cardCodes);
        model.setSong(song);
        view.updateClefsFromModel();
        view.refresh();
    }

    private Song convertNoteCardCodesToSong(String codes){
        Song song = new Song();
        //song.setInstrument(Instrument.VOICE);
        String[] cardCodes = codes.split(" ");
        for (String cardCode: cardCodes){
            NoteCard noteCard = model.getNoteCardDeck().getCard(cardCode);
            if (noteCard != null){
                song.getNotes().addAll(noteCard.getNotes());
            }
        }
        return song;
    }

    private Pattern getItsyBitsySpider(){
        Pattern header = new Pattern("KFmaj T100 V0 I[Trumpet] V1 I[CHURCH_ORGAN] ");
        // "Itsy, bitsy spider, climbed up the water spout."
        // and "itsy, bitsy spider went up the spout again."
        Pattern pattern1 = new Pattern("V0 F5q F5i F5q G5i | A5q. A5q A5i | G5q F5i G5q A5i | F5q. Rq. | ");
        // "Down came the rain and washed the spider out."
        Pattern pattern2 = new Pattern("V0 A5q. A5q B5i | C6q. C6q. | B5q A5i B5q C6i | A5q. Rq. | ");
        // "Out came the sun and dried up all the rain, so the"
        Pattern pattern3 = new Pattern("V0 F5q. F5q G5i | A5q. A5q. | G5q F5i G5q A5i | F5q. C5q C5i | ");


        //1st, 3rd, and 4th lines (third chord specified as notes)
        Pattern chord1 = new Pattern("V1 Fmajh. | Fmajh. | E3h.+G3h.+C4h. | Fmajh. | ");
        //2nd line
        Pattern chord2 = new Pattern("V1 Fmajh. | Fmajh. | Bmajh. | Fmajh. | ");

        // Put the whole song together
        Pattern song = new Pattern();
        song.add(header);

        //melody
        song.add(pattern1);
        song.add(pattern2);
        song.add(pattern3);
        song.add(pattern1);


        //chords
        song.add(chord1);
        song.add(chord2);
        song.add(chord1, 2);

        return song;
    }
}
