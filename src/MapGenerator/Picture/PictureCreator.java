package MapGenerator.Picture;


import MapGenerator.Configurations;
import MapGenerator.ImageWork.Image;
import MapGenerator.MapAnalysis.MapData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;

public class PictureCreator {

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        java.awt.Image tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public File createMapPicture(Set<Image> images, MapData mapData){
        BufferedImage mapPicture = creatBufferedImage(mapData);

        if(Configurations.drawBorders){
            createBorders(mapPicture, mapData);
        }
    }

    private void createBorders(BufferedImage picture, MapData mapData){
        Graphics2D graphics2D = (Graphics2D) mapPicture.getGraphics();
        graphics2D.
    }

    private BufferedImage creatBufferedImage(MapData mapData){
        BufferedImage bufferedImage = new BufferedImage(mapData.getSizeUnitW()* Configurations.mapFormatX *mapData.getX(),
                mapData.getSizeUnitH()* Configurations.mapFormatY *mapData.getY(), BufferedImage.TYPE_INT_RGB);
        return bufferedImage;
    }
}
