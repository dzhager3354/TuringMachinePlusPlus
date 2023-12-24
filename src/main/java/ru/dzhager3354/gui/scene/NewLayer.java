package ru.dzhager3354.gui.scene;

import org.lwjgl.opengl.GL11;
import ru.dzhager3354.gui.elements.Drawable;

public class NewLayer implements Drawable {
    //Количество ячеек на экране
    private int cellScale = 10;
    private double scale = 2.0/cellScale;

    @Override
    public void draw() {
        GL11.glPushMatrix();
            GL11.glScaled(scale, scale, 0);
            GL11.glBegin(GL11.GL_LINES);
                GL11.glColor3d(1, 0, 0);
                GL11.glVertex2d(-cellScale, 0);
                GL11.glVertex2d(cellScale, 0);
                GL11.glVertex2d(0, cellScale);
                GL11.glVertex2d(0, -cellScale);
            GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void editScale(int param) {
        cellScale += param * 2;
        if (cellScale > 30) cellScale = 30;
        if (cellScale < 6) cellScale = 6;
        scale = 2.0 / cellScale;
    }
}
