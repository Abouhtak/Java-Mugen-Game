package com.example;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;



public class UI {
    GamePanel gamePanel ;
    Graphics2D graph2;

    public UI(GamePanel gamePanel){
        this.gamePanel = gamePanel ;
        //loadBackgroundImage();
    }

    public int commandNum = 0 ;
    public int titleScreenState = 0 ; // 0: first screen 
    int subState = 0 ;

    
    public void draw(Graphics2D graph2){
        this.graph2 = graph2;
        //Title State
        if(gamePanel.gameState == gamePanel.titleState){
            drawTitleScreen();
        }

        //Play State
        if(gamePanel.gameState == gamePanel.playState){
            drawHealthBar();
            //playState stuff
        }
        //Pause State
        if(gamePanel.gameState == gamePanel.pauseState){
            
            drawPauseScreen();
        }
        //Game Over State
        if(gamePanel.gameState == gamePanel.gameOverState){
            drawGameOverScreen();
        }
        //Win State
        if(gamePanel.gameState == gamePanel.winState){
            drawGameWinScreen();
        }

    }

    public void drawHealthBar() {
        // Set the color for the health bar background
        graph2.setColor(Color.red);
        graph2.fillRect(20, 20, 300, 30);

        // Set the color for the health bar
        graph2.setColor(Color.green);
        int healthBarWidth = (int)((double)gamePanel.player.health / gamePanel.player.maxHealth * 300);
        graph2.fillRect(20, 20, healthBarWidth, 30);

        // Set the color for the health bar border
        graph2.setColor(Color.white);
        graph2.drawRect(20, 20, 300, 30);

        // Rival's health bar
        graph2.setColor(Color.red);
        graph2.fillRect(gamePanel.screenWidth - 320, 20, 300, 30);

        graph2.setColor(Color.green);
        int rivalHealthBarWidth = (int)((double)gamePanel.rival.health / gamePanel.rival.maxHealth * 300);
        graph2.fillRect(gamePanel.screenWidth - 320, 20, rivalHealthBarWidth, 30);

        graph2.setColor(Color.white);
        graph2.drawRect(gamePanel.screenWidth - 320, 20, 300, 30);
    }

    public void drawTitleScreen(){
        if(titleScreenState == 0) {
            graph2.setColor(Color.black);
        graph2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        // Title Name
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD,96F));
        String text = "Mugen Game";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize*3;
        //Title Shadow 
        graph2.setColor(Color.gray);
        graph2.drawString(text, x+5, y+5);
        //Main Color
        graph2.setColor(Color.white);
        graph2.drawString(text, x, y);
        // Menu
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD,48F));
        
        text = "PLAY";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize*3 ;
        graph2.drawString(text, x, y);

        if(commandNum == 0){
            graph2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "OPTIONS";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize ;
        graph2.drawString(text, x, y);

        if(commandNum == 1){
            graph2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graph2.drawString(text, x, y);

        if(commandNum == 2){
            graph2.drawString(">", x-gamePanel.tileSize, y);
        }
        
        }
        else if(titleScreenState == 1){
            graph2.setColor(Color.white);
            graph2.setFont(graph2.getFont().deriveFont(42F));

            String text = "Select a map";
            int x = getXforCenteredText(text);
            int y = gamePanel.tileSize*3 ;
            graph2.drawString(text, x, y);

            text = "Konoha";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize ;
            graph2.drawString(text, x, y);
            if(commandNum == 0) {
                graph2.drawString(">", x-gamePanel.tileSize, y);
            }
            text = "Supmti";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize ;
            graph2.drawString(text, x, y);
            if(commandNum == 1) {
                graph2.drawString(">", x-gamePanel.tileSize, y);
            }
            text = "Final Valley";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize ;
            graph2.drawString(text, x, y);
            if(commandNum == 2) {
                graph2.drawString(">", x-gamePanel.tileSize, y);
            }
            text = "Cancel";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize*2 ;
            graph2.drawString(text, x, y);
            if(commandNum == 3) {
                graph2.drawString(">", x-gamePanel.tileSize, y);
            }
        }
        
    }
    public void drawPauseScreen(){
    /* Initial pause screen 
       graph2.setFont(graph2.getFont().deriveFont(Font.PLAIN,80));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        
        int y = gamePanel.screenHeight/2;

        graph2.drawString(text, x, y);*/

        graph2.setColor(Color.white);
        graph2.setFont(graph2.getFont().deriveFont(32F));

        //Sub Window
        int frameWidth = gamePanel.tileSize*8;
        int frameHeight = gamePanel.tileSize*10;
        int frameX = (gamePanel.screenWidth/2)-(frameWidth/2);
        int frameY = (gamePanel.screenHeight/2)-(frameHeight/2);
    
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:options_top(frameX, frameY); break;
            case 1:options_control(frameX, frameY);break;
        }


    }

    public void drawGameWinScreen(){
        graph2.setColor(new Color(0,0,0,150));
        graph2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x ;
        int y ;
        String text ;
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You win !";
        //Shadow
        graph2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize*4;
        graph2.drawString(text, x, y);
        //Main
        graph2.setColor(Color.white);
        graph2.drawString(text, x-4, y-4);

        // Retry
        graph2.setFont(graph2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y+=gamePanel.tileSize*4;
        graph2.drawString(text, x, y);
        if(commandNum == 0){
            graph2.drawString(">",x-40,y);
        }

        //Back to title screen
        text = "Main Menu";
        x = getXforCenteredText(text);
        y+= 55 ;
        graph2.drawString(text, x, y);
        if(commandNum == 1){
            graph2.drawString(">",x-40,y);
        }

    }
    public void drawGameOverScreen(){
        graph2.setColor(new Color(0,0,0,150));
        graph2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        int x ;
        int y ;
        String text ;
        graph2.setFont(graph2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        //Shadow
        graph2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize*4;
        graph2.drawString(text, x, y);
        //Main
        graph2.setColor(Color.white);
        graph2.drawString(text, x-4, y-4);

        // Retry
        graph2.setFont(graph2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y+=gamePanel.tileSize*4;
        graph2.drawString(text, x, y);
        if(commandNum == 0){
            graph2.drawString(">",x-40,y);
        }

        //Back to title screen
        text = "Main Menu";
        x = getXforCenteredText(text);
        y+= 55 ;
        graph2.drawString(text, x, y);
        if(commandNum == 1){
            graph2.drawString(">",x-40,y);
        }

    }

    public void options_top(int frameX , int frameY){
        int textX;
        int textY;

        //Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize;
        graph2.drawString(text, textX, textY);

        //Control
        textX = frameX + gamePanel.tileSize ;
        textY += gamePanel.tileSize*2;
        graph2.drawString("Control", textX, textY);
        if(commandNum == 0){
            graph2.drawString(">", textX-25, textY);
        }

        //More..
        textY += gamePanel.tileSize*2;
        graph2.drawString("More", textX, textY);
        if(commandNum == 1){
            graph2.drawString(">", textX-25, textY);
        }

        //End Game
        textY += gamePanel.tileSize*2;
        graph2.drawString("End Game", textX, textY);
        if(commandNum == 2){
            graph2.drawString(">", textX-25, textY);
        }

        //Back
        textY += gamePanel.tileSize*2;
        graph2.drawString("Back", textX, textY);
        if(commandNum == 3){
            graph2.drawString(">", textX-25, textY);
        }
    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gamePanel.tileSize ; 
        graph2.drawString(text, textX, textY);

        textX = frameX + gamePanel.tileSize;
        textY += gamePanel.tileSize;
        graph2.drawString("Move", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("Attack", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("Special", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("Confirm", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("Pause", textX, textY); textY+=gamePanel.tileSize;

        textX = frameX + gamePanel.tileSize*5;
        textY = frameY + gamePanel.tileSize*2;
        graph2.drawString("WASD", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("J", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("U", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("ENTER", textX, textY); textY+=gamePanel.tileSize;
        graph2.drawString("P", textX, textY); textY+=gamePanel.tileSize;

        //Back
        textX = frameX + gamePanel.tileSize;
        textY = frameY + gamePanel.tileSize*9;
        graph2.drawString("Back", textX, textY);
        
    }

    public int getXforCenteredText(String text){
        int length = (int)graph2.getFontMetrics().getStringBounds(text, graph2).getWidth();
        int x =gamePanel.screenWidth/2 - length/2;
        return x; 
    }
    public void drawSubWindow(int x , int y , int width , int height){
        Color c = new Color(0,0,0,220);
        graph2.setColor(c);
        graph2.fillRoundRect(x, y, width, height,35 , 35);

        c = new Color(255 ,255 ,255);
        graph2.setColor(c);
        graph2.setStroke(new BasicStroke(5));
        graph2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
}
