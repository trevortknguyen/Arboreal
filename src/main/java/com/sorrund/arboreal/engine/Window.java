package com.sorrund.arboreal.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Window {
	private final String title;
	private int width;
	private int height;
	
	private long windowHandle;
	
	//
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWWindowSizeCallback windowSizeCallback;
	
	private boolean resized;
	private boolean vSync;
	
	private boolean isInitialized;
	
	public Window(String title, int width, int height, boolean vSync) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;
		this.resized = false;
		this.isInitialized = false;
	}
	
	/**
	 * Does not leave the OpenGL context bound to the window.
	 */
	public void init() {
		errorCallback = GLFWErrorCallback.createPrint(System.err);
		errorCallback.set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialized GLFW windowing system.");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if (windowHandle == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		windowSizeCallback = GLFWWindowSizeCallback.create((window, width, height) -> {
				Window.this.width = width;
				Window.this.height = height;
				Window.this.setResized(true);
		});
		windowSizeCallback.set(windowHandle);
		
		keyCallback = GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
					glfwSetWindowShouldClose(window, true);
				}
		});
		keyCallback.set(windowHandle);
		
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(
				windowHandle,
				(vidmode.width() - width) / 2,
				(vidmode.height() - height) / 2
		);
		

		glfwMakeContextCurrent(windowHandle);
		if (isvSync()) {
			glfwSwapInterval(1);
		}
		glfwMakeContextCurrent(NULL);

		glfwShowWindow(windowHandle);
		
		isInitialized = true;
	}
	
	public void setClearColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}
	
	public void makeContextCurrent() {
		glfwMakeContextCurrent(windowHandle);
	}
	
	/**
	 * Determines if a given key is being pressed as input.
	 * @param keyCode the GLFW keyCode as defined in the GLFW class
	 * @return if the key is being pressed
	 */
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}
	
	public boolean windowShouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isResized() {
		return resized;
	}
	
	public void setResized(boolean resized) {
		this.resized = resized;
	}
	
	public boolean isvSync() {
		return vSync;
	}
	
	public void setvSync(boolean vSync) {
		this.vSync = vSync;
	}
	
	public boolean isInitialized() {
		return isInitialized;
	}
	
	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}
}
