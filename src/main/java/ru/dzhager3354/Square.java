package ru.dzhager3354;

import org.lwjgl.opengl.GL11;
import ru.dzhager3354.core.LogicalElement;
import ru.dzhager3354.gui.elements.Drawable;

public class Square extends LogicalElement implements Drawable {
    private double a;

    public Square(double x, double y, double a) {
        super(x, y);
        this.a = a;
    }

    @Override
    public void draw() {
        GL11.glLineWidth(5);
        GL11.glColor3d(1, 0, 0);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            GL11.glVertex2d(getX(), getY());
            GL11.glVertex2d(getX()+a, getY());
            GL11.glVertex2d(getX() + a, getY() + a);
            GL11.glVertex2d(getX(), getY() + a);
        GL11.glEnd();
    }

    @Override
    public boolean tick() {
        setLastLogicalExit(!isLastLogicalExit());
        return isLastLogicalExit();
    }
}
