package Test4;

public class Main {
    static void main() {
        // Starts Launcher in new thread
        new Thread(new TestLauncher()).start();
    }
}
