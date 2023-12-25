package ru.dzhager3354.core.elements;

public class RandomSignal implements Signalable{
    @Override
    public boolean getSignal() {
        return (Math.round(Math.random()) == 1L);
    }
}
