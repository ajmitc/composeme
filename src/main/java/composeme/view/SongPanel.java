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

    public SongPanel(Model model, View view){
        this.model = model;
        this.view = view;

        taNoteCardCodes = new JTextArea();
        taNoteCardCodes.setRows(5);
        taNoteCardCodes.setColumns(20);
        taNoteCardCodes.setWrapStyleWord(true);
        taNoteCardCodes.setLineWrap(true);

        taNoteCardCodes.setText("0212 0201 0012 0222 0234 0242");

        btnPlay = new JButton("Play");

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(taNoteCardCodes);
        add(btnPlay);


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

    }

    public JTextArea getTaNoteCardCodes() {
        return taNoteCardCodes;
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }
}
