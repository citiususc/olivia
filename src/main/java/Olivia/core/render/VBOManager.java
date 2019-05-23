package Olivia.core.render;

import Olivia.core.Olivia;
import Olivia.core.data.Point3D_id;
import Olivia.core.data.PointArray;
import Olivia.core.render.colours.ColourArray;
import Olivia.core.render.colours.PointColour;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * This manager provides methods for handle the VBOs (Vertex buffer objects), VBOs need to be built first, then drawn, if the data changes they need to be repacked before drawing
 *
 * @author jorge.martinez.sanchez
 */
public class VBOManager {
    /**
     * Default Red value in case colours are not defined
     */
    public static float DEFAULT_R = 1.0f;
    /**
     * Default Green value in case colours are not defined
     */
    public static float DEFAULT_G = 1.0f;
    /**
     * Default Blue value in case colours are not defined
     */
    public static float DEFAULT_B = 1.0f;
    
    /**
     * Draws the VBO on the screen using OpneGL
     * @param screen The OpenGL screen where it will be rendered
     * @param indices an array with the indices (the names) of the buffer to render
     * @param num the number of vertices in each buffer
     * @param renderMode the render mode (how the vertices will be read, as points one by obne, as lines, in twos, as triangles, in threes ...)
     * @param rasterMode the raster mode (how the shapes will be drawn, just the points, with lines, wireframe, of faces filled )
     * @param size the size to draw the points and lines
     */
    public static void draw(OpenGLScreen screen, int[] indices, int[] num, int[] vao, int renderMode, int rasterMode, int size) {
        screen.getGl2().glBindVertexArray(vao[0]);
        screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
        
        screen.getGl2().glEnableClientState(GL2.GL_VERTEX_ARRAY);
        screen.getGl2().glEnableClientState(GL2.GL_COLOR_ARRAY);
        screen.getGl2().glVertexPointer(3, GL.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 0);
        screen.getGl2().glColorPointer(3, GL.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 3 * Buffers.SIZEOF_FLOAT);
        
        /*
        screen.getGl2().glVertexAttribPointer(screen.vertexShaderPosition(), 3, GL.GL_FLOAT, false, 6*Buffers.SIZEOF_FLOAT, 0);
        screen.getGl2().glEnableVertexAttribArray(screen.vertexShaderPosition());
        //screen.getGl2().glVertexAttribPointer(screen.colorShaderPosition(), 3, GL.GL_FLOAT, false, 6*Buffers.SIZEOF_FLOAT, 3*Buffers.SIZEOF_FLOAT);
        //screen.getGl2().glEnableVertexAttribArray(screen.colorShaderPosition());
        screen.getGl2().glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6*Buffers.SIZEOF_FLOAT, 3*Buffers.SIZEOF_FLOAT);
        screen.getGl2().glEnableVertexAttribArray(1);*/
        screen.useShader();
        
        screen.getGl2().glPointSize(size);
        screen.getGl2().glLineWidth(size);
        screen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, rasterMode);
        screen.getGl2().glBindVertexArray(vao[0]);
        screen.getGl2().glDrawArrays(renderMode, 0, num[0]);
        screen.stopUsingShader();
        screen.getGl2().glDisableClientState(GL2.GL_VERTEX_ARRAY);
        screen.getGl2().glDisableClientState(GL2.GL_COLOR_ARRAY);
        screen.getGl2().glBindVertexArray(0);
    }
   
    /**
     * Loads a buffer with data
     * @param points the vertices to be loaded in the buffer
     * @param buffer the buffer, must be large enough to fit all the vertices (not checked directly)
     * @param colors The colours of each vertex, must be the same size as the points
     * @param show An array with boolean flags indicating whether to load that vertex or not, must be the same size as the points
     */
    private static void loadBuffer(PointArray points, FloatBuffer buffer, ColourArray<PointColour> colors, boolean[] show) {
        if (points.size() != colors.size()) {
            Olivia.textOutputter.println("Caution: Points and Colours do not match size: " + points.size() + " vs. " + colors.size() + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        if (points.size() != show.length) {
            Olivia.textOutputter.println("Caution: Points and show flags do not match size: " + points.size() + " vs. " + show.length + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        for (int i = 0; i < points.getVboBufferCapacity(); i++) {
            if (show[i]) {
                buffer.put((float) ((Point3D_id) points.get(i)).getX());
                buffer.put((float) ((Point3D_id) points.get(i)).getY());
                buffer.put((float) ((Point3D_id) points.get(i)).getZ());
                buffer.put(colors.get(i).getR());
                buffer.put(colors.get(i).getG());
                buffer.put(colors.get(i).getB());
            }
        }
    }
    
    /**
     * Loads a buffer with data
     * @param points the vertices to be loaded in the buffer
     * @param buffer the buffer, must be large enough to fit all the vertices (not checked directly)
     * @param colors The colours of each vertex, must be the same size as the points
     */
    private static void loadBuffer(PointArray points, FloatBuffer buffer, ColourArray<PointColour> colors) {
        if (points.size() != colors.size()) {
            Olivia.textOutputter.println("Caution: Points and Colours do not match size: " + points.size() + " vs. " + colors.size() + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        for (int i = 0; i < points.getVboBufferCapacity(); i++) {
                buffer.put((float) ((Point3D_id) points.get(i)).getX());
                buffer.put((float) ((Point3D_id) points.get(i)).getY());
                buffer.put((float) ((Point3D_id) points.get(i)).getZ());
                buffer.put(colors.get(i).getR());
                buffer.put(colors.get(i).getG());
                buffer.put(colors.get(i).getB());
        }
    }
    
    /**
     * Loads a buffer with data using the default colours
     * @param points the vertices to be loaded in the buffer
     * @param buffer the buffer, must be large enough to fit all the vertices (not checked directly)
     * @param show An array with boolean flags indicating whether to load that vertex or not, must be the same size as the points
     */
    private static void loadBuffer(PointArray points, FloatBuffer buffer, boolean[] show) {
        if (points.size() != show.length) {
            Olivia.textOutputter.println("Caution: Points and show flags do not match size: " + points.size() + " vs. " + show.length + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        for (int i = 0; i < points.getVboBufferCapacity(); i++) {
            if (show[i]) {
                buffer.put((float) ((Point3D_id) points.get(i)).getX());
                buffer.put((float) ((Point3D_id) points.get(i)).getY());
                buffer.put((float) ((Point3D_id) points.get(i)).getZ());
                buffer.put(DEFAULT_R);
                buffer.put(DEFAULT_G);
                buffer.put(DEFAULT_B);
            }
        }
    }
    
    /**
     * Loads a buffer with data, using the default colours
     * @param points the vertices to be loaded in the buffer
     * @param buffer the buffer, must be large enough to fit all the vertices (not checked directly)
     */
    private static void loadBuffer(PointArray points, FloatBuffer buffer) {
        for (int i = 0; i < points.getVboBufferCapacity(); i++) {
                buffer.put((float) ((Point3D_id) points.get(i)).getX());
                buffer.put((float) ((Point3D_id) points.get(i)).getY());
                buffer.put((float) ((Point3D_id) points.get(i)).getZ());
                buffer.put(DEFAULT_R);
                buffer.put(DEFAULT_G);
                buffer.put(DEFAULT_B);
        }
    }

    /**
     * Builds the VBO, allocating the memory
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     * @param colors The colours of each vertex, must be the same size as the points
     * @param show An array with boolean flags indicating whether to load that vertex or not, must be the same size as the points
     */
    public static void build(OpenGLScreen screen, PointArray points, ColourArray colors, boolean[] show) {
        if (!points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Building VBO for first time");
            long size = 0;
            int[] indices = new int[]{-1};
            int[] vertices = new int[]{points.size()};
            int[] vao = new int[]{-1};
            screen.getGl2().glGenVertexArrays(1, vao, 0);
            screen.getGl2().glGenBuffers(1, indices, 0); //(number of buffer, buffers, offset)
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
            size = (long) points.size() * 3L * (long)Buffers.SIZEOF_FLOAT * 2L;
            Olivia.textOutputter.println("GPU mem: " + size / 1e6 + "MB");
            screen.getGl2().glBufferData(GL2.GL_ARRAY_BUFFER, size, null, GL2.GL_STATIC_DRAW);
            points.setVboBuffer(points.size(),indices,vertices,vao);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors, show);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done VBO: name=" + indices[0] + ", size=" + size );
        }else{
            Olivia.textOutputter.println("Trying to rebuild VBO buffer, use repack instead");
        }
    }
    
    /**
     * Builds the VBO, allocating the memory
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     * @param colors The colours of each vertex, must be the same size as the points
     */
    public static void build(OpenGLScreen screen, PointArray points, ColourArray colors) {
        if (!points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Building VBO for first time");
            long size;
            int[] indices = new int[]{-1};
            int[] vertices = new int[]{points.size()};
            int[] vao = new int[]{-1};
            screen.getGl2().glGenVertexArrays(1, vao, 0);
            screen.getGl2().glGenBuffers(1, indices, 0); //(number of buffer, buffers, offset)
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
            size = (long) points.size() * 3L * (long)Buffers.SIZEOF_FLOAT * 2L;
            Olivia.textOutputter.println("GPU mem: " + size / 1e6 + "MB");
            screen.getGl2().glBufferData(GL2.GL_ARRAY_BUFFER, size, null, GL2.GL_STATIC_DRAW);
            points.setVboBuffer(points.size(),indices,vertices,vao);
            Olivia.textOutputter.println("vboBuffer set" + points.size() + " "+ indices[0]+ " "+vertices[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done VBO: name=" + indices[0] + ", size=" + size );
        }else{
            Olivia.textOutputter.println("Trying to rebuild VBO buffer, use repack instead");
        }
    }
    
    /**
     * Builds the VBO, allocating the memory, with default colours
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     * @param show An array with boolean flags indicating whether to load that vertex or not, must be the same size as the points
     */
    public static void build(OpenGLScreen screen, PointArray points, boolean[] show) {
        if (!points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Building VBO for first time");
            long size = 0;
            int[] indices = new int[]{-1};
            int[] vertices = new int[]{points.size()};
            int[] vao = new int[]{-1};
            screen.getGl2().glGenVertexArrays(1, vao, 0);
            screen.getGl2().glGenBuffers(1, indices, 0); //(number of buffer, buffers, offset)
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
            size = (long) points.size() * 3L * (long)Buffers.SIZEOF_FLOAT * 2L;
            Olivia.textOutputter.println("GPU mem: " + size / 1e6 + "MB");
            screen.getGl2().glBufferData(GL2.GL_ARRAY_BUFFER, size, null, GL2.GL_STATIC_DRAW);
            points.setVboBuffer(points.size(),indices,vertices,vao);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, show);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done VBO: name=" + indices[0] + ", size=" + size );
        }else{
            Olivia.textOutputter.println("Trying to rebuild VBO buffer, use repack instead");
        }
    }
    
    /**
     * Builds the VBO, allocating the memory, with default colours
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     */
    public static void build(OpenGLScreen screen, PointArray points) {
        if (!points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Building VBO for first time");
            long size;
            int[] indices = new int[]{-1};
            int[] vertices = new int[]{points.size()};
            screen.getGl2().glGenBuffers(1, indices, 0); //(number of buffer, buffers, offset)
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
            int[] vao = new int[]{-1};
            screen.getGl2().glGenVertexArrays(1, vao, 0);
            size = (long) points.size() * 3L * (long)Buffers.SIZEOF_FLOAT * 2L;
            Olivia.textOutputter.println("GPU mem: " + size / 1e6 + "MB");
            screen.getGl2().glBufferData(GL2.GL_ARRAY_BUFFER, size, null, GL2.GL_STATIC_DRAW);
            points.setVboBuffer(points.size(),indices,vertices,vao);
            Olivia.textOutputter.println("vboBuffer set" + points.size() + " "+ indices[0]+ " "+vertices[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done VBO: name=" + indices[0] + ", size=" + size );
        }else{
            Olivia.textOutputter.println("Trying to rebuild VBO buffer, use repack instead");
        }
    }
 
    /**
     * Repacks and already allocated buffer with new points or colours
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     * @param colors The colours of each vertex, must be the same size as the points
     * @param show An array with boolean flags indicating whether to load that vertex or not, must be the same size as the points
     */
    public static void repack(OpenGLScreen screen, PointArray points, ColourArray colors, boolean[] show) {
        if (points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Repacking VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, points.getVboIndices()[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors, show);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done repacking VBO: name=" + points.getVboIndices()[0]);
        }else{
            Olivia.textOutputter.println("Trying to repack non existing VBO buffer, use build instead");
        }
    }
    
    /**
     * Repacks and already allocated buffer with new points or colours
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     * @param colors The colours of each vertex, must be the same size as the points
     * */
    public static void repack(OpenGLScreen screen, PointArray points, ColourArray colors) {
        if (points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Repacking VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, points.getVboIndices()[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done repacking VBO: name=" + points.getVboIndices()[0]);
        }else{
            Olivia.textOutputter.println("Trying to repack non existing VBO buffer, use build instead");
        }
    }
    
    /**
     * Repacks and already allocated buffer with new points and default colours
     * @param screen The OpenGL screen where it will be rendered
     * @param points the vertices to be loaded in the buffer
     * @param show An array with boolean flags indicating whether to load that vertex or not, must be the same size as the points
     */
    public static void repack(OpenGLScreen screen, PointArray points, boolean[] show) {
        if (points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Repacking VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, points.getVboIndices()[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, show);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done repacking VBO: name=" + points.getVboIndices()[0]);
        }else{
            Olivia.textOutputter.println("Trying to repack non existing VBO buffer, use build instead");
        }
    }
    
    /**
    * Repacks and already allocated buffer with new points and default colours
    * @param screen The OpenGL screen where it will be rendered
    * @param points the vertices to be loaded in the buffer
    */
    public static void repack(OpenGLScreen screen, PointArray points) {
        if (points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Repacking VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, points.getVboIndices()[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            Olivia.textOutputter.println("Done repacking VBO: name=" + points.getVboIndices()[0]);
        }else{
            Olivia.textOutputter.println("Trying to repack non existing VBO buffer, use build instead");
        }
    }
    
    /**
     * Frees the native memory of the VBOs buffers
     * @param screen The OpenGL screen where it will be rendered
     * @param points the point array which has the VBOs
     */
    public static void free(OpenGLScreen screen, PointArray points) {
        if (points.isVboAlreadySet()) {
            Olivia.textOutputter.println("Freeing VBO: name=" + points.getVboIndices()[0]);
            if(!screen.animatorIsAnimating()){
                screen.getGl2().glDeleteBuffers(1, points.getVboIndices(), 0);
                Olivia.textOutputter.println("Freed VBO: name=" + points.getVboIndices()[0]);
            }else{
                Olivia.textOutputter.println("Screen is animating! should not free VBO: name=" + points.getVboIndices()[0]);
            }
        }else{
            Olivia.textOutputter.println("Trying to free non existing VBO buffer, ?");
        }
    }
}
