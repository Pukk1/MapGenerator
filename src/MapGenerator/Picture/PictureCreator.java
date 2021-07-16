package MapGenerator.Picture;


import MapGenerator.Configurations;
import MapGenerator.ImageWork.Image;
import MapGenerator.MapAnalysis.MapData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
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

        try {
            createFirstLayer(mapPicture, mapData, images);
        } catch (IOException e) {
            System.out.println("Problem with first layer creating");
            e.printStackTrace();
        }

        return null;
    }

    private void createFirstLayer(BufferedImage mapPicture, MapData mapData, Set<Image> images) throws IOException {
        for(Image image : images){
            if(image.getZ() == 0) {
                BufferedImage addedPicture = null;
                try {
                    addedPicture = ImageIO.read(new File(image.getPath()));
                } catch (IOException e) {
                    System.out.println("Problem with file reading: " + image.getPath());
                    throw e;
                }

                addedPicture = resize(addedPicture, mapData.getSizeUnitW() * Configurations.mapFormatX, mapData.getSizeUnitH() * Configurations.mapFormatY);
                createCellShape(addedPicture, mapData);

                addPictureToMap(mapPicture, addedPicture, image, mapData);
            }
        }

        try {
            ImageIO.write(mapPicture, "png", new File("main.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addPictureToMap(BufferedImage mapPicture, BufferedImage addedPicture, Image image, MapData mapData){
        java.awt.Image addedImage = makeColorTransparent(addedPicture, Configurations.maskColor);

        Graphics2D graphics2D = (Graphics2D) mapPicture.getGraphics();

        int x = image.getX() * mapData.getSizeUnitW()*5 + image.getY()*mapData.getSizeUnitW()*3;
        int y = mapPicture.getHeight()/2 + image.getY() * mapData.getSizeUnitH()*2 - image.getX()*mapData.getSizeUnitH()*2 - addedImage.getHeight(null)/2;
        graphics2D.drawImage(addedImage, x, y, null);
    }

    private BufferedImage createCellShape(BufferedImage addedPicture, MapData mapData){
        java.awt.Image image = null;
        if(Configurations.mapFormatX/Configurations.mapFormatY == 2) {

            if(Configurations.drawBorders){
                createBorders(addedPicture, mapData);
            }

            createMaskZones(addedPicture, mapData);
        }

        return addedPicture;
    }

    private void createBorders(BufferedImage addedPicture, MapData mapData){
        Graphics2D graphics2D = (Graphics2D) addedPicture.getGraphics();

        graphics2D.setColor(Configurations.bordersColor);

        graphics2D.fill(new Polygon(new int[]{0, mapData.getPixSizeCellW()/Configurations.mapFormatX*5, mapData.getPixSizeCellW()/Configurations.mapFormatX*5 + Configurations.borderSize, 0 + Configurations.borderSize},
                new int[]{addedPicture.getHeight() / 2 , 0, 0, addedPicture.getHeight() / 2}, 4));
        graphics2D.fill(new Polygon(new int[]{0, mapData.getPixSizeCellW()/Configurations.mapFormatX*3, mapData.getPixSizeCellW()/Configurations.mapFormatX*3 + Configurations.borderSize, 0 + Configurations.borderSize},
                new int[]{addedPicture.getHeight() / 2 , addedPicture.getHeight(), addedPicture.getHeight(), addedPicture.getHeight() / 2}, 4));
        graphics2D.fill(new Polygon(new int[]{mapData.getPixSizeCellW()/Configurations.mapFormatX*5 - Configurations.borderSize, mapData.getPixSizeCellW()/Configurations.mapFormatX*5, addedPicture.getWidth(), addedPicture.getWidth()-Configurations.borderSize},
                new int[]{0 , 0, addedPicture.getHeight()/2, addedPicture.getHeight() / 2}, 4));
        graphics2D.fill(new Polygon(new int[]{mapData.getPixSizeCellW()/Configurations.mapFormatX*3 - Configurations.borderSize, mapData.getPixSizeCellW()/Configurations.mapFormatX*3, addedPicture.getWidth(), addedPicture.getWidth()-Configurations.borderSize},
                new int[]{addedPicture.getHeight() , addedPicture.getHeight(), addedPicture.getHeight()/2, addedPicture.getHeight() / 2}, 4));
        
    }

    private void createMaskZones(BufferedImage addedPicture, MapData mapData){
        Graphics2D graphics2D = (Graphics2D) addedPicture.getGraphics();

        graphics2D.setColor(Configurations.maskColor);
        Shape shape = new Polygon(new int[]{0, 0, mapData.getPixSizeCellW()/Configurations.mapFormatX*5},
                new int[]{0, addedPicture.getHeight() / 2, 0}, 3);
        graphics2D.fill(shape);
        shape = new Polygon(new int[]{0, 0, mapData.getPixSizeCellW()/Configurations.mapFormatX*3},
                new int[]{addedPicture.getHeight(), addedPicture.getHeight() / 2, addedPicture.getHeight()}, 3);
        graphics2D.fill(shape);
        shape = new Polygon(new int[]{addedPicture.getWidth(), addedPicture.getWidth(), mapData.getPixSizeCellW()/Configurations.mapFormatX*5},
                new int[]{0, addedPicture.getHeight() / 2, 0}, 3);
        graphics2D.fill(shape);
        shape = new Polygon(new int[]{addedPicture.getWidth(), addedPicture.getWidth(), mapData.getPixSizeCellW()/Configurations.mapFormatX*3},
                new int[]{addedPicture.getHeight(), addedPicture.getHeight() / 2, addedPicture.getHeight()}, 3);
        graphics2D.fill(shape);
    }

    private BufferedImage creatBufferedImage(MapData mapData){
        BufferedImage bufferedImage = new BufferedImage(mapData.getSizeUnitW()* Configurations.mapFormatX *mapData.getXSize(),
                mapData.getSizeUnitH()* Configurations.mapFormatY *mapData.getYSize(), BufferedImage.TYPE_INT_RGB);
        return bufferedImage;
    }

    public static java.awt.Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
