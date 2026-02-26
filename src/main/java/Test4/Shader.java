package Test4;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public class Shader {
    private final int program;

    public Shader(String filename){
        program = GL20.glCreateProgram();

        // Vertex Shader
        int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vs, readFile(filename + "/" + filename + ".vs"));
        GL20.glCompileShader(vs);

        if(GL20.glGetShaderi(vs, GL20.GL_COMPILE_STATUS) != 1){
            System.err.println(GL20.glGetShaderInfoLog(vs));
            System.exit(1);
        }

        // Fragment Shader
        int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fs, readFile(filename + "/" + filename + ".fs"));
        GL20.glCompileShader(fs);

        if(GL20.glGetShaderi(fs, GL20.GL_COMPILE_STATUS) != 1){
            System.err.println(GL20.glGetShaderInfoLog(fs));
            System.exit(1);
        }

        GL20.glAttachShader(program, vs);
        GL20.glAttachShader(program, fs);

        GL20.glBindAttribLocation(program, 0, "vertices");

        GL20.glLinkProgram(program);

        if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != 1){
            System.err.println(GL20.glGetProgramInfoLog(program));
            System.exit(1);
        }

        GL20.glValidateProgram(program);

        if(GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != 1){
            System.err.println(GL20.glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public void bind(){
        GL20.glUseProgram(program);
    }

    public void setUniformFloat(String name, float value){
        int location = GL20.glGetUniformLocation(program, name);
        if(location != -1){
            GL20.glUniform1f(location, value);
        }
    }

    public void setUniformInt(String name, int value){
        int location = GL20.glGetUniformLocation(program, name);
        if(location != -1){
            GL20.glUniform1i(location, value);
        }
    }

    public void setUniformMatrix4f(String name, Matrix4f value){
        int location = GL20.glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if(location != -1){
            GL20.glUniformMatrix4fv(location, false, buffer);
        }
    }


    private String readFile(String filename){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader("./shaders/" + filename));
            String line;
            while ((line = br.readLine()) != null){
                string.append(line);
                string.append("\n");
            }
            br.close();
        }catch (IOException e) {
            throw new RuntimeException("Failed to load shader file: " + filename, e);
        }
        return string.toString();
    }
}
