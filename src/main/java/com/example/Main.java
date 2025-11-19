package com.example;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Mugen Game");

        try {
            File iconFile = new File("./src/player/mugen_logo.png");
            Image icon = ImageIO.read(iconFile);
            window.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocation(1080,720);
        window.setVisible(true);
        gamePanel.startGameThread();
    }
    
}

