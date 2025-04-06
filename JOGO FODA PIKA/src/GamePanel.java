import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener, Salvavel {

    private int boardWidth = 750;
    private int boardHeight = 250;

    private Pikachu pikachu;
    private ArrayList<Block> obstacles;

    private int velocityY = 0;
    private int gravity = 1;

    private boolean gameOver = false;
    private int score = 0;
    private String playerName;
    private MainFrame mainFrame;

    private Timer gameLoop;
    private Timer placeObstacleTimer;

    private Image pikachuRunImg, pikachuDeadImg, pikachuJumpImg, ekansImg, caliopeGengarImg;

    public GamePanel(String playerName, MainFrame mainFrame) {
        this.playerName = playerName;
        this.mainFrame = mainFrame;

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.PINK);
        setFocusable(true);
        addKeyListener(this);

        loadImages();
        initializeGame();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        placeObstacleTimer = new Timer(1500, e -> placeObstacle());
        placeObstacleTimer.start();
    }

    private void loadImages() {
        pikachuRunImg = new ImageIcon("src/imagens/pikachurun.gif").getImage();
        pikachuDeadImg = new ImageIcon("src/imagens/pikachumorto.png").getImage();
        pikachuJumpImg = new ImageIcon("src/imagens/pikachujump.png").getImage();
        ekansImg = new ImageIcon("src/imagens/ekans.png").getImage();
        caliopeGengarImg = new ImageIcon("src/imagens/calipegengar.png").getImage();
    }

    private void initializeGame() {
        pikachu = new Pikachu(50, boardHeight - 50, 60, 50, pikachuRunImg);
        obstacles = new ArrayList<>();
    }

    private void placeObstacle() {
        if (gameOver) return;

        double chance = Math.random();
        if (chance > 0.5) {
            obstacles.add(new Block(700, boardHeight - 60, 80, 80, ekansImg));
        } else {
            obstacles.add(new Block(700, boardHeight - 65, 100, 68, caliopeGengarImg));
        }

        if (obstacles.size() > 10) {
            obstacles.remove(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        pikachu.desenhar(g);
        for (Block obstacle : obstacles) {
            obstacle.desenhar(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.PLAIN, 32));
        g.drawString(gameOver ? "Game Over: " + score : "Score: " + score, 10, 35);
    }

    private void move() {
        velocityY += gravity;
        pikachu.y += velocityY;

        if (pikachu.y > boardHeight - pikachu.height) {
            pikachu.y = boardHeight - pikachu.height;
            velocityY = 0;
            pikachu.img = pikachuRunImg;
        }

        for (Block obstacle : obstacles) {
            obstacle.mover();
            if (collision(pikachu, obstacle)) {
                gameOver = true;
                pikachu.img = pikachuDeadImg;
            }
        }

        if (!gameOver) score++;
    }

    private boolean collision(Block a, Block b) {
        return a.x < b.x + b.width && a.x + a.width > b.x &&
                a.y < b.y + b.height && a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            saveScore();
            placeObstacleTimer.stop();
            gameLoop.stop();
            showGameOverScreen();
        }
    }

    private void saveScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
            writer.write(playerName + ": " + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showGameOverScreen() {
        GameOverPanel gameOverPanel = new GameOverPanel(score, this::restartGame, this::backToMenu, this::viewScores);
        mainFrame.remove(this);
        mainFrame.add(gameOverPanel);
        mainFrame.revalidate();
    }

    private void restartGame() {
        mainFrame.getContentPane().removeAll();
        GamePanel newGamePanel = new GamePanel(playerName, mainFrame);
        mainFrame.add(newGamePanel);
        mainFrame.revalidate();
        newGamePanel.requestFocusInWindow();
    }

    private void backToMenu() {
        mainFrame.getContentPane().removeAll();
        StartPanel startPanel = new StartPanel(mainFrame::startGame);
        mainFrame.add(startPanel);
        mainFrame.revalidate();
    }

    private void viewScores() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
            String line;
            StringBuilder scores = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                scores.append(line).append("\n");
            }
            reader.close();
            JOptionPane.showMessageDialog(this, scores.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && pikachu.y == boardHeight - pikachu.height) {
            velocityY = -15;
            pikachu.img = pikachuJumpImg;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void salvar() {
        saveScore();
    }
}
