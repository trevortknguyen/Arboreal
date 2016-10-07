package com.sorrund.arboreal.testing;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import com.sorrund.arboreal.engine.*;
import com.sorrund.arboreal.engine.graph.Mesh;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class Dummy implements GameLogic {

	private int debug_direction = 0;
	
	private float debug_color = 0.0f;
	
	private final Renderer renderer;
	
	private Model[] models;
	
	public Dummy() {
		renderer = new Renderer();
	}
	
	public void debugStuff() {

		models = new Model[1];
		
		float[] positions = new float[] {
			    // VO
			    -0.5f,  0.5f,  0.5f,
			    // V1
			    -0.5f, -0.5f,  0.5f,
			    // V2
			    0.5f, -0.5f,  0.5f,
			    // V3
			     0.5f,  0.5f,  0.5f,
			    // V4
			    -0.5f,  0.5f, -0.5f,
			    // V5
			     0.5f,  0.5f, -0.5f,
			    // V6
			    -0.5f, -0.5f, -0.5f,
			    // V7
			     0.5f, -0.5f, -0.5f,
			};
	    
	    float[] colors = new float[]{
	    	    0.3f, 0.0f, 0.0f,
	    	    0.0f, 0.3f, 0.0f,
	    	    0.0f, 0.0f, 0.3f,
	    	    0.0f, 0.3f, 0.3f,
	    	    0.3f, 0.0f, 0.3f,
	    	    0.3f, 0.3f, 0.0f,
	    	    0.0f, 0.0f, 0.0f,
	    	    0.3f, 0.3f, 0.3f,
	    	};
	    
	    int[] indices = new int[] {
	    	    // Front face
	    	    0, 1, 3, 3, 1, 2,
	    	    // Top Face
	    	    4, 0, 3, 5, 4, 3,
	    	    // Right face
	    	    3, 2, 7, 5, 3, 7,
	    	    // Left face
	    	    0, 1, 6, 4, 0, 6,
	    	    // Bottom face
	    	    6, 1, 2, 7, 6, 2,
	    	    // Back face
	    	    4, 6, 7, 5, 4, 7,
	    	};
	    
	    models[0] = new Model(new Mesh(positions, colors, indices));
	}
	
	/**
	 * Binds the OpenGL context to the parameter window.
	 */
	public void init(Window window) throws Exception {
		if (!window.isInitialized()) {
			throw new IllegalStateException("Window has not been initialized.");
		}
		
		window.makeContextCurrent();

		GL.createCapabilities();

		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		
		renderer.init(window);
		
		// Debug stuff
		debugStuff();
	}

	public void input(Window window) {
		if (window.isKeyPressed(GLFW_KEY_UP)) {
			debug_direction = 1;
		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			debug_direction = -1;
		} else {
			debug_direction = 0;
		}
		
		if (window.isKeyPressed(GLFW_KEY_X)) {
			Vector3f rotation = models[0].getRotation();
			float xRot = rotation.x;
			float yRot = rotation.y;
			float zRot = rotation.z;
			models[0].setRotation(xRot + 0.01f, yRot, zRot);
		}
		
		if (window.isKeyPressed(GLFW_KEY_Y)) {
			Vector3f rotation = models[0].getRotation();
			float xRot = rotation.x;
			float yRot = rotation.y;
			float zRot = rotation.z;
			models[0].setRotation(xRot, yRot + 0.01f, zRot);
		}

		if (window.isKeyPressed(GLFW_KEY_Z)) {
			Vector3f rotation = models[0].getRotation();
			float xRot = rotation.x;
			float yRot = rotation.y;
			float zRot = rotation.z;
			models[0].setRotation(xRot, yRot, zRot + 0.01f);
		}
		
		if (window.isKeyPressed(GLFW_KEY_EQUAL)) {
			Vector3f position = models[0].getPosition();
			float x = position.x;
			float y = position.y;
			float z = position.z;
			models[0].setPosition(x, y, z + 0.01f);
		}
		
		if (window.isKeyPressed(GLFW_KEY_MINUS)) {
			Vector3f position = models[0].getPosition();
			float x = position.x;
			float y = position.y;
			float z = position.z;
			models[0].setPosition(x, y, z - 0.01f);
		}
		

		if (window.isKeyPressed(GLFW_KEY_W)) {
			float scale = models[0].getScale();
			models[0].setScale(scale + 0.1f);
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			float scale = models[0].getScale();
			models[0].setScale(scale - 0.1f);
		}
	}

	public void update(float interval) {
		debug_color += debug_direction * 0.01f;
		if (debug_color > 1) { 
			debug_color = 1.0f;
		} else if (debug_color < 0) {
			debug_color = 0.0f;
		}
		
		Vector3f rotationV = models[0].getRotation();

		float xRot = (rotationV.x + 0.01f) % (2*(float)Math.PI);
		float yRot = (rotationV.y + 0.01f) % (2*(float)Math.PI);
		float zRot = (rotationV.z + 0.01f) % (2*(float)Math.PI);
		
		models[0].setRotation(xRot, yRot, zRot);
	}

	public void render(Window window) {
		window.setClearColor(debug_color, 0.5f, 0.5f, 0.0f);
		renderer.render(window);
		renderer.render(models);
	}

	public void cleanup() {
		renderer.cleanup();
		
		for (Model m : models) {
			m.getMesh().cleanup();
		}
	}

}
