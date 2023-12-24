package ru.dzhager3354.core;

import org.lwjgl.opengl.GL11;
import ru.dzhager3354.gui.elements.Drawable;

public class Line implements Drawable {
    private LogicalElement input;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public Line(LogicalElement element, double x1, double y1, double x2, double y2) {
        this.input = element;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw() {
        int color = input.isLastLogicalExit() ? 1 : 0;
        GL11.glBegin(GL11.GL_LINES);
            GL11.glColor3d(color, color^1, 0);
            GL11.glLineWidth(5);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x2, y2);
        GL11.glEnd();
    }
}
