package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonReader {

    public static ObjectNode getRandomCity(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(filePath);
            
            JsonNode jsonNode = objectMapper.readTree(file);
            ArrayNode citiesArray = (ArrayNode) jsonNode.get("cities");
            
            List<ObjectNode> cityList = new ArrayList<>();
            for (int i = 0; i < citiesArray.size(); i++) {
                cityList.add((ObjectNode) citiesArray.get(i));
            }
            
            Random random = new Random();
            int index = random.nextInt(cityList.size());
            ObjectNode randomCity = cityList.get(index);
            
            return randomCity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ObjectNode getRandomPerson(String filePath, String ArrayNameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(filePath);

            JsonNode jsonNode = objectMapper.readTree(file);
            ArrayNode citiesArray = (ArrayNode) jsonNode.get(ArrayNameJson);

            List<ObjectNode> cityList = new ArrayList<>();
            for (int i = 0; i < citiesArray.size(); i++) {
                cityList.add((ObjectNode) citiesArray.get(i));
            }

            Random random = new Random();
            int index = random.nextInt(cityList.size());
            ObjectNode randomCity = cityList.get(index);

            return randomCity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        String filePath = "bigbrockerfonctionaltestcourtier/src/main/java/files/testdata/ppetestdata.json";
        ObjectNode randomCity = getRandomPerson(filePath, "persone");
        
        if (randomCity != null) {
            System.out.println("Firstname : " + randomCity);
            System.out.println("Firstname : " + randomCity.get("Firstname").asText());
            System.out.println("Lastname : " + randomCity.get("Lastname").asText());
//            System.out.println("Code postal: " + randomCity.get("zip_code").asText());
//            System.out.println("Latitude: " + randomCity.get("latitude").asText());
//            System.out.println("Longitude: " + randomCity.get("longitude").asText());
//
//            System.out.println("Département:");
//            System.out.println("Nom: " + randomCity.get("department_name").asText());
//            System.out.println("department_number: " + randomCity.get("department_number").asText());
//
//            System.out.println("Région:");
//            System.out.println("Nom: " + randomCity.get("region_name").asText());
//            System.out.println("Nom GeoJSON: " + randomCity.get("region_geojson_name").asText());
        }
    }
}