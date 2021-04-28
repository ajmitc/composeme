package composeme.view;

import composeme.Model;

import javax.swing.*;
import java.awt.*;

public class SongPanel extends JPanel {
    private Model model;
    private View view;

    private JButton btnPlay;

    public SongPanel(Model model, View view){
        this.model = model;
        this.view = view;

        btnPlay = new JButton("Play");

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(btnPlay);
    }

    public void refresh(){

    }

    public JButton getBtnPlay() {
        return btnPlay;
    }
}
