package com.sorrund.arboreal.engine.graph;

import java.nio.FloatBuffer;
import java.util.*;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	private final int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private final Map<String, Integer> uniforms;
	
	public ShaderProgram() throws Exception {
		programId = glCreateProgram();
		if (programId == 0) {
			throw new Exception("Could not create Shader");
		}
		
		uniforms = new HashMap<>();
	}
	
	public void createUniform(String uniformName) throws Exception {
		int uniformLocation = glGetUniformLocation(programId, uniformName);
		if (uniformLocation < 0) {
			throw new Exception("Could not find uniform: " + uniformName + " in shader program.");
		}
		
		uniforms.put(uniformName, uniformLocation);
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		FloatBuffer fbuf = BufferUtils.createFloatBuffer(4*4);
		value.get(fbuf);
		glUniformMatrix4fv(uniforms.get(uniformName), false, fbuf);
	}
	
	public void createVertexShader(String shaderSource) throws Exception {
		vertexShaderId = createShader(shaderSource, GL_VERTEX_SHADER);
	}
	
	public void createFragmentShader(String shaderSource) throws Exception {
		fragmentShaderId = createShader(shaderSource, GL_FRAGMENT_SHADER);
	}
	
	protected int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderId = glCreateShader(shaderType);
		if (shaderId == 0) {
			throw new Exception("Error creating shader. Code: " + shaderId);
		}
		
		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);
		
		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}
		
		glAttachShader(programId, shaderId);
		
		return shaderId;
	}
	
	public void link() throws Exception {
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking shader code: " + glGetProgramInfoLog(programId, 1024));
		}
		
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
		}
	}
	
	public void bind() {
		glUseProgram(programId);
	}
	
	public void unbind() {
		glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if (programId != 0) {
			if (vertexShaderId != 0)
				glDetachShader(programId, vertexShaderId);
			if (fragmentShaderId != 0)
				glDetachShader(programId, fragmentShaderId);
		}
		glDeleteProgram(programId);
	}
}
