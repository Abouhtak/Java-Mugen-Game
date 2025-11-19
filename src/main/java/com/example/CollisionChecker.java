package com.example;

import entity.Player;
import entity.Rival;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean checkCollision(Player player, Rival rival) {
        
        player.updateSolidArea();
        rival.updateSolidArea();

        boolean collision = player.solidArea.intersects(rival.solidArea);

        // Reset solid area positions
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        rival.solidArea.x = rival.solidAreaDefaultX;
        rival.solidArea.y = rival.solidAreaDefaultY;

        return collision;
        
    }
    public boolean checkAttackCollision(Player player, Rival rival) {
        player.updateSolidArea();
        rival.updateSolidArea();

        boolean playerAttackCollision = false;
        boolean rivalAttackCollision = false;

        // Check if player is attacking and collides with rival
        if (player.attacking) {
            playerAttackCollision = player.solidArea.intersects(rival.solidArea);
        }

        // Check if rival is attacking and collides with player
        if (rival.attacking) {
            rivalAttackCollision = rival.solidArea.intersects(player.solidArea);
        }

        // Reset solid area positions
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        rival.solidArea.x = rival.solidAreaDefaultX;
        rival.solidArea.y = rival.solidAreaDefaultY;

        return playerAttackCollision || rivalAttackCollision;
    }
}
