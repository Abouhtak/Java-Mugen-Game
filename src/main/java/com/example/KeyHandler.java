package com.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, jPressed;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Return the number of the key that was pressed

        // Title State
        if (gamePanel.gameState == gamePanel.titleState) {
            handleTitleState(code);
        }

        // Play State
        if (gamePanel.gameState == gamePanel.playState) {
            handlePlayState(code);
        }

        // Pause State
        if (gamePanel.gameState == gamePanel.pauseState) {
            handlePauseState(code);
        }

        // Game Over State
        if (gamePanel.gameState == gamePanel.gameOverState) {
            handleGameOverState(code);
        }
        //Win State
        if(gamePanel.gameState == gamePanel.winState){
            handleWinState(code);
        }
        //Pause Toggle
        if (code == KeyEvent.VK_P) {
            if (gamePanel.gameState == gamePanel.playState) {
                gamePanel.gameState = gamePanel.pauseState;
            } else if (gamePanel.gameState == gamePanel.pauseState) {
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }

    private void handleTitleState(int code) {
        if (gamePanel.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if (gamePanel.ui.commandNum < 0) {
                    gamePanel.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if (gamePanel.ui.commandNum > 2) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.ui.commandNum == 0) {
                    gamePanel.gameState = gamePanel.playState;
                }
                if (gamePanel.ui.commandNum == 1) {
                    // Change Map
                    gamePanel.ui.titleScreenState = 1;
                }
                if (gamePanel.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        } else if (gamePanel.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if (gamePanel.ui.commandNum < 0) {
                    gamePanel.ui.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if (gamePanel.ui.commandNum > 3) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.ui.commandNum == 0) {
                    gamePanel.player.setMap(0);;
                    gamePanel.gameState = gamePanel.playState;
                }
                if (gamePanel.ui.commandNum == 1) {
                    gamePanel.player.setMap(1);;
                    gamePanel.gameState = gamePanel.playState;
                }
                if (gamePanel.ui.commandNum == 2) {
                    gamePanel.player.setMap(2);
                    gamePanel.gameState = gamePanel.playState;
                }
                if (gamePanel.ui.commandNum == 3) {
                    gamePanel.ui.titleScreenState = 0;
                }
            }
        }
    }

    private void handlePlayState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_J) {
            jPressed = true;
        }
    }

    
    private void handlePauseState(int code) {
       
        if (gamePanel.gameState == gamePanel.pauseState) {
            gamePanel.ui.subState = 0;
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;
                if (gamePanel.ui.commandNum < 0) {
                    gamePanel.ui.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;
                if (gamePanel.ui.commandNum > 3) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gamePanel.ui.commandNum) {
                    case 0:
                        // Control
                        gamePanel.ui.subState = 1;
                        break;
                    case 1:
                        // More
                        gamePanel.gameState = gamePanel.playState;
                        break;
                    case 2:
                        // End Game
                        System.exit(0);
                        break;
                    case 3:
                        // Back to Title Screen
                        gamePanel.gameState = gamePanel.titleState;
                        break;
                }
            }
        }
    }

    private void handleGameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            if (gamePanel.ui.commandNum < 0) {
                gamePanel.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            if (gamePanel.ui.commandNum > 1) {
                gamePanel.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.ui.commandNum == 0) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
            
            } else if (gamePanel.ui.commandNum == 1) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.retry();
            }
        }
    }
    private void handleWinState(int code) {
        if (code == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            if (gamePanel.ui.commandNum < 0) {
                gamePanel.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            if (gamePanel.ui.commandNum > 1) {
                gamePanel.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.ui.commandNum == 0) {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
            
            } else if (gamePanel.ui.commandNum == 1) {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.retry();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_J) {
            jPressed = false;
        }
    }
}
