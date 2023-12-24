package ru.dzhager3354.gui.scene;

import org.lwjgl.opengl.GL11;
import ru.dzhager3354.Main;
import ru.dzhager3354.gui.elements.Drawable;

public class Layer implements Drawable {
    private double xOffset = 0;
    private double yOffset = 0;
    private double xPixel;
    private double yPixel;
    private double scale = 1;
    private boolean isMousePressed = false;

    private Main main;

    public Layer(Main main) {
        this.main = main;
    }

    public void drawGrid() {
        GL11.glPushMatrix();
        scale();
        GL11.glTranslated(xOffset, yOffset, 0);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor3d(0, 0, 0);
        GL11.glVertex2d(-20, 0);
        GL11.glVertex2d(20, 0);
        GL11.glVertex2d(0, 20);
        GL11.glVertex2d(0, -20);
        GL11.glColor3d(1, 0, 0);
//        for (double i = -1.0/scale; i < 1.0/scale; i+= 0.2) {
//            //parallel y lines
//            GL11.glVertex2d(i, 1/scale);
//            GL11.glVertex2d(i, -1/scale);
//            //parallel x lines
//            GL11.glVertex2d(-1/scale, i);
//            GL11.glVertex2d(1/scale, i);
//        }
        GL11.glEnd();
        drawHover();
        GL11.glPopMatrix();
    }

    @Override
    public void draw() {
        drawGrid();
    }

    public void scale() {
        GL11.glScaled(scale, scale, 0);
    }

    public void startMove(double startX, double startY) {
        xPixel = startX;
        yPixel = startY;
        isMousePressed = true;
    }

    public void cursorMove(double x, double y) {
        double divX = x - xPixel;
        double divY = yPixel - y;
        double xConvert = (divX/500)*2/scale;
        double yConvert = (divY/500)*2/scale;

        if (isMousePressed){
            xOffset += xConvert;
            yOffset += yConvert;

            xPixel = x;
            yPixel = y;
        }
    }

    public void drawHover() {
        if (isMousePressed) return;
        double divX = 0;//main.getCursorX()-250;
        double divY = 0;// 250-main.getCursorY();
        double xConvert = (divX/500)*2/scale;
        double yConvert = (divY/500)*2/scale;

        xConvert = -xOffset+xConvert-1/scale+1;
        yConvert = -yOffset+yConvert + 1/scale-1;
        System.out.println("convert: " + xConvert + " " + yConvert);
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glColor3d(0, 1, 1);
//        GL11.glVertex2d(xConvert-xConvert % 0.2, yConvert-yConvert % 0.2);
//        double x = xConvert - xConvert % 0.2 + (xConvert > 0 ? 0.2 : -0.2);
//        GL11.glVertex2d(x, yConvert-yConvert % 0.2);
//        double y = yConvert - yConvert % 0.2 + (yConvert > 0 ? 0.2 : -0.2);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(xConvert-xConvert % 0.2, y);
            GL11.glTranslated(-xConvert, -yConvert, 0);
            GL11.glVertex2d(0, 0);
            GL11.glVertex2d(0, 0.2);
            GL11.glVertex2d(0.2, 0);
            GL11.glVertex2d(0.2, 0.2);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void setScale(int level) {
        scale += level * 0.05;
        if (scale > 2) scale = 2;
        if (scale < 0.2) scale = 0.2;
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        isMousePressed = mousePressed;
    }
}
