package org.lwjgl.game;

import org.lwjgl.engine.Utils;
import org.lwjgl.engine.Window;
import org.lwjgl.engine.graph.ShaderProgram;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private ShaderProgram shaderProgram;
    private int vaoId;
    private int vboId;



    public Renderer() {
    }

    public void init() throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
        shaderProgram.link();

        float[] vertices = new float[]{
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };

        FloatBuffer verticesBuffer = null;

        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            verticesBuffer.put(vertices).flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Create the VBO and bind to it
            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

            // Define structure of the data
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Unbind the VBO
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            // Unbind the VAO
            glBindVertexArray(0);
        } finally { //free the memory
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }

    }

    public void render(Window window){
        clear();

        if (window.isResized()){
            glViewport(0,0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        //Bind to the VAO
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        //Draw the vertices
        glDrawArrays(GL_TRIANGLES, 0 , 3);

        //restore state
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shaderProgram.unbind();
    }


    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    //clean up resources
    public void cleanup() {
        if(shaderProgram != null){
            shaderProgram.cleanup();
        }


        glDisableVertexAttribArray(0);

        //Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        //Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);

    }
}