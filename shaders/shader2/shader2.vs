#version 330

attribute vec3 vertices;

uniform mat4 projection;
uniform mat4 model;

void main(){
    gl_Position = projection * model * vec4(vertices, 1.0);
}