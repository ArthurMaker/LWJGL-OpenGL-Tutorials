package com.ra4king.opengl.arcsynthesis.gl33.chapter3.example4;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.ra4king.opengl.GLProgram;
import com.ra4king.opengl.util.ShaderProgram;

public class Example3_4 extends GLProgram {
	public static void main(String[] args) {
		new Example3_4().run(true);
	}
	
	private ShaderProgram program;
	private int timeLocation;
	private int vbo;
	
	private long elapsedTime;
	
	public Example3_4() {
		super("Example 3.4 - Frag Change Color", 500, 500, true);
	}
	
	@Override
	public void init() {
		glClearColor(0, 0, 0, 0);
		
		program = new ShaderProgram(readFromFile("example3.4.vert"), readFromFile("example3.4.frag"));
		timeLocation = program.getUniformLocation("time");
		
		int loopDurationLocation = program.getUniformLocation("loopDuration");
		int fragLoopDuration = program.getUniformLocation("fragLoopDuration");
		program.begin();
		glUniform1f(loopDurationLocation, 5);
		glUniform1f(fragLoopDuration, 10);
		program.end();
		
		vbo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, (FloatBuffer)BufferUtils.createFloatBuffer(12).put(new float[] { 0.25f, 0.25f, 0.0f, 1.0f,
				0.25f, -0.25f, 0.0f, 1.0f,
				-0.25f, -0.25f, 0.0f, 1.0f }).flip(), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// In core OpenGL, Vertex Array Objects (VAOs) are required for all draw calls. VAOs will be explained in Chapter 5.
		glBindVertexArray(glGenVertexArrays());
	}
	
	@Override
	public void update(long deltaTime) {
		elapsedTime += deltaTime;
	}
	
	@Override
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		
		program.begin();
		
		glUniform1f(timeLocation, elapsedTime / (float)1e9);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		
		glDrawArrays(GL_TRIANGLES, 0, 3);
		
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		program.end();
	}
}
