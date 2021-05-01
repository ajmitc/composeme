package composeme.view;

import composeme.Model;
import composeme.song.NoteCard;

import javax.swing.*;
import java.awt.*;

public class NoteCardPanel extends JPanel {
    private NoteCard noteCard;
    private ClefPanel clefPanel;

    public NoteCardPanel(NoteCard noteCard, Model model, View view){
        super();
        this.noteCard = noteCard;

        setPreferredSize(new Dimension(300, 150));

        clefPanel = new ClefPanel(model, view, 1);
        clefPanel.setNotes(noteCard.getNotes());
        clefPanel.setDrawCardCode(false);

        JLabel lblCardCode = new JLabel(noteCard.getCode());

        setLayout(new BorderLayout());
        add(lblCardCode, BorderLayout.NORTH);
        add(clefPanel, BorderLayout.CENTER);
    }

    public NoteCard getNoteCard() {
        return noteCard;
    }

    public ClefPanel getClefPanel() {
        return clefPanel;
    }
}
