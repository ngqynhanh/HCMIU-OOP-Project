package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.MainCharater;
import logic.CollisionChecker;
import logic.KeyHandler;
import logic.TileMangement;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;

    KeyHandler keyHandler = new KeyHandler();
    public TileMangement tileManager = new TileMangement(this);
    public MainCharater mainCharacter = new MainCharater(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public final int FPS = 60;
    
    public final int originalTileSize = 16;
    public final int tileSize = originalTileSize * 3;
    public final int maxCol = 26;
    public final int maxRow = 18;
    public final int screenWidth = tileSize*maxCol;
    public final int screenHeight = tileSize*maxRow;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {        
        double drawInterval = 1000000000.0 / FPS; // Assuming 60 FPS
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while (gameThread != null) {
            update();
            repaint();
            double remainingTime = nextDrawTime - System.nanoTime();
            if (remainingTime > 0) {
                try {
                    Thread.sleep((long) (remainingTime / 1000000)); // Convert to milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            nextDrawTime += drawInterval;
        }
    }
    
    public void update() {
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        mainCharacter.draw(g2);
    }

}
