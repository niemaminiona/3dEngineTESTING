package Test4;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TestLauncher implements Runnable {
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

        Model model = getModel();

        Shader shader = new Shader("shader2");

        Matrix4f projection = new Matrix4f()
                .ortho2D((float) -windowWidth /2, (float) windowWidth /2, (float) -windowHeight /2, (float) windowHeight /2)
                .scale(256).scale(4)
                ;

        GLFW.glfwShowWindow(window);

        // Enable v-Sync
        GLFW.glfwSwapInterval(GLFW.GLFW_TRUE);

        // sets key callback, when ESC is pressed, window closes
        GLFW.glfwSetKeyCallback(window, (win, key, _, action, _) -> {
            if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS){
                GLFW.glfwSetWindowShouldClose(win, true);
            }
        });

        float angle = 0;
        float green = 0.01f;
        float greenChange = 0.01f;

        while(!GLFW.glfwWindowShouldClose(window)){
            GLFW.glfwPollEvents();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            shader.bind();
            shader.setUniformFloat("red", 1);
            shader.setUniformMatrix4f("projection", projection);
            model.render();

            GLFW.glfwSwapBuffers(window);
        }

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private static Model getModel() {
        float size = 0.1f;

        float[] vertices = new float[]{
                -size,size, 0,  // Top left      0
                size, size, 0,  // Top right     1
                size,-size, 0,  // Bottom right  2
                -size,-size,0,  // Bottom left   3
                5*size,size,0,
                size,5*size,0
        };

        int[] indices = new int[]{
                0, 1, 5,
                2, 3, 0,
                4, 2, 1
        };

        return new Model(vertices, indices);
    }
}