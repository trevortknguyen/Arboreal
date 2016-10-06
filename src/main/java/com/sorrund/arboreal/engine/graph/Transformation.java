package com.sorrund.arboreal.engine.graph;

import org.joml.*;

public class Transformation {
	private final Matrix4f projectionMatrix;
	private final Matrix4f worldMatrix;
	
	public Transformation() {
		projectionMatrix = new Matrix4f();
		worldMatrix = new Matrix4f();
	}
	
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		float aspectRatio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
		return projectionMatrix;
	}
	
	public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		worldMatrix.identity().translate(offset).
			rotateX(rotation.x).
			rotateY(rotation.y).
			rotateZ(rotation.z).
			scale(scale);
		return worldMatrix;
	}
}
