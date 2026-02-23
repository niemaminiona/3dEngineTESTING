package Test1;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;

public class Launcher implements Runnable {
    @Override
    public void run() {
        int windowWidth = 1600;
        int windowHeight = 900;

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
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        // Requests *any profile*
//        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_ANY_PROFILE);
        // Requests *compat profile*
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);

        long window = GLFW.glfwCreateWindow(windowWidth, windowHeight, "Render Engine", 0, 0);

        // Checks if the window creation failed (0 = NULL pointer).
        if(window == 0L){
            throw new IllegalStateException("Failed to create GLFW window");
        }

        // Gets information about screen
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        // sets window to middle of the screen
        if (vidMode != null) {
            GLFW.glfwSetWindowPos(window, (vidMode.width() - windowWidth) / 2, (vidMode.height() - windowHeight) / 2);
        }

        // Makes the OpenGL context of this window current on the calling thread so OpenGL commands affect this window
        GLFW.glfwMakeContextCurrent(window);

        // Loads OpenGL functions for the current window context.
        GL.createCapabilities();

        float[] verticies1 = new float[]{
            -0.25f,-0.25f, 0,
             0, 0.25f, 0,
             0.25f,-0.25f, 0
        };

        float[] verticies2 = new float[]{
            -0.1f,0.15f, 0,
            0.1f,-0.15f, 0,
            0.1f, 0.15f, 0,

            -0.1f,0.15f, 0,
            0.1f,-0.15f, 0,
            -0.1f, -0.15f, 0,

        };

        float[] vertices = verticies2;

        Model model = new Model(verticies1);

        // show the window
        GLFW.glfwShowWindow(window);

        // Enable v-Sync
        GLFW.glfwSwapInterval(GLFW.GLFW_TRUE);

        // sets key callback, when ESC is pressed, window closes
        GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS){
                GLFW.glfwSetWindowShouldClose(win, true);
            }
        });

        // updates window when it shouldn't close
        while(!GLFW.glfwWindowShouldClose(window)){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS){
                vertices = moveFloatPointsToLeft(vertices);
            }
            if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS){
                vertices = moveFloatPointsToRight(vertices);
            }
            if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS){
                vertices = moveFloatPointsToUp(vertices);
            }
            if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS){
                vertices = moveFloatPointsToDown(vertices);
            }

            model.setVertices(vertices);

            model.render();

            // Checks for user input and window events (like key presses or close requests) and processes them
            GLFW.glfwPollEvents();

            // Swaps the back buffer with the front buffer.
            // Shows what was just rendered on the screen (prevents flickering).
            GLFW.glfwSwapBuffers(window);
        }

        // "Destroys" window and frees resources
        GLFW.glfwDestroyWindow(window);

        // Properly shuts down GLFW and frees resources.
        GLFW.glfwTerminate();
    }

    private float speed = 0.025f;

    private float[] moveFloatPointsToRight(float[] vertices){
        for(int i = 0; i < vertices.length; i+=3){
            vertices[i] += speed;
        }
        return vertices;
    }

    private float[] moveFloatPointsToLeft(float[] vertices){
        for(int i = 0; i < vertices.length; i+=3){
            vertices[i] -= speed;
        }
        return vertices;
    }

    private float[] moveFloatPointsToUp(float[] vertices){
        for(int i = 1; i < vertices.length; i+=3){
            vertices[i] += speed;
        }
        return vertices;
    }

    private float[] moveFloatPointsToDown(float[] vertices){
        for(int i = 1; i < vertices.length; i+=3){
            vertices[i] -= speed;
        }
        return vertices;
    }
}