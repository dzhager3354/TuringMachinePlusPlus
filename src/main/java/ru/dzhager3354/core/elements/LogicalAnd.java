package ru.dzhager3354.core.elements;

public class LogicalAnd implements Signalable {
    private Signalable a;
    private Signalable b;

    @Override
    public boolean getSignal() {
        return a.getSignal() & b.getSignal();
    }

    public LogicalAnd setA(Signalable signalable) {
        if (signalable != null) a = signalable;
        return this;
    }

    public LogicalAnd setB(Signalable signalable) {
        if (signalable != null) b = signalable;
        return this;
    }
}
