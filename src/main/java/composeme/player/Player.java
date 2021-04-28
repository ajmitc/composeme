package composeme.player;

import composeme.song.Song;
import org.jfugue.pattern.Pattern;

public interface Player {
    void play(Song song);
    void play(Pattern song);
}
