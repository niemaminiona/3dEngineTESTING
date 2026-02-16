package Test1;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Launcher implements Runnable {
    @Override
    public void run() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()){
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        long window = GLFW.glfwCreateWindow(640, 480, "Render Engine", 0, 0);

        if(window == 0L){
            throw new IllegalStateException("Failed to create GLFW window");
        }

        GLFW.glfwTerminate();
    }
}