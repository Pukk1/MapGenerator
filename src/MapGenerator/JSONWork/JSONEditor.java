package MapGenerator.JSONWork;

import MapGenerator.Configurations;
import MapGenerator.ImageWork.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.HashSet;
import java.util.Set;

public class JSONEditor {

    String xName = Configurations.x;
    String yName = Configurations.y;
    String zName = Configurations.z;

    public Set<Image> createImagesArray(String jsonString){
        JSONArray jsonArrOfImages = toImagesJSONArr(jsonString);
        Set<Image> images = creatImageArr(jsonArrOfImages);
        return images;
    }

    private JSONArray toImagesJSONArr(String jsonString){
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = null;

        try {
            jsonObject = (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int i = 1;
        while (true){
            Object obj = jsonObject.get(yName + i);
            if (obj == null){
                break;
            }
            else {
                jsonArray.add(obj);
            }
            i++;
        }
        return jsonArray;
    }

    private Set<Image> creatImageArr(JSONArray jsonArray){
        Set<Image> images = new HashSet<>();

        for (int y = 0; y < jsonArray.size(); y++){
            int x = 1;
            JSONObject jsonObjectY = (JSONObject) jsonArray.get(y);
            while (jsonObjectY.get(xName + x) != null){
                int z = 1;
                JSONObject jsonObjectX = (JSONObject) jsonObjectY.get(xName +x);
                while (jsonObjectX.get(zName + z) != null){
                    images.add(new Image(x-1, y, z-1, (String) jsonObjectX.get(zName + z)));
                    z++;
                }
                x++;
            }
        }

        return images;
    }
}
