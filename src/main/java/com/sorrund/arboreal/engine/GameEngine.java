package com.sorrund.arboreal.engine;

public class GameEngine implements Runnable {

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;
    
	private final Window window;
	
	private final Thread gameLoopThread;
	
	private final Timer timer;
	
	private final GameLogic gameLogic;
	
	public GameEngine(String windowTitle, int width, int height, boolean vSync, GameLogic gameLogic) {
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
		window = new Window(windowTitle, width, height, vSync);
		this.gameLogic = gameLogic;
		timer = new Timer();
	}

	public void start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
	}
	
	@Override
	public void run() {
		try {
			init();
			gameLoop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanup();
		}
	}
	
	protected void init() throws Exception {
		timer.init();
		window.init();
		gameLogic.init(window);
	}
	
	protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if ( !window.isvSync() ) {
                sync();
            }
        }
	}
	
	protected void cleanup() {
		gameLogic.cleanup();
	}
	
	private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
	}
	
	protected void input() {
		gameLogic.input(window);
	}
	
	protected void update(float interval) {
		gameLogic.update(interval);
	}
	
	protected void render() {
		gameLogic.render(window);
		window.update();
	}
}
