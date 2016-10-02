package com.sorrund.arboreal.engine.graph;

import java.nio.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
	private int textureId;
	
	CharSequence filename;
	
	public Texture() {
		
	}
	
	public void init() {
		textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		ByteBuffer image = STBImage.stbi_load(filename, w, h, comp, 4);
		if (image == null) {
		    throw new RuntimeException("Failed to load a texture file!"
		            + System.lineSeparator() + STBImage.stbi_failure_reason());
		}
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(),
			    h.get(), 0, GL_RGBA8, GL_UNSIGNED_BYTE, comp);
		
		glGenerateMipmap(GL_TEXTURE_2D);
	}
}
