package composeme.view;

import composeme.Model;
import composeme.song.Duration;
import composeme.song.N;
import composeme.song.Note;
import composeme.song.Song;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This panel shows a clef with time signature, bars, measures, notes, etc.
 */
public class ClefPanel extends JPanel {
    private static final int LINE_SPACING = 16;
    private static final int TOP_LINE_YOFFSET = LINE_SPACING * 2;

    private static final Stroke END_BAR_STROKE = new BasicStroke(3.f);
    private static final Font CLEF_FONT = new Font("Bravura", Font.PLAIN, 72);
    private static final Point CLEF_POINT = new Point(0, TOP_LINE_YOFFSET + (LINE_SPACING * 4) - 6);
    private static final String G_CLEF_UNICODE = "\uD834\uDD1E";
    private static final String F_CLEF_UNICODE = "\uD834\uDD22";

    private static final int NUM_CARDS_PER_LINE = 3;
    private static final int NUM_BEATS_PER_CARD = 4;
    private static final int NOTES_XOFFSET = 70;
    private static final int NOTE_WIDTH  = LINE_SPACING + 2;
    private static final int NOTE_HEIGHT = LINE_SPACING;
    private static final int NOTE_SPACING = 40;
    private static final int NOTE_STAFF_LENGTH = 40;
    private static final int NOTE_DOT_SIZE = 3;
    private static final Stroke LONG_NOTE_STROKE = new BasicStroke(2.f);

    private Model model;
    private View view;
    private int lineNum;

    public ClefPanel(Model model, View view, int lineNumber){
        super();
        this.model = model;
        this.view = view;
        this.lineNum = lineNumber;
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw lines
        g.setColor(Color.BLACK);
        for (int i = 0; i < 5; ++i)
            g.drawLine(0, TOP_LINE_YOFFSET + (i * LINE_SPACING), getWidth(), TOP_LINE_YOFFSET + (i * LINE_SPACING));

        // Draw end bars
        Stroke oldStroke = g.getStroke();
        g.setStroke(END_BAR_STROKE);
        g.drawLine(0, TOP_LINE_YOFFSET, 0, TOP_LINE_YOFFSET + (LINE_SPACING * 4));
        g.drawLine(getWidth() - 3, TOP_LINE_YOFFSET, getWidth() - 3, TOP_LINE_YOFFSET + (LINE_SPACING * 4));
        g.setStroke(oldStroke);

        // Draw Clef
        Font oldFont = g.getFont();
        g.setFont(CLEF_FONT);
        g.drawString(G_CLEF_UNICODE, CLEF_POINT.x, CLEF_POINT.y);
        g.setFont(oldFont);

        // Draw cards
        g.setColor(Color.BLACK);
        Song song = model.getSong();
        if (song != null) {
            List<Note> noteList = song.getNotes();
            double startBeat = (lineNum - 1) * (NUM_CARDS_PER_LINE * NUM_BEATS_PER_CARD);
            double lastBeat = lineNum * (NUM_CARDS_PER_LINE * NUM_BEATS_PER_CARD);
            int startIndex = 0;
            double totalBeats = 0;
            while (startIndex < noteList.size() && totalBeats < startBeat) {
                Note note = noteList.get(startIndex);
                if (totalBeats + note.getNumBeats() == startBeat)
                    break;
                totalBeats += note.getNumBeats();
                ++startIndex;
            }
            int index = startIndex;
            while (index < noteList.size()) {
                Note note = noteList.get(index);
                drawNote(g, note, index - startIndex);
                totalBeats += note.getNumBeats();
                if (totalBeats == lastBeat)
                    break;
                ++index;
            }
        }
    }

    private void drawNote(Graphics2D g, Note note, int noteIndex){
        int x = NOTES_XOFFSET + (noteIndex * NOTE_SPACING);
        // Top line is F6
        int F6Value = N.Fq(6).getNoteValue();
        int myValue = note.getNoteValue();
        int diff = F6Value - myValue;
        int y = TOP_LINE_YOFFSET + (diff * (LINE_SPACING / 2)) - (NOTE_HEIGHT / 2);

        // TODO If y < TOP_LINE_YOFFSET or y > bottom line, figure out if on line or not and draw small horizontal line(s) above/below/behind note
        if (note.getNoteValue() > N.Gq(6).getNoteValue()){
            int lineDiff = N.Gq(6).getNoteValue() - myValue;
            if (lineDiff % 2 == 1){
                // Draw line
                g.drawLine(x - 10, y, x + 10, y);
            }
        }
        if (note.getNoteValue() < N.Dq().getNoteValue()){
            int lineDiff = N.Dq().getNoteValue() - myValue;
            if (lineDiff % 2 == 1){
                // Draw line
                g.drawLine(x - 7, y + (NOTE_HEIGHT / 2), x + NOTE_WIDTH + 7, y + (NOTE_HEIGHT / 2));
            }
        }

        if (note.getDuration().getBeats() <= 1.0)
            g.fillOval(x, y, NOTE_WIDTH, NOTE_HEIGHT);
        else {
            Stroke oldStroke = g.getStroke();
            g.setStroke(LONG_NOTE_STROKE);
            g.drawOval(x, y, NOTE_WIDTH, NOTE_HEIGHT);
            g.setStroke(oldStroke);
        }

        if (note.getDuration() != Duration.WHOLE){
            // Draw note staff
            // If below B5, draw staff up, else draw staff down
            if (myValue < N.Bq().getNoteValue())
                g.drawLine(x + NOTE_WIDTH - 1, y + (NOTE_HEIGHT / 2), x + NOTE_WIDTH - 1, y - NOTE_STAFF_LENGTH);
            else
                g.drawLine(x, y + (NOTE_HEIGHT / 2), x, y + NOTE_STAFF_LENGTH + (NOTE_HEIGHT / 2));
        }

        if (note.getDuration().getBeats() < 1.0){
            // TODO Draw flag(s) on staff
        }

        if (note.isDottedDuration()){
            g.fillOval(x + NOTE_WIDTH + 3, y, NOTE_DOT_SIZE, NOTE_DOT_SIZE);
        }
    }

    public void refresh(){
        repaint();
    }
}
