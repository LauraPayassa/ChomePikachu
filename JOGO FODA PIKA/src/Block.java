import java.awt.*;

public class Block extends GameObject implements Movimentavel {
    public Block(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    @Override
    void desenhar(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void mover() {
        this.x += -12;
    }
}
