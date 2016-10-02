package com.sorrund.arboreal.testing;

import com.sorrund.arboreal.engine.*;
import com.sorrund.arboreal.engine.graph.*;

import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
	private ShaderProgram shaderProgram;
	
	private final float FOV;
	private final float Z_NEAR;
	private final float Z_FAR;
	
	private float aspectRatio;
	
	private Transformation transformation;
	
	public Renderer() {
		FOV = (float) Math.toRadians(60.0);
		Z_NEAR = 0.01f;
		Z_FAR = 1000.0f;
		
		transformation = new Transformation();
	}

	public void init(Window window) throws Exception {
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
		shaderProgram.link();
		
		aspectRatio = (float) window.getWidth() / (float) window.getHeight();
		
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("worldMatrix");
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Window window) {
		clear();
		
		if ( window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			aspectRatio = (float) window.getWidth() / (float) window.getHeight();
			window.setResized(false);
		}
	}
	
	public void render(Model[] models) {
		shaderProgram.bind();
		
		Matrix4f projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);

		for (Model m : models) {
			Matrix4f worldMatrix = transformation.getWorldMatrix(
					m.getPosition(),
					m.getRotation(),
					m.getScale()
			);
			
			shaderProgram.setUniform("worldMatrix", worldMatrix);
			
			m.getMesh().render();
		}
		
		
		shaderProgram.unbind();
	}
	
	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}
