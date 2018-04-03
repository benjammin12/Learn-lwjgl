package org.lwjgl.game;

import org.lwjgl.engine.GameLogic;
import org.lwjgl.engine.Window;
import org.lwjgl.engine.graph.Mesh;


import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements GameLogic {

    private int direction = 0;

    private float color = 0.0f;

    private final Renderer renderer;

    private Mesh mesh;

    public DummyGame() {
        renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        float[] positions = new float[]{ //our 4 vertices needed to render the 2 triangles
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,};

        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };

        int[] indices = new int[]{ //the order in which we should render those vertices
                0, 1, 3, 3, 1, 2,};

        mesh = new Mesh(positions, colours, indices);
    }

    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
            direction = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
            direction = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)){
            direction = 1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window,mesh);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        mesh.cleanUp();
    }
}