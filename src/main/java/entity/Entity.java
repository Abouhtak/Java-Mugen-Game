package entity;

import java.awt.image.BufferedImage;

import com.example.GamePanel;

public class Entity {
    GamePanel gamePanel ; 
    public int x,y ;
    public int speed ;

    public BufferedImage up1,up2, down1 , down2 , left1 , left2 , right1 , right2 , hurt;
    public BufferedImage attackUp1 , attackUp2 ,
    attackDown1 , attackDown2 ,attackRight1 , attackRight2, attackLeft1 , attackLeft2 ;
    public boolean Hurt = false;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    
    
  
}
