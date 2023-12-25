package ru.dzhager3354.core.sheme;

import java.util.ArrayList;
import java.util.List;

public class Scheme implements Runnable{
    private List<SchemeLayer> layerList = new ArrayList<>();
    private int currentLayer;
    public boolean isRun = true;

    {
        layerList.add(new SchemeLayer());
    }

    @Override
    public void run() {
        while(isRun) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}