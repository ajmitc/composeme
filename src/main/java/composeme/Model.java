package composeme;

import composeme.player.JFuguePlayer;
import composeme.player.Player;
import composeme.song.N;
import composeme.song.NoteCardDeck;
import composeme.song.Song;

public class Model {
    private Player player;
    private NoteCardDeck noteCardDeck = new NoteCardDeck();
    private Song song;

    public Model(){
        player = new JFuguePlayer();

        /*
        song = new Song();
        song.getNotes().add(N.Cq());
        song.getNotes().add(N.Dq());
        song.getNotes().add(N.Eq());
        song.getNotes().add(N.Fq());
        song.getNotes().add(N.Gq());
        song.getNotes().add(N.Aq());
        song.getNotes().add(N.Bq());
        song.getNotes().add(N.Cq(6));
        song.getNotes().add(N.Dq(6));
        song.getNotes().add(N.Eq(6));
        song.getNotes().add(N.Fq(6));
         */
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public NoteCardDeck getNoteCardDeck() {
        return noteCardDeck;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
