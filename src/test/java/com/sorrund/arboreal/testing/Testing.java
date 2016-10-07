package com.sorrund.arboreal.testing;

import com.sorrund.arboreal.engine.*;

public class Test {
	public static void main(String[] args) {
		try {
			boolean vSync = true;
			GameLogic gameLogic = new Dummy();
			GameEngine gameEngine = new GameEngine("GAME", 600, 480, vSync, gameLogic);
			gameEngine.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}