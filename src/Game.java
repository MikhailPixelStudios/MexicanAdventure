import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Ученик on 20.11.2017.
 */
public class Game extends Canvas implements Runnable {
    public static Sprite hero;
    public static Sprite block;
    public static Sprite background;
    private static final long serialVersionUID = 1L;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private static int x = 0;
    private static int y =0;
    private static int backx=0;
    boolean running = false;

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;
        init();

        while (running) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            update(delta);
            render();
        }
    }

    public static int WIDTH = 1800;
    public static int HEIGHT = 1800;
    public static String NAME = "Game forever";
    static JFrame frame = new JFrame(Game.NAME);
    public static void main(String args[]) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        game.start();
    }

    public void start() {
        running = true;

        new Thread(this).start();
    }

    public void update(long delta) {

        if (leftPressed && x>0) {
            x-=2;

            backx+=1;
        }
        if (rightPressed && x+hero.getWidtth()<frame.getWidth()) {
            x+=2;
            backx-=1;
        }

        if (leftPressed) {
            hero = getSprite("manl.png");
        } else if (rightPressed) {
            hero = getSprite("manr.png");
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());

        background.draw(g,backx,0);

        hero.draw(g, x, y);
       // block.draw(g,20,20);
        g.dispose();
        bs.show();

    }

    public void init() {
        block=getSprite("sandblock.jpg");
        hero = getSprite("manl.png");
        background=getSprite("back.jpg");
        addKeyListener(new KeyInputHandler());
    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
        return sprite;
    }

    private class KeyInputHandler extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
        }


        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
        }
    }
}

