package ru.dzhager3354.core.sheme;

import ru.dzhager3354.core.elements.LogicalAnd;
import ru.dzhager3354.core.elements.RandomSignal;
import ru.dzhager3354.core.elements.Signalable;

import java.util.ArrayList;
import java.util.List;

public class SchemeLayer {
    List<Signalable> signalableList = new ArrayList<>();

    {
        signalableList.add(new LogicalAnd().setA(new RandomSignal()).setB(new RandomSignal()));
    }

    public void addElement(Signalable element) {
        if (element == null) return;
        signalableList.add(element);
    }
}
