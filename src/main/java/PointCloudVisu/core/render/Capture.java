package PointCloudVisu.core.render;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.util.GLBuffers;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.BytePointer;
import static org.bytedeco.javacpp.opencv_core.CV_8UC4;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import static org.bytedeco.javacpp.opencv_videoio.CV_FOURCC;
import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

/**
 * This class handles screen capture, both image and video
 *
 * @author jorge.martinez.sanchez
 */
public class Capture {

    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    private static final int CHANNELS = 4;
    private static final int FPS = 48;
    private final OpenGLScreen renderScreen;
    private final VideoWriter[] videoWriters;
    private final ByteBuffer[] pixelBuffs;
    private final Mat[] pixelMats;
    private int width;
    private int height;
    private boolean captureImage;
    private boolean captureVideo;

    public Capture(OpenGLScreen renderScreen) {
        this.renderScreen = renderScreen;
        videoWriters = new VideoWriter[2];
        pixelBuffs = new ByteBuffer[2];
        pixelMats = new Mat[2];
        videoWriters[0] = new VideoWriter();
        videoWriters[1] = new VideoWriter();
        captureImage = false;
        captureVideo = false;
        updateResolution();
    }

    public boolean isCaptureImage() {
        return captureImage;
    }

    public boolean isCaptureVideo() {
        return captureVideo;
    }

    public void setCaptureImage(boolean captureImage) {
        this.captureImage = captureImage;
    }

    public void toggleCaptureVideo() {
        if (!captureVideo) {
            captureVideo = true;
            renderScreen.getMainFrame().logIntoConsole("Recording...");
        } else if (captureVideo) {
            //try {
                videoWriters[0].close();
                videoWriters[1].close();
                captureVideo = false;
                renderScreen.getMainFrame().logIntoConsole("Video recorded");
                //rotateVideo();
            //}
            //catch (FrameGrabber.Exception | FrameFilter.Exception | FrameRecorder.Exception ex) {
                //Logger.getLogger(Capture.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
    }

    /**
     * Resolution must be updated always to consider frame resizing
     */
    private void updateResolution() {
        if (renderScreen.getFrame() != null) {
            width = renderScreen.getFrame().getWidth();
            height = renderScreen.getFrame().getHeight();
        }
    }

    /**
     * Must be called from display() or won't work (black screenshot)
     */
    public void screenshot() {
        updateResolution();
        try {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics gfx = img.getGraphics();
            ByteBuffer buf = GLBuffers.newDirectByteBuffer(width * height * CHANNELS);
            renderScreen.getGl2().glReadPixels(0, 0, width, height, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, buf);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    gfx.setColor(new Color((buf.get() & 0xff), (buf.get() & 0xff), (buf.get() & 0xff)));
                    buf.get();
                    gfx.drawRect(j, height - i, 1, 1); // flipping the image
                }
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String path = "Screenshot " + dateFormat.format(date) + ".png";
            File file = new File(path);
            ImageIO.write(img, "png", file);
            System.out.println(path + " saved");
            captureImage = false;
        }
        catch (IOException ex) {
        }
    }

    private void createVideo(int side) {
        boolean isColor = true;
        int fourcc = CV_FOURCC((byte) 'M', (byte) 'J', (byte) 'P', (byte) 'G');
        updateResolution();
        Size size = new Size(width, height);
        videoWriters[side].open("video180_" + side + ".avi", fourcc, renderScreen.isStereo3D ? FPS / 2 : FPS, size, isColor);
        System.out.println("Video opened: " + videoWriters[side].isOpened());
        System.out.println("Use 'ffmpeg -i video180.avi -vf \"transpose=0,transpose=2\" -q 0 video.avi' to flip the video 180º");
        pixelBuffs[side] = ByteBuffer.allocate(CHANNELS * width * height);
        pixelMats[side] = new Mat(height, width, CV_8UC4);
    }

    /**
     * The recorded video is flipped vertically (thanks to OpenGL). Undoing the
     * flipping while recording is too slow, do it externally with ffmpeg e.g.
     */
    public void recordVideo() {
        if (!videoWriters[0].isOpened()) {
            createVideo(0);
        } else {
            renderScreen.getGl2().glReadPixels(0, 0, width, height, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixelBuffs[0]);
            BytePointer bp = new BytePointer(pixelBuffs[0]);
            pixelMats[0].data().put(bp);
            videoWriters[0].write(pixelMats[0]);
        }
    }

    public void recordVideo3D(int side) {
        if (!videoWriters[side].isOpened()) {
            createVideo(side);
        } else {
            if (side == LEFT_SIDE) {
                renderScreen.getGl2().glReadBuffer(GL2GL3.GL_BACK_LEFT);
            } else {
                renderScreen.getGl2().glReadBuffer(GL2GL3.GL_BACK_RIGHT);
            }
            renderScreen.getGl2().glReadPixels(0, 0, width, height, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixelBuffs[side]);
            BytePointer bp = new BytePointer(pixelBuffs[side]);
            pixelMats[side].data().put(bp);
            videoWriters[side].write(pixelMats[side]);
        }
    }

//    private void rotateVideo() throws FrameGrabber.Exception, FrameFilter.Exception, FrameRecorder.Exception {
//        renderScreen.getMainFrame().logIntoConsole("Rotating video...");
//
//        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("video180_0.avi");
//        grabber.setFormat("avi");
//        grabber.start();
//
//        FFmpegFrameFilter filter = new FFmpegFrameFilter("transpose=0, transpose=2", grabber.getImageWidth(), grabber.getImageHeight());
//        filter.setPixelFormat(grabber.getPixelFormat());
//        filter.setAspectRatio(grabber.getAspectRatio());
//        filter.setFrameRate(grabber.getFrameRate());
//        filter.start();
//
//        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("video0.avi", grabber.getImageWidth(), grabber.getImageHeight());
//
//        /*
//            filterRotateVideoFrontCam.push(yuvFrame);
//            while ((pulledFrame = filterRotateVideoFrontCam.pull()) != null) {
//            mVideoRecorder.record(pulledFrame);
//         */
//        Frame frame;
//        while ((frame = grabber.grab()) != null) {
//            if (frame.image != null) {
//                filter.push(frame);
//                recorder.record(filter.pull());
//            }
//        }
//        /*FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("video.avi", grabber.getImageWidth(), grabber.getImageHeight(), 2);
//        recorder.setVideoCodec(AV_CODEC_ID_H264);
//        for (int i = 0; i < grabber.getFrameNumber(); i++) {
//            Frame frame = grabber.grabFrame();
//            filter.push(frame, grabber.getPixelFormat());
//            recorder.record(frame);
//        }
//        recorder.stop();
//        recorder.release();*/
//        filter.stop();
//        filter.release();
//        grabber.stop();
//        grabber.release();
//        recorder.stop();
//        recorder.release();
//    }
}
