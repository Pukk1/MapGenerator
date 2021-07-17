package MapGenerator;

import MapGenerator.ImageWork.Image;
import MapGenerator.ImageWork.ImageCreator;
import MapGenerator.JSONWork.JSONEditor;
import MapGenerator.MapAnalysis.MapAnalyzer;
import MapGenerator.MapAnalysis.MapData;
import MapGenerator.Picture.PictureCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

public class MapGenerator {
    public static void main(String [] args){
        File file = new File("Test2.json");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (Exception e){
            System.out.println(1);
        }

        String line = null;
        String jSONString = new String();
        try {
            while((line = bufferedReader.readLine()) != null) {
                jSONString += line;
            }
        }
        catch (Exception e){

        }

        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.generateMap(jSONString, 1250, 550);
    }

    public File generateMap(String jSON, int pixSizeX, int pixSizeY){

        pixSizeX = 1250;
        pixSizeY = 550;

        JSONEditor jsonEditor = new JSONEditor();
        Set<Image> images = jsonEditor.createImagesArray(jSON);

        MapAnalyzer mapAnalyzer = new MapAnalyzer();
        MapData mapData = mapAnalyzer.analyseMap(images, pixSizeX, pixSizeY);

        mapData.setPixSizeX(pixSizeX);
        mapData.setPixSizeY(pixSizeY);

        PictureCreator pictureCreator = new PictureCreator();
        File mapPictureFile = pictureCreator.createMapPicture(images, mapData);

        return mapPictureFile;
    }
}
