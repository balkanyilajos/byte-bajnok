package gui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameModel;

public class MenuPanel extends JPanel {
    private GameModel gameModel;
    private long startTime;
    private JLabel timerText;
    private DateTimeFormatter timeFormatter;

    public MenuPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.timeFormatter = DateTimeFormatter.ofPattern("mm:ss");
        this.startTime = System.nanoTime();
        this.timerText = new JLabel();
        timerText.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 15));
        timerText.setForeground(Color.WHITE);
        updateTimerText();

        setBackground(Color.GRAY);
        add(timerText, BorderLayout.CENTER);
    }

    private void updateTimerText() {
        int seconds = (int)((System.nanoTime() - startTime) / 1_000_000_000.0);
        timerText.setText(LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC).format(timeFormatter));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        updateTimerText();
    }

}
