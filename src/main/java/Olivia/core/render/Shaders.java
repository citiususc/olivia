/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Olivia.core.render;

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
    public static final int PROTANOPIA = 1;
    public static final int DEUTERANOPIA = 2;
    public static final int TRITANOPIA = 3;
    
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
    
    public String[] colorblindFilter(){
        return new String []{
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
        };
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
