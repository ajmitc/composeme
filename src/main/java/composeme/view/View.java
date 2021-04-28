package composeme.view;

import composeme.Model;

import javax.swing.*;
import java.awt.*;

public class View {
    private Model model;
    private JFrame frame;

    private SongPanel songPanel;

    public View(Model model){
        this.model = model;
        this.frame = new JFrame();
        frame.setTitle("Compose Me");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);

        songPanel = new SongPanel(model, this);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(songPanel, BorderLayout.CENTER);
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
}
