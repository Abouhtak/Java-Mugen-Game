package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.GamePanel;
import com.example.KeyHandler;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyH;
    //For collision detection
    public Rectangle solidArea ;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // Player's health attribute
    public int maxHealth = 100;
    public int health = maxHealth;

    public Player(GamePanel gamePanel, KeyHandler keyH){
        this.gamePanel = gamePanel;
        this.keyH = keyH;

        //Solid Area set up
        solidArea = new Rectangle(8, 16, 32, 32); 
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackimage();
        loadBackgroundImage();
    }
    //Player's default values
    public void setDefaultValues(){
        x = 100;
        y = 355;
        speed = 2 ;
        direction = "down";
        health = maxHealth;
    }
    BufferedImage backgroundImage;

    boolean isJumping = false;
    double jumpSpeed = -5; // Initial jump speed
    double gravity = 0.6; // Gravity acceleration
    double yVelocity = 0; // Vertical velocity

    
    public boolean attacking = false ;
    public int map = 0 ;


    //Background maps
    File fb1 = new File("./src/player/konoha.png");
    File fb2 = new File("./src/player/supmti.png");
    File fb3 = new File("./src/player/fValley.png");

    //Player Movements
    File f1 = new File("./src/player/naruto_up_1.png");
    File f2 = new File("./src/player/naruto_up_2.png");
    File f3 = new File("./src/player/naruto_down_1.png");
    File f4 = new File("./src/player/naruto_down_2.png");
    File f5 = new File("./src/player/naruto_left_1.png");
    File f6 = new File("./src/player/naruto_left_2.png");
    File f7 = new File("./src/player/naruto_right_1.png");
    File f8 = new File("./src/player/naruto_right_2.png");
    //File f17 = new File("./src/player/naruto_hurt.png");
    //Player Attacks
    File f9 = new File("./src/player/naruto_attack_right_1.png");
    File f10 = new File("./src/player/naruto_attack_right_2.png");
    File f11 = new File("./src/player/naruto_attack_left_1.png");
    File f12 = new File("./src/player/naruto_attack_left_2.png");
    File f13 = new File("./src/player/naruto_attack_down_1.png");
    File f14 = new File("./src/player/naruto_attack_down_2.png");
    File f15 = new File("./src/player/naruto_attack_up_1.png");
    File f16 = new File("./src/player/naruto_attack_up_2.png");

    //Change map depending on the selected option
    public void setMap(int map) {
        this.map = map;
        loadBackgroundImage();
    }
    
    public void loadBackgroundImage() {
        switch ((map)) {
            case 0:
            try {
            backgroundImage = ImageIO.read(fb1);
            } 
            catch (IOException e) {
            e.printStackTrace();
            }
            break;
            case 1:
            try {
                backgroundImage = ImageIO.read(fb2);
                } 
                catch (IOException e) {
                e.printStackTrace();
                }
            break;
            case 2:
            try {
                backgroundImage = ImageIO.read(fb3);
                } 
                catch (IOException e) {
                e.printStackTrace();
                }
            break;
        }
        
    }

    // Handle player jump
    public void handleJump() {
        if (keyH.upPressed && !isJumping) {
            yVelocity = jumpSpeed;
            isJumping = true;
        }
    }

    // Apply gravity to the player
    public void applyGravity() {
        if (isJumping) {
            y += yVelocity;
            yVelocity += gravity;
            // Check if player has landed
            if (y >= 355) {
                y = 355;
                isJumping = false;
                yVelocity = 0;
                
            }
            
        }
    }
    //Handle the screen's boundaries
    public void handleScreenBounds() {
        // Check if player is moving right
        if (keyH.rightPressed) {
            if (x + speed <= gamePanel.getWidth() - gamePanel.tileSize) {
                x += speed;
            } else {
                x = gamePanel.getWidth() - gamePanel.tileSize;
            }
        }
        // Check if player is moving left
        if (keyH.leftPressed) {
            if (x - speed >= 0) {
                x -= speed;
            } else {
                x = 0;
            }
        }
    }
    
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(f1); // read the image file ; can also use the getClass().getClassLoader().getResourceAsStream() 
            up2 = ImageIO.read(f2);
            down1 = ImageIO.read(f3);
            down2 = ImageIO.read(f4);
            left1 = ImageIO.read(f5);
            left2 = ImageIO.read(f6);
            right1 = ImageIO.read(f7);
            right2 = ImageIO.read(f8);
            //hurt = ImageIO.read(f17);
        } catch (Exception e) {
            e.printStackTrace(); //for errors
        }
    };

    public void getPlayerAttackimage(){
        try {
            attackRight1 = ImageIO.read(f9);
            attackRight2 = ImageIO.read(f10);
            attackLeft1 = ImageIO.read(f11);
            attackLeft2 = ImageIO.read(f12);
            attackDown1 = ImageIO.read(f13);
            attackDown2 = ImageIO.read(f14);
            attackUp1 = ImageIO.read(f15);
            attackUp2 = ImageIO.read(f16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    //Handle player's attacking
    public void attacking(){
        spriteCounter ++ ;

        if(spriteCounter <=5){
            spriteNum = 1 ;
        }
        if(spriteCounter>5 && spriteCounter <=25){
            spriteNum = 2 ;
        }
        if(spriteCounter > 25){
            spriteNum = 1 ;
            spriteCounter = 0;
            attacking = false ;
        }
    }

    //Solid area method
    public void updateSolidArea() {
        solidArea.x = x + solidAreaDefaultX;
        solidArea.y = y + solidAreaDefaultY;
    }

    //gets called 60 times/frames per second because it's inside the game loop
    public void update(){
        handleJump();
        applyGravity();
        handleScreenBounds();
        updateSolidArea();
        
        //check if player's attacking
        if(keyH.jPressed ){
            attacking = true ;
        }

        if(attacking){
            attacking();
            
        }

         //change player's position
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true ){
            if(keyH.upPressed == true){//if "W" is pressed player goes up
            direction ="up"; 
             y -= speed;
     }
     else if(keyH.downPressed == true){
        direction ="down";
         //y += speed;
     }
     else if(keyH.leftPressed == true){
        direction ="left";
         x -= speed;
     }
     else if(keyH.rightPressed== true){
        direction ="right";
         x += speed;
     }

    
     //this is called in each frame and increases the spriteCounter by 1
     spriteCounter++;
     //the player image changes every 10 frames
     if(spriteCounter > 10){
        if (spriteNum ==1){
            spriteNum = 2;
        }
        else if(spriteNum ==2){
            spriteNum = 1;
        }
        spriteCounter = 0;
     }
     
     
         }
  

        
    }

    


    public void draw(Graphics2D graph2){
        // Graphics2D methods to draw a rectangle
        //graph2.setColor(Color.RED);
        //graph2.fillRect(x , y, gamePanel.tileSize, gamePanel.tileSize);
        if (backgroundImage != null) {
            graph2.drawImage(backgroundImage, 0, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);
        } 
        BufferedImage image = null ;
        switch (direction) {
            case "up":
            if(attacking == false){
                if(spriteNum == 1){image = up1;}
                if(spriteNum == 2){image = up2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackUp1;}
                if(spriteNum == 2){image = attackUp2;}
            }
            
                break;
            case "down":
            if(attacking == false){
                if(spriteNum == 1){image = down1;}
                if(spriteNum == 2){image = down2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackDown1;}
                if(spriteNum == 2){image = attackDown2;}
            }
            
                break;
            case "left":
            if(attacking == false){
                if(spriteNum == 1){image = left1;}
                if(spriteNum == 2){image = left2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackLeft1;}
                if(spriteNum == 2){image = attackLeft2;}
            }
            
                break;
            case "right":
            if(attacking == false){
                if(spriteNum == 1){image = right1;}
                if(spriteNum == 2){image = right2;}
            }
            if(attacking == true){
                if(spriteNum == 1){image = attackRight1;}
                if(spriteNum == 2){image = attackRight2;}
            }
            
                break;
                
            
        }
        if(Hurt == true){
            image = hurt ;
        }

        graph2.drawImage(image,x,y,gamePanel.tileSize, gamePanel.tileSize ,null);
    }
}
