import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Pikachu Dino Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartPanel startPanel = new StartPanel(this::startGame);
        add(startPanel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    void startGame() {
        String playerName = JOptionPane.showInputDialog(this, "Digite seu nome:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Jogador Desconhecido";
        }

        getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(playerName, this);
        add(gamePanel);
        revalidate();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
