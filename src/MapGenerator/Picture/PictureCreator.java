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

    public BufferedImage createMapPicture(Set<Image> images, MapData mapData){
        BufferedImage mapPicture = creatBufferedImage(mapData);

        try {
            createFirstLayer(mapPicture, mapData, images);
        } catch (IOException e) {
            System.out.println("Problem with first layer creating");
            e.printStackTrace();
        }

        creatObjects(mapPicture, mapData, images);

        mapPicture = createPictureFormat(mapPicture, mapData.getPixSizeX(), mapData.getPixSizeY());

        return mapPicture;
    }

    private BufferedImage createPictureFormat(BufferedImage original, int xSize, int ySize){
        BufferedImage result = new BufferedImage(xSize, ySize, original.getType());
        Graphics2D graphics2D = (Graphics2D) result.getGraphics();
        graphics2D.drawImage(original, result.getWidth()/2 - original.getWidth()/2, result.getHeight()/2 - original.getHeight()/2, null);

        return result;
    }

    private void creatObjects(BufferedImage mapPicture, MapData mapData, Set<Image> images){
        for(Image image : images){
            if(image.getZ() != 0){
                try {
                    BufferedImage bufferedImage = ImageIO.read(new File(image.getPath()));
                    bufferedImage = resize(bufferedImage, bufferedImage.getWidth()/5, bufferedImage.getHeight()/5);
                    addPictureToMap(mapPicture, bufferedImage, image, mapData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
    }

    private void addPictureToMap(BufferedImage mapPicture, BufferedImage addedPicture, Image image, MapData mapData){
        java.awt.Image addedImage = makeColorTransparent(addedPicture, Configurations.maskColor);

        Graphics2D graphics2D = (Graphics2D) mapPicture.getGraphics();

//        System.out.println(mapData.getSizeUnitW());

        //почему это работает правильно науке пока что не известно
        int x = (int) (image.getX() * (mapData.getSizeUnitW()*13 -2) + image.getY()*mapData.getSizeUnitW()*13 + mapData.getPixSizeCellW()/2.0-addedPicture.getWidth()/2.0);
        int y = (int) (mapPicture.getHeight()/10*5 + image.getY() * mapData.getSizeUnitH()*6 - (image.getX()*(mapData.getSizeUnitH()*6)) - addedPicture.getHeight()/2);


        graphics2D.drawImage(addedImage, x, y, null);
    }

    private BufferedImage createCellShape(BufferedImage addedPicture, MapData mapData){
        java.awt.Image image = null;

        if(Configurations.drawBorders){
            createBorders(addedPicture, mapData);
        }

        createMaskZones(addedPicture, mapData);

        return addedPicture;
    }

    private void createBorders(BufferedImage addedPicture, MapData mapData){
        Graphics2D graphics2D = (Graphics2D) addedPicture.getGraphics();

        graphics2D.setColor(Configurations.bordersColor);

        graphics2D.fill(new Polygon(new int[]{0, (int) (addedPicture.getWidth()/2+Configurations.curvatureX), (int) ((addedPicture.getWidth()/2+Configurations.curvatureX) + Configurations.borderSize), Configurations.borderSize},
                new int[]{(int)(addedPicture.getHeight()/2 - Configurations.curvatureY) , 0, 0, (int)(addedPicture.getHeight()/2 - Configurations.curvatureY) }, 4));
        graphics2D.fill(new Polygon(new int[]{0, (int) (addedPicture.getWidth()/2 - Configurations.curvatureX), (int) ((addedPicture.getWidth()/2 - Configurations.curvatureX) + Configurations.borderSize), Configurations.borderSize},
                new int[]{(int)(addedPicture.getHeight()/2 - Configurations.curvatureY) , addedPicture.getHeight(), addedPicture.getHeight(), (int)(addedPicture.getHeight()/2 - Configurations.curvatureY)}, 4));
        graphics2D.fill(new Polygon(new int[]{(int) (addedPicture.getWidth()/2+Configurations.curvatureX) -Configurations.borderSize, (int) (addedPicture.getWidth()/2+Configurations.curvatureX), addedPicture.getWidth(), addedPicture.getWidth()-Configurations.borderSize},
                new int[]{0 , 0, (int)(addedPicture.getHeight()/2 - Configurations.curvatureY) , (int)(addedPicture.getHeight()/2 - Configurations.curvatureY) }, 4));
        graphics2D.fill(new Polygon(new int[]{(int) (addedPicture.getWidth()/2 - Configurations.curvatureX) - Configurations.borderSize, (int) (addedPicture.getWidth()/2 - Configurations.curvatureX), addedPicture.getWidth(), addedPicture.getWidth()-Configurations.borderSize},
                new int[]{addedPicture.getHeight() , addedPicture.getHeight(), (int)(addedPicture.getHeight()/2 - Configurations.curvatureY), (int)(addedPicture.getHeight()/2 - Configurations.curvatureY)}, 4));

    }

    private void createMaskZones(BufferedImage addedPicture, MapData mapData){
        Graphics2D graphics2D = (Graphics2D) addedPicture.getGraphics();

        graphics2D.setColor(Configurations.maskColor);
        Shape shape = new Polygon(new int[]{0, 0, (int) (addedPicture.getWidth()/2+Configurations.curvatureX)},
                new int[]{0, (int)(addedPicture.getHeight()/2 - Configurations.curvatureY), 0}, 3);
        graphics2D.fill(shape);
        shape = new Polygon(new int[]{0, 0, (int) (addedPicture.getWidth()/2 - Configurations.curvatureX)},
                new int[]{addedPicture.getHeight(), (int)(addedPicture.getHeight()/2 - Configurations.curvatureY), addedPicture.getHeight()}, 3);
        graphics2D.fill(shape);
        shape = new Polygon(new int[]{addedPicture.getWidth(), addedPicture.getWidth(), (int) (addedPicture.getWidth()/2+Configurations.curvatureX)},
                new int[]{0, (int)(addedPicture.getHeight()/2 - Configurations.curvatureY), 0}, 3);
        graphics2D.fill(shape);
        shape = new Polygon(new int[]{addedPicture.getWidth(), addedPicture.getWidth(), (int) (addedPicture.getWidth()/2 - Configurations.curvatureX)},
                new int[]{addedPicture.getHeight(), (int)(addedPicture.getHeight()/2 - Configurations.curvatureY), addedPicture.getHeight()}, 3);
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
