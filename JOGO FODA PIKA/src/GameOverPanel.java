import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    public GameOverPanel(int score, Runnable restartCallback, Runnable backToMenuCallback, Runnable viewScoresCallback) {
        setPreferredSize(new Dimension(750, 250));
        setBackground(Color.pink);
        setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("Game Over! Score: " + score, SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Courier", Font.BOLD, 32));
        gameOverLabel.setForeground(Color.white);
        add(gameOverLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.pink);
        buttonPanel.setLayout(new FlowLayout());

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Courier", Font.PLAIN, 24));
        restartButton.addActionListener(e -> restartCallback.run());
        buttonPanel.add(restartButton);

        JButton backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setFont(new Font("Courier", Font.PLAIN, 24));
        backToMenuButton.addActionListener(e -> backToMenuCallback.run());
        buttonPanel.add(backToMenuButton);

        JButton viewScoresButton = new JButton("View Scores");
        viewScoresButton.setFont(new Font("Courier", Font.PLAIN, 24));
        viewScoresButton.addActionListener(e -> viewScoresCallback.run());
        buttonPanel.add(viewScoresButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
