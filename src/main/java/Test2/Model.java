package Test2;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class Model {

    private int draw_count;
    private final int v_id;
    private float[] vertices;

    public Model(float[] vertices) {
        this.vertices = vertices;
        v_id = GL15.glGenBuffers();
        uploadVertexData();
    }

    private void uploadVertexData() {
        draw_count = vertices.length / 3;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void render() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, draw_count);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
        uploadVertexData();
    }
}