package ru.dzhager3354.gui;

import ru.dzhager3354.gui.elements.Drawable;
import ru.dzhager3354.gui.scene.NewLayer;

import java.util.ArrayList;
import java.util.List;

public class Window implements Drawable {
    private List<NewLayer> layerList = new ArrayList<>();
    private int currentNewLayer;
    private int windowWidth;
    private int windowHeight;
    private final long windowID;

    public Window(long windowID, int windowWidth, int windowHeight) {
        this.windowID = windowID;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    {
        layerList.add(new NewLayer());
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


}
