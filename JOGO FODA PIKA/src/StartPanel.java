import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public StartPanel(Runnable startGameCallback) {
        setPreferredSize(new Dimension(750, 250));
        setBackground(Color.PINK);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Pikachu Dino Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier", Font.BOLD, 32));
        add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Courier", Font.PLAIN, 24));
        startButton.addActionListener(e -> startGameCallback.run());
        add(startButton, BorderLayout.SOUTH);
    }
}
