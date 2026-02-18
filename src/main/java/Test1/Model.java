package Test1;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class Model {
    private int draw_count;
    private int v_id;
    public Model(float[] vertices){
        draw_count = vertices.length / 3;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        v_id = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer ,GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void render(){
        GL15.glEnableClientState(GL15.GL_VERTEX_ARRAY);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, v_id);
        GL15.glVertexPointer(3, GL11.GL_FLOAT, 0,0);

        GL15.glDrawArrays(GL11.GL_TRIANGLES, 0, draw_count);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glDisableClientState(GL15.GL_VERTEX_ARRAY);
    }
}
