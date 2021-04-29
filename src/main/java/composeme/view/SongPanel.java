package composeme.view;

import composeme.Model;

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

    private ClefPanel clefPanel1;
    private ClefPanel clefPanel2;

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

        clefPanel1 = new ClefPanel(model, view, 1);
        clefPanel2 = new ClefPanel(model, view, 2);

        JPanel clefsPanel = new JPanel();
        clefsPanel.setLayout(new BoxLayout(clefsPanel, BoxLayout.Y_AXIS));
        clefsPanel.add(clefPanel1);
        clefsPanel.add(clefPanel2);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(taNoteCardCodes);
        leftPanel.add(btnPlay);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(clefsPanel, BorderLayout.CENTER);


        taNoteCardCodes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
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

    public void refresh(){
        clefPanel1.refresh();
        clefPanel2.refresh();
    }

    public JTextArea getTaNoteCardCodes() {
        return taNoteCardCodes;
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }
}
