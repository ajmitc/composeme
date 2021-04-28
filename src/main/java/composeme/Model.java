package composeme;

import composeme.player.JFuguePlayer;
import composeme.player.Player;

public class Model {
    private Player player;

    public Model(){
        player = new JFuguePlayer();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
