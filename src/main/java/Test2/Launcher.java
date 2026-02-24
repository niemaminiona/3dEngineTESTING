package Test2;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Launcher implements Runnable {
    @Override
    public void run() {
        int windowWidth = 1600;
        int windowHeight = 900;

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()){
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);

        long window = GLFW.glfwCreateWindow(windowWidth, windowHeight, "Render Engine", 0, 0);

        if(window == 0L){
            throw new IllegalStateException("Failed to create GLFW window");
        }

        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (vidMode != null) {
            GLFW.glfwSetWindowPos(window, (vidMode.width() - windowWidth) / 2, (vidMode.height() - windowHeight) / 2);
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        float[] vertices = new float[]{
                -0.5f,0.5f, 0,  // Top left      0
                0.5f, 0.5f, 0,  // Top right     1
                0.5f,-0.5f, 0,  // Bottom right  2
                -0.5f,-0.5f,0,  // Bottom left   3
        };

        int[] indices = new int[]{
                0, 1, 2
        };

        Model model = new Model(vertices, indices);

        GLFW.glfwShowWindow(window);

        // Enable v-Sync
        GLFW.glfwSwapInterval(GLFW.GLFW_TRUE);

        // sets key callback, when ESC is pressed, window closes
        GLFW.glfwSetKeyCallback(window, (win, key, _, action, _) -> {
            if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS){
                GLFW.glfwSetWindowShouldClose(win, true);
            }
        });

        int angle = 0;

        while(!GLFW.glfwWindowShouldClose(window)){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            angle++;
            vertices[0] = (float)Math.sin(Math.toRadians(angle)) * 0.5f;
            vertices[1] = (float)Math.cos(Math.toRadians(angle)) * 0.5f;

            vertices[3] = (float)Math.sin(Math.toRadians(angle + 90)) * 0.5f;
            vertices[4] = (float)Math.cos(Math.toRadians(angle + 90)) * 0.5f;

            vertices[6] = (float)Math.sin(Math.toRadians(angle + 270)) * 0.5f;
            vertices[7] = (float)Math.cos(Math.toRadians(angle + 270)) * 0.5f;
            model.setVertices(vertices);

            model.render();

            GLFW.glfwPollEvents();

            GLFW.glfwSwapBuffers(window);
        }

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}