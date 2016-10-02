package com.sorrund.arboreal.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Timer {
	private double lastLoopTime;
	
	public void init() {
		lastLoopTime = getTime();
	}
	
	/**
	 * 
	 * @return the time in seconds
	 */
	public double getTime() {
		return glfwGetTime();
	}
	
	public float getElapsedTime() {
		double time = getTime();
		float elapsedTime = (float) (time - lastLoopTime);
		lastLoopTime = time;
		return elapsedTime;
	}
	
	public double getLastLoopTime() {
		return lastLoopTime;
	}
}
