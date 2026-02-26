package Test4;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Model {
    private final int draw_count;
    private final int v_id;
    private final int i_id;

    public Model(float[] vertices, int[] indices) {
        draw_count = indices.length;

        v_id = GL15.glGenBuffers();
        setVertices(vertices);

        i_id = GL15.glGenBuffers();
        setIndices(indices);
    }

    public void render() {
        GL20.glEnableVertexAttribArray(0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);
        GL20.glVertexAttribPointer(0,3, GL11.GL_FLOAT, false,0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, i_id);

        GL11.glDrawElements(GL11.GL_TRIANGLES, draw_count, GL11.GL_UNSIGNED_INT, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL20.glDisableVertexAttribArray(0);
    }

    private FloatBuffer createFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private IntBuffer createIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void setVertices(float[] data){
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, createFloatBuffer(data), GL15.GL_STATIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void setIndices(int[] data){
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, i_id);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(data),GL15.GL_STATIC_DRAW);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
