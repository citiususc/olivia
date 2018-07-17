package PointCloudVisu.core.render;

import PointCloudVisu.core.data.Point3D;
import PointCloudVisu.core.data.PointArray;
import PointCloudVisu.core.render.colours.ColourArray;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * This manager provides methods for handle the VBOs (Vertex buffer objects)
 *
 * @author jorge.martinez.sanchez
 */
public class VBOManager {

    public static void draw(OpenGLScreen screen, int[] indices, int[] num, int drawType, int size) {
        screen.getGl2().glEnableClientState(GL2.GL_VERTEX_ARRAY);
        screen.getGl2().glEnableClientState(GL2.GL_COLOR_ARRAY);
        screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
        screen.getGl2().glVertexPointer(3, GL.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 0);
        screen.getGl2().glColorPointer(3, GL.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 3 * Buffers.SIZEOF_FLOAT);
        screen.getGl2().glPointSize(size);
        screen.getGl2().glLineWidth(size);
        if (drawType == GL2.GL_QUADS) {
            screen.getGl2().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        }
        screen.getGl2().glDrawArrays(drawType, 0, num[0]);
        screen.getGl2().glDisableClientState(GL2.GL_VERTEX_ARRAY);
        screen.getGl2().glDisableClientState(GL2.GL_COLOR_ARRAY);
    }
   
    private static void loadBuffer(PointArray points, FloatBuffer buffer, ColourArray colors, boolean[] show) {
        if (points.size() != colors.size()) {
            System.out.println("Caution: Points and Colours do not match size: " + points.size() + " vs. " + colors.size() + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        if (points.size() != show.length) {
            System.out.println("Caution: Points and show flags do not match size: " + points.size() + " vs. " + show.length + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        for (int i = 0; i < points.getVboBufferCapacity(); i++) {
            if (show[i]) {
                buffer.put((float) ((Point3D) points.get(i)).getX());
                buffer.put((float) ((Point3D) points.get(i)).getY());
                buffer.put((float) ((Point3D) points.get(i)).getZ());
                buffer.put(colors.get(i).getR());
                buffer.put(colors.get(i).getG());
                buffer.put(colors.get(i).getB());
            }
        }
    }
    
    private static void loadBuffer(PointArray points, FloatBuffer buffer, ColourArray colors) {
        if (points.size() != colors.size()) {
            System.out.println("Caution: Points and Colours do not match size: " + points.size() + " vs. " + colors.size() + ", VBO buffer at " + points.getVboBufferCapacity() );
        }
        for (int i = 0; i < points.getVboBufferCapacity(); i++) {
                buffer.put((float) ((Point3D) points.get(i)).getX());
                buffer.put((float) ((Point3D) points.get(i)).getY());
                buffer.put((float) ((Point3D) points.get(i)).getZ());
                buffer.put(colors.get(i).getR());
                buffer.put(colors.get(i).getG());
                buffer.put(colors.get(i).getB());
        }
    }

    public static void build(OpenGLScreen screen, PointArray points, ColourArray colors, boolean[] show) {
        if (!points.isVboAlreadySet()) {
            System.out.println("Building VBO for first time");
            long size = 0;
            int[] indices = new int[]{-1};
            int[] vertices = new int[]{points.size()};
            screen.getGl2().glGenBuffers(1, indices, 0); //(number of buffer, buffers, offset)
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
            size = (long) points.size() * 3L * (long)Buffers.SIZEOF_FLOAT * 2L;
            System.out.println("GPU mem: " + size / 1e6 + "MB");
            screen.getGl2().glBufferData(GL2.GL_ARRAY_BUFFER, size, null, GL2.GL_STATIC_DRAW);
            points.setVboBuffer(points.size(),indices,vertices);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors, show);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            System.out.println("Done VBO: name=" + indices[0] + ", size=" + size );
        }else{
            System.out.println("Trying to rebuild VBO buffer, use repack instead");
        }
    }
    
    public static void build(OpenGLScreen screen, PointArray points, ColourArray colors) {
        if (!points.isVboAlreadySet()) {
            System.out.println("Building VBO for first time");
            long size;
            int[] indices = new int[]{-1};
            int[] vertices = new int[]{points.size()};
            screen.getGl2().glGenBuffers(1, indices, 0); //(number of buffer, buffers, offset)
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, indices[0]);
            size = (long) points.size() * 3L * (long)Buffers.SIZEOF_FLOAT * 2L;
            System.out.println("GPU mem: " + size / 1e6 + "MB");
            screen.getGl2().glBufferData(GL2.GL_ARRAY_BUFFER, size, null, GL2.GL_STATIC_DRAW);
            points.setVboBuffer(points.size(),indices,vertices);
            System.out.println("vboBuffer set" + points.size() + " "+ indices[0]+ " "+vertices[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            System.out.println("Done VBO: name=" + indices[0] + ", size=" + size );
        }else{
            System.out.println("Trying to rebuild VBO buffer, use repack instead");
        }
    }
 
    public static void repack(OpenGLScreen screen, PointArray points, ColourArray colors, boolean[] show) {
        if (points.isVboAlreadySet()) {
            System.out.println("Repacking VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, points.getVboIndices()[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors, show);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            System.out.println("Done repacking VBO: name=" + points.getVboIndices()[0]);
        }else{
            System.out.println("Trying to repack non existing VBO buffer, use build instead");
        }
    }
    
    public static void repack(OpenGLScreen screen, PointArray points, ColourArray colors) {
        if (points.isVboAlreadySet()) {
            System.out.println("Repacking VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glBindBuffer(GL2.GL_ARRAY_BUFFER, points.getVboIndices()[0]);
            ByteBuffer bbuffer = screen.getGl2().glMapBuffer(GL2.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY);
            FloatBuffer fbuffer = bbuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
            loadBuffer(points, fbuffer, colors);
            screen.getGl2().glUnmapBuffer(GL2.GL_ARRAY_BUFFER);
            System.out.println("Done repacking VBO: name=" + points.getVboIndices()[0]);
        }else{
            System.out.println("Trying to repack non existing VBO buffer, use build instead");
        }
    }
    
    public static void free(OpenGLScreen screen, PointArray points) {
        if (points.isVboAlreadySet()) {
            System.out.println("Freeing VBO: name=" + points.getVboIndices()[0]);
            screen.getGl2().glDeleteBuffers(1, points.getVboIndices(), 0);
            System.out.println("Freed VBO: name=" + points.getVboIndices()[0]);
        }else{
            System.out.println("Trying to free non existing VBO buffer, ?");
        }
    }
}
