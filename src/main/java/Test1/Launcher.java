package Test1;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

public class Launcher implements Runnable {
    @Override
    public void run() {
        // Sets an error callback so GLFW errors are printed to stderr.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initializes the GLFW library.
        // If initialization fails, the program stops with an error.
        if (!GLFW.glfwInit()){
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Requests an OpenGL context version 3.3 from the graphics driver.
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        // Requests the *core profile*
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        long window = GLFW.glfwCreateWindow(640, 480, "Render Engine", 0, 0);

        // Checks if the window creation failed (0 = NULL pointer).
        if(window == 0L){
            throw new IllegalStateException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        // show the window
//        GLFW.glfwShowWindow(window);

        // sets key callback, when ESC is pressed, window closes
        GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS){
                GLFW.glfwSetWindowShouldClose(win, true);
            }
        });

        // updates window
        while(!GLFW.glfwWindowShouldClose(window)){
           update(window);
           GLFW.glfwPollEvents();
        }

        // "Destroys" window and frees resources
        GLFW.glfwDestroyWindow(window);

        // Properly shuts down GLFW and frees resources.
        GLFW.glfwTerminate();
    }

    private void update(long window){
        int[] w = new int[1];
        int[] h = new int[1];

        GLFW.glfwGetFramebufferSize(window, w, h);

        int width = w[0];
        int height = h[0];

        GL11.glViewport(0, 0, width, height);
    }
}