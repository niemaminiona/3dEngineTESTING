package Test4;

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

        float size = 0.1f;

        float[] vertices = new float[]{
                -size,size, 0,  // Top left      0
                size, size, 0,  // Top right     1
                size,-size, 0,  // Bottom right  2
                -size,-size,0,  // Bottom left   3

                -size,size, 0,  // Top left      4
                size, size, 0,  // Top right     5
                size,-size, 0,  // Bottom right  6
                -size,-size,0,  // Bottom left   7
        };

        int[] indices = new int[]{
                0, 1, 2,
                2, 3, 0,
        };

        Model model = new Model(vertices, indices);

        Shader shader = new Shader("shader1");

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

        while(!GLFW.glfwWindowShouldClose(window)){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            model.setVertices(rotateTriangles(vertices, angle));

            angle += 0.25f;
            green += 0.01f;

            shader.bind();
            shader.setUniform("red", 1);
            shader.setUniform("green", green);

            model.render();

            GLFW.glfwPollEvents();

            GLFW.glfwSwapBuffers(window);
        }

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private float[] rotateTriangles(float[] vertices, float angle) {

        float rad = (float)Math.toRadians(angle);
        float cos = (float)Math.cos(rad);
        float sin = (float)Math.sin(rad);

        float[] rotated = new float[vertices.length];

        for (int i = 0; i < vertices.length; i += 3) {

            float x = vertices[i];
            float y = vertices[i + 1];
            float z = vertices[i + 2];

            rotated[i]     = x * cos - y * sin;
            rotated[i + 1] = x * sin + y * cos;
            rotated[i + 2] = z; // unchanged
        }

        return rotated;
    }

}