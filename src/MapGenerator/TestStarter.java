package MapGenerator;

import MapGenerator.ImageWork.ImageCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestStarter {
    public static void main(String [] args){
        File file = new File("D:\\programs\\Java\\projects\\GifCreator\\src\\Test2.json");
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

        ImageCreator imageCreator = new ImageCreator();
        imageCreator.combineImages(jSONString, 1000, 1000);
    }
}
