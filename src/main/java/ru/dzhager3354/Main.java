package ru.dzhager3354;

import ru.dzhager3354.core.sheme.Scheme;
import ru.dzhager3354.gui.Window;

public class Main {
    public static final Window window = new Window(500, 500);
    public static final Scheme scheme = new Scheme();
    public static void main(String[] args) {
        System.out.println("hello world");
        new Thread(scheme).start();
        new Thread(window).start();
    }
}
