package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import modules.UseFullMethods;

public class WriteReadJsonFile {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Method of reading JsonNode from json file
     * @param jsonFilePath the path of json file.
     * @return JsonNode
     */
    public static JsonNode readJsonNodeFromFile(String jsonFilePath) {
        try {
            return objectMapper.readTree(new File(jsonFilePath));
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("\nError in reading from json file : " + io.getMessage());
            return null;
        }
    }
    /**
     * Method of reading data from json file
     *
     * @param jsonFilePath the path of json file.
     */
    public static JsonNode readJsonDataFile(String jsonFilePath) {
        try {
            JsonNode jsonNode = objectMapper.readTree(new File(jsonFilePath));
            return jsonNode;
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("\nError in reading from json file : " + io.getMessage());
            return null;
        }
    }

    /**
     * Method of writing to json file
     *
     * @param pathFile the path of json file.
     */
    public static void writeToJsonFile(String pathFile, ObjectNode jsonNode) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(pathFile), jsonNode);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("\nError in writing to json file : " + io.getMessage());
        }
    }

    /**
     * Method of writing object to json file
     *
     * @param <T> the type of the object to write
     * @param pathFile the path of json file
     * @param payload the object to write
     */
    public static <T> void writeObjectToFile(String pathFile, T payload) {
        try {
            objectMapper.writeValue(new File(pathFile), payload);
        } catch (IOException io) {
            io.printStackTrace();
            UseFullMethods.logError("Erreur d'écriture dans le fichier json :", io);
        }
    }

    /**
     * Method of reading data from json file
     *
     * @param <T> the type of the object to read
     * @param jsonFilePath the path of json file
     * @param clazz the class of the object to read
     * @return the object read from the json file
     */
    public static <T> T readJsonDataFile(String jsonFilePath, Class<T> classData) {
        try {
            return objectMapper.readValue(new File(jsonFilePath), classData);
        } catch (IOException io) {
            io.printStackTrace();
            UseFullMethods.logError("Erreur de la lecture du fichier json :", io);
            return null;
        }
    }

    /**
     * Method of reading data from json file
     *
     * @param filePath the path of json file
     * @return the radom object read from the json file
     */

    public static ObjectNode getRandomCity(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(filePath);
            
            JsonNode jsonNode = mapper.readTree(file);
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
            UseFullMethods.logError("Erreur de la lecture du fichier json :", e);
            return null;
        }
    }
}
