/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render;

import Olivia.core.Olivia;
import com.jogamp.opengl.GL2;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author oscar.garcia
 */
public class Shaders {
    
    public static String[] VertexShader={
            "#version 460 compatibility\n"+
            //"#version 330 compatibility\n"+
            "layout(location = 0) in vec3 aPos; \n"+ // the position variable has attribute position 0
            "layout(location = 1) in vec3 aColor; \n"+ // the color variable has attribute position 1
            //"in vec3 aPos; // the position variable has attribute position 0
            //"in vec3 aColor; // the color variable has attribute position 1
            "\n"+
            "out vec4 ourColor;\n"+ // specify a color output to the fragment shader
            "\n"+
            "void main()\n"+
            "{\n"+
                "gl_Position = vec4(aPos, 1.0);\n"+ // see how we directly give a vec3 to vec4's constructor
                "gl_PointSize = 1.0;\n"+
                "ourColor = gl_Color;\n"+ // set ourColor to the input color we got from the vertex data
            "}\n"
    };
    
    public static String[] FragmentShader={
            "#version 460 core\n"+
            "out vec4 FragColor;\n"+
            "\n"+
            "in vec4 vertexColor; // the input variable from the vertex shader (same name and same type)\n"+
            "\n"+
            "void main()\n"+
            "{\n"+
            "FragColor = vertexColor;\n"+
            "}\n"
    };
    
    public static String[] FRAGMENT_SHADER_TO_RED={
            "#version 460 compatibility\n"+
            "out vec4 FragColor;\n"+
            "\n"+
            "in vec4 ourColor; // the input variable from the vertex shader (same name and same type)\n"+
            "\n"+
            "void main()\n"+
            "{\n"+
             "FragColor = vec4(0.5, 0.0, 0.0, 0.5);\n"+
            "}\n"
    };
    
    public static String[] FRAGMENT_SHADER_NOTHING={
            "#version 130\n"+
            "out vec4 FragColor;\n"+
            "in vec3 ourColor;\n"+
            "\n"+
            "\n"+
            "void main()\n"+
            "{\n"+
            //"FragColor = vec4(ourColor, 1.0);\n"+
            "FragColor = gl_Color.xyzw;\n"+
            "}\n"
    };
    
    public static String[] FRAGMENT_SHADER_R_TO_G={
            /*"#version 460 core\n"+
            "out vec4 FragColor;\n"+
            "in vec3 ourColor;\n"+
            "\n"+
            "\n"+
            "void main()\n"+
            "{\n"+
            "FragColor = vec4(ourColor[1],ourColour[0],ourColour[2], 1.0);\n"+
            "}\n"*/
            "#version 130\n"+
            "out vec4 FragColor;\n"+
            "\n"+
            "\n"+
            "void main()\n"+
            "{\n"+
            "FragColor = gl_Color.yxzw;\n"+
            "}\n"
    };
        
    protected String blindnessFilterData = "";
    protected String blindnessFilterCode = "";
    public static final int NORMAL = 1;
    public static final int PROTANOPIA = 2;
    public static final int PROTANOMALY = 3;
    public static final int DEUTERANOPIA = 4;
    public static final int DEUTERANOMALY = 5;
    public static final int TRITANOPIA = 6;
    public static final int TRITANOMALY = 7;
    protected String filter = "";
    protected int[] shaderProgramArray;
    protected OpenGLScreen screen;
    protected int chosenShader;
    
    protected static final String NORMAL_VISION= "const vec3 blindVisionR = vec3( 1.0,  0.0, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.0,  1.0,  0.0);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.0,  1.0);\n";
    protected static final String PROTANOPIA_VISION= "const vec3 blindVisionR = vec3( 0.56667,  0.43333, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.55833,  0.44167,  0.0);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.24167,  0.75833);\n";
    protected static final String PROTANOMALY_VISION= "const vec3 blindVisionR = vec3( 0.81667,  0.18333, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.33333,  0.66667,  0.0);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.125,  0.875);\n";
    protected static final String DEUTERANOPIA_VISION= "const vec3 blindVisionR = vec3( 0.625,  0.375, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.70,  0.30,  0.0);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.30,  0.70);\n";
    protected static final String DEUTERANOMALY_VISION= "const vec3 blindVisionR = vec3( 0.80,  0.20, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.0,  0.25833,  0.74167);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.14167,  0.85833);\n";
    protected static final String TRITANOPIA_VISION= "const vec3 blindVisionR = vec3( 0.95,  0.05, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.0,  0.43333,  0.56667);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.475,  0.525);\n";
    protected static final String TRITANOMALY_VISION= "const vec3 blindVisionR = vec3( 0.9667,  0.03333, 0.0);\n" +
                            "const vec3 blindVisionG = vec3( 0.0,  0.73333,  0.26667);\n" +
                            "const vec3 blindVisionB = vec3( 0.0, 0.18333,  0.81667);\n";
    /*
    public Shaders(){
        int blindnessFilter=1;
        int blindnessVision=0;
        // Correction for color blindness (more here: http://tylerdavidhoward.com/thesis/)
        if (blindnessFilter != 0) {
            switch (blindnessFilter) {
            // opponentColor: x => luminance, y => red-green, z => blue-yellow
            case PROTANOPIA: {
                blindnessFilterCode = "vec3 opponentColor = RGBtoOpponentMat * vec3(fragColor.r, fragColor.g, fragColor.b);\n" +
                                "opponentColor.x -= opponentColor.y * 1.5;                                           \n" + // reds (y <= 0) become lighter, greens (y >= 0) become darker
                                "vec3 rgbColor = OpponentToRGBMat * opponentColor;                                   \n" +
                                "fragColor = vec4(rgbColor.r, rgbColor.g, rgbColor.b, fragColor.a);                  \n";
                break;
                }
            case DEUTERANOPIA: {
                blindnessFilterCode = "vec3 opponentColor = RGBtoOpponentMat * vec3(fragColor.r, fragColor.g, fragColor.b);\n" +
                                "opponentColor.x -= opponentColor.y * 1.5;                                           \n" + // reds (y <= 0) become lighter, greens (y >= 0) become darker
                                "vec3 rgbColor = OpponentToRGBMat * opponentColor;                                   \n" +
                                "fragColor = vec4(rgbColor.r, rgbColor.g, rgbColor.b, fragColor.a);                  \n";
                break;
                }
                case TRITANOPIA: {
                blindnessFilterCode = "vec3 opponentColor = RGBtoOpponentMat * vec3(fragColor.r, fragColor.g, fragColor.b);\n" +
                                "opponentColor.x -= ((3 * opponentColor.z) - opponentColor.y) * 0.25;                \n" +
                                "vec3 rgbColor = OpponentToRGBMat * opponentColor;                                   \n" +
                                "fragColor = vec4(rgbColor.r, rgbColor.g, rgbColor.b, fragColor.a);                  \n";
                break;
                }
                default: 
                    throw new RuntimeException("Color filter not implemented for " + blindnessFilter);
            }
            blindnessFilterData = "const mat3 RGBtoOpponentMat = mat3(0.2814, -0.0971, -0.0930, 0.6938, 0.1458,-0.2529, 0.0638, -0.0250, 0.4665);\n" +
                            "const mat3 OpponentToRGBMat = mat3(1.1677, 0.9014, 0.7214, -6.4315, 2.5970, 0.1257, -0.5044, 0.0159, 2.0517);\n";
        }else if (blindnessVision != 0) {
            switch (blindnessVision) {
                case PROTANOPIA: {
                blindnessFilterData = "const vec4 blindVisionR = vec4( 0.20,  0.99, -0.19, 0.0);\n" +
                            "const vec4 blindVisionG = vec4( 0.16,  0.79,  0.04, 0.0);\n" +
                            "const vec4 blindVisionB = vec4( 0.01, -0.01,  1.00, 0.0);\n";
                break;
            }
            case DEUTERANOPIA: {
                blindnessFilterData = "const vec4 blindVisionR = vec4( 0.43,  0.72, -0.15, 0.0 );\n" +
                            "const vec4 blindVisionG = vec4( 0.34,  0.57,  0.09, 0.0 );\n" +
                            "const vec4 blindVisionB = vec4(-0.02,  0.03,  1.00, 0.0 );\n";
                break;
            }
            case TRITANOPIA: {
             blindnessFilterData = "const vec4 blindVisionR = vec4( 0.97,  0.11, -0.08, 0.0 );\n" +
                            "const vec4 blindVisionG = vec4( 0.02,  0.82,  0.16, 0.0 );\n" +
                            "const vec4 blindVisionB = vec4(-0.06,  0.88,  0.18, 0.0 );\n";
            break;
        }
        }
        blindnessFilterCode = "fragColor = vec4(dot(fragColor, blindVisionR), dot(fragColor, blindVisionG), dot(fragColor, blindVisionB), fragColor.a);\n";
    }
    }
    */
    
    public Shaders(OpenGLScreen screen){
        this.chosenShader = 0;
        this.screen = screen;
        shaderProgramArray = new int[]{-1,-1,-1,-1,-1,-1,-1};
        filter = NORMAL_VISION; 
        for(int i = 0; i<7;i++){
            this.changeVisionSimulation(i+1);
            shaderProgramArray[i] = makeShaderProgram();
        }
    }
    
    public int getShader(){
        return shaderProgramArray[chosenShader];
    }
    
    public void chooseShader(int visionIssue){
        chosenShader = visionIssue-1;
    }
    
    protected int makeShaderProgram(){
        int shaderProgram;
        int fragmentShader = screen.getGl2().glCreateShader(GL2.GL_FRAGMENT_SHADER);
        //gl2.glShaderSource(fragmentShader, 1, Shaders.FRAGMENT_SHADER_NOTHING, null);
        //gl2.glShaderSource(fragmentShader, 1, Shaders.FRAGMENT_SHADER_TO_RED, null);
        //gl2.glShaderSource(fragmentShader, 1, Shaders.FRAGMENT_SHADER_R_TO_G, null);
        screen.getGl2().glShaderSource(fragmentShader, 1, this.colorblindFilter(), null);
        screen.getGl2().glCompileShader(fragmentShader);
        IntBuffer  intBuffer = IntBuffer.allocate(1);
        screen.getGl2().glGetShaderiv(fragmentShader, GL2.GL_COMPILE_STATUS, intBuffer);
        Olivia.textOutputter.println("Fragment shader compile status " + intBuffer.get(0));
        // check for shader compile errors
        if (intBuffer.get(0) != 1) {
            screen.getGl2().glGetShaderiv(fragmentShader, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            if (size > 0) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		screen.getGl2().glGetShaderInfoLog(fragmentShader, size, intBuffer, byteBuffer);
		Olivia.textOutputter.println(new String(byteBuffer.array()));
            }
	}
        // link shaders
        shaderProgram = screen.getGl2().glCreateProgram();
        //gl2.glAttachShader(shaderProgram, vertexShader);
        screen.getGl2().glAttachShader(shaderProgram, fragmentShader);
        screen.getGl2().glLinkProgram(shaderProgram);
        // check for linking errors
        intBuffer = IntBuffer.allocate(1);
        screen.getGl2().glGetProgramiv(shaderProgram, GL2.GL_LINK_STATUS, intBuffer);
        Olivia.textOutputter.println("Shader program link status " + intBuffer.get(0));
        if (intBuffer.get(0) != 1) {
            screen.getGl2().glGetProgramiv(shaderProgram, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            if (size > 0) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		screen.getGl2().glGetProgramInfoLog(shaderProgram, size, intBuffer, byteBuffer);
		Olivia.textOutputter.println(new String(byteBuffer.array()));
            }
	}
        screen.getGl2().glValidateProgram(shaderProgram);
        intBuffer = IntBuffer.allocate(1);
        screen.getGl2().glGetProgramiv(shaderProgram, GL2.GL_VALIDATE_STATUS, intBuffer);
        Olivia.textOutputter.println("Shader program validate status " + intBuffer.get(0));
	if (intBuffer.get(0) != 1) {
            screen.getGl2().glGetProgramiv(shaderProgram, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            if (size > 0) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		screen.getGl2().glGetProgramInfoLog(shaderProgram, size, intBuffer, byteBuffer);
		Olivia.textOutputter.println(new String(byteBuffer.array()));
            }
	}
        /*int pos =  screen.getGl2().glGetAttribLocation(shaderProgram, "aPos");
        int pos2 =  screen.getGl2().glGetAttribLocation(shaderProgram, "aColor");
        Olivia.textOutputter.println("Pos " + pos + " " + pos2);*/
        //gl2.glUseProgram(shaderProgram);
        //gl2.glDeleteShader(vertexShader);
        screen.getGl2().glDeleteShader(fragmentShader);
        return shaderProgram;
    }
    
    protected void changeVisionSimulation(int visionIssue){
        switch (visionIssue) {
            case NORMAL:
                filter = NORMAL_VISION;
                break;
            case PROTANOPIA:
                filter = PROTANOPIA_VISION;
                break;
            case PROTANOMALY:
                filter = PROTANOMALY_VISION;
                break;
            case DEUTERANOPIA:
                filter = DEUTERANOPIA_VISION;
                break;
            case DEUTERANOMALY:
                filter = DEUTERANOMALY_VISION;
                break;
            case TRITANOPIA:
                filter = TRITANOPIA_VISION;
                break;
            case TRITANOMALY:
                filter = TRITANOMALY_VISION;
                break;
            default:
                filter = NORMAL_VISION;
                break;
        }
    }
    
    public String[] colorblindFilter(){
        return new String []{
            "#version 130\n"+
            "out vec4 FragColor;\n"+
            "\n"+
            "\n"+
            filter +
            "void main()\n" +
            "{\n" +
            "  vec4 fragColor;\n" +
            "  fragColor = gl_Color;\n" +
            "  FragColor.r = fragColor.r * blindVisionR.x + fragColor.g * blindVisionR.y + fragColor.b * blindVisionR.z;\n" +
            "  FragColor.g = fragColor.r * blindVisionG.x + fragColor.g * blindVisionG.y + fragColor.b * blindVisionG.z;\n" +
            "  FragColor.b = fragColor.r * blindVisionB.x + fragColor.g * blindVisionB.y + fragColor.b * blindVisionB.z;\n" +
            "  FragColor.a = fragColor.a;\n" +   
            "}\n"
        };
        /*return new String []{
            "#version 130\n"+
            "out vec4 FragColor;\n"+
            "\n"+
            "\n"+
            blindnessFilterData + 
            //blindnessVisionData + 
            "void main()\n" +
            "{\n" +
            "  vec4 fragColor;\n" +
            "  fragColor = gl_Color;\n" +
            blindnessFilterCode +
           //blindnessVisionCode +
            "  FragColor = fragColor;                           \n" +
            "}                                                     \n"
        };*/
        /*return new String []{
                        "attribute vec4 a_position;                            \n" +
            "attribute vec2 a_texCoord;                            \n" +
            "attribute vec4 a_color;                               \n" +
            "varying vec2 v_texCoord;                              \n" +
            "varying vec4 v_color;                                 \n" +
            "void main()                                           \n" +
            "{                                                     \n" +
            "   gl_Position = a_position;                          \n" +
            "   v_texCoord = a_texCoord;                           \n" +
            "   v_color = a_color;                                 \n" +
            "}                                                     \n", 
            "#ifdef GL_ES                                          \n" +
            "precision mediump float;                              \n" +
            "#endif                                                \n" +
            "varying vec2 v_texCoord;                              \n" +
            "varying vec4 v_color;                                 \n" +
            "uniform sampler2D s_texture;                          \n" +
            blindnessFilterData + 
            //blindnessVisionData + 
            "void main()                                           \n" +
            "{                                                     \n" +
            "  vec4 fragColor = texture2D(s_texture, v_texCoord);  \n" +
            "  fragColor *= v_color;                               \n" +
            blindnessFilterCode +
           //blindnessVisionCode +
            "  gl_FragColor = fragColor;                           \n" +
            "}                                                     \n"
        };*/
    }
    
}
