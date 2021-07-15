package MapGenerator.Placement;

import GifLibrary.ImageWork.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LayerAnalyst {

    private int mainPictureSizeX;
    private int mainPictureSizeY;
    private int mainPictureSizeZ = 0;

    public LayerAnalyst(int mainPictureSizeX, int mainPictureSizeY){
        this.mainPictureSizeX = mainPictureSizeX;
        this.mainPictureSizeY = mainPictureSizeY;
    }

    public Set<FirstLayerLine> lineUpFirstLayer(Set <Image> images){
        mainPictureSizeZ = 0;
        Set<FirstLayerLine> lines = new HashSet<>();

        Iterator imagesIterator = images.iterator();
        while (imagesIterator.hasNext()){

            Image image = (Image) imagesIterator.next();

            if(image.getY() > lines.size()){
                for(int i = lines.size()+1; i <= image.getY(); i++){
                    FirstLayerLine line = new FirstLayerLine();
                    line.setNumber(i);
                    lines.add(line);
                }
            }
        }

        imagesIterator = images.iterator();
        while (imagesIterator.hasNext()){
            Image image = (Image) imagesIterator.next();

            if(image.getZ() > mainPictureSizeZ){
                mainPictureSizeZ = image.getZ();
            }

            Iterator lineIterator = lines.iterator();
            while (lineIterator.hasNext()){
                FirstLayerLine line = (FirstLayerLine)lineIterator.next();
                if((line.getNumber() == image.getY()) & (image.getZ() == 1)){
                    line.setNumberOfPictures(line.getNumberOfPictures() + 1);
                }
            }
        }

        Iterator lineIterator = lines.iterator();
        while (lineIterator.hasNext()){
            FirstLayerLine line = (FirstLayerLine) lineIterator.next();
            line.setPictureX(mainPictureSizeX/line.getNumberOfPictures());
            line.setPictureY(mainPictureSizeY/lines.size());
        }

        return lines;
    }

    public int getMainPictureSizeZ() {
        return mainPictureSizeZ;
    }
}
