package composeme.view;

import composeme.Model;
import composeme.song.Duration;
import composeme.song.N;
import composeme.song.Note;
import composeme.song.Song;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    public static final int NUM_CARDS_PER_LINE = 3;
    public static final int NUM_BEATS_PER_CARD = 4;
    private static final int NOTES_XOFFSET = 70;
    private static final int NOTE_WIDTH  = LINE_SPACING + 2;
    private static final int NOTE_HEIGHT = LINE_SPACING;
    private static final int NOTE_SPACING = 45;
    private static final int NOTE_STAFF_LENGTH = 40;
    private static final int NOTE_DOT_SIZE = 3;
    private static final Stroke LONG_NOTE_STROKE = new BasicStroke(2.f);
    private static final Stroke STAFF_FLAG_STROKE = new BasicStroke(2.f);

    private Model model;
    private View view;
    private int lineNum;
    private List<Note> notes;

    private String lastCardCode;

    private boolean drawCardCode = true;
    private Color backgroundColor = Color.WHITE;

    public ClefPanel(Model model, View view, int lineNumber){
        super();
        this.model = model;
        this.view = view;
        this.lineNum = lineNumber;
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
    }

    public void setNotesFromModel(){
        notes = new ArrayList<>();
        Song song = model.getSong();
        if (song != null) {
            List<Note> noteList = song.getNotes();
            // Clef 1: [0-12)
            // Clef 2: [12-24)
            double startBeat = lineNum * (NUM_CARDS_PER_LINE * NUM_BEATS_PER_CARD);
            double lastBeat = (lineNum + 1) * (NUM_CARDS_PER_LINE * NUM_BEATS_PER_CARD);
            int startIndex = 0;
            double totalBeats = 0.0;
            while (startIndex < noteList.size() && totalBeats <= startBeat) {
                Note note = noteList.get(startIndex);
                if (totalBeats + note.getNumBeats() > startBeat)
                    break;
                totalBeats += note.getNumBeats();
                ++startIndex;
            }

            int index = startIndex;
            while (index < noteList.size()) {
                Note note = noteList.get(index);
                if (totalBeats + note.getNumBeats() > lastBeat)
                    break;
                notes.add(note);
                totalBeats += note.getNumBeats();
                ++index;
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(backgroundColor);
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
        lastCardCode = null;
        g.setColor(Color.BLACK);
        if (notes != null && !notes.isEmpty()) {
            int index = 0;
            while (index < notes.size()) {
                List<Note> beatNotes = getNextBeatNotes(index);
                drawBeatNotes(g, beatNotes, index);
                index += beatNotes.size();
            }
        }
    }

    private List<Note> getNextBeatNotes(int index){
        double beats = 0.0;
        List<Note> notesInBeat = new ArrayList<>();
        for (int i = index; i < this.notes.size(); ++i){
            Note note = this.notes.get(i);
            // If this note is a quarter note or more and it's the first note we've looked at, return just this note
            if (note.getDuration().getBeats() >= 1.0 && notesInBeat.isEmpty()){
                notesInBeat.add(note);
                break;
            }

            // If this note is less than a quarter note, see where the beat is at if we add it to the list
            double beatsAfterNote = beats + note.getNumBeats();
            // If adding this note will cause the beats to exceed 1.0, return what we have so far
            if (beatsAfterNote > 1.0000001)
                break;
            else {
                // Beats <= 1.0, add the note and look at the next one
                notesInBeat.add(note);
                beats = beatsAfterNote;
            }
        }
        return notesInBeat;
    }


    /**
     * Draw the notes in the beat and return the number of beats
     * @param g
     * @param beatNotes
     * @param noteIndex
     * @return
     */
    private double drawBeatNotes(Graphics2D g, List<Note> beatNotes, int noteIndex){
        double totalBeats = 0.0;
        Boolean drawUp = null;
        for (int i = 0; i < beatNotes.size(); ++i){
            Note note = beatNotes.get(i);
            if (drawUp == null) {
                drawUp = note.getNoteValue() < N.Bq().getNoteValue();
            }
            drawNote(g, note, noteIndex + i, drawUp);
            totalBeats += note.getNumBeats();
        }
        if (beatNotes.size() > 1){
            for (int i = 0; i < beatNotes.size() - 1; ++i){
                Note note1 = beatNotes.get(i);
                Note note2 = beatNotes.get(i + 1);
                connectNoteStaffs(g, note1, note2, noteIndex + i, drawUp);
            }
        }
        else if (beatNotes.get(0).getDuration().getBeats() < 1.0){
            drawFlag(g, beatNotes.get(0), noteIndex);
        }
        return totalBeats;
    }

    private void drawNote(Graphics2D g, Note note, int noteIndex, boolean drawStaffUp){
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
            int staffx0 = 0;
            int staffy0 = 0;
            int staffx1 = 0;
            int staffy1 = 0;

            if (drawStaffUp){
                staffx0 = x + NOTE_WIDTH - 1;
                staffy0 = y + (NOTE_HEIGHT / 2);
                staffx1 = x + NOTE_WIDTH - 1;
                staffy1 = y - NOTE_STAFF_LENGTH;
            }
            else {
                staffx0 = x;
                staffy0 = y + (NOTE_HEIGHT / 2);
                staffx1 = x;
                staffy1 = y + NOTE_STAFF_LENGTH + (NOTE_HEIGHT / 2);
            }
            Stroke oldStroke = g.getStroke();
            g.setStroke(STAFF_FLAG_STROKE);
            g.drawLine(staffx0, staffy0, staffx1, staffy1);
            g.setStroke(oldStroke);
        }

        if (note.isDottedDuration()){
            g.fillOval(x + NOTE_WIDTH + 3, y, NOTE_DOT_SIZE, NOTE_DOT_SIZE);
        }

        if (drawCardCode && (lastCardCode == null || !lastCardCode.equals(note.getCardCode()))){
            int codey = TOP_LINE_YOFFSET - 15;
            g.drawString(note.getCardCode(), x, codey);
            lastCardCode = note.getCardCode();
        }
    }


    private void drawFlag(Graphics2D g, Note note, int noteIndex){
        int x = NOTES_XOFFSET + (noteIndex * NOTE_SPACING);
        int note1Value = note.getNoteValue();
        int diff = N.Fq(6).getNoteValue() - note1Value;
        int y = TOP_LINE_YOFFSET + (diff * (LINE_SPACING / 2)) - (NOTE_HEIGHT / 2);

        // Get staff end point for note
        int staffx = 0;
        int staffy = 0;
        int staffyd = 0;
        if (note1Value < N.Bq().getNoteValue()) {
            staffx = x + NOTE_WIDTH - 1;
            staffy = y - NOTE_STAFF_LENGTH;
            staffyd = 10;
        }
        else {
            staffx = x;
            staffy = y + NOTE_STAFF_LENGTH + (NOTE_HEIGHT / 2);
            staffyd = -10;
        }
        int staffy1 = staffy + staffyd;

        Stroke oldStroke = g.getStroke();
        g.setStroke(STAFF_FLAG_STROKE);
        g.drawLine(staffx, staffy, staffx + 10, staffy1);
        // TODO Draw extra flags for 16th, 32nd, etc
        g.setStroke(oldStroke);
    }

    private void connectNoteStaffs(Graphics2D g, Note note1, Note note2, int note1Index, boolean drawStaffUp){
        int x1 = NOTES_XOFFSET + (note1Index * NOTE_SPACING);
        int note1Value = note1.getNoteValue();
        int diff = N.Fq(6).getNoteValue() - note1Value;
        int y1 = TOP_LINE_YOFFSET + (diff * (LINE_SPACING / 2)) - (NOTE_HEIGHT / 2);

        // Get staff end point for note1
        int staffx1 = 0;
        int staffy1 = 0;
        if (drawStaffUp) {
            staffx1 = x1 + NOTE_WIDTH - 1;
            staffy1 = y1 - NOTE_STAFF_LENGTH;
        }
        else {
            staffx1 = x1;
            staffy1 = y1 + NOTE_STAFF_LENGTH + (NOTE_HEIGHT / 2);
        }

        // Get staff end point for note2
        int x2 = NOTES_XOFFSET + ((note1Index + 1) * NOTE_SPACING);
        int note2Value = note2.getNoteValue();
        diff = N.Fq(6).getNoteValue() - note2Value;
        int y2 = TOP_LINE_YOFFSET + (diff * (LINE_SPACING / 2)) - (NOTE_HEIGHT / 2);

        int staffx2 = 0;
        int staffy2 = 0;
        if (drawStaffUp) {
            staffx2 = x2 + NOTE_WIDTH - 1;
            staffy2 = y2 - NOTE_STAFF_LENGTH;
        }
        else {
            staffx2 = x2;
            staffy2 = y2 + NOTE_STAFF_LENGTH + (NOTE_HEIGHT / 2);
        }

        Stroke oldStroke = g.getStroke();
        g.setStroke(STAFF_FLAG_STROKE);
        // Connect staff end points
        g.drawLine(staffx1, staffy1, staffx2, staffy2);
        // TODO Draw extra flags for 16th, 32nd, etc
        g.setStroke(oldStroke);
    }


    private boolean shouldDrawStaffUp(Note note, int noteIndex){
        int myValue = note.getNoteValue();
        boolean drawUp = myValue < N.Bq().getNoteValue();
        if (note.getDuration().getBeats() < 1.0){// && myValue == N.Bq().getNoteValue()){
            // Look at lastNote and nextNote for <Quarter and match staff direction
            int otherIndex = noteIndex - 1;
            Note otherNote = null;
            Boolean otherDrawUp = null;
            /*
            if (otherIndex >= 0){
                otherNote = notes.get(otherIndex);
                if (otherNote.getDuration().getBeats() < 1.0) {
                    otherDrawUp = otherNote.getNoteValue() < N.Bq().getNoteValue();
                }
            }
             */
            if (otherDrawUp == null){
                otherIndex = noteIndex + 1;
                if (otherIndex < notes.size()){
                    otherNote = notes.get(otherIndex);
                    if (otherNote.getDuration().getBeats() < 1.0) {
                        otherDrawUp = otherNote.getNoteValue() < N.Bq().getNoteValue();
                    }
                }
            }
            if (otherDrawUp != null)
                drawUp = otherDrawUp;
        }
        return drawUp;
    }

    public void refresh(){
        repaint();
    }

    public void setDrawCardCode(boolean drawCardCode) {
        this.drawCardCode = drawCardCode;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
