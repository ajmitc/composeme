package composeme;

import composeme.player.JFuguePlayer;
import composeme.player.Player;
import composeme.song.NoteCardDeck;

public class Model {
    private Player player;
    private NoteCardDeck noteCardDeck = new NoteCardDeck();

    public Model(){
        player = new JFuguePlayer();
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
}
