package com.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.Main.gamePanel;

public class KeyHandler implements KeyListener {

    gamePanel g;
    public boolean showScore=false;

    public KeyHandler(gamePanel gp) {
        this.g=gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int c=e.getKeyCode();
        if(g.gameState==g.titleState) {
            titleStates(c);
        }else if(g.gameState==g.fState) {
            fStates(c);
        }else if(g.gameState==g.eState) {
            eStates(c);
        }else if(g.gameState==g.gameOverState) {
            gameOverStates(c);
        }
        else if(g.gameState==g.autoState) {
            autoStates(c);
        }
        if(c==KeyEvent.VK_BACK_SPACE) {
            g.reset(g.titleState);
        }
        if(c==KeyEvent.VK_F) {
            if(g.fullScreenOn==true)
                g.fullScreenOn=false;
            else g.fullScreenOn=true;
            g.config.saveConfig();
            System.exit(0);
        }
        if(g.gameState!=g.titleState && g.gameState!=g.gameOverState) {
            if(c==KeyEvent.VK_P) {
                if(g.gameState==g.pauseState) {
                    g.gameState=g.prevGameState;
                } else {
                    g.prevGameState=g.gameState;
                    g.gameState=g.pauseState;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void autoStates(int c) {
        if(c==KeyEvent.VK_SPACE) {
            g.reset(g.autoState);
        }
        if(c==KeyEvent.VK_X) {
            if(g.showPath==false) g.showPath=true;
            else g.showPath=false;
        }
//		if ((c==KeyEvent.VK_D || c==KeyEvent.VK_RIGHT|| c==KeyEvent.VK_NUMPAD6)) {
//				g.pF.setNodes(g.s.snakeHead.col, g.s.snakeHead.row, g.food.col, g.food.row);
//				if(g.pF.search()==true) {
//					g.pF.setNodes(g.food.col, g.food.row, g.s.snakeHead.col, g.s.snakeHead.row);
//					for(int i=0;i<g.pF.pathList.size();i++) {
//						g.pF.pathList.get(i).solid=true;
//					}
//					if(g.pF.search()==true) {
//						g.pF.setNodes(g.s.snakeHead.col, g.s.snakeHead.row, g.food.col, g.food.row);
//						g.pF.search();
//						g.s.followPath(g.pF.pathList);
//					} else{g.pF.setNodes(g.s.snakeHead.col, g.s.snakeHead.row, g.s.snakeBody.get(g.s.snakeBody.size()-1).col, g.s.snakeBody.get(g.s.snakeBody.size()-1).row);
//					g.pF.search();
//					g.s.followPath(g.pF.pathList);
//					}
//				}
//			}
        if ((c==KeyEvent.VK_D || c==KeyEvent.VK_RIGHT|| c==KeyEvent.VK_NUMPAD6)) {
            if(g.autoStateFPS<40)
                g.autoStateFPS++;
        }
        if ((c==KeyEvent.VK_A || c==KeyEvent.VK_LEFT|| c==KeyEvent.VK_NUMPAD4)) {
            if(g.autoStateFPS>0)
                g.autoStateFPS--;
        }

    }
    public void gameOverStates(int c) {
        if (c==KeyEvent.VK_SPACE) {
            g.reset(g.prevGameState);
        }
        if(c==KeyEvent.VK_R) {
            if(g.showingRec==true) { g.showingRec=false; g.gameState=g.gameOverState;}
            else { g.showingRec=true;
                g.gameState=g.prevGameState;
            }

        }
    }
    public void eStates(int c) {
        if((c==KeyEvent.VK_NUMPAD8)) {
            g.mult=1;
            g.s.velocityx=0;
            g.s.velocityy=-1;
            if(g.mult==2) {
                g.mult=1;
                g.s.velocityx=0;
                g.s.velocityy=-1;
            }
            else if(g.mult==1) {
                if(g.s.velocityy!=1) {
                    g.mult=1;
                    g.s.velocityx=0;
                    g.s.velocityy=-1;
                }
            }
        }
        else if(( c==KeyEvent.VK_NUMPAD2)) {
            if(g.mult==2) {
                g.mult=1;
                g.s.velocityx=0;
                g.s.velocityy=1;
            }
            else if(g.mult==1) {
                if(g.s.velocityy!=-1) {
                    g.mult=1;
                    g.s.velocityx=0;
                    g.s.velocityy=1;
                }
            }
        }
        else if (( c==KeyEvent.VK_NUMPAD6)) {
            if(g.mult==2) {
                g.mult=1;
                g.s.velocityx=1;
                g.s.velocityy=0;
            }
            else if(g.mult==1) {
                if(g.s.velocityx!=-1) {
                    g.mult=1;
                    g.s.velocityx=1;
                    g.s.velocityy=0;
                }
            }
        }
        else if (( c==KeyEvent.VK_NUMPAD4)) {
            if(g.mult==2) {
                g.mult=1;
                g.s.velocityx=-1;
                g.s.velocityy=0;
            }
            else if(g.mult==1) {
                if(g.s.velocityx!=1) {
                    g.mult=1;
                    g.s.velocityx=-1;
                    g.s.velocityy=0;
                }
            }
        }
        else if(( c==KeyEvent.VK_NUMPAD3) && !(g.s.velocityy==-1&&g.s.velocityx==-1)) {
            g.mult=2;
            g.s.velocityx=1;
            g.s.velocityy=1;
        }
        else if(( c==KeyEvent.VK_NUMPAD1) && !(g.s.velocityy==-1&&g.s.velocityx==1)) {
            g.mult=2;
            g.s.velocityx=-1;
            g.s.velocityy=1;
        }
        else if(( c==KeyEvent.VK_NUMPAD9) && !(g.s.velocityy==1&&g.s.velocityx==-1)) {
            g.mult=2;
            g.s.velocityx=1;
            g.s.velocityy=-1;
        }
        else if(( c==KeyEvent.VK_NUMPAD7) && !(g.s.velocityy==1&&g.s.velocityx==1)) {
            g.mult=2;
            g.s.velocityx=-1;
            g.s.velocityy=-1;
        }
        if (c==KeyEvent.VK_SPACE) {
            g.reset(g.eState);
        }
        if(c==KeyEvent.VK_R) {
            if(g.showingRec==true) g.showingRec=false;
            else g.showingRec=true;
        }
    }
    public void titleStates(int c) {
        if(showScore==false) {
            if((c==KeyEvent.VK_W || c==KeyEvent.VK_UP || c==KeyEvent.VK_NUMPAD8)) {
                g.ui.commandNum--;
                if(g.ui.commandNum==-1) {
                    g.ui.commandNum=3;
                }
            } else if((c==KeyEvent.VK_S || c==KeyEvent.VK_DOWN|| c==KeyEvent.VK_NUMPAD2)) {
                g.ui.commandNum++;
                if(g.ui.commandNum==4) {
                    g.ui.commandNum=0;
                }
            } else if(c==KeyEvent.VK_ENTER) {
                if(g.ui.commandNum==0) {
                    g.reset(g.fState);
                    g.prevGameState=g.gameState;

                }
                else if(g.ui.commandNum==1) {
                    g.reset(g.eState);
                    g.prevGameState=g.gameState;
                }
                else if(g.ui.commandNum==2) {
                    g.reset(g.autoState);
                    g.prevGameState=g.gameState;
                }
                else if(g.ui.commandNum==3) {
                    System.exit(0);
                }
            }
        } if(c==KeyEvent.VK_H) {
            if(showScore==true) {
                showScore=false;
            } else showScore=true;
        }
    }
    public void fStates(int c) {
        if((c==KeyEvent.VK_W || c==KeyEvent.VK_UP || c==KeyEvent.VK_NUMPAD8) && g.s.velocityy!=1) {
            g.s.velocityx=0;
            g.s.velocityy=-1;
        }
        else if((c==KeyEvent.VK_S || c==KeyEvent.VK_DOWN|| c==KeyEvent.VK_NUMPAD2) && g.s.velocityy!=-1) {
            g.s.velocityx=0;
            g.s.velocityy=1;
        }
        else if ((c==KeyEvent.VK_D || c==KeyEvent.VK_RIGHT|| c==KeyEvent.VK_NUMPAD6) && g.s.velocityx != -1) {
            g.s.velocityx=1;
            g.s.velocityy=0;
        }
        else if ((c==KeyEvent.VK_A || c==KeyEvent.VK_LEFT|| c==KeyEvent.VK_NUMPAD4)&& g.s.velocityx!=1) {
            g.s.velocityx=-1;
            g.s.velocityy=0;
        }
        else if (c==KeyEvent.VK_SPACE) {
            g.reset(g.fState);
        }
        if(c==KeyEvent.VK_R) {
            if(g.showingRec==true) g.showingRec=false;
            else g.showingRec=true;
        }
    }
}