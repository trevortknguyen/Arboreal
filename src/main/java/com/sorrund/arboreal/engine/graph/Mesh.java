package com.sorrund.arboreal.engine.graph;

import java.nio.*;
import java.util.*;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
	private final int vaoId;
	
	private List<Integer> vboIds;
	private Texture texture;
	
	private final int vertexCount;
	
	public Mesh(float[] positions, float[] colors, int[] indices) {
		this.texture = texture;
		
		vboIds = new LinkedList<>();
		
		vertexCount = indices.length;
		
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		// Position VBO
		int vboId = glGenBuffers();
		vboIds.add(vboId);
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
		verticesBuffer.put(positions).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		// Texture VBO
		vboId = glGenBuffers();
		vboIds.add(vboId);
		FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(colors.length);
		texCoordsBuffer.put(colors).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		
		// Index VBO
		vboId = glGenBuffers();
		vboIds.add(vboId);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		indicesBuffer.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public Mesh(float[] positions, float[] texCoords, int[] indices, Texture texture) {
		this.texture = texture;
		
		vboIds = new LinkedList<>();
		
		vertexCount = indices.length;
		
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		// Position VBO
		int vboId = glGenBuffers();
		vboIds.add(vboId);
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(positions.length);
		verticesBuffer.put(positions).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

		// Texture VBO
		vboId = glGenBuffers();
		vboIds.add(vboId);
		FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
		texCoordsBuffer.put(texCoords).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		// Index VBO
		vboId = glGenBuffers();
		vboIds.add(vboId);
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
		indicesBuffer.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void render() {
		glBindVertexArray(getVaoId());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(getVaoId());
	}
	
	public int getVaoId() {
		return vaoId;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	
	public void cleanup() {
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		for (int vboId : vboIds) {
			glDeleteBuffers(vboId);
		}
	
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}
}
