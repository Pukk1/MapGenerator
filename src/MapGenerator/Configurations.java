package MapGenerator;

import java.awt.*;

public class Configurations {

//    Names of JSON-heads in JSON String

    public static String x = "x";
    public static String y = "y";
    public static String z = "z";

    public static String mainFilePath = "main.";

    public static boolean drawBorders = true;
    public static Color bordersColor = Color.GRAY;
    public static int borderSize = 2;

    public static int mapFormatX = 25;
    public static int mapFormatY = 11;

    public static float curvatureX = -0.5f;        //съезд относительно ровного ромба в сторону
    public static float curvatureY = 0;   //съезд относительно ровного ромба в сторону

    public static Color maskColor = Color.WHITE;
}
