package IrcClient.Logger;

public class ConsoleLogger implements LoggerInterface {
    public void addLog (String message) {
        System.out.println(message);
    }
}
