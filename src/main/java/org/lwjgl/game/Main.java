package org.lwjgl.game;


import org.lwjgl.engine.GameEngine;
import org.lwjgl.engine.GameLogic;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            GameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("GAME", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}