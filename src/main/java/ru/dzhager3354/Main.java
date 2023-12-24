package ru.dzhager3354;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import ru.dzhager3354.core.Line;
import ru.dzhager3354.gui.Window;
import ru.dzhager3354.gui.elements.Drawable;
import ru.dzhager3354.gui.scene.Layer;
import ru.dzhager3354.gui.scene.NewLayer;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;


public class Main {
    private long window;
    private final int ticks = 20;
    private int tick = ticks;
    private Layer layer = new Layer(this);
    private Drawable newLayer = new NewLayer();

    private final double[] cursorX = new double[1];
    private final double[] cursorY = new double[1];

    public void init() {
        GLFW.glfwInit();

        glfwDefaultWindowHints();
        window = glfwCreateWindow(500, 500, new StringBuffer("Hello"), 0, 0);

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                glfwGetCursorPos(window, cursorX, cursorY);
                layer.startMove(cursorX[0], cursorY[0]);
            }
            else if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
                layer.setMousePressed(false);
            }
        });

        glfwSetCursorPosCallback(window, (window, x, y)-> {
            layer.cursorMove(x, y);
            cursorX[0] = x;
            cursorY[0] = y;
        });

        glfwSetScrollCallback(window, (window, x, y) -> layer.setScale((int)y));

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(5);

        glfwShowWindow(window);
    }

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void loop() {
        GL.createCapabilities();
        glClearColor(1, 1, 1, 1);
        Square drawable = new Square(0, 0, 0.5);
        Line line = new Line(drawable, 1, 1, -1, -1);
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//            line.draw();
//            drawable.draw();
//            drawable.tick();
            layer.draw();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public double getCursorX() {
        return cursorX[0];
    }

    public double getCursorY() {
        return cursorY[0];
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        new Main().run();
    }
}
