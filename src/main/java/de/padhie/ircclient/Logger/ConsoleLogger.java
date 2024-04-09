package de.padhie.ircclient.Logger;

public class ConsoleLogger implements LoggerInterface {
    public void addLog (String message) {
        System.out.println(message);
    }
}
