package composeme.view;

import composeme.Model;
import composeme.song.NoteCard;

import javax.swing.*;
import java.awt.*;

public class NoteCardListCellRenderer implements ListCellRenderer<NoteCard> {
    private static final Color SELECTED_BACKGROUND_COLOR = Color.CYAN;
    private Model model;
    private View view;

    public NoteCardListCellRenderer(Model model, View view){
        this.model = model;
        this.view = view;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends NoteCard> list, NoteCard value, int index, boolean isSelected, boolean cellHasFocus) {
        NoteCardPanel noteCardPanel = new NoteCardPanel(value, model, view);
        if (isSelected){
            noteCardPanel.getClefPanel().setBackgroundColor(SELECTED_BACKGROUND_COLOR);
        }
        return noteCardPanel;
    }
}
