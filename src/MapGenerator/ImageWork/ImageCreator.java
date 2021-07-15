package MapGenerator.ImageWork;

import MapGenerator.Configurations;
import MapGenerator.ImageCombining;
import MapGenerator.JSONWork.JSONEditor;
import MapGenerator.Placement.FirstLayerLine;
import MapGenerator.Placement.LayerAnalyst;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class ImageCreator implements ImageCombining {

    private File combineImages(Set<Image> images, Set<FirstLayerLine> lines, int sizeX, int sizeY, int sizeZ, ImageFormat imageFormat){


        BufferedImage mainImage = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);

        WritableRaster mainImageRaster = mainImage.getRaster();

        for(int z = 0; z < sizeZ; z++){
            for(Image image : images){
                if(image.getZ() == (z+1)){

                    FirstLayerLine line = null;
                    Iterator lineIterator = lines.iterator();
                    while (lineIterator.hasNext()){
                        line = (FirstLayerLine)lineIterator.next();
                        if(line.getNumber() == image.getY()){
                            break;
                        }
                    }

                    BufferedImage addedImage = loadAdditionalImage(image, line);

                    WritableRaster bufferedImageRaster = addedImage.getRaster();

                    addToCenter(image, bufferedImageRaster, mainImageRaster, line);
                }
            }
        }

        mainImage.setData(mainImageRaster);

        File mainImageFile = new File(Configurations.mainFilePath + imageFormat);
        try {
            mainImageFile.createNewFile();
            ImageIO.write(mainImage, imageFormat.toString(), mainImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mainImageFile;
    }

    private void addToCenter(Image image, WritableRaster from, WritableRaster to, FirstLayerLine line) {

        int indentX = line.getPictureX()/2 - from.getWidth()/2;
        int indentY = line.getPictureY()/2 - from.getHeight()/2;

//        System.out.println((from.getPixel(0, 0, new int[4]))[3]);
//        System.out.println(from.getWidth() + " " + from.getHeight());

        for(int h = 0; h < from.getHeight(); h++){
            for(int w = 0; w < from.getWidth(); w++){
                int[] pix = from.getPixel(w, h, new int[4]);

                if(pix[0] == 255 & pix[1] == 255 & pix[2] == 255 & pix[3] == 0){
                    continue;
                }
                else {
                    int[] arr = from.getPixel(w, h, new int[4]);

//                    for (int i : arr){
//                        System.out.print(i + " ");
//                    }
//                    System.out.println(w + " " + h);

//                    System.out.println(indentX + w + line.getPictureX()*(image.getX()-1) + " " + indentY + h + line.getPictureY()*(image.getY()-1));
                    to.setPixel(indentX + w + line.getPictureX()*(image.getX()-1), indentY + h + line.getPictureY()*(image.getY()-1), arr);
                }

            }
        }

    }

    public File combineImages(String jSONString, int sizeX, int sizeY){
        JSONEditor jsonEditor = new JSONEditor();
        Set <Image> images = jsonEditor.createImagesArray(jSONString);

        LayerAnalyst layerAnalyst = new LayerAnalyst(sizeX, sizeY);
        Set<FirstLayerLine> lines = layerAnalyst.lineUpFirstLayer(images);

        File mainImageFile = combineImages(images, lines, sizeX, sizeY, layerAnalyst.getMainPictureSizeZ(), ImageFormat.PNG);

        return mainImageFile;
    }

    private enum ImageFormat{
        PNG{
            @Override
            public String toString(){
                return "png";
            }
        }
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH) {
        java.awt.Image tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private BufferedImage loadAdditionalImage(Image image, FirstLayerLine line){

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(image.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if((bufferedImage.getWidth() > line.getPictureX()) & (bufferedImage.getHeight() > line.getPictureY())){
            int sizeX = 0;
            int sizeY = 0;
            if(bufferedImage.getWidth() - line.getPictureX() > bufferedImage.getHeight() - line.getPictureY()){
                sizeX = line.getPictureX();
                sizeY = bufferedImage.getHeight() - (bufferedImage.getWidth() - line.getPictureX());
            }
            else {
                sizeY = line.getPictureY();
                sizeX = bufferedImage.getWidth() - (bufferedImage.getHeight() - line.getPictureY());
            }
            bufferedImage = resize(bufferedImage, sizeX, sizeY);
        }
        return bufferedImage;

    }
}
