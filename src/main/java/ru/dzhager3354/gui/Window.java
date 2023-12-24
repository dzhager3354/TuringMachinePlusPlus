package ru.dzhager3354.gui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import ru.dzhager3354.Square;
import ru.dzhager3354.core.Line;
import ru.dzhager3354.gui.elements.Drawable;
import ru.dzhager3354.gui.scene.Layer;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Window implements Drawable, Runnable {
    private List<Layer> layerList = new ArrayList<>();
    private int currentNewLayer;
    private int windowWidth;
    private int windowHeight;
    private long windowID;

    //Текущие кооринаты мыши на экране в пикселях
    private final double[] cursorX = new double[1];
    private final double[] cursorY = new double[1];

    public Window(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    {
        layerList.add(new Layer(this));
    }

    @Override
    public void draw() {
        layerList.get(currentNewLayer).draw();
    }

    public boolean swapLayer(boolean isNextLayer) {
        if (isNextLayer && layerList.size() < currentNewLayer+1) return false;
        if (!isNextLayer && currentNewLayer-1 < 0) return false;
        currentNewLayer++;
        return true;
    }

    @Override
    public void run() {
        init();
        loop();

        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);

        glfwTerminate();
    }

    private void loop() {
        GL.createCapabilities();
        glClearColor(1, 1, 1, 1);
        Square drawable = new Square(0, 0, 0.5);
        Line line = new Line(drawable, 1, 1, -1, -1);
        while ( !glfwWindowShouldClose(windowID) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//            line.draw();
//            drawable.draw();
//            drawable.tick();
            getCurrentLayer().draw();

            glfwSwapBuffers(windowID);
            glfwPollEvents();
        }
    }

    private void init() {
        GLFW.glfwInit();

        glfwDefaultWindowHints();
        windowID = glfwCreateWindow(windowWidth, windowHeight, new StringBuffer("TuringMachine++"), 0, 0);

        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        glfwSetMouseButtonCallback(windowID, (window, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                glfwGetCursorPos(window, cursorX, cursorY);
                getCurrentLayer().startMove(cursorX[0], cursorY[0]);
            }
            else if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
                getCurrentLayer().setMousePressed(false);
            }
        });

        glfwSetCursorPosCallback(windowID, (window, x, y)-> {
            getCurrentLayer().cursorMove(x, y);
            cursorX[0] = x;
            cursorY[0] = y;
        });

        glfwSetScrollCallback(windowID, (window, x, y) -> getCurrentLayer().setScale((int)y));

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowID, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowID,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(windowID);
        // Enable v-sync
        glfwSwapInterval(5);

        glfwShowWindow(windowID);
    }

    //
    //START GET/SET ZONE
    //

    private Layer getCurrentLayer() {
        return layerList.get(currentNewLayer);
    }


    public double getCursorX() {
        return cursorX[0];
    }

    public double getCursorY() {
        return cursorY[0];
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
}
