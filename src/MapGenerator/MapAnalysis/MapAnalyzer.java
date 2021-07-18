package MapGenerator.MapAnalysis;

import MapGenerator.Configurations;
import MapGenerator.ImageWork.Image;
import java.util.Set;

public class MapAnalyzer {
    public MapData analyseMap(Set<Image> images, int pixSizeX, int pixSizeY){
        MapData mapData = new MapData();

        findMaxSizes(images, mapData);

        creatSizeUnit(mapData.getXSize(), mapData.getYSize(), pixSizeX, pixSizeY, mapData);

        return mapData;
    }

    private void creatSizeUnit(int meshSizeX, int mashSizeY, int pixSizeX, int pixSizeY,  MapData outMapData){
        if(pixSizeX%(meshSizeX* Configurations.mapFormatX) == 0 & pixSizeY%(mashSizeY*Configurations.mapFormatY) == 0){
            outMapData.setNeedFinishResize(false);
        }
        else {
            outMapData.setNeedFinishResize(true);
        }

//        outMapData.setSizeUnitW(pixSizeX/(meshSizeX*Configurations.mapFormatX));
//        outMapData.setSizeUnitH(pixSizeY/(mashSizeY*Configurations.mapFormatY));

//        pixSizeX = closestResolution(pixSizeX, Configurations.mapFormatX);
//        pixSizeY = closestResolution(pixSizeY, Configurations.mapFormatY);

        System.out.println(pixSizeX + " " + pixSizeY);

        if(pixSizeX/(meshSizeX*Configurations.mapFormatX) > pixSizeY/(mashSizeY*Configurations.mapFormatY)){
            outMapData.setSizeUnitW(pixSizeY/(mashSizeY*Configurations.mapFormatY));
            outMapData.setSizeUnitH(pixSizeY/(mashSizeY*Configurations.mapFormatY));
        }
        else {
            outMapData.setSizeUnitW(pixSizeX/(meshSizeX*Configurations.mapFormatX));
            outMapData.setSizeUnitH(pixSizeX/(meshSizeX*Configurations.mapFormatX));
        }

//        outMapData.setCellW(pixSizeX/(meshSizeX*outMapData.getSizeUnitW()));
//        outMapData.setCellH(pixSizeY/(mashSizeY*outMapData.getSizeUnitH()));

        outMapData.setCellW(Configurations.mapFormatX);
        outMapData.setCellH(Configurations.mapFormatY);

        outMapData.createPixSizeCell();

    }

    private int closestResolution(int sizeX, int sizeInUnits){
        for(int i = sizeX; i > 0; i--){
            if((i%sizeInUnits == 0) & (i/sizeInUnits%2 == 0)){
                return i;
            }
        }
        return 0;
    }

    private void findMaxSizes(Set<Image> images, MapData outMapData){
        int maxX = -1;
        int maxY = -1;
        int maxZ = -1;

        for(Image image: images){
            if(image.getX() > maxX){
                maxX = image.getX();
            }
            if(image.getY() > maxY){
                maxY = image.getY();
            }
            if(image.getZ() > maxZ){
                maxZ = image.getZ();
            }
        }

        outMapData.setXSize(maxX+1);
        outMapData.setYSize(maxY+1);
        outMapData.setZSize(maxZ+1);
    }
}
