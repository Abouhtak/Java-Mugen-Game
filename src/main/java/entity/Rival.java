package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.example.GamePanel;

public class Rival extends Entity {
    GamePanel gamePanel;
    // Player's health attribute
    public int maxHealth = 100;
    public int health = maxHealth;

    //For collision detection
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    boolean isJumping = false;
    double jumpSpeed = -10; // Initial jump speed 
    double gravity = 0.6; // Gravity acceleration 
    double yVelocity = 0; // Vertical velocity

    Random random = new Random();
    int actionLockCounter = 0;

    public Rival(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        //Solid Area set up
        solidArea = new Rectangle(8, 16, 32, 32); 
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getRivalImage();
        getRivalAttackImage();
    }

    public void setDefaultValues(){
        x = 600;
        y = 355;
        speed = 3;
        direction = "down"; // Initially face the player
        health = maxHealth ;
    }

    public boolean attacking = false ;

    //Rival Movements
    File f1 = new File("./src/rival/sasuke_up_1.png");
    File f2 = new File("./src/rival/sasuke_up_2.png");
    File f3 = new File("./src/rival/sasuke_down_1.png");
    File f4 = new File("./src/rival/sasuke_down_2.png");
    File f5 = new File("./src/rival/sasuke_left_1.png");
    File f6 = new File("./src/rival/sasuke_left_2.png");
    File f7 = new File("./src/rival/sasuke_right_1.png");
    File f8 = new File("./src/rival/sasuke_right_2.png");
    //Rival Attacks
    File f9 = new File("./src/rival/sasuke_attack_right_1.png");
    File f10 = new File("./src/rival/sasuke_attack_right_2.png");
    File f11 = new File("./src/rival/sasuke_attack_left_1.png");
    File f12 = new File("./src/rival/sasuke_attack_left_2.png");
    File f13 = new File("./src/rival/sasuke_attack_down_1.png");
    File f14 = new File("./src/rival/sasuke_attack_down_2.png");
    File f15 = new File("./src/rival/sasuke_attack_up_1.png");
    File f16 = new File("./src/rival/sasuke_attack_up_2.png");

    public void getRivalImage(){
        try {
            up1 = ImageIO.read(f1);
            up2 = ImageIO.read(f2);
            down1 = ImageIO.read(f3);
            down2 = ImageIO.read(f4);
            left1 = ImageIO.read(f5);
            left2 = ImageIO.read(f6);
            right1 = ImageIO.read(f7);
            right2 = ImageIO.read(f8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRivalAttackImage() {
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



    // Apply gravity to the rival
    public void applyGravity() {
        if (!isJumping) {
            y += yVelocity;
            yVelocity += gravity;
            // Check if rival has landed
            if (y >= 355) {
                y = 355;
                yVelocity = 0;
            }
        }
    }

    // Handle screen's boundaries
    public void handleScreenBounds() {
        if (x < 0) {
            x = 0;
        }
        if (x > gamePanel.getWidth() - gamePanel.tileSize) {
            x = gamePanel.getWidth() - gamePanel.tileSize;
        }
        if (y < 0) {
            y = 0;
        }
    }

    //Handle rival's attacking
    public void attacking(){
        spriteCounter++;

        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    

    //Solid area method
    public void updateSolidArea() {
        solidArea.x = x + solidAreaDefaultX;
        solidArea.y = y + solidAreaDefaultY;
    }
    //Check distance to player
    public double getDistanceToPlayer() {
        int playerX = gamePanel.player.x;
        int playerY = gamePanel.player.y;
        return Math.sqrt(Math.pow(playerX - this.x, 2) + Math.pow(playerY - this.y, 2));
    }
    //Set the direction towards the player
    public String getDirectionTowardsPlayer() {
        int playerX = gamePanel.player.x;
        int playerY = gamePanel.player.y;
    
        if (Math.abs(playerX - this.x) > Math.abs(playerY - this.y)) {
            if (playerX > this.x) {
                return "right";
            } else {
                return "left";
            }
        } else {
            if (playerY > this.y) {
                return "down";
            } else {
                return "up";
            }
        }
    }
    


    public void update() {
        updateSolidArea();

        double distanceToPlayer = getDistanceToPlayer();

        if (distanceToPlayer < 50) { // If within attack range, start attacking
            direction = getDirectionTowardsPlayer();
            attacking = true;
        } else {
            attacking = false; // Stop attacking if the player is out of range
            direction = getDirectionTowardsPlayer();
        }

        if (attacking) {
            attacking();
        } else {
            if (direction.equals("up")) {
                y -= speed;
            } else if (direction.equals("down")) {
                y += speed;
            } else if (direction.equals("left")) {
                x -= speed;
            } else if (direction.equals("right")) {
                x += speed;
            }
        }

        applyGravity();
        handleScreenBounds();

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = spriteNum == 1 ? 2 : 1;
            spriteCounter = 0;
        }
    }
    
    public void draw(Graphics2D graph2){
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if(attacking == false){
                    image = spriteNum == 1 ? up1 : up2;
                } else {
                    image = spriteNum == 1 ? attackUp1 : attackUp2;
                }
                break;
            case "down":
                if(attacking == false){
                    image = spriteNum == 1 ? down1 : down2;
                } else {
                    image = spriteNum == 1 ? attackDown1 : attackDown2;
                }
                break;
            case "left":
                if(attacking == false){
                    image = spriteNum == 1 ? left1 : left2;
                } else {
                    image = spriteNum == 1 ? attackLeft1 : attackLeft2;
                }
                break;
            case "right":
                if(attacking == false){
                    image = spriteNum == 1 ? right1 : right2;
                } else {
                    image = spriteNum == 1 ? attackRight1 : attackRight2;
                }
                break;
        }
        graph2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}

