package Test1;

// LWJGL utility for creating native buffers
import org.lwjgl.BufferUtils;

// OpenGL 1.1 functions (drawing, vertex arrays, constants)
import org.lwjgl.opengl.GL11;

// OpenGL 1.5 functions (VBOs / buffer objects)
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

// Represents a 3D model stored in a Vertex Buffer Object (VBO)
public class Model {

    // Number of vertices to draw
    private int draw_count;

    // OpenGL ID of the vertex buffer
    private int v_id;

    // Constructor receives an array of vertex positions (x, y, z, x, y, z, ...)
    public Model(float[] vertices) {

        // Each vertex has 3 floats → total vertices = length / 3
        draw_count = vertices.length / 3;

        // Create a native FloatBuffer to send data to OpenGL
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);

        // Copy the vertex array into the buffer
        buffer.put(vertices);

        // Prepare the buffer for reading by OpenGL
        buffer.flip();

        // Generate a new VBO and store its ID
        v_id = GL15.glGenBuffers();

        // Bind the VBO as the current array buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);

        // Upload vertex data to the GPU (STATIC = won't change often)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Unbind the VBO to avoid accidental modification
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    // Draw the model
    public void render() {

        // Enable vertex array functionality (old OpenGL fixed pipeline)
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        // Bind our VBO so OpenGL uses its data
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);

        // Tell OpenGL how vertex data is laid out:
        // 3 values per vertex (x, y, z), type = float, tightly packed, start at offset 0
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

        // Draw the vertices as triangles starting from index 0
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, draw_count);

        // Unbind the VBO after drawing
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // Disable vertex array functionality
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }
}
