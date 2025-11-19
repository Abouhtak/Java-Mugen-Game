package com.example;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import entity.Rival;
public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread; //class for repeating process 60 fps
    public Player player = new Player(this, keyH);
    public Rival rival = new Rival(this);
    public UI ui = new UI(this);
    CollisionChecker collisionChecker = new CollisionChecker(this);

     
    // Game State(Pause)
    public int gameState ;
    // Game State(Title Screen)
    public final int titleState = 0 ;
    public final int playState = 1 ;
    public final int pauseState = 2 ;
    public final int gameOverState = 3;
    public final int winState = 4 ;
 
    
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH); // gamepanel can recognize the key input
        this.setFocusable(true); // gamepanel can be focused to receive key input
        setupGame();
    
    }

    public void setupGame(){
        gameState = titleState ;
    }
    //Reset Player and Rival's values
    public void retry(){
        player.setDefaultValues();
        rival.setDefaultValues();
    }     

    public void startGameThread(){
        gameThread = new Thread(this); //passing gamePanel class to the thread constructor
        gameThread.start(); //begin execution and calls the run method
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread != null){ //game loop is running
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval; //every loop the past time divided by drawInterval is added to delta
            lastTime = currentTime ;
            if(delta >= 1){ // update when delta reaches the drawInterval
                // update character positions
                update();
                repaint(); //to call the paintComponent method
                delta--; //reset
            }  
    }
    }
    public void update(){
        if(gameState == playState){
            player.update();
            rival.update();
        /*  Initial collision code
           if (collisionChecker.checkCollision(player, rival)) {
                // Handle collision (e.g., reduce health, bounce back, etc.)
                //player.health -= 10;
                rival.health -= 2.5;
                System.out.println("Collision detected!");
            }*/
            if (collisionChecker.checkAttackCollision(player, rival)) {
                if (player.attacking) {
                    rival.health -= 10;
                }
                if (rival.attacking) {
                    player.health -= 1;
                }
                player.attacking = false;
                rival.attacking = false;
            }
    
            if (player.health <= 0 ) {
                gameState = gameOverState;
            }
            if (rival.health <= 0 ) {
                gameState = winState;
            }
        }
        if(gameState == pauseState){
            //paused
        }
    }
    public void paintComponent(Graphics graph){
        super.paintComponent(graph); //Jpanel
        Graphics2D graph2 = (Graphics2D)graph; //converting to Graphics2D because it has more methods
        
        // Title Screen
        if(gameState == titleState) {
            ui.draw(graph2);
        }
        //Others
        else {
            //Player
            player.draw(graph2);
            //UI
            ui.draw(graph2);
            //Rival
            rival.draw(graph2);
        }
        
        graph2.dispose();
    }

   /*  public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }   
    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }*/

}

