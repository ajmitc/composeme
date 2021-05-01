package composeme.player;

import composeme.song.Note;
import composeme.song.Song;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JFuguePlayer implements composeme.player.Player {
    private static Logger logger = Logger.getLogger(JFuguePlayer.class.getName());

    public JFuguePlayer(){
    }

    public void play(Song song){
        String fugueSong = toJFugueSong(song);
        logger.info(fugueSong);
        new Player().play(fugueSong);

        // Different V# are played simultaneously
        //Player player = new Player();
        //Pattern p1 = new Pattern("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq");
        //Pattern p2 = new Pattern("V1 I[Flute] Rw     | Rw     | GmajQQQ  CmajQ");
        //player.play(p1, p2);
    }

    public void play(Pattern song){
        Player player = new Player();
        player.play(song);
    }

    private String toJFugueSong(Song song){
        List<String> noteList = new ArrayList<>();
        System.out.println("JFuguePlayer - instrument = " + song.getInstrument());
        if (song.getInstrument() != null){
            noteList.add("I[" + song.getInstrument().getName() + "]");
        }
        for (Note note: song.getNotes()){
            StringBuilder sb = new StringBuilder();
            sb.append(note.getNote().name());
            if (note.getAccidental() != null)
                sb.append(note.getAccidental().getCode());
            sb.append(note.getOctave());
            sb.append(note.getDuration().getCode());
            if (note.isDottedDuration())
                sb.append(".");
            noteList.add(sb.toString());
        }
        return noteList.stream().collect(Collectors.joining(" "));
        /*
        return "F5q F5i F5q G5i A5q. A5q A5i G5q F5i G5q A5i F5q. Rq. " +
            // "Down came the rain and washed the spider out."
            "A5q. A5q Bb5i C6q. C6q. Bb5q A5i Bb5q C6i A5q. Rq. " +
            // "Out came the sun and dried up all the rain, so the"
            "F5q. F5q G5i A5q. A5q. G5q F5i G5q A5i F5q. C5q C5i " +
            // "itsy, bitsy spider went up the spout again."
            "F5q F5i F5q G5i A5q. A5q A5i G5q F5i G5q A5i F5q. Rq.";
         */
    }

    public void save(Song song, String path){
        // save as a midi file for use in the next example
        //player.saveMidi(song, new File("spider.midi"));
    }
}
