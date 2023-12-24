package ru.dzhager3354.gui.scene;

import org.lwjgl.opengl.GL11;
import ru.dzhager3354.gui.Window;
import ru.dzhager3354.gui.elements.Drawable;

public class Layer implements Drawable {
    //Количество ячеек на экране
    private int cellScale = 2;
    private double scale = 2.0/cellScale;
    private double xOffset;
    private double yOffset;

    private Window window;

    //переменные, ответственные за место нажатия лкм
    private double clickXPos;
    private double clickYPos;
    private boolean isMousePressed;

    public Layer(Window window) {
        this.window = window;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
            GL11.glScaled(scale, scale, 0);
            GL11.glTranslated(xOffset, yOffset, 0);
            GL11.glBegin(GL11.GL_LINES);
                GL11.glColor3d(1, 0, 0);
                GL11.glVertex2d(-cellScale, 0);
                GL11.glVertex2d(cellScale, 0);
                GL11.glVertex2d(0, cellScale);
                GL11.glVertex2d(0, -cellScale);
            GL11.glEnd();
            if (!isMousePressed)
                drawHover();
        GL11.glPopMatrix();
    }

    private void drawHover() {
        double divX = (window.getCursorX()- window.getWindowWidth()/2)/(window.getWindowWidth()/2)/scale;
        double divY = (window.getWindowHeight()/2 - window.getCursorY())/(window.getWindowHeight()/2)/scale;

        divX -= xOffset;
        divY -= yOffset;

        double cellStartX = Math.floor(divX);
        double cellStartY = Math.floor(divY);
        double cellEndX = Math.ceil(divX);
        double cellEndY = Math.ceil(divY);

        System.out.println(String.format("%f %f %f %f", cellStartX, cellStartY, cellEndX, cellEndY));

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            GL11.glColor3d(0, 1, 1);
            GL11.glVertex2d(cellStartX, cellStartY);
            GL11.glVertex2d(cellStartX, cellEndY);
            GL11.glVertex2d(cellEndX, cellEndY);
            GL11.glVertex2d(cellEndX, cellStartY);
        GL11.glEnd();
    }

    public void startMove(double startX, double startY) {
        clickXPos = startX;
        clickYPos = startY;
        isMousePressed = true;
    }

    private void editScale() {
        scale = 2.0 / cellScale;
    }

    public void setMousePressed(boolean mousePressed) {
        isMousePressed = mousePressed;
    }

    public void cursorMove(double x, double y) {
        double divX = x - clickXPos;
        double divY = clickYPos - y;
        double xConvert = (divX/500)*2/scale;
        double yConvert = (divY/500)*2/scale;

        if (isMousePressed){
            xOffset += xConvert;
            yOffset += yConvert;

            clickXPos = x;
            clickYPos = y;
            System.out.println(String.format("%f, %f", xOffset, yOffset));
        }
    }

    public void setScale(int scale) {
        cellScale += scale * 2;
        if (cellScale < 2) cellScale = 2;
        if (cellScale > 10) cellScale = 10;
        editScale();
    }
}
