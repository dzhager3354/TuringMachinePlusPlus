package ru.dzhager3354.core;

import java.util.ArrayList;
import java.util.List;

public abstract class LogicalElement {
    private double x;
    private double y;
    private boolean lastLogicalExit = false;
    private List<LogicalElement> inputs = new ArrayList<>();

    public LogicalElement(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean tick();

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void addInput(LogicalElement element) {
        if (element != null) inputs.add(element);
    }

    public boolean isLastLogicalExit() {
        return lastLogicalExit;
    }

    public void setLastLogicalExit(boolean lastLogicalExit) {
        this.lastLogicalExit = lastLogicalExit;
    }
}
