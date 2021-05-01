package composeme.view;

import composeme.Model;
import composeme.song.NoteCard;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class View {
    private Model model;
    private JFrame frame;

    private SongPanel songPanel;

    private DefaultListModel<NoteCard> noteCardDefaultListModel;
    private JList<NoteCard> noteCardPanelJList;

    public View(Model model){
        this.model = model;
        this.frame = new JFrame();
        frame.setTitle("Compose Me");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);

        songPanel = new SongPanel(model, this);

        noteCardDefaultListModel = new DefaultListModel<>();
        noteCardPanelJList = new JList<>(noteCardDefaultListModel);
        noteCardPanelJList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        noteCardPanelJList.setVisibleRowCount(-1);
        noteCardPanelJList.setCellRenderer(new NoteCardListCellRenderer(model, this));

        List<NoteCard> noteCardList =
                model.getNoteCardDeck().getCards().stream().sorted(new Comparator<NoteCard>() {
                    @Override
                    public int compare(NoteCard o1, NoteCard o2) {
                        return o1.getCode().compareTo(o2.getCode());
                    }
                }).collect(Collectors.toList());

        for (NoteCard noteCard: noteCardList){
            noteCardDefaultListModel.addElement(noteCard);
        }

        JSplitPane songNoteCardSplitPane = new JSplitPane();
        songNoteCardSplitPane.setOneTouchExpandable(true);
        songNoteCardSplitPane.setDividerLocation(1500);
        songNoteCardSplitPane.setLeftComponent(songPanel);
        songNoteCardSplitPane.setRightComponent(new JScrollPane(noteCardPanelJList));

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(songNoteCardSplitPane, BorderLayout.CENTER);
    }

    public void updateClefsFromModel(){
        songPanel.updateClefsFromModel();
    }

    public void refresh(){
        songPanel.refresh();
    }

    public JFrame getFrame() {
        return frame;
    }

    public SongPanel getSongPanel() {
        return songPanel;
    }

    public JList<NoteCard> getNoteCardPanelJList() {
        return noteCardPanelJList;
    }
}
