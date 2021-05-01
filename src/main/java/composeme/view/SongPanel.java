package composeme.view;

import composeme.Model;
import composeme.song.Instrument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SongPanel extends JPanel {
    private Model model;
    private View view;

    private JTextArea taNoteCardCodes;
    private JButton btnPlay;
    private JButton btnClear;
    private JComboBox<Instrument> cbInstrument;

    private List<ClefPanel> clefPanels = new ArrayList<>();
    private JPanel clefsPanelPanel;

    public SongPanel(Model model, View view){
        this.model = model;
        this.view = view;

        taNoteCardCodes = new JTextArea();
        taNoteCardCodes.setRows(5);
        taNoteCardCodes.setColumns(20);
        taNoteCardCodes.setWrapStyleWord(true);
        taNoteCardCodes.setLineWrap(true);
        taNoteCardCodes.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        taNoteCardCodes.setText("0212 0201 0012 0222 0234 0242");

        btnPlay = new JButton("Play");
        btnClear = new JButton("Clear");
        cbInstrument = new JComboBox<>();

        for (Instrument instrument: Instrument.values()){
            cbInstrument.addItem(instrument);
        }

        for (int i = 0; i < 4; ++i){
            clefPanels.add(new ClefPanel(model, view, i));
        }

        clefsPanelPanel = new JPanel();
        clefsPanelPanel.setLayout(new BoxLayout(clefsPanelPanel, BoxLayout.Y_AXIS));
        for (ClefPanel clefPanel: clefPanels) {
            clefsPanelPanel.add(clefPanel);
        }

        JScrollPane clefsPanelPanelScrollPane = new JScrollPane(clefsPanelPanel);
        clefsPanelPanelScrollPane.getViewport().setMinimumSize(new Dimension(500, 500));
        clefsPanelPanelScrollPane.getViewport().setPreferredSize(new Dimension(500, 500));

        JPanel buttonpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonpanel.add(cbInstrument);
        buttonpanel.add(btnPlay);
        buttonpanel.add(btnClear);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(taNoteCardCodes);
        leftPanel.add(buttonpanel);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(clefsPanelPanelScrollPane, BorderLayout.CENTER);


        taNoteCardCodes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    return;
                }
                String text = taNoteCardCodes.getText();
                if (text.isBlank())
                    return;
                List<String> codes = new ArrayList<>(Arrays.asList(text.split(" ")));
                if (codes.isEmpty())
                    return;
                String lastCode = codes.remove(codes.size() - 1);
                if (lastCode.length() >= 4){
                    String code1 = lastCode.substring(0, 4);
                    String code2 = lastCode.substring(4);
                    codes.add(code1);
                    codes.add(code2);
                    taNoteCardCodes.setText(codes.stream().collect(Collectors.joining(" ")));
                }
            }
        });
    }

    public void updateClefsFromModel(){
        if (model.getSong() != null) {
            double totalBeats = model.getSong().getTotalBeats();
            double numClefs = Math.ceil(totalBeats / (ClefPanel.NUM_CARDS_PER_LINE * ClefPanel.NUM_BEATS_PER_CARD));
            while (clefPanels.size() < numClefs){
                ClefPanel clefPanel = new ClefPanel(model, view, clefPanels.size());
                clefsPanelPanel.add(clefPanel);
                clefPanels.add(clefPanel);
            }
            for (ClefPanel clefPanel : clefPanels)
                clefPanel.setNotesFromModel();
        }
    }

    public void refresh(){
        for (ClefPanel clefPanel: clefPanels)
            clefPanel.refresh();
    }

    public JTextArea getTaNoteCardCodes() {
        return taNoteCardCodes;
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }

    public JButton getBtnClear() {
        return btnClear;
    }

    public JComboBox<Instrument> getCbInstrument() {
        return cbInstrument;
    }
}
