package Test;

public class Main {
    public static void main(String[] args) {
        // Starts Launcher in new thread
        new Thread(new Launcher()).start();
    }
}
